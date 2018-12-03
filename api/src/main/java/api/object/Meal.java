package api.object;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Meal {
    private int id;
    private int accountId;
    private int foodId;
    private LocalDate date;
    private Type type;
    private int servingAmmount;

    public enum Type {
        breakfast,
        lunch,
        dinner,
        snack_1,
        snack_2,
        snack_3
    }

    public static Type stringToType(String val){
        if (null == val) return null;
        switch (val.toLowerCase()){
            case "breakfast":
                return Type.breakfast;
            case "lunch":
                return Type.lunch;
            case "dinner":
                return Type.dinner;
            case "snack_1":
                return Type.snack_1;
            case "snack_2":
                return Type.snack_2;
            case "snack_3":
                return Type.snack_3;
            default:
                return null;
        }
    }

    public Meal(int id, int accountId, int foodId, LocalDate date, Type type, int servingAmmount) {
        this.id = id;
        this.accountId = accountId;
        this.foodId = foodId;
        this.date = date;
        this.type = type;
        this.servingAmmount = servingAmmount;
    }

    public Meal() {
        this(0,0,0,LocalDate.now(),null,0);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();

        json.put("id",this.id);
        json.put("account_id",this.accountId);
        json.put("food_id",this.foodId);
        json.put("date",date.toEpochDay());
        json.put("type",this.type.toString());
        json.put("serving_ammount",this.servingAmmount);

        return json;
    }

    public void addToJson(JSONObject json){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getServingAmmount() {
        return servingAmmount;
    }

    public void setServingAmmount(int servingAmmount) {
        this.servingAmmount = servingAmmount;
    }
}
