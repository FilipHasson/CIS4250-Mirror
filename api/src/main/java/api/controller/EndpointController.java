package api.controller;

import api.dao.FoodDAO;
import api.dao.RecipeDAO;
import api.object.Food;
import api.object.Nutrition;
import api.object.Recipe;
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
import java.util.ArrayList;
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
        return new FoodDAO().findById(id).toJson();
    }

    @RequestMapping(value="/foods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getFoods(){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findAllOrderByFieldLimit("time_created",100)));
        return json;
    }

    @RequestMapping(value="/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getRecipes(){
        JSONArray json = new JSONArray();
        List<Integer> recipeIds = Recipe.getIds(new RecipeDAO().findAllOrderByTimeLimit(100));
        for (int id : recipeIds){
            json.add(new FoodDAO().findByInt("recipe_id",id).getId());
        }
        return json;
    }

    @RequestMapping(value="/foods/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getFoods(@PathVariable("category") String category){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findByRecipeCategory(category)));
        return json;
    }

    @RequestMapping(value="/food")
    @ResponseBody
    public void newFood(@RequestBody String jsonString){
        JSONObject json = null;
        Food food  = new Food();
        Recipe recipe = new Recipe();
        Nutrition nutrition = new Nutrition();

        JSONParser parser = new JSONParser();
        try {
             json = (JSONObject)parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (null != json){
            food.setTitle(jsonString(json,"title"));
            food.setId(jsonInt(json,"id")); // UPDATE OR CREATE
            if (0 == food.getId()){
                food.setNutritionId(0);
                food.setRecipeId(0);
                nutrition.setId(0);
                recipe.setId(0);
                food.setTimeCreated(OffsetDateTime.now());
                food.setTimeUpdated(OffsetDateTime.now());
            } else {
                food.setNutritionId(jsonInt(json, "nutrition_id"));// UPDATE OR CREATE
                food.setRecipeId(jsonInt(json, "recipe_id"));// UPDATE OR CREATE
                nutrition.setId(food.getNutritionId());
                recipe.setId(food.getRecipeId());
                food.setTimeUpdated(OffsetDateTime.now());
                long epoch = 0;
                if (json.containsKey("time_created")) epoch = (long)json.get("time_created");
                if (epoch != 0) food.setTimeCreated(OffsetDateTime.ofInstant(Instant.ofEpochMilli(epoch), TimeZone.getDefault().toZoneId()));
                else food.setTimeCreated(OffsetDateTime.now());
            }

            food.setTimeCreated(OffsetDateTime.now());
            food.setTimeUpdated(OffsetDateTime.now());
            nutrition.setCaffeine_mg(jsonDouble(json,"caffine_mg"));
            nutrition.setCalcium_mg(jsonDouble(json,"calcium_mg"));
            nutrition.setCalories(jsonDouble(json,"calories"));
            nutrition.setCarbohydrate_g(jsonDouble(json,"carbohydrate_g"));
            nutrition.setCholesterol_mg(jsonDouble(json,"cholesterol_mg"));
            nutrition.setFat_mono_g(jsonDouble(json,"fat_mono_g"));
            nutrition.setFat_poly_g(jsonDouble(json,"fat_poly_g"));
            nutrition.setFat_sat_g(jsonDouble(json,"fat_sat_g"));
            nutrition.setFat_trans_g(jsonDouble(json,"fat_trans_g"));
            nutrition.setFolate_mcg(jsonDouble(json,"folate_mcg"));
            nutrition.setMagnesium_mg(jsonDouble(json,"magnesium_mg"));
            nutrition.setManganese_mg(jsonDouble(json,"manganese_mg"));
            nutrition.setNiacin_mg(jsonDouble(json,"niacin_mg"));
            nutrition.setPotassium_mg(jsonDouble(json,"potassium_mg"));
            nutrition.setProtein_g(jsonDouble(json,"protein_g"));
            nutrition.setRiboflavin_mg(jsonDouble(json,"riboflavin_mg"));
            nutrition.setSodium_mg(jsonDouble(json,"sodium_mg"));
            nutrition.setSugars_g(jsonDouble(json,"sugars_g"));
            nutrition.setThiamin_mg(jsonDouble(json,"thiamin_mg"));
            nutrition.setTotal_fiber_g(jsonDouble(json,"total_fiber_g"));
            nutrition.setTotal_lipid_g(jsonDouble(json,"total_lipid_g"));
            nutrition.setVitamin_a_iu(jsonDouble(json,"vitamin_a_iu"));
            nutrition.setVitamin_b6_mg(jsonDouble(json,"vitamin_b6_mg"));
            nutrition.setVitamin_c_mg(jsonDouble(json,"vitamin_c_mg"));
            nutrition.setVitamin_d_iu(jsonDouble(json,"vitamin_d_iu"));
            nutrition.setVitamin_k_mcg(jsonDouble(json,"vitamin_k_mcg"));
            nutrition.setWater_g(jsonDouble(json,"water_g"));
            nutrition.setZinc_mg(jsonDouble(json,"zinc_mg"));
            recipe.setAccountId(jsonInt(json,"account_id"));
            recipe.setStars(jsonInt(json,"star_count"));
            recipe.setViews(jsonInt(json,"view_count"));
            recipe.setPortions(jsonDouble(json,"portions"));

            if (json.containsKey("steps")){
                JSONArray array = (JSONArray) json.get("steps");
                String[] stepStrings = new String[array.size()];// = new String[steps.size()];
                for (int i = 0; i < array.size(); i++) stepStrings[i] = (String)array.get(i);
                recipe.setSteps(stepStrings);
            } else recipe.setSteps(new String[] {""});

            if (json.containsKey("categories")){
                JSONArray array = (JSONArray) json.get("categories");
                String[] categoryStrings = new String[array.size()];// = new String[steps.size()];
                for (int i = 0; i < array.size(); i++) categoryStrings[i] = (String)array.get(i);
                recipe.setSteps(categoryStrings);
            } else recipe.setCategories((Recipe.Category[]) null);

            if (0 == food.getId()){
                //FoodDao.insertFood
                //RecipeDao.insertRecipe
                //NutritionDAO.insertNutrition
            } else {
                //FoodDao.updateFood
                //RecipeDao.updateRecipe
                //NutritionDao.updateNutrition
            }
        }
    }

    private int jsonInt(JSONObject json, String field){
        if (json.containsKey(field)) return ((Long)json.get(field)).intValue();
        return 0;
    }

    private String jsonString(JSONObject json, String field){
        if (json.containsKey(field)) return (String)json.get(field);
        return "";
    }

    private double jsonDouble(JSONObject json, String field){
        if (json.containsKey(field)) return (Double)json.get(field);
        return 0;
    }


}
