package api.controller;

import api.dao.FoodDAO;
import api.dao.RecipeDAO;
import api.object.Food;
import api.object.Recipe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
}
