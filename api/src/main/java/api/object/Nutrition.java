package api.object;

import org.json.simple.JSONObject;

/**
 * Recipe Buisness Object for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class Nutrition {
    /*
     calcium_mg REAL DEFAULT 0,
  calories REAL DEFAULT 0,
  carbs_fibre_g REAL DEFAULT 0,
  carbs_sugar_g REAL DEFAULT 0,
  carbs_total_g REAL DEFAULT 0,
  cholesterol_mg REAL DEFAULT 0,
  fat_sat_g REAL DEFAULT 0,
  fat_total_g REAL DEFAULT 0,
  fat_trans_g REAL DEFAULT 0,
  iron_mg REAL DEFAULT 0,
  protein_g REAL DEFAULT 0,
  sodium_mg REAL DEFAULT 0,
  vitamin_a_iu REAL DEFAULT 0,
  vitamin_c_mg REAL DEFAULT 0
     */
    private int id;
    private double calcium_mg;
    private double calories;
    private double carbs_fibre_g;
    private double carbs_sugar_g;
    private double carbs_total_g;
    private double cholesterol_mg;
    private double fat_sat_g;
    private double fat_total_g;
    private double fat_trans_g;
    private double iron_mg;
    private double protein_g;
    private double sodium_mg;
    private double vitamin_a_iu;
    private double vitamin_c_mg;


    public Nutrition(int id, double calcium_mg, double calories, double carbs_fibre_g, double carbs_sugar_g, double carbs_total_g, double cholesterol_mg, double fat_sat_g, double fat_total_g, double fat_trans_g, double iron_mg, double protein_g, double sodium_mg, double vitamin_a_iu, double vitamin_c_mg) {
        this.id = id;
        this.calcium_mg = calcium_mg;
        this.calories = calories;
        this.carbs_fibre_g = carbs_fibre_g;
        this.carbs_sugar_g = carbs_sugar_g;
        this.carbs_total_g = carbs_total_g;
        this.cholesterol_mg = cholesterol_mg;
        this.fat_sat_g = fat_sat_g;
        this.fat_total_g = fat_total_g;
        this.fat_trans_g = fat_trans_g;
        this.iron_mg = iron_mg;
        this.protein_g = protein_g;
        this.sodium_mg = sodium_mg;
        this.vitamin_a_iu = vitamin_a_iu;
        this.vitamin_c_mg = vitamin_c_mg;
    }

    public Nutrition() {
        this(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    }

    public static void addToJsonResponse(JSONObject json, Nutrition nutrition) {
        if (null == nutrition) {

            json.put("nutrition_id", null);
            json.put("calcium_mg", null);
            json.put("calories", null);
            json.put("cholesterol_mg",null);
            json.put("fat_sat_g", null);
            json.put("fat_trans_g", null);
            json.put("protein_g", null);
            json.put("sodium_mg", null);
            json.put("vitamin_a_iu", null);
            json.put("vitamin_c_mg", null);
            json.put("carbs_fibre_g",null);
            json.put("carbs_sugar_g",null);
            json.put("carbs_total_g",null);
            json.put("fat_total_g",null);
            json.put("iron_mg",null);
        } else {

            json.put("nutrition_id", nutrition.getId());
            json.put("calcium_mg", nutrition.getCalcium_mg());
            json.put("calories", nutrition.getCalories());
            json.put("cholesterol_mg", nutrition.getCholesterol_mg());
            json.put("fat_sat_g", nutrition.getFat_sat_g());
            json.put("fat_trans_g", nutrition.getFat_trans_g());
            json.put("protein_g", nutrition.getProtein_g());
            json.put("sodium_mg", nutrition.getSodium_mg());
            json.put("vitamin_a_iu", nutrition.getVitamin_a_iu());
            json.put("vitamin_c_mg", nutrition.getVitamin_c_mg());
            json.put("carbs_fibre_g",nutrition.getCarbs_fibre_g());
            json.put("carbs_sugar_g",nutrition.getCarbs_sugar_g());
            json.put("carbs_total_g",nutrition.getCarbs_total_g());
            json.put("fat_total_g",nutrition.getFat_total_g());
            json.put("iron_mg",nutrition.getIron_mg());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCalcium_mg() {
        return calcium_mg;
    }

    public void setCalcium_mg(double calcium_mg) {
        this.calcium_mg = calcium_mg;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCholesterol_mg() {
        return cholesterol_mg;
    }

    public void setCholesterol_mg(double cholesterol_mg) {
        this.cholesterol_mg = cholesterol_mg;
    }

    public double getFat_sat_g() {
        return fat_sat_g;
    }

    public void setFat_sat_g(double fat_sat_g) {
        this.fat_sat_g = fat_sat_g;
    }

    public double getFat_trans_g() {
        return fat_trans_g;
    }

    public void setFat_trans_g(double fat_trans_g) {
        this.fat_trans_g = fat_trans_g;
    }

    public double getProtein_g() {
        return protein_g;
    }

    public void setProtein_g(double protein_g) {
        this.protein_g = protein_g;
    }

    public double getSodium_mg() {
        return sodium_mg;
    }

    public void setSodium_mg(double sodium_mg) {
        this.sodium_mg = sodium_mg;
    }

    public double getVitamin_a_iu() {
        return vitamin_a_iu;
    }

    public void setVitamin_a_iu(double vitamin_a_iu) {
        this.vitamin_a_iu = vitamin_a_iu;
    }

    public double getVitamin_c_mg() {
        return vitamin_c_mg;
    }

    public void setVitamin_c_mg(double vitamin_c_mg) {
        this.vitamin_c_mg = vitamin_c_mg;
    }

    public double getCarbs_fibre_g() {
        return carbs_fibre_g;
    }

    public void setCarbs_fibre_g(double carbs_fibre_g) {
        this.carbs_fibre_g = carbs_fibre_g;
    }

    public double getCarbs_sugar_g() {
        return carbs_sugar_g;
    }

    public void setCarbs_sugar_g(double carbs_sugar_g) {
        this.carbs_sugar_g = carbs_sugar_g;
    }

    public double getCarbs_total_g() {
        return carbs_total_g;
    }

    public void setCarbs_total_g(double carbs_total_g) {
        this.carbs_total_g = carbs_total_g;
    }

    public double getFat_total_g() {
        return fat_total_g;
    }

    public void setFat_total_g(double fat_total_g) {
        this.fat_total_g = fat_total_g;
    }

    public double getIron_mg() {
        return iron_mg;
    }

    public void setIron_mg(double iron_mg) {
        this.iron_mg = iron_mg;
    }

    @Override
    public String toString() {
        return "Nutrition{" +
                "id=" + id +
                ", calcium_mg=" + calcium_mg +
                ", calories=" + calories +
                ", carbs_fibre_g=" + carbs_fibre_g +
                ", carbs_sugar_g=" + carbs_sugar_g +
                ", carbs_total_g=" + carbs_total_g +
                ", cholesterol_mg=" + cholesterol_mg +
                ", fat_sat_g=" + fat_sat_g +
                ", fat_total_g=" + fat_total_g +
                ", fat_trans_g=" + fat_trans_g +
                ", iron_mg=" + iron_mg +
                ", protein_g=" + protein_g +
                ", sodium_mg=" + sodium_mg +
                ", vitamin_a_iu=" + vitamin_a_iu +
                ", vitamin_c_mg=" + vitamin_c_mg +
                '}';
    }
}