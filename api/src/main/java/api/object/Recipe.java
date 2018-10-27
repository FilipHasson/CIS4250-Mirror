package api.object;

import javax.persistence.Entity;
import javax.persistence.Table;

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

    private Category category;
    private int id;
    private int accountId;
    private double portions;
    private String[] steps;
    private int views;
    private int stars;


    public Recipe(int id, int accountId, double portions, Category category,
                  String[] steps, int views, int stars) {
        this.category = category;
        this.id = id;
        this.accountId = accountId;
        this.portions = portions;
        this.steps = steps;
        this.views = views;
        this.stars = stars;
    }

    public Recipe(){
        this (0, 0, 0.0, Category.NONE, new String[]{""},0,0);
    }

    public static Category stringToCategory(String val){
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
                return Category.NONE;
        }
    }
}
