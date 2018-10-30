package api.dao;

import api.object.Nutrition;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Nutrition DAO for YUMM app
 * @author Filip Hasson
 * @version 1.0
 * @since 2018-10-26
 */
public class NutritionDAO extends DAO{
    public Nutrition findById(int id){
        ResultSet resultSet = super.findByInt("nutrition","id",id);
        try {
            resultSet.next();
            return getNutritionFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertNutrition(Nutrition nutrition){

        return true;
    }

    public boolean updateNutrition(Nutrition nutrition){

        return true;
    }

    private Nutrition getNutritionFromResultSet(ResultSet resultSet){
        try {
            return new Nutrition(
                    resultSet.getInt("id"),
                    resultSet.getDouble("caffeine_mg"),
                    resultSet.getDouble("calcium_mg"),
                    resultSet.getDouble("calories"),
                    resultSet.getDouble("carbohydrate_g"),
                    resultSet.getDouble("cholesterol_mg"),
                    resultSet.getDouble("fat_mono_g"),
                    resultSet.getDouble("fat_poly_g"),
                    resultSet.getDouble("fat_sat_g"),
                    resultSet.getDouble("fat_trans_g"),
                    resultSet.getDouble("folate_mcg"),
                    resultSet.getDouble("magnesium_mg"),
                    resultSet.getDouble("manganese_mg"),
                    resultSet.getDouble("niacin_mg"),
                    resultSet.getDouble("potassium_mg"),
                    resultSet.getDouble("protein_g"),
                    resultSet.getDouble("riboflavin_mg"),
                    resultSet.getDouble("sodium_mg"),
                    resultSet.getDouble("sugars_g"),
                    resultSet.getDouble("thiamin_mg"),
                    resultSet.getDouble("total_fiber_g"),
                    resultSet.getDouble("total_lipid_g"),
                    resultSet.getDouble("vitamin_a_iu"),
                    resultSet.getDouble("vitamin_b6_mg"),
                    resultSet.getDouble("vitamin_c_mg"),
                    resultSet.getDouble("vitamin_d_iu"),
                    resultSet.getDouble("vitamin_k_mcg"),
                    resultSet.getDouble("water_g"),
                    resultSet.getDouble("zinc_mg")
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
