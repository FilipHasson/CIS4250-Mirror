package api.dao;

import api.object.Health;
import api.object.Recipe;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.ArrayList;

public class HealthDAO extends DAO{


    public long insertHealth(Health health, int accountId){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "INSERT INTO health (account_id, age, weight, height, lifestyle, categories)" +
                " VALUES (?, ?, ?, ?, CAST(? AS activity_level), ?)";

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1,accountId);
            statement.setInt(2, health.getAge());
            statement.setInt(3,health.getWeight());
            statement.setInt(4,health.getHeight());
            //TODO Switch when activity levels are changed to lowercase
            statement.setString(5,health.getLifestyleAsString());
//            statement.setString(5,"Sedentary");
            Array restrictionArray = connection.createArrayOf("recipe_category",health.getRestrictions());
            statement.setArray(6,restrictionArray);


            int val = statement.executeUpdate();
            super.disconnect(connection);
            return val;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public long insertGoal(int calories, String date, int accountId)
    {
        Connection connection = super.connect();
        PreparedStatement statement;
        LocalDate newDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "INSERT INTO goals (account_id, calorie_goal, date_goal)" +
                "VALUES (?, ?, ?)";

        try{
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, accountId);
            statement.setInt(2, calories);
            statement.setObject(3, newDate);

            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.disconnect(connection);

        return 0;
    }

    public Health findByAccountId(int accountId){
        if (0 != accountId){
            Connection connection = connect();
            PreparedStatement statement;
            ResultSet resultSet;
            String query = "SELECT * FROM health WHERE account_id = ?";

            try {
                Health health = null;
                statement = connection.prepareStatement(query);
                statement.setInt(1,accountId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) health = getHealthFromResultSet(resultSet);
                super.disconnect(connection);
                return health;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            super.disconnect(connection);
        }
        return null;
    }

    private Health getHealthFromResultSet(ResultSet resultSet){
        try {
            return new Health(
                    resultSet.getInt("age"),
                    resultSet.getInt("weight"),
                    resultSet.getInt("height"),
                    Health.stringToLifeStyle(resultSet.getString("lifestyle")),
                    Recipe.stringsToCategories((String[])resultSet.getArray("categories").getArray())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException i){
            try {
                return new Health(
                        resultSet.getInt("age"),
                        resultSet.getInt("weight"),
                        resultSet.getInt("height"),
                        Health.stringToLifeStyle(resultSet.getString("lifestyle")),
                        null
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
