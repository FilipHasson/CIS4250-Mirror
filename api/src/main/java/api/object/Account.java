package api.object;

import api.dao.HealthDAO;
import api.validator.AccountValidator;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private String email;
    private int id;
    private byte[] passwordHash;
    private Health health;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passwordHash = AccountValidator.hashPassword(password);
        this.id = 0;
        this.health = null;
    }

    public Account(String username, String password) {
        this.username = username;
        this.passwordHash = AccountValidator.hashPassword(password);
        this.email = null;
        this.health = null;
        this.id = 0;
    }

    public Account(int id, String username, String email, byte[] passwordHash) {
        this(id,username,email,passwordHash,new HealthDAO().findByAccountId(id));
    }

    public Account(int id, String username, String email){
        this.username = username;
        this.email = email;
        this.id = id;
        this.health = new HealthDAO().findByAccountId(id);
        this.passwordHash = null;
    }

    public Account(int id, String username, String email, byte[]passwordHash,Health health){
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.health = health;
    }

    public static List<Integer> getIds(List<Account> accounts){
        ArrayList<Integer> accountIds = new ArrayList<>();

        for (Account account : accounts){
            accountIds.add(account.getId());
        }

        return accountIds;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();

        json.put("email",this.email);
        json.put("username",this.username);
        json.put("id",this.id);

        Health.addToJsonResponse(json,this.health);
        return json;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = AccountValidator.hashPassword(password);
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }
}
