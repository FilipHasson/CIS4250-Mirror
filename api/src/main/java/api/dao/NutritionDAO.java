package api.dao;

import api.object.Nutrition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NutritionDAO extends DAO{
    public Nutrition findById(int id){
        return getNutritionFromResultSet(super.findByInt("nutrition","id",id));
    }

    private Nutrition getNutritionFromResultSet(ResultSet resultSet){
        try {
            return new Nutrition(
                    resultSet.getInt("id"),
                    null,
                    null,
                    null
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
