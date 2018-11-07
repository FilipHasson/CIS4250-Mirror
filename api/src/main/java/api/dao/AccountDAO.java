package api.dao;

import api.exception.NotFoundException;
import api.object.Account;
import api.validator.AccountValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends DAO{

    public List<Account> findAllOrderByFieldLimit(String field, int limit){
        ResultSet resultSet =  super.findAllOrderByFieldLimit("account",field,limit);
        List<Account> accounts = new ArrayList<>();

        try {
            while (resultSet.next()) {
                accounts.add(getAccountFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NotFoundException();
        }

        return accounts;
    }


    public Account findById(int searchIndex){
        if (0 != searchIndex) {
            try {
                ResultSet resultSet = super.findByInt("account","id",searchIndex);
                resultSet.next();
                return getAccountFromResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        return null;
    }

    public Account findByUsername(String searchIndex){
        if (null != searchIndex) {
            try {
                ResultSet resultSet = super.findByString("account","username",searchIndex);
                resultSet.next();
                return getAccountFromResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        return null;
    }

    public byte[] findAccountHash(String username){
        if (null != username) {
            try {
                ResultSet resultSet = super.findByString("account","username",username);
                resultSet.next();
                return AccountValidator.hexStringToByteArray(resultSet.getString("password"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        return null;
    }

    public byte[] findAccountHash(int id){
        if (0 != id) {
            try {
                ResultSet resultSet = super.findByInt("account","id",id);
                resultSet.next();
                return AccountValidator.hexStringToByteArray(resultSet.getString("password"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        return null;
    }

    public long insertAccount(Account account){
        Connection connection = super.connect();
        PreparedStatement statement;
        String query = "INSERT INTO account (username, password, email) VALUES (?, ?, ?)";
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, account.getUsername());
            //TODO do not trim hashes when grant updates DB
            statement.setString(2, AccountValidator.byteArrayToHexString(account.getPasswordHash()).substring(0,32));
            statement.setString(3, account.getEmail());

            return super.checkUpdated(connection,statement,statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.disconnect(connection);
        return 0;
    }

    public Account getAccountFromResultSet(ResultSet resultSet) throws SQLException{
//        try {
            return new Account(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("email")
            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public Account getAccountFromResultSetWithPassword(ResultSet resultSet){
        try {
            return new Account(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("email"),
                    AccountValidator.hexStringToByteArray(resultSet.getString("password"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}