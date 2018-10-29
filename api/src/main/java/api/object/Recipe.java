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
        NONE,
        ATKINS,
        BEVERAGE,
        COMFORT,
        DESSERT,
        DINNER,
        KETO,
        LUNCH,
        QUICK,
        SALAD,
        SAUCE,
        SIDE,
        SNACK,
        SOUP;
    }

    private Category[] categories;
    private int id;
    private int accountId;
    private double portions;
    private String[] steps;
    private int views;
    private int stars;


    public Recipe(int id, int accountId, double portions, Category[] categories,
                  String[] steps, int views, int stars) {
        this.categories = categories;
        this.id = id;
        this.accountId = accountId;
        this.portions = portions;
        this.steps = steps;
        this.views = views;
        this.stars = stars;
    }

    public Recipe(int id, int accountId, double portions, Category[] categories, String[] steps) {
        this.categories = categories;
        this.id = id;
        this.accountId = accountId;
        this.portions = portions;
        this.steps = steps;
    }


    public Recipe(){
        this (0, 0, 0.0, new Category[]{null}, new String[]{""},0,0);
    }

    public static Category[] stringsToCategories(String[] vals){
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
        switch (val.toUpperCase()){
            case "NONE":
                return Category.NONE;
            case "ATKINS":
                return Category.ATKINS;
            case "BEVERAGE":
                return Category.BEVERAGE;
            case "COMFORT":
                return Category.COMFORT;
            case "DESSERT":
                return Category.DESSERT;
            case "DINNER":
                return Category.DINNER;
            case "KETO":
                return Category.KETO;
            case "LUNCH":
                return Category.LUNCH;
            case "QUICK":
                return Category.QUICK;
            case "SALAD":
                return Category.SALAD;
            case "SAUCE":
                return Category.SAUCE;
            case "SIDE":
                return Category.SIDE;
            case "SNACK":
                return Category.SNACK;
            case "SOUP":
                return Category.SOUP;
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

        if (null == recipe){
            json.put("recipe_id",null);
            json.put("account_id",null);
            json.put("portions",null);
            json.put("star_count",null);
            json.put("view_count",null);
        } else {
            json.put("recipe_id",recipe.id);
            categories.addAll(Arrays.asList(recipe.getCategoriesAsStrings()));
            steps.add(Arrays.asList(recipe.getSteps()));
            json.put("account_id",recipe.getAccountId());
            json.put("portions",recipe.getPortions());
            json.put("star_count",recipe.getStars());
            json.put("view_count",recipe.getViews());
        }

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

    public double getPortions() {
        return portions;
    }

    public void setPortions(double portions) {
        this.portions = portions;
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
}
