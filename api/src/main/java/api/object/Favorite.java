package api.object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class Favorite {

    private int id;
    private int account_id;
    private int recipe_id;

    public Favorite(int id, int account_id, int recipe_id)
    {
        this.id = id;
        this.account_id = account_id;
        this.recipe_id = recipe_id;
    }

    public Favorite()
    {
        this(0, 0, 0);
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getaccount_id() {
        return account_id;
    }

    public void setaccount_id(int account_id) {
        this.account_id = account_id;
    }

    // public static List<Integer> getIds(List<Favorite> favorites){
    //     ArrayList<Integer> recipeIds = new ArrayList<>();

    //     for (Favorite recipe : recipes){
    //         recipeIds.add(recipe.getId());
    //     }

    //     return recipeIds;
    // }

}
