package api.controller;


import api.dao.AccountDAO;
import api.dao.FoodDAO;
import api.exception.BadRequestException;
import api.object.Account;
import api.object.Food;
import api.validator.AccountValidator;
import api.validator.JsonValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static api.validator.AccountValidator.VALID_TOKEN;
import static api.validator.JsonValidator.getData;
import static api.validator.JsonValidator.jsonString;

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
