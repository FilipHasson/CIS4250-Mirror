package api.object;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Meal {
    private LocalDate date;
    private int account_id;
    private HashMap<Type,ArrayList<Serving>> servings;

    public Meal(LocalDate date, int account_id, HashMap<Type, ArrayList<Serving>> servings) {
        this.date = date;
        this.account_id = account_id;
        this.servings = servings;
    }

    public Meal(LocalDate date, int account_id){
        this.date = date;
        this.account_id = account_id;
        this.servings = new HashMap<>();
    }

    public Meal() {
        this (LocalDate.now(),0,new HashMap<>());
    }

    public enum Type {
        breakfast,
        lunch,
        dinner,
        snack_1,
        snack_2,
        snack_3
    }

    public void addServing(Meal.Type type, Serving serving){
        if (servings.containsKey(type)){
            servings.get(type).add(serving);
        } else {
            servings.put(type,new ArrayList<>());
            servings.get(type).add(serving);
        }
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public HashMap<Type, ArrayList<Serving>> getServings() {
        return servings;
    }

    public void setServings(HashMap<Type, ArrayList<Serving>> servings) {
        this.servings = servings;
    }
}
