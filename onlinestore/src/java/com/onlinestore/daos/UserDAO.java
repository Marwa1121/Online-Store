/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinestore.daos;

import com.onlinestore.database.Database;
import com.onlinestore.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author THE PR!NCE
 */
public class UserDAO implements DAO<User> {

    Connection conn = Database.getConnectionInstance();

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> allUsers = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from users where is_deleted=false");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setBirthday(rs.getString("birthday"));
                user.setCreditLimit(rs.getInt("credit_limit"));
                user.setJob(rs.getString("job"));
                user.setAddress(rs.getString("address"));
                user.setInterests(rs.getString("interests"));
                user.setIsAdmin(rs.getBoolean("is_admin"));
                allUsers.add(user);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allUsers;
    }

    @Override
    public boolean save(User user) {
        boolean stmtSuccess = true;
        try (Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO users (username,password,email,birthday,credit_limit,job,address,interests, is_admin, is_deleted)"
                    + " VALUES" + "(" + "'" + user.getUserName() + "'" + ","
                    + "'" + user.getPassword() + "'" + ","
                    + "'" + user.getEmail() + "'" + ","
                    + "'" + user.getBirthday() + "'" + ","
                    + user.getCreditLimit() + ","
                    + "'" + user.getJob() + "'" + ","
                    + "'" + user.getAddress() + "'" + ","
                    + "'" + user.getInterests() + "'" + ","
                    + "false" + ","
                    + "false" + ")";
                    
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            stmtSuccess = false;
            System.out.println(ex.getMessage());
        }
        return stmtSuccess;

    }
    
    public int saveAndReturnId(User user) {
        int newRecordId;
        String sqlCommand = "Insert into users (username, password, email, birthday, credit_limit,"
                            + " job, address, interests, is_admin, is_deleted)"
                            + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getBirthday());
            pstmt.setInt(5, user.getCreditLimit());
            pstmt.setString(6, user.getJob());
            pstmt.setString(7, user.getAddress());
            pstmt.setString(8, user.getInterests());
            pstmt.setBoolean(9, user.getIsAdmin());
            pstmt.setBoolean(10, false);
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();
            newRecordId = generatedKeys.getInt(1);
        } catch (SQLException ex) {
            newRecordId = 0;
            System.out.println(ex.getMessage());
        }
        return newRecordId;
    }

    public User search(String userName, String password) {
        String sql;
        User user = null;
        sql = "SELECT * FROM users WHERE username = "
                + "'" + userName + "'"
                + "AND password = "
                + "'" + password + "'";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setBirthday(rs.getString("birthday"));
                user.setCreditLimit(rs.getInt("credit_limit"));
                user.setJob(rs.getString("job"));
                user.setAddress(rs.getString("address"));
                user.setInterests(rs.getString("interests"));
                user.setIsAdmin(rs.getBoolean("is_admin"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;

    }

    @Override
    public boolean update(User user) {

        boolean stmtSuccess = true;
        String sqlCommand = "update users set username = ?, "
                            + "password = ?, email = ?, birthday = ?, credit_limit = ?, "
                            + "job = ?, address = ?, interests = ?, is_admin = ? "
                            + "where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCommand)) {
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getBirthday());
            pstmt.setInt(5, user.getCreditLimit());
            pstmt.setString(6, user.getJob());
            pstmt.setString(7, user.getAddress());
            pstmt.setString(8, user.getInterests());
            pstmt.setBoolean(9, user.getIsAdmin());
            pstmt.setInt(10, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            stmtSuccess = false;
            System.out.println(ex.getMessage());
        }
        return stmtSuccess;
    }

    @Override
    public boolean delete(int id) {
        boolean stmtSuccess = true;
        try (Statement stmt = conn.createStatement()) {
            String sqlCommand = "update users set is_deleted = true where id = "+ id;
            stmt.executeUpdate(sqlCommand);
        } catch (SQLException ex) {
            stmtSuccess = false;
            System.out.println(ex.getMessage());
        }
        return stmtSuccess;
    }
}


