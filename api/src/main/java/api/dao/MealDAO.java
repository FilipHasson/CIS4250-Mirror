package api.dao;

import api.object.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MealDAO extends DAO {

    public int insertMeal(Meal meal){
//        Connection connection = super.connect();
//        PreparedStatement statement;
//        String query = "INSERT INTO meals (meal_day, account_id) VALUES (?, ?)";
//
//        try {
////            String query = "INSERT INTO recipe (account_id, categories, steps, view_count, star_count, description)
//// VALUES (?, CAST(? AS recipe_category[]), ?, ?, ?, ?)";
//
//            statement = connection.prepareStatement(query);
//            statement.setObject(1,meal.getDate());
//            statement.setInt(2,meal.getAccount_id());
////            statement.setInt();
//
//            return statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return 0;
    }

    public int deleteAccountAndDate(Meal meal) {
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "DELETE FROM meals WHERE meal_day = ? AND account_id = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1,meal.getDate());
            statement.setInt(2,meal.getAccount_id());

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
