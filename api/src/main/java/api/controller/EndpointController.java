package api.controller;

import api.dao.FoodDAO;
import api.object.Food;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * EndPoint Controller for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-27
 */
@Controller
public class EndpointController {

    @RequestMapping(value="/api/food/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getFood(@PathVariable("id") int id){
        return new FoodDAO().findById(id).toJson();
    }

    @RequestMapping(value="/api/foods{", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getFoods(){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findAll()));
        return json;
    }

    @RequestMapping(value="/api/foods/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONArray getFoods(@PathVariable("category") String category){
        JSONArray json = new JSONArray();
        json.addAll(Food.getIds(new FoodDAO().findByRecipeCategory(category)));
        return json;
    }
}
