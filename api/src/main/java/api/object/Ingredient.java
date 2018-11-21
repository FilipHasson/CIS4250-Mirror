package api.object;

import org.json.simple.JSONObject;

public class Ingredient {
    private int recipe_id;
    private int food_id;
    private double quantity;

    public Ingredient(int recipe_id, int food_id, double quantity) {
        this.recipe_id = recipe_id;
        this.food_id = food_id;
        this.quantity = quantity;
    }

    public Ingredient(){
        this(0,0,0);
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public static void addToJsonResponse(JSONObject json, Ingredient ingredient){
        if (null != ingredient){
            json.put(ingredient.getFood_id(),ingredient.getQuantity());
        }
    }
}
