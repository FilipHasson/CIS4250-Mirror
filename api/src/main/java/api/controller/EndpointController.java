package api.controller;

import api.dao.FoodDAO;
import api.dao.NutritionDAO;
import api.dao.RecipeDAO;
import api.object.Food;
import api.object.Nutrition;
import api.object.Recipe;
import api.validator.JsonValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimeZone;

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
    public JSONObject getFoods(){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findAllOrderByFieldLimit("time_created",100)));
        return initJsonReturn(json);
    }

    @RequestMapping(value="/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getRecipes(){
        JSONArray json = new JSONArray();
        List<Integer> recipeIds = Recipe.getIds(new RecipeDAO().findAllOrderByTimeLimit(100));
        for (int id : recipeIds){
            json.add(new FoodDAO().findByInt("recipe_id",id).getId());
        }
        return initJsonReturn(json);
    }

    @RequestMapping(value="/foods/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getFoods(@PathVariable("category") String category){
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
        Nutrition nutrition;// = new Nutrition();
        String nutritionString = null;
        int foodId;

        try {
            foodId = Integer.parseInt(sFoodId);
        } catch (NumberFormatException e){
            foodId = 0;
        }

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            if (!JsonValidator.isValidJson(json)) return;
            data = jsonJson(json,"data");
            meta = jsonJson(json,"meta");
//            dataString = jsonString(json,"data");
//            metaString = jsonString(json, "meta");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }



        if (null != data){
            if (data.containsKey("nutrition")) {
                jNutrition = jsonJson(data, "nutrition");
                //jNutrition = (JSONObject) parser.parse(nutritionString);
            }
            food.setTitle(jsonString(data,"title"));
            food.setId(jsonInt(data,"id")); // UPDATE OR CREATE
            if (0 == foodId){
                food.setNutritionId(0);
                food.setRecipeId(0);
                recipe.setId(0);
                food.setTimeCreated(OffsetDateTime.now());
                food.setTimeUpdated(OffsetDateTime.now());
            } else {
                food.setNutritionId(jsonInt(data, "nutrition_id"));// UPDATE OR CREATE
                food.setRecipeId(jsonInt(data, "recipe_id"));// UPDATE OR CREATE
//                nutrition.setId(food.getNutritionId());
                recipe.setId(food.getRecipeId());
                food.setTimeUpdated(OffsetDateTime.now());
                long epoch = 0;
                if (data.containsKey("time_created")) epoch = (long)data.get("time_created");
                if (epoch != 0) food.setTimeCreated(OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), TimeZone.getDefault().toZoneId()));
                else food.setTimeCreated(OffsetDateTime.now());
            }

            nutrition = fillNutrition(jNutrition,foodId);

            recipe.setAccountId(jsonInt(data,"account_id"));
            recipe.setStars(jsonInt(data,"star_count"));
            recipe.setViews(jsonInt(data,"view_count"));
            recipe.setServing_count(jsonInt(data,"serving_count"));
            recipe.setServing_size(jsonString(data,"serving_size"));

            if (data.containsKey("steps")){
                JSONArray array = (JSONArray) data.get("steps");
                String[] stepStrings = new String[array.size()];// = new String[steps.size()];
                for (int i = 0; i < array.size(); i++) stepStrings[i] = (String)array.get(i);
                recipe.setSteps(stepStrings);
            } else recipe.setSteps(new String[] {""});

            if (data.containsKey("categories")){
                JSONArray array = (JSONArray) data.get("categories");
                String[] categoryStrings = new String[array.size()];// = new String[steps.size()];
                for (int i = 0; i < array.size(); i++) categoryStrings[i] = ((String)array.get(i)).toLowerCase();
                recipe.setCategories(categoryStrings);
            } else recipe.setCategories((Recipe.Category[]) null);

            System.out.println("FOOD: "+food.toString());
            System.out.println("Recipe: "+recipe.toString());
            System.out.println("Nutrition: "+nutrition.toString());

            if (0 == foodId) {
                int recipeId = ((Long)new RecipeDAO().insertRecipe(recipe)).intValue();
                if (0 == recipeId) {
                    return;
                }
                int nutritionId = ((Long)new NutritionDAO().insertNutrition(nutrition)).intValue();
                if (0 == nutritionId) {
//                    new DAO().deleteByInt("recipe","id",recipeId);
                    return;
                }
                food.setRecipeId(recipeId);
                food.setNutritionId(nutritionId);
                foodId = ((Long)new FoodDAO().insertFood(food)).intValue();
                if (0 == foodId) {
//                    new DAO().deleteByInt("recipe","id",recipeId);
//                    new DAO().deleteByInt("nutrition","id",nutritionId);
                    return;
                }
            } else {
//                Recipe rDbCopy = new RecipeDAO().findById(recipe.getId());
//                Nutrition nDbCopy = new NutritionDAO().findById(nutrition.getId());

                if (0 == new RecipeDAO().updateRecipe(recipe)) return;
                if (0 == new NutritionDAO().updateNutrition(nutrition)) {
//                    new DAO().deleteByInt("recipe","id",rDbCopy.getId());
//                    new RecipeDAO().insertRecipe(rDbCopy);
                    return;
                }
                if (0 == new FoodDAO().updateFood(food)){
//                    new DAO().deleteByInt("recipe","id",rDbCopy.getId());
//                    new RecipeDAO().insertRecipe(rDbCopy);
//                    new DAO().deleteByInt("nutrition","id",nDbCopy.getId());
//                    new NutritionDAO().insertNutrition(nDbCopy);
                    return;
                }
            }
        }
    }

    private Nutrition fillNutrition(JSONObject json, int id){
        Nutrition  nutrition = new Nutrition();

        nutrition.setId(id);
//        nutrition.setCaffeine_mg(jsonDouble(json,"caffine_mg"));
//        nutrition.setCalcium_mg(jsonDouble(json,"calcium_mg"));
//        nutrition.setCalories(jsonDouble(json,"calories"));
//        nutrition.setCarbohydrate_g(jsonDouble(json,"carbohydrate_g"));
//        nutrition.setCholesterol_mg(jsonDouble(json,"cholesterol_mg"));
//        nutrition.setFat_mono_g(jsonDouble(json,"fat_mono_g"));
//        nutrition.setFat_poly_g(jsonDouble(json,"fat_poly_g"));
//        nutrition.setFat_sat_g(jsonDouble(json,"fat_sat_g"));
//        nutrition.setFat_trans_g(jsonDouble(json,"fat_trans_g"));
//        nutrition.setFolate_mcg(jsonDouble(json,"folate_mcg"));
//        nutrition.setMagnesium_mg(jsonDouble(json,"magnesium_mg"));
//        nutrition.setManganese_mg(jsonDouble(json,"manganese_mg"));
//        nutrition.setNiacin_mg(jsonDouble(json,"niacin_mg"));
//        nutrition.setPotassium_mg(jsonDouble(json,"potassium_mg"));
//        nutrition.setProtein_g(jsonDouble(json,"protein_g"));
//        nutrition.setRiboflavin_mg(jsonDouble(json,"riboflavin_mg"));
//        nutrition.setSodium_mg(jsonDouble(json,"sodium_mg"));
//        nutrition.setSugars_g(jsonDouble(json,"sugars_g"));
//        nutrition.setThiamin_mg(jsonDouble(json,"thiamin_mg"));
//        nutrition.setTotal_fiber_g(jsonDouble(json,"total_fiber_g"));
//        nutrition.setTotal_lipid_g(jsonDouble(json,"total_lipid_g"));
//        nutrition.setVitamin_a_iu(jsonDouble(json,"vitamin_a_iu"));
//        nutrition.setVitamin_b6_mg(jsonDouble(json,"vitamin_b6_mg"));
//        nutrition.setVitamin_c_mg(jsonDouble(json,"vitamin_c_mg"));
//        nutrition.setVitamin_d_iu(jsonDouble(json,"vitamin_d_iu"));
//        nutrition.setVitamin_k_mcg(jsonDouble(json,"vitamin_k_mcg"));
//        nutrition.setWater_g(jsonDouble(json,"water_g"));
//        nutrition.setZinc_mg(jsonDouble(json,"zinc_mg"));

        return nutrition;
    }

    private int jsonInt(JSONObject json, String field){
        if (json.containsKey(field)) return ((Long)json.get(field)).intValue();
        return 0;
    }

    private JSONObject jsonJson(JSONObject json, String field){
        if (json.containsKey(field)) return (JSONObject)json.get(field);
        return null;
    }

    private String jsonString(JSONObject json, String field){
        if (json.containsKey(field)) return (String)json.get(field);
        return "";
    }

    private double jsonDouble(JSONObject json, String field){
        if (json.containsKey(field)) return (Double)json.get(field);
        return 0;
    }

    private JSONObject initJsonReturn(JSONObject data){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",new JSONObject());
        return json;
    }

    private JSONObject initJsonReturn(JSONArray data){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",new JSONObject());
        return json;
    }

    private JSONObject initJsonReturn(JSONObject data, JSONObject meta){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",meta);
        return json;
    }

    private JSONObject initJsonMeta(String token){
        JSONObject json  = new JSONObject();
        json.put("token",token);
        return json;
    }

}
