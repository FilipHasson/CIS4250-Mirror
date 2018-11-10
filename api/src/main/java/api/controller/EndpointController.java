package api.controller;

import api.dao.FoodDAO;
import api.dao.IngredientDAO;
import api.dao.NutritionDAO;
import api.dao.RecipeDAO;
import api.exception.BadRequestException;
import api.exception.ConflictException;
import api.exception.UnauthorizedException;
import api.object.Food;
import api.object.Nutrition;
import api.object.Recipe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static api.validator.JsonValidator.*;

/**
 * EndPoint Controller for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-27
 */
@Controller
@RequestMapping("/")
public class EndpointController {

    @RequestMapping(value="/food/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONObject getFood(@PathVariable("id") int id){
        return initJsonReturn(new FoodDAO().findById(id).toJson());
    }

    @RequestMapping(value="/foods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getFoods(@RequestParam(value = "search", required = false)String title){
        JSONArray json = new JSONArray();
        if(null != title){
            json.addAll(Food.getIds(new FoodDAO().search(title)));
            return initJsonReturn(json);
        } else{
            json.addAll(Food.getIds(new FoodDAO().findAllOrderByFieldLimit("time_created",100)));
            return initJsonReturn(json);
        }
    }

    @RequestMapping(value="/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getRecipes(@RequestParam(value = "search", required = false)String title,
                                 @RequestParam(value = "id", required = false)String sId ){
        JSONArray json = new JSONArray();
        if(null != title && null != sId){
            try {
                int id = Integer.parseInt(sId);
                List<Integer> recipeIds = (Recipe.getIds(new RecipeDAO().search(title,id)));
                for (int rId : recipeIds){
                    json.add(new FoodDAO().findByInt("recipe_id",rId).getId());
                }
            } catch (NumberFormatException e){
                e.printStackTrace();
                throw new BadRequestException();
            }
        } else if (null != title ^ null != sId){
            System.out.println("Only one parameter null");
            throw new BadRequestException();
        } else {
            List<Integer> recipeIds = Recipe.getIds(new RecipeDAO().findAllOrderByTimeLimit(100));
            for (int id : recipeIds){
                json.add(new FoodDAO().findByInt("recipe_id",id).getId());
            }
        }
        return initJsonReturn(json);
    }

    @RequestMapping(value="/foods/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getFoodsByCategory(@PathVariable("category") String category){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findByRecipeCategory(category)));
        return initJsonReturn(json);
    }


    @RequestMapping(value="/food")
    @ResponseBody
    public void newFood(@RequestBody String jsonString) {
        newFood(jsonString,"0");
    }

    @RequestMapping(value="/food/{id}")
    @ResponseBody
    public void newFood(@RequestBody String jsonString, @PathVariable("id")String sFoodId){
        JSONObject json = null;
        JSONObject meta = null;
        JSONObject data = null;
        JSONObject jNutrition = null;
        Food food  = new Food();
        Recipe recipe = new Recipe();
        Nutrition nutrition = new Nutrition();
        int foodId;

        try {
            foodId = Integer.parseInt(sFoodId);
        } catch (NumberFormatException e){
            throw new BadRequestException();
        }

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            if (!isValidJson(json)) throw new BadRequestException();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (!isValidRecipe(json)) throw new BadRequestException();
        if (null == (data = jsonJson(json,"data")) || null == (meta = jsonJson(json,"meta"))) throw new BadRequestException();
        if (!isValidToken(jsonString(meta,"token"),jsonInt(data,"account_id"))) throw new UnauthorizedException();

        jNutrition = jsonJson(data, "nutrition");
        food.setTitle(jsonString(data,"title"));
        food.setServing_count(jsonDouble(data,"serving_count"));
        food.setServing_size(jsonString(data,"serving_size"));
        if (0 == foodId){
            food.setTimeCreated(OffsetDateTime.now());
            food.setTimeUpdated(OffsetDateTime.now());
        } else {
            food.setId(foodId); // UPDATE OR CREATE
            int recipeId = new FoodDAO().findById(foodId).getRecipeId();
            int nutritionId = new FoodDAO().findById(foodId).getNutritionId();
            if (jsonInt(data,"account_id")!= new FoodDAO().findById(foodId).getRecipe().getAccountId())throw new UnauthorizedException();
            food.setRecipeId(recipeId);
            food.setNutritionId(nutritionId);
            food.setTimeUpdated(OffsetDateTime.now());
            recipe.setId(recipeId);
            nutrition.setId(nutritionId);
            long epoch = 0;
            if (data.containsKey("time_created")) epoch = (long)data.get("time_created");
            if (epoch != 0) food.setTimeCreated(OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), TimeZone.getDefault().toZoneId()));
            else food.setTimeCreated(OffsetDateTime.now());
        }

        nutrition = fillNutrition(jNutrition,foodId);
        recipe.setAccountId(jsonInt(data,"account_id"));
        recipe.setStars(jsonInt(data,"star_count"));
        recipe.setViews(jsonInt(data,"view_count"));
//        recipe.setServing_count(jsonInt(data,"serving_count"));
//        recipe.setServing_size(jsonString(data,"serving_size"));
        recipe.setDescription(jsonString(data,"description"));

        if (data.containsKey("steps")){//Populate steps
            JSONArray array = (JSONArray) data.get("steps");
            String[] stepStrings = new String[array.size()];
            for (int i = 0; i < array.size(); i++) stepStrings[i] = (String)array.get(i);
            recipe.setSteps(stepStrings);
        } else recipe.setSteps(new String[] {""});

        if (data.containsKey("categories")){ //Populate Categories
            JSONArray array = (JSONArray) data.get("categories");
            String[] categoryStrings = new String[array.size()];
            for (int i = 0; i < array.size(); i++) categoryStrings[i] = ((String)array.get(i)).toLowerCase();
            recipe.setCategories(categoryStrings);
        } else recipe.setCategories((Recipe.Category[]) null);



        System.out.println("FOOD: "+food.toString());
        System.out.println("Recipe: "+recipe.toString());
        System.out.println("Nutrition: "+nutrition.toString());
        if (0 == foodId) { // New Recipe
            int recipeId = ((Long)new RecipeDAO().insertRecipe(recipe)).intValue();
            if (0 == recipeId) {
                throw new ConflictException();
            }
            recipe.setId(recipeId);
            if (data.containsKey("ingredients")){//Populate Ingredients
                JSONObject jIngredient = jsonJson(data,"ingredients");
                for (Object key : jIngredient.keySet()){
                    recipe.addIngredient(Integer.parseInt((String)key),jsonDouble(jIngredient,(String)key));
                }
            }

            if (recipe.getIngredients().size() != new IngredientDAO().insertIngredients(recipe.getIngredients()))
                System.out.println("Something Weird Happened");
            //TODO Make whole transaction work instead of doing it in stages
            int nutritionId = ((Long)new NutritionDAO().insertNutrition(nutrition)).intValue();
            if (0 == nutritionId) {
//              new DAO().deleteByInt("recipe","id",recipeId);
                throw new ConflictException();
            }
            food.setRecipeId(recipeId);
            food.setNutritionId(nutritionId);
            foodId = ((Long)new FoodDAO().insertFood(food)).intValue();
            if (0 == foodId) {
//              new DAO().deleteByInt("recipe","id",recipeId);
//              new DAO().deleteByInt("nutrition","id",nutritionId);
                throw new ConflictException();
            }
        } else { // Update Recipe
//          Recipe rDbCopy = new RecipeDAO().findById(recipe.getId());
//          Nutrition nDbCopy = new NutritionDAO().findById(nutrition.getId());
            if (0 == new RecipeDAO().updateRecipe(recipe)) return;
            if (0 == new NutritionDAO().updateNutrition(nutrition)) {
//              new DAO().deleteByInt("recipe","id",rDbCopy.getId());
//              new RecipeDAO().insertRecipe(rDbCopy);
//                System.out.println("it can't be");
//                throw new ConflictException();
            }
            if (0 == new FoodDAO().updateFood(food)){
//              new DAO().deleteByInt("recipe","id",rDbCopy.getId());
//              new RecipeDAO().insertRecipe(rDbCopy);
//              new DAO().deleteByInt("nutrition","id",nDbCopy.getId());
//              new NutritionDAO().insertNutrition(nDbCopy);
//                System.out.println("id feel dumb");
//                throw new ConflictException();
            }
        }
    }

    private Nutrition fillNutrition(JSONObject json, int id){
        Nutrition  nutrition = new Nutrition();

        nutrition.setId(id);
        nutrition.setCalcium_mg(jsonDouble(json,"calcium_mg"));
        nutrition.setCalories(jsonDouble(json,"calories"));
        nutrition.setCarbs_fibre_g(jsonDouble(json,"carbs_fibre_g"));
        nutrition.setCarbs_sugar_g(jsonDouble(json,"carbs_sugar_g"));
        nutrition.setCarbs_total_g(jsonDouble(json,"carbs_total_g"));
        nutrition.setCholesterol_mg(jsonDouble(json,"cholesterol_mg"));
        nutrition.setFat_sat_g(jsonDouble(json,"fat_sat_g"));
        nutrition.setFat_total_g(jsonDouble(json,"fat_total_g"));
        nutrition.setFat_trans_g(jsonDouble(json,"fat_trans_g"));
        nutrition.setIron_mg(jsonDouble(json,"iron_mg"));
        nutrition.setProtein_g(jsonDouble(json,"protein_g"));
        nutrition.setSodium_mg(jsonDouble(json,"sodium_mg"));
        nutrition.setVitamin_a_iu(jsonDouble(json,"vitamin_a_iu"));
        nutrition.setVitamin_c_mg(jsonDouble(json,"vitamin_c_mg"));

        return nutrition;
    }

    //TODO Replace with Real one
    private boolean isValidToken(String token, int account_id){
        return true;
    }


}
