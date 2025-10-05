/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDao {
    public static void registerUser(User user){
        String sql = "INSERT INTO users(name,studentNumber,mobileNumber,email,username,password)"
                + "VALUES (?,?,?,?,?,?)";
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getStudentNumber());
            ps.setString(3, user.getMobileNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getPassword());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "User registered successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
    public static boolean login(String username, String password) {
    String sql = "SELECT 1 FROM users WHERE username=? AND password=?";
    
    try (Connection con = ConnectionProvider.getCon();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);
        ps.setString(2, password);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next(); // true if a record exists
            }
        } 
        catch (Exception e) {
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
        }
    }
    
}
