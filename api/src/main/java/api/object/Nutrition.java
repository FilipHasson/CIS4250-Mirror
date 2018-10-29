package api.object;

import org.json.simple.JSONObject;

/**
 * Recipe Buisness Object for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class Nutrition {
    private int id;
    private double caffeine_mg;
    private double calcium_mg;
    private double calories;
    private double carbohydrate_g;
    private double cholesterol_mg;
    private double fat_mono_g;
    private double fat_poly_g;
    private double fat_sat_g;
    private double fat_trans_g;
    private double folate_mcg;
    private double magnesium_mg;
    private double manganese_mg;
    private double niacin_mg;
    private double potassium_mg;
    private double protein_g;
    private double riboflavin_mg;
    private double sodium_mg;
    private double sugars_g;
    private double thiamin_mg;
    private double total_fiber_g;
    private double total_lipid_g;
    private double vitamin_a_iu;
    private double vitamin_b6_mg;
    private double vitamin_c_mg;
    private double vitamin_d_iu;
    private double vitamin_k_mcg;
    private double water_g;
    private double zinc_mg;

    public Nutrition(int id, double caffeine_mg, double calcium_mg, double calories, double carbohydrate_g, double cholesterol_mg, double fat_mono_g, double fat_poly_g, double fat_sat_g, double fat_trans_g, double folate_mcg, double magnesium_mg, double manganese_mg, double niacin_mg, double potassium_mg, double protein_g, double riboflavin_mg, double sodium_mg, double sugars_g, double thiamin_mg, double total_fiber_g, double total_lipid_g, double vitamin_a_iu, double vitamin_b6_mg, double vitamin_c_mg, double vitamin_d_iu, double vitamin_k_mcg, double water_g, double zinc_mg) {
        this.id = id;
        this.caffeine_mg = caffeine_mg;
        this.calcium_mg = calcium_mg;
        this.calories = calories;
        this.carbohydrate_g = carbohydrate_g;
        this.cholesterol_mg = cholesterol_mg;
        this.fat_mono_g = fat_mono_g;
        this.fat_poly_g = fat_poly_g;
        this.fat_sat_g = fat_sat_g;
        this.fat_trans_g = fat_trans_g;
        this.folate_mcg = folate_mcg;
        this.magnesium_mg = magnesium_mg;
        this.manganese_mg = manganese_mg;
        this.niacin_mg = niacin_mg;
        this.potassium_mg = potassium_mg;
        this.protein_g = protein_g;
        this.riboflavin_mg = riboflavin_mg;
        this.sodium_mg = sodium_mg;
        this.sugars_g = sugars_g;
        this.thiamin_mg = thiamin_mg;
        this.total_fiber_g = total_fiber_g;
        this.total_lipid_g = total_lipid_g;
        this.vitamin_a_iu = vitamin_a_iu;
        this.vitamin_b6_mg = vitamin_b6_mg;
        this.vitamin_c_mg = vitamin_c_mg;
        this.vitamin_d_iu = vitamin_d_iu;
        this.vitamin_k_mcg = vitamin_k_mcg;
        this.water_g = water_g;
        this.zinc_mg = zinc_mg;
    }

    public Nutrition() {
        this(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    }

    public static void addToJsonResponse(JSONObject json, Nutrition nutrition) {
        if (null == nutrition) {
            json.put("nutrition_id", null);
            json.put("caffeine_mg", null);
            json.put("calcium_mg", null);
            json.put("calories", null);
            json.put("carbohydrate_g", null);
            json.put("cholesterol_mg", null);
            json.put("fat_mono_g", null);
            json.put("fat_poly_g", null);
            json.put("fat_sat_g", null);
            json.put("fat_trans_g", null);
            json.put("folate_mcg", null);
            json.put("magnesium_mg", null);
            json.put("manganese_mg", null);
            json.put("niacin_mg", null);
            json.put("potassium_mg", null);
            json.put("protein_g", null);
            json.put("riboflavin_mg", null);
            json.put("sodium_mg", null);
            json.put("sugars_g", null);
            json.put("thiamin_mg", null);
            json.put("total_fiber_g", null);
            json.put("total_lipid_g", null);
            json.put("vitamin_a_iu", null);
            json.put("vitamin_b6_mg", null);
            json.put("vitamin_c_mg", null);
            json.put("vitamin_d_iu", null);
            json.put("vitamin_k_mcg", null);
            json.put("water_g", null);
            json.put("zinc_mg", null);
        } else {
            json.put("nutrition_id", nutrition.getId());
            json.put("caffeine_mg", nutrition.getCaffeine_mg());
            json.put("calcium_mg", nutrition.getCalcium_mg());
            json.put("calories", nutrition.getCalories());
            json.put("carbohydrate_g", nutrition.getCarbohydrate_g());
            json.put("cholesterol_mg", nutrition.getCholesterol_mg());
            json.put("fat_mono_g", nutrition.getFat_mono_g());
            json.put("fat_poly_g", nutrition.getFat_poly_g());
            json.put("fat_sat_g", nutrition.getFat_sat_g());
            json.put("fat_trans_g", nutrition.getFat_trans_g());
            json.put("folate_mcg", nutrition.getFolate_mcg());
            json.put("magnesium_mg", nutrition.getMagnesium_mg());
            json.put("manganese_mg", nutrition.getManganese_mg());
            json.put("niacin_mg", nutrition.getNiacin_mg());
            json.put("potassium_mg", nutrition.getPotassium_mg());
            json.put("protein_g", nutrition.getProtein_g());
            json.put("riboflavin_mg", nutrition.getRiboflavin_mg());
            json.put("sodium_mg", nutrition.getSodium_mg());
            json.put("sugars_g", nutrition.getSugars_g());
            json.put("thiamin_mg", nutrition.getThiamin_mg());
            json.put("total_fiber_g", nutrition.getTotal_fiber_g());
            json.put("total_lipid_g", nutrition.getTotal_lipid_g());
            json.put("vitamin_a_iu", nutrition.getVitamin_a_iu());
            json.put("vitamin_b6_mg", nutrition.getVitamin_b6_mg());
            json.put("vitamin_c_mg", nutrition.getVitamin_c_mg());
            json.put("vitamin_d_iu", nutrition.getVitamin_d_iu());
            json.put("vitamin_k_mcg", nutrition.getVitamin_k_mcg());
            json.put("water_g", nutrition.getWater_g());
            json.put("zinc_mg", nutrition.getZinc_mg());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCaffeine_mg() {
        return caffeine_mg;
    }

    public void setCaffeine_mg(double caffeine_mg) {
        this.caffeine_mg = caffeine_mg;
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

    public double getCarbohydrate_g() {
        return carbohydrate_g;
    }

    public void setCarbohydrate_g(double carbohydrate_g) {
        this.carbohydrate_g = carbohydrate_g;
    }

    public double getCholesterol_mg() {
        return cholesterol_mg;
    }

    public void setCholesterol_mg(double cholesterol_mg) {
        this.cholesterol_mg = cholesterol_mg;
    }

    public double getFat_mono_g() {
        return fat_mono_g;
    }

    public void setFat_mono_g(double fat_mono_g) {
        this.fat_mono_g = fat_mono_g;
    }

    public double getFat_poly_g() {
        return fat_poly_g;
    }

    public void setFat_poly_g(double fat_poly_g) {
        this.fat_poly_g = fat_poly_g;
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

    public double getFolate_mcg() {
        return folate_mcg;
    }

    public void setFolate_mcg(double folate_mcg) {
        this.folate_mcg = folate_mcg;
    }

    public double getMagnesium_mg() {
        return magnesium_mg;
    }

    public void setMagnesium_mg(double magnesium_mg) {
        this.magnesium_mg = magnesium_mg;
    }

    public double getManganese_mg() {
        return manganese_mg;
    }

    public void setManganese_mg(double manganese_mg) {
        this.manganese_mg = manganese_mg;
    }

    public double getNiacin_mg() {
        return niacin_mg;
    }

    public void setNiacin_mg(double niacin_mg) {
        this.niacin_mg = niacin_mg;
    }

    public double getPotassium_mg() {
        return potassium_mg;
    }

    public void setPotassium_mg(double potassium_mg) {
        this.potassium_mg = potassium_mg;
    }

    public double getProtein_g() {
        return protein_g;
    }

    public void setProtein_g(double protein_g) {
        this.protein_g = protein_g;
    }

    public double getRiboflavin_mg() {
        return riboflavin_mg;
    }

    public void setRiboflavin_mg(double riboflavin_mg) {
        this.riboflavin_mg = riboflavin_mg;
    }

    public double getSodium_mg() {
        return sodium_mg;
    }

    public void setSodium_mg(double sodium_mg) {
        this.sodium_mg = sodium_mg;
    }

    public double getSugars_g() {
        return sugars_g;
    }

    public void setSugars_g(double sugars_g) {
        this.sugars_g = sugars_g;
    }

    public double getThiamin_mg() {
        return thiamin_mg;
    }

    public void setThiamin_mg(double thiamin_mg) {
        this.thiamin_mg = thiamin_mg;
    }

    public double getTotal_fiber_g() {
        return total_fiber_g;
    }

    public void setTotal_fiber_g(double total_fiber_g) {
        this.total_fiber_g = total_fiber_g;
    }

    public double getTotal_lipid_g() {
        return total_lipid_g;
    }

    public void setTotal_lipid_g(double total_lipid_g) {
        this.total_lipid_g = total_lipid_g;
    }

    public double getVitamin_a_iu() {
        return vitamin_a_iu;
    }

    public void setVitamin_a_iu(double vitamin_a_iu) {
        this.vitamin_a_iu = vitamin_a_iu;
    }

    public double getVitamin_b6_mg() {
        return vitamin_b6_mg;
    }

    public void setVitamin_b6_mg(double vitamin_b6_mg) {
        this.vitamin_b6_mg = vitamin_b6_mg;
    }

    public double getVitamin_c_mg() {
        return vitamin_c_mg;
    }

    public void setVitamin_c_mg(double vitamin_c_mg) {
        this.vitamin_c_mg = vitamin_c_mg;
    }

    public double getVitamin_d_iu() {
        return vitamin_d_iu;
    }

    public void setVitamin_d_iu(double vitamin_d_iu) {
        this.vitamin_d_iu = vitamin_d_iu;
    }

    public double getVitamin_k_mcg() {
        return vitamin_k_mcg;
    }

    public void setVitamin_k_mcg(double vitamin_k_mcg) {
        this.vitamin_k_mcg = vitamin_k_mcg;
    }

    public double getWater_g() {
        return water_g;
    }

    public void setWater_g(double water_g) {
        this.water_g = water_g;
    }

    public double getZinc_mg() {
        return zinc_mg;
    }

    public void setZinc_mg(double zinc_mg) {
        this.zinc_mg = zinc_mg;
    }
}