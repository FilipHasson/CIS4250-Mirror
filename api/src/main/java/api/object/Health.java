package api.object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class Health {
    public enum Lifestyle{
        extremely_inactive,
        sedentary,
        moderately_active,
        vigorously_active,
        extremely_active
    }

    private int age;
    private int weight;
    private int height;
    private Lifestyle lifestyle;
    private Recipe.Category[] restrictions;

    public Health(int age, int weight, int height, Lifestyle lifestyle, Recipe.Category[] restrictions) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.lifestyle = lifestyle;
        this.restrictions = restrictions;
    }

    public Health() {
        this(0,0,0,null,null);
    }

    public static Lifestyle stringToLifeStyle(String val){
        if (null == val) return null;
        switch (val.toLowerCase()){
            case "extremely_inactive":
                return Lifestyle.extremely_inactive;
            case "sedentary":
                return Lifestyle.sedentary;
            case "moderately_active":
                return Lifestyle.moderately_active;
            case "vigorously_active":
                return Lifestyle.vigorously_active;
            case "extremely_active":
                return Lifestyle.extremely_active;
            default:
                return null;
        }
    }

    public static void addToJsonResponse(JSONObject json, Health health){
        JSONArray restrictions = new JSONArray();
        JSONObject healthJson = new JSONObject();

        if (null == health){
            healthJson.put("age",null);
            healthJson.put("weight",null);
            healthJson.put("height",null);
            healthJson.put("lifestyle",null);
        } else {
            healthJson.put("age",health.getAge());
            healthJson.put("weight",health.getWeight());
            healthJson.put("height",health.getHeight());
            healthJson.put("lifestyle",health.getLifestyleAsString());
            restrictions.addAll(Arrays.asList(health.getRestrictionsAsStrings()));
        }

        healthJson.put("restrictions",restrictions);
        json.put("health",healthJson);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(Lifestyle lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getLifestyleAsString(){
        return this.lifestyle.toString();
    }

    public Recipe.Category[] getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Recipe.Category[] restrictions) {
        this.restrictions = restrictions;
    }

    public String[] getRestrictionsAsStrings(){
        String [] sRestrictions = new String[this.restrictions.length];
        int i = 0;

        for (Recipe.Category restrictions : this.restrictions){
            sRestrictions[i] = restrictions.toString();
            i++;
        }

        return sRestrictions;
    }
}
