package api.controller;

import api.dao.MealDAO;
import api.exception.BadRequestException;
import api.exception.NotFoundException;
import api.exception.UnauthorizedException;
import api.object.Meal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import static api.validator.JsonValidator.*;
import static api.validator.JsonValidator.jsonInt;

@Controller
@RequestMapping("/")
public class MealController {

    @RequestMapping(value="/account/{id}/meals/{date}", method = RequestMethod.POST)
    @ResponseBody
    public void setMealDay (@PathVariable("id")String sAccountId, @PathVariable("date")String sdate,
                            @RequestBody String jsonString) {
        JSONObject json;
        JSONObject data;
        JSONObject meta;
        JSONObject breakfast;
        JSONObject lunch;
        JSONObject dinner;
        JSONObject snack_1;
        JSONObject snack_2;
        JSONObject snack_3;
        int accountId;
        LocalDate date;

        JSONParser parser = new JSONParser();
        try {
            accountId = Integer.parseInt(sAccountId);
            date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            json = (JSONObject)parser.parse(jsonString);
            data = getData(json);
            meta = jsonJson(json,"meta");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        //TODO Validate token

        if (null != data){
            breakfast = jsonJson(data,"breakfast");
            lunch = jsonJson(data,"lunch");
            dinner = jsonJson(data,"dinner");
            snack_1 = jsonJson(data,"snack_1");
            snack_2 = jsonJson(data,"snack_2");
            snack_3 = jsonJson(data,"snack_3");

            insertMeals(breakfast,"breakfast",date,accountId);
            insertMeals(lunch,"breakfast",date,accountId);
            insertMeals(dinner,"dinner",date,accountId);
            insertMeals(snack_1,"snack_1",date,accountId);
            insertMeals(snack_2,"snack_2",date,accountId);
            insertMeals(snack_3,"snack_3",date,accountId);

        } else throw new BadRequestException();
    }

    private void insertMeals(JSONObject json, String type, LocalDate date, int accountId){
        Meal meal = new Meal();
        meal.setAccountId(accountId);
        meal.setDate(date);
        meal.setType(Meal.stringToType(type));

        for (Object key : json.keySet()){
            try {
                meal.setFoodId(Integer.parseInt((String) key));
                meal.setServingAmmount(((Long) json.get(key)).intValue());
                new MealDAO().insertMeal(meal);
            } catch (NumberFormatException e){
                throw new BadRequestException();
            }
        }
    }

    @RequestMapping(value="/account/{id}/meals/{date}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getMealDay(@PathVariable("id")String sAccountId, @PathVariable("date")String sdate) {
        JSONObject json = new JSONObject();
        int accountId;
        LocalDate date;
        JSONObject breakfast = new JSONObject();
        JSONObject lunch = new JSONObject();
        JSONObject dinner = new JSONObject();
        JSONObject snack_1 = new JSONObject();
        JSONObject snack_2 = new JSONObject();
        JSONObject snack_3 = new JSONObject();
        List<Meal> meals;


        try {
            accountId = Integer.parseInt(sAccountId);
            date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException();
        }

        meals = new MealDAO().getByDateAccount(date,accountId);

        if (null == meals) throw new NotFoundException();


        for (Meal meal : meals){
            switch (meal.getType()){
                case breakfast:
                    breakfast.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                case lunch:
                    lunch.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                case dinner:
                    dinner.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                case snack_1:
                    snack_1.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                case snack_2:
                    snack_2.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                case snack_3:
                    snack_3.put(Integer.toString(meal.getFoodId()),meal.getServingAmmount());
                    break;
                default:
            }
        }


        json.put("breakfast",breakfast);
        json.put("lunch",lunch);
        json.put("dinner",dinner);
        json.put("snack_1",snack_1);
        json.put("snack_2",snack_2);
        json.put("snack_3",snack_3);
        return initJsonReturn(json);
    }


    private boolean isValidToken(String token, int account_id) {
        return true;
    }
}
