package api.dao;

import api.object.Nutrition;

import java.sql.*;

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

    public long insertNutrition(Nutrition nutrition){
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
//        String query = "INSERT INTO nutrition (caffeine_mg, calcium_mg, calories, carbohydrate_g, cholesterol_mg," +
//                "fat_mono_g, fat_poly_g, fat_sat_g, fat_trans_g, folate_mcg, magnesium_mg, manganese_mg, niacin_mg," +
//                "potassium_mg, protein_g, riboflavin_mg, sodium_mg, sugars_g, thiamin_mg, total_fiber_g, total_lipid_g," +
//                "vitamin_a_iu, vitamin_b6_mg, vitamin_c_mg, vitamin_d_iu, vitamin_k_mcg, water_g, zinc_mg) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String query = "INSERT INTO nutrition (calcium_mg, calories, carbs_fibre_g,carbs_sugar_g,carbs_total_g," +
                "cholesterol_mg,fat_sat_g,fat_total_g,fat_trans_g,iron_mg,protein_g,sodium_mg,vitamin_a_iu,vitamin_c_mg)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(1,nutrition.getCalcium_mg());
            statement.setDouble(2,nutrition.getCalories());
            statement.setDouble(3,nutrition.getCarbs_fibre_g());
            statement.setDouble(4,nutrition.getCarbs_sugar_g());
            statement.setDouble(5,nutrition.getCarbs_total_g());
            statement.setDouble(6,nutrition.getCholesterol_mg());
            statement.setDouble(7,nutrition.getFat_sat_g());
            statement.setDouble(8,nutrition.getFat_total_g());
            statement.setDouble(9,nutrition.getFat_trans_g());
            statement.setDouble(10,nutrition.getIron_mg());
            statement.setDouble(11,nutrition.getProtein_g());
            statement.setDouble(12,nutrition.getSodium_mg());
            statement.setDouble(13,nutrition.getVitamin_a_iu());
            statement.setDouble(14,nutrition.getVitamin_c_mg());
            /*
                getCarbs_fibre_g
                getCarbs_sugar_g
                getCarbs_total_g
                getFat_total_g
                getIron_mg
            */
//            statement.setDouble(1,nutrition.getCaffeine_mg());
//            statement.setDouble(4,nutrition.getCarbohydrate_g());
//            statement.setDouble(6,nutrition.getFat_mono_g());
//            statement.setDouble(7,nutrition.getFat_poly_g());
//            statement.setDouble(10,nutrition.getFolate_mcg());
//            statement.setDouble(11,nutrition.getMagnesium_mg());
//            statement.setDouble(12,nutrition.getManganese_mg());
//            statement.setDouble(13,nutrition.getNiacin_mg());
//            statement.setDouble(14,nutrition.getPotassium_mg());
//            statement.setDouble(16,nutrition.getRiboflavin_mg());
//            statement.setDouble(18,nutrition.getSugars_g());
//            statement.setDouble(19,nutrition.getThiamin_mg());
//            statement.setDouble(20,nutrition.getTotal_fiber_g());
//            statement.setDouble(21,nutrition.getTotal_lipid_g());
//            statement.setDouble(23,nutrition.getVitamin_b6_mg());
//            statement.setDouble(25,nutrition.getVitamin_d_iu());
//            statement.setDouble(26,nutrition.getVitamin_k_mcg());
//            statement.setDouble(27,nutrition.getWater_g());
//            statement.setDouble(28,nutrition.getZinc_mg());
            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public int updateNutrition(Nutrition nutrition){
//        String query = "UPDATE recipe SET account_id = ?, portions = ?, categories = CAST(? AS recipe_category), steps = ?, view_count = ?, star_count = ? WHERE id = ?";
        Connection connection = super.connect();
        PreparedStatement statement;
        int affectedRows = 0;
        String query = "UPDATE nutrition  SET caffeine_mg = ?, calcium_mg = ?, calories = ?, carbohydrate_g = ?, cholesterol_mg = ?," +
                "fat_mono_g = ?, fat_poly_g = ?, fat_sat_g = ?, fat_trans_g = ?, folate_mcg = ?, magnesium_mg = ?, manganese_mg = ?, niacin_mg = ?," +
                "potassium_mg = ?, protein_g = ?, riboflavin_mg = ?, sodium_mg = ?, sugars_g = ?, thiamin_mg = ?, total_fiber_g = ?, total_lipid_g = ?," +
                "vitamin_a_iu = ?, vitamin_b6_mg = ?, vitamin_c_mg = ?, vitamin_d_iu = ?, vitamin_k_mcg = ?, water_g = ?, zinc_mg  = ?" +
                "WHERE id = ?";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(2,nutrition.getCalcium_mg());
            statement.setDouble(3,nutrition.getCalories());
            statement.setDouble(5,nutrition.getCholesterol_mg());
            statement.setDouble(8,nutrition.getFat_sat_g());
            statement.setDouble(9,nutrition.getFat_trans_g());
            statement.setDouble(15,nutrition.getProtein_g());
            statement.setDouble(17,nutrition.getSodium_mg());
            statement.setDouble(22,nutrition.getVitamin_a_iu());
            statement.setDouble(24,nutrition.getVitamin_c_mg());
            statement.setInt(29,nutrition.getId());

//            statement.setDouble(1,nutrition.getCaffeine_mg());
//            statement.setDouble(4,nutrition.getCarbohydrate_g());
//            statement.setDouble(6,nutrition.getFat_mono_g());
//            statement.setDouble(7,nutrition.getFat_poly_g());
//            statement.setDouble(10,nutrition.getFolate_mcg());
//            statement.setDouble(11,nutrition.getMagnesium_mg());
//            statement.setDouble(12,nutrition.getManganese_mg());
//            statement.setDouble(13,nutrition.getNiacin_mg());
//            statement.setDouble(14,nutrition.getPotassium_mg());
//            statement.setDouble(16,nutrition.getRiboflavin_mg());
//            statement.setDouble(18,nutrition.getSugars_g());
//            statement.setDouble(19,nutrition.getThiamin_mg());
//            statement.setDouble(20,nutrition.getTotal_fiber_g());
//            statement.setDouble(21,nutrition.getTotal_lipid_g());
//            statement.setDouble(23,nutrition.getVitamin_b6_mg());
//            statement.setDouble(25,nutrition.getVitamin_d_iu());
//            statement.setDouble(26,nutrition.getVitamin_k_mcg());
//            statement.setDouble(27,nutrition.getWater_g());
//            statement.setDouble(28,nutrition.getZinc_mg());

            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return affectedRows;
    }

    private Nutrition getNutritionFromResultSet(ResultSet resultSet){
        try {
            resultSet.getInt("id");
            return new Nutrition(
//                    resultSet.getInt("id"),
//                    resultSet.getDouble("caffeine_mg"),
//                    resultSet.getDouble("calcium_mg"),
//                    resultSet.getDouble("calories"),
//                    resultSet.getDouble("carbohydrate_g"),
//                    resultSet.getDouble("cholesterol_mg"),
//                    resultSet.getDouble("fat_mono_g"),
//                    resultSet.getDouble("fat_poly_g"),
//                    resultSet.getDouble("fat_sat_g"),
//                    resultSet.getDouble("fat_trans_g"),
//                    resultSet.getDouble("folate_mcg"),
//                    resultSet.getDouble("magnesium_mg"),
//                    resultSet.getDouble("manganese_mg"),
//                    resultSet.getDouble("niacin_mg"),
//                    resultSet.getDouble("potassium_mg"),
//                    resultSet.getDouble("protein_g"),
//                    resultSet.getDouble("riboflavin_mg"),
//                    resultSet.getDouble("sodium_mg"),
//                    resultSet.getDouble("sugars_g"),
//                    resultSet.getDouble("thiamin_mg"),
//                    resultSet.getDouble("total_fiber_g"),
//                    resultSet.getDouble("total_lipid_g"),
//                    resultSet.getDouble("vitamin_a_iu"),
//                    resultSet.getDouble("vitamin_b6_mg"),
//                    resultSet.getDouble("vitamin_c_mg"),
//                    resultSet.getDouble("vitamin_d_iu"),
//                    resultSet.getDouble("vitamin_k_mcg"),
//                    resultSet.getDouble("water_g"),
//                    resultSet.getDouble("zinc_mg")
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
