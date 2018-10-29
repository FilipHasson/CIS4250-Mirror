package api.object;

import api.dao.NutritionDAO;
import api.dao.RecipeDAO;
import org.json.simple.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Food Business Object for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class Food {
    private int id;
    private String title;
    private int nutritionId;
    private Nutrition nutrition;
    private int recipeId;

    private Recipe recipe;
    private OffsetDateTime timeCreated;
    private OffsetDateTime timeUpdated;

    public Food(int id, String title, int nId, int rId, OffsetDateTime created, OffsetDateTime updated, Recipe r, Nutrition n) {
        this.id = id;
        this.title = title;
        this.nutritionId = nId;
        this.recipeId = rId;
        this.timeCreated = created;
        this.timeUpdated = updated;
        if (0 == recipeId) this.recipe = null;
        else this.recipe = r;
        if (0 == nutritionId) this.nutrition = null;
        else this.nutrition = n;
    }

    public Food(int id,String title, int nId, int rId, OffsetDateTime created, OffsetDateTime updated){
        this(id, title, nId, rId, created, updated, new RecipeDAO().findById(rId), new NutritionDAO().findById(nId));
    }

    public Food(){
        this(0,",", 0,0,OffsetDateTime.now(),OffsetDateTime.now(),new Recipe(),new Nutrition());
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();

        json.put("id",this.id);
        json.put("title",this.title);
        json.put("header","don't really know what you want here Jessy");
        json.put("time_created",this.timeCreated.toEpochSecond());
        json.put("time_modified",this.timeUpdated.toEpochSecond());

        Recipe.addToJsonResponse(json,this.recipe);
        Nutrition.addToJsonResponse(json,this.nutrition);

        return json;
    }

    public static List<Integer> getIds(List<Food> foods){
        ArrayList<Integer> foodIds = new ArrayList<>();

        for (Food food : foods){
            foodIds.add(food.getId());
        }

        return foodIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(int nutritionId) {
        this.nutritionId = nutritionId;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public OffsetDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(OffsetDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public OffsetDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(OffsetDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}
