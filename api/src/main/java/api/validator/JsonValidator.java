package api.validator;

import api.exception.BadRequestException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonValidator {

    public static boolean isValidJson(JSONObject json){
        return  (json.containsKey("data") && json.containsKey("meta"));
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
        if (json.containsKey(field)) return (Double)json.get(field);
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
