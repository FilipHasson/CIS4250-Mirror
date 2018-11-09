package api.validator;

import org.json.simple.JSONObject;

public class JsonValidator {

    public static boolean isValidJson(JSONObject json){
        return  (json.containsKey("data") && json.containsKey("meta"));
    }
    
    public static boolean hasValidFood(JSONObject json){
        if ()
        return true;
    }

    public static boolean hasValidNutrition(JSONObject json){
        return true;
    }

    public static boolean isValidRecipe(JSONObject json){
        return true;
    }
}
