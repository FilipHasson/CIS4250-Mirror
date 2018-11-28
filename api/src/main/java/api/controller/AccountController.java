package api.controller;


import api.dao.AccountDAO;
import api.dao.FoodDAO;
import api.dao.HealthDAO;
import api.exception.BadRequestException;
import api.exception.ConflictException;
import api.object.Account;
import api.object.Food;
import api.object.Health;
import api.object.Recipe;
import api.validator.AccountValidator;
import api.validator.JsonValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static api.validator.AccountValidator.VALID_TOKEN;
import static api.validator.JsonValidator.*;

@Controller
@RequestMapping("/")
public class AccountController {

    @RequestMapping(value="/account/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONObject getAccount(@PathVariable("id") int id){
        JSONObject accountJson = new AccountDAO().findById(id).toJson();
        JSONArray recipeIds = new JSONArray();
        recipeIds.addAll(Food.getIds(new FoodDAO().findByAccountId(id)));
        accountJson.put("recipe_ids",recipeIds);

        return JsonValidator.initJsonReturn(accountJson);
    }

    @RequestMapping(value="/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject getAccounts(){
        JSONArray json = new JSONArray();
        json.addAll(Account.getIds(new AccountDAO().findAllOrderByFieldLimit("id",100)));
        return JsonValidator.initJsonReturn(json);
    }

    @RequestMapping(value="/accounts/login")
    @ResponseBody
    public JSONObject login(@RequestBody String jsonString){
        JSONObject json;
        JSONObject data;
        JSONObject accountJson;
        String username = "";
        String token = null;

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            data = getData(json);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (null != data){
            String password = jsonString(data,"password");
            username = jsonString(data,"username");
            if (null != password && null != username){
                token = AccountValidator.loginRequest(username,password);
            }
        }

        accountJson = new AccountDAO().findByUsername(username).toJson();
        return JsonValidator.initJsonReturn(accountJson,JsonValidator.initJsonMeta(token));
    }

    @RequestMapping(value="/accounts/logout")
    @ResponseBody
    public JSONObject logout(@RequestBody String jsonString) {
        JSONObject json;
        JSONObject data;
        JSONObject meta;
        JSONObject accountJson;
        String token = null;
        String username = null;

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            if (!JsonValidator.isValidJson(json)) throw new BadRequestException();
            data = JsonValidator.jsonJson(json,"data");
            meta = JsonValidator.jsonJson(json,"meta");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (null != meta) token = jsonString(meta,"token");
        if (token.equals(VALID_TOKEN)){
            //TODO check against meaningful token
            if (null != data){
                username = jsonString(data,"username");
                accountJson = new AccountDAO().findByUsername(username).toJson();
                return JsonValidator.initJsonReturn(accountJson,JsonValidator.initJsonMeta(token));
            }
        }
        throw new BadRequestException();
    }


    @RequestMapping(value="/account")
    @ResponseBody
    public JSONObject createAccount(@RequestBody String jsonString){
        JSONObject json;
        JSONObject data;
        JSONObject accountJson;
        String username = "";
        String token = null;
        String password = null;
        String email = null;

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            data = getData(json);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (null != data){
            password = jsonString(data,"password");
            username = jsonString(data,"username");
            email = jsonString(data,"email");
            if (null != password && null != username && null != email){
                new AccountDAO().insertAccount(new Account(username,email,password));
                token = AccountValidator.loginRequest(username,password);
            }
        } else throw new BadRequestException();

        accountJson = new AccountDAO().findByUsername(username).toJson();
        return JsonValidator.initJsonReturn(accountJson,JsonValidator.initJsonMeta(token));
    }

    @RequestMapping(value="/account/info/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void setHealthInfo (@PathVariable("id")String sAccountId, @RequestBody String jsonString) {
        JSONObject json;
        JSONObject data;
        JSONObject healthJson;
        JSONObject meta;
        int accountId;
        Health health;
        String[] restrictionStrings = null;

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            data = getData(json);
            meta = jsonJson(json,"meta");
            healthJson = jsonJson(data,"health");
            accountId = Integer.parseInt(sAccountId);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (accountId == 0) throw new BadRequestException();
        if (null == new AccountDAO().findById(accountId)) throw new BadRequestException();
        if (null != healthJson){
            JSONArray array = (JSONArray) healthJson.get("restrictions");
            if (array == null){
                System.out.println("Empty Array");
            } else {
                restrictionStrings = new String[array.size()];
                for (int i = 0; i < array.size(); i++) restrictionStrings[i] = (String) array.get(i);
            }

            health = new Health(
                    jsonInt(healthJson,"age"),
                    jsonInt(healthJson,"weight"),
                    jsonInt(healthJson,"height"),
                    Health.stringToLifeStyle(jsonString(healthJson,"lifestyle")),
                    Recipe.stringsToCategories(restrictionStrings)
//                    null
            );

            if (0 == new HealthDAO().insertHealth(health,accountId))throw new ConflictException();
        }
    }

    @RequestMapping(value="/account/info/weight/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject setHealthTarget (@PathVariable("id")String sAccountId, @RequestBody String jsonString) {
        JSONObject json;
        JSONObject data;
        int weight;
        JSONObject meta;
        int accountId;
        Health health;
        String[] restrictionStrings = null;

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject)parser.parse(jsonString);
            data = getData(json);
            meta = jsonJson(json,"meta");
            accountId = Integer.parseInt(sAccountId);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }

        if (accountId == 0) throw new BadRequestException();
        if (null == new AccountDAO().findById(accountId)) throw new BadRequestException();
        if (null != data){

            int target = jsonInt(data,"weight");
            Health healthInfo = new HealthDAO().findByAccountId(accountId);

            // calculates estimated caloric intake to lose 0.5kg per week
            int cals = (int) (1.25 * ((10 * healthInfo.getWeight()) + (6.25 * healthInfo.getHeight()) - (5 * healthInfo.getAge()) + 5));
            int slowLoss = cals - 500;
            int dateGoal = (int) ((healthInfo.getWeight() - target) * 2);

            String test = LocalDate.now().plusWeeks(dateGoal).toString();

            JSONParser respParse = new JSONParser();
            JSONObject resp = new JSONObject();

            // create result JSON that is sent back to caller, with calorie and date info
            String jsonStr = "{\"weight\":" + slowLoss + ", \"date\":\"" + test + "\"}";
            try
            {
                resp = (JSONObject) respParse.parse(jsonStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new BadRequestException();
            }

            if(0 == new HealthDAO().insertGoal(slowLoss, test, accountId))throw new ConflictException();

            return resp;

        }

        return null;
    }

    @RequestMapping(value="/account/health/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getHealth(@PathVariable("id")String sAccountId){
        int accountId;

        try {
            accountId = Integer.parseInt(sAccountId);
        } catch (NumberFormatException e){
            e.printStackTrace();
            throw new BadRequestException();
        }

        return initJsonReturn(new AccountDAO().findById(accountId).toJson());
    }

    //TODO remove generation method when account creation possible
    @RequestMapping(value="/accounts/generate")
    @ResponseBody
    public void generate(){
        AccountDAO dao = new AccountDAO();
        dao.insertAccount(new Account("filip","fhasson@uoguelph.ca","password"));
        dao.insertAccount(new Account("jessy","jwranco@uoguelph.ca","test"));
        dao.insertAccount(new Account("grant","gdougla@uoguelph.ca","secure"));
    }

}
