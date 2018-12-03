package api.object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Recipe Buisness Object for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class Recipe {
    public enum Category{
        none,
        atkins,
        beverage,
        comfort,
        dessert,
        dinner,
        keto,
        lunch,
        quick,
        salad,
        sauce,
        side,
        snack,
        soup;
    }

    private Category[] categories;
    private int id;
    private int accountId;
    private String[] steps;
    private int views;
    private int stars;
    private ArrayList<Ingredient> ingredients;
    private String description;

    public Recipe(int id, int accountId, Category[] categories,
                  String[] steps, int views, int stars, ArrayList<Ingredient> ingredients, String description) {
        this.categories = categories;
        this.id = id;
        this.accountId = accountId;
        this.steps = steps;
        this.views = views;
        this.stars = stars;
        this.ingredients = ingredients;
        this.description = description;
    }

    public Recipe(){
        this (0, 0,  new Category[]{null}, new String[]{""},0,0,new ArrayList<>(),"");
    }

    public static Category[] stringsToCategories(String[] vals){
        if (vals == null) return null;
        Category[] categories = new Category[vals.length];
        int i = 0;

        for (String val : vals){
            categories[i] = Recipe.stringToCategory(val);
            i++;
        }

        return categories;
    }

    public static Category stringToCategory(String val){
        if (null == val) return null;
        switch (val.toLowerCase()){
            case "none":
                return Category.none;
            case "atkins":
                return Category.atkins;
            case "beverage":
                return Category.beverage;
            case "comfort":
                return Category.comfort;
            case "dessert":
                return Category.dessert;
            case "dinner":
                return Category.dinner;
            case "keto":
                return Category.keto;
            case "lunch":
                return Category.lunch;
            case "quick":
                return Category.quick;
            case "salad":
                return Category.salad;
            case "sauce":
                return Category.sauce;
            case "side":
                return Category.side;
            case "snack":
                return Category.snack;
            case "soup":
                return Category.soup;
            default:
                return null;
        }
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public void setCategories(String[] categories){
        Category[] categoryArray = new Category[categories.length];
        int i =0;
        for (String string : categories){
            categoryArray[i] = Recipe.stringToCategory(string);
            i++;
        }
        this.setCategories(categoryArray);
    }

    public String[] getCategoriesAsStrings(){
        String [] sCategories = new String[this.categories.length];
        int i = 0;

        for (Category category : this.categories){
            sCategories[i] = category.toString();
            i++;
        }

        return sCategories;
    }

    public static void addToJsonResponse(JSONObject json, Recipe recipe){
        JSONArray categories = new JSONArray();
        JSONArray steps = new JSONArray();
        JSONObject ingredients = new JSONObject();


        if (null == recipe){
            json.put("recipe_id",null);
            json.put("account_id",null);
            json.put("star_count",null);
            json.put("view_count",null);
        } else {
            json.put("recipe_id",recipe.id);
            categories.addAll(Arrays.asList(recipe.getCategoriesAsStrings()));
            steps.addAll(Arrays.asList(recipe.getSteps()));
            json.put("account_id",recipe.getAccountId());
            json.put("star_count",recipe.getStars());
            json.put("view_count",recipe.getViews());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.addToJsonResponse(ingredients,ingredient);
            }
        }

        json.put("ingredients",ingredients);
        json.put("categories",categories);
        json.put("steps",steps);
    }

    public static List<Integer> getIds(List<Recipe> recipes){
        ArrayList<Integer> recipeIds = new ArrayList<>();

        for (Recipe recipe : recipes){
            recipeIds.add(recipe.getId());
        }

        return recipeIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(int foodId, double quantity){
        this.ingredients.add(new Ingredient(this.id,foodId,quantity));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "categories=" + Arrays.toString(categories) +
                ", id=" + id +
                ", accountId=" + accountId +
                ", steps=" + Arrays.toString(steps) +
                ", views=" + views +
                ", stars=" + stars +
                '}';
    }
}
