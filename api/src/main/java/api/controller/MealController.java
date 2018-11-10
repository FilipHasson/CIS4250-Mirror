package api.controller;

import api.exception.BadRequestException;
import api.exception.UnauthorizedException;
import api.object.Meal;
import api.object.Serving;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static api.validator.JsonValidator.*;
import static api.validator.JsonValidator.jsonInt;

public class MealController {

    @RequestMapping(value="/account/{id}/meals/{date}")
    @ResponseBody
    public void getMealDay(@RequestBody String jsonString, @PathVariable("id")String sAccountId, @PathVariable("date")String sdate) {
        JSONObject json, data, meta;
        Meal meal;
        int accountId;
        HashMap<Meal.Type,JSONObject> daysMeals = new HashMap<>();

        try {
            accountId = Integer.parseInt(sAccountId);
            LocalDate date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            meal = new Meal(date,accountId);
        } catch (Exception e){
            e.printStackTrace();
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
        if (!isValidToken(jsonString(meta,"token"),jsonInt(data,"account_id"))) throw new UnauthorizedException();//TODO replce with proper

        daysMeals.put(Meal.Type.breakfast,jsonJson(data,"breakfast"));
        daysMeals.put(Meal.Type.lunch,jsonJson(data,"lunch"));
        daysMeals.put(Meal.Type.dinner,jsonJson(data,"dinner"));
        daysMeals.put(Meal.Type.snack_1,jsonJson(data,"snack_1"));
        daysMeals.put(Meal.Type.snack_2,jsonJson(data,"snack_2"));
        daysMeals.put(Meal.Type.snack_3,jsonJson(data,"snack_3"));

        for (Meal.Type type : Meal.Type.values()) {
            for (Object key : daysMeals.get(type).keySet()) {
                try {
                    meal.addServing(type,
                            new Serving(
                                    Integer.parseInt((String) key),
                                    (Integer) daysMeals.get(type).get(key),
                                    type
                            ));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new BadRequestException();
                }
            }
        }


    }


    private boolean isValidToken(String token, int account_id) {
        return true;
    }
}
