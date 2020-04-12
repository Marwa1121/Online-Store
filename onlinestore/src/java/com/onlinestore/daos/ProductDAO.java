/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onlinestore.daos;

import com.onlinestore.database.Database;
import com.onlinestore.models.Product;
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
public class ProductDAO implements DAO<Product> {

    Connection conn = Database.getConnectionInstance();

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> allProducts = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from products");
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setQuantity(rs.getInt("quantity"));
                product.setPrice(rs.getFloat("price"));
                product.setDescription(rs.getString("description"));
                product.setImgurl(rs.getString("imgurl"));
                allProducts.add(product);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return allProducts;
    }

    @Override
    public Product get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public int saveAndReturnId(Product product) {
        int newRecordId;
//        boolean stmtSuccess = true;
        String sqlCommand = "Insert into products (product_name, quantity, category_id, description, price, imgurl)"
                            + " values(?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCommand, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setInt(2, product.getQuantity());
            pstmt.setInt(3, product.getCategoryId());
            pstmt.setString(4, product.getDescription());
            pstmt.setFloat(5, product.getPrice());
            pstmt.setString(6, product.getImgurl());
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            generatedKeys.next();
            newRecordId = generatedKeys.getInt(1);
//            System.out.println("############### " + generatedKeys.getLong(1));
        } catch (SQLException ex) {
//            stmtSuccess = false;
            newRecordId = 0;
            System.out.println(ex.getMessage());
        }
        return newRecordId;
    }

    @Override
    public boolean update(Product product) {
        boolean stmtSuccess = true;
        String sqlCommand = "update products set product_name = ?, "
                            + "quantity = ?, category_id = ?, "
                            + "description = ?, imgurl = ?, "
                            + "price = ? where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCommand)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setInt(2, product.getQuantity());
            pstmt.setInt(3, product.getCategoryId());
            pstmt.setString(4, product.getDescription());
            pstmt.setString(5, product.getImgurl());
            pstmt.setFloat(6, product.getPrice());
            pstmt.setInt(7, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            stmtSuccess = false;
            System.out.println(ex.getMessage());
        }
        return stmtSuccess;
    }

    @Override
    public boolean delete(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean save(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
