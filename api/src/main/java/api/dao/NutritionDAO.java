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

    public long insertNutrition(Nutrition nutrition){
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
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
            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public int updateNutrition(Nutrition nutrition){
        Connection connection = super.connect();
        PreparedStatement statement;
        int affectedRows = 0;
        String query = "UPDATE nutrition set calcium_mg = ?, calories = ?, carbs_fibre_g = ?, carbs_sugar_g = ?, carbs_total_g = ?, cholesterol_mg = ?, fat_sat_g = ?," +
                "fat_total_g = ?, fat_trans_g = ?, iron_mg = ?, protein_g =?, sodium_mg = ?, vitamin_a_iu = ?, vitamin_c_mg = ?" +
                "WHERE id = ?";
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
            statement.setDouble(15,nutrition.getId());
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
                    resultSet.getInt("id"),
                    resultSet.getDouble("calcium_mg"),
                    resultSet.getDouble("calories"),
                    resultSet.getDouble("carbs_fibre_g"),
                    resultSet.getDouble("carbs_sugar_g"),
                    resultSet.getDouble("carbs_total_g"),
                    resultSet.getDouble("cholesterol_mg"),
                    resultSet.getDouble("fat_sat_g"),
                    resultSet.getDouble("fat_total_g"),
                    resultSet.getDouble("fat_trans_g"),
                    resultSet.getDouble("iron_mg"),
                    resultSet.getDouble("protein_g"),
                    resultSet.getDouble("sodium_mg"),
                    resultSet.getDouble("vitamin_a_iu"),
                    resultSet.getDouble("vitamin_c_mg")
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
