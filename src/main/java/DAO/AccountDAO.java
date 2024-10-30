package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    /**
     * Inserts an account into the database.
     * @param account
     * @return account with generated account_ID
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            String username = account.getUsername();
            String password = account.getPassword();

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getInt(1);
                account.setAccount_id(generated_account_id);
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("account_id"), username, rs.getString("password"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("account_id"), username, password);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(account_id, rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
