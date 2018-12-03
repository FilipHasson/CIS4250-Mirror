package api.validator;

import api.exception.BadRequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonValidator {

    public static boolean isValidJson(JSONObject json){
        return  (json.containsKey("data") && json.containsKey("meta"));
    }

    public static boolean hasValidFood(JSONObject json){
        return dataHasValidFood(getData(json));

    }

    public static boolean hasValidNutrition(JSONObject json){
        return dataHasValidNutrition(getData(json));
    }

    public static boolean dataHasValidFood(JSONObject data){
        if (data.containsKey("title") && data.containsKey("serving_count") && data.containsKey("serving_size"))
            return true;
        return false;

    }

    public static boolean dataHasValidNutrition(JSONObject data){
        JSONObject nutrition = jsonJson(data,"nutrition");
        if (nutrition != null && nutrition.containsKey("calcium_mg") && nutrition.containsKey("calories") &&
                nutrition.containsKey("carbs_fibre_g") && nutrition.containsKey("carbs_sugar_g") &&
                nutrition.containsKey("carbs_total_g") && nutrition.containsKey("cholesterol_mg") &&
                nutrition.containsKey("fat_sat_g") && nutrition.containsKey("fat_total_g") &&
                nutrition.containsKey("fat_trans_g") && nutrition.containsKey("iron_mg") &&
                nutrition.containsKey("protein_g") && nutrition.containsKey("sodium_mg") &&
                nutrition.containsKey("vitamin_a_iu") && nutrition.containsKey("vitamin_c_mg"))
            return true;
        return false;
    }

    public static boolean dataHasValidRecipeObject(JSONObject data){
            if (data.containsKey("account_id") && data.containsKey("description") && data.containsKey("star_count") &&
                    data.containsKey("view_count") && data.containsKey("ingredients") && data.containsKey("categories") &&
                    data.containsKey("steps"))
                return true;
        return false;
    }

    public static boolean isValidRecipe(JSONObject json) {
        JSONObject data;
        if (null != (data = jsonJson(json,"data"))){
            if(dataHasValidRecipeObject(data) && dataHasValidFood(data) && dataHasValidNutrition(data))
                return true;
        }
        return false;
    }

    public static int jsonInt(JSONObject json, String field){
        if (json.containsKey(field)) return ((Long)json.get(field)).intValue();
        return 0;
    }

    public static JSONObject jsonJson(JSONObject json, String field){
        if (json.containsKey(field)) return (JSONObject)json.get(field);
        return null;
    }

    public static String jsonString(JSONObject json, String field){
        if (json.containsKey(field)) return (String)json.get(field);
        return "";
    }

    public static double jsonDouble(JSONObject json, String field){
        try {
            if (json.containsKey(field)) return (Double) json.get(field);
        } catch (ClassCastException e){
            Long i = new Long((Long)json.get(field));
            return i.doubleValue();
        }
        return 0;
    }

    public static JSONObject initJsonReturn(JSONObject data){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",new JSONObject());
        return json;
    }

    public static JSONObject initJsonReturn(JSONArray data){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",new JSONObject());
        return json;
    }

    public static JSONObject initJsonReturn(JSONObject data, JSONObject meta){
        JSONObject json = new JSONObject();
        json.put("data",data);
        json.put("meta",meta);
        return json;
    }

    public static JSONObject initJsonMeta(String token){
        JSONObject json  = new JSONObject();
        json.put("token",token);
        return json;
    }

    public static JSONObject getData(JSONObject json){
        if (!JsonValidator.isValidJson(json)) throw new BadRequestException();
        return JsonValidator.jsonJson(json,"data");
    }
}
