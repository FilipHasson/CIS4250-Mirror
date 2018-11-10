package api.dao;

import api.object.Meal;

import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealDAO extends DAO {

    public long insertMeal(Meal meal){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "INSERT INTO meals (meal_day, account_id, type, food_id, serving_amount)" +
                "VALUES (?, ?, CAST(? AS meal_type), ?, ?)";

        try{
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1,meal.getDate());
            statement.setInt(2,meal.getAccountId());
            statement.setString(3,meal.getType().toString());
            statement.setInt(4,meal.getFoodId());
            statement.setInt(5,meal.getServingAmmount());

            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.disconnect(connection);
        return 0;
    }

    public List<Meal> getByDateAccount(LocalDate date, int accountId){
        ArrayList<Meal>meals = new ArrayList<>();
        Connection connection = super.connect();
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "SELECT * FROM meals WHERE meal_day = ? AND account_id = ?";

        try{
            statement = connection.prepareStatement(query);
            statement.setObject(1,date);
            statement.setInt(2,accountId);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                meals.add(getMealFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meals;
    }

    public int deleteAccountAndDate(LocalDate date, int id) {
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "DELETE FROM meals WHERE meal_day = ? AND account_id = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1,date);
            statement.setInt(2,id);

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Meal getMealFromResultSet(ResultSet resultSet){
        try{
            return new Meal(
                    resultSet.getInt("id"),
                    resultSet.getInt("account_id"),
                    resultSet.getInt("food_id"),
                    resultSet.getObject("meal_day", LocalDate.class),
                    Meal.stringToType(resultSet.getString("type")),
                    resultSet.getInt("serving_amount")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
