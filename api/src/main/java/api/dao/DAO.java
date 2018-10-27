package api.dao;

import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

public class DAO {

    private static final String SELECT_ALL = "SELECT * FROM";
    private static final String WHERE = " WHERE";
    private static final String PARAM = " ?";
    private static final String EQUALS = " =";


    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;


    public Connection connect (){
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.out.println("This should never happen");
        }

        try {
            connection = DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void disconnect (Connection connection){
        try {
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet findAll(String table){
        Connection connection = connect();
        PreparedStatement statement;
        ResultSet resultSet = null;
        String query = SELECT_ALL + PARAM;

        try{
            statement = connection.prepareStatement(query);
            statement.setString(1,table);

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet findByInt (String table, String field, int searchIndex){
        Connection connection = connect();
        PreparedStatement statement;
        ResultSet resultSet = null;
        String query = SELECT_ALL + PARAM + WHERE + PARAM + EQUALS + PARAM;
        //System.out.println(query);
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, table);
            statement.setString(2, field);
            statement.setInt(3, searchIndex);

            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
