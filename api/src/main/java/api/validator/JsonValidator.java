package api.validator;

import org.json.simple.JSONObject;

public class JsonValidator {

    public static boolean isValidJson(JSONObject json){
        return  (json.containsKey("data") && json.containsKey("meta"));
    }
}
