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
import model.Counsellor;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
public class UserDao {
    public static void registerUser(User user){
        String sql = "INSERT INTO UsersTbl(name,age,maritalStatus,studentNumber,mobileNumber,email,username,password)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getAge());      
            ps.setString(3, user.getmaritalStatus());            
            ps.setString(4, user.getStudentNumber());
            ps.setString(5, user.getMobileNumber());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getUsername());
            ps.setString(8, user.getPassword());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "User registered successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }
    
public static boolean UserLogin(String username, String password) {
    String sql = "SELECT password FROM UsersTbl WHERE username = ?";

    try (Connection con = ConnectionProvider.getCon();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);
        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String storedHash = rs.getString("password");

                // ✅ Compare the entered password (unhashed) with the stored bcrypt hash
                if (BCrypt.checkpw(password, storedHash)) {
                    return true; // login success
                } else {
                    return false; // wrong password
                }
            } else {
                return false; // no such user
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
    
        public static void registerCounsellor(Counsellor counsellor){
            String sql = "INSERT INTO counsellorstbl(name,specialty,license,availability,mobileNumber,email,username,password)"
                + "VALUES (?,?,?,?,?,?,?,?)";
            try (Connection con = ConnectionProvider.getCon();
                PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, counsellor.getName());
                ps.setString(2, counsellor.getSpecialty());
                ps.setString(3, counsellor.getLicense());
                ps.setString(4, counsellor.getAvailability());
                ps.setString(5, counsellor.getMobileNumber());
                ps.setString(6, counsellor.getEmail());
                ps.setString(7, counsellor.getUsername());
                ps.setString(8, counsellor.getPassword());
            
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "User registered successfully!");

            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
    
        }
    
        public static boolean CounsellorLogin(String username, String password) {
            String sql = "SELECT password FROM CounsellorsTbl WHERE username = ?";

            try (Connection con = ConnectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String storedHash = rs.getString("password");

                //Compare the entered password (unhashed) with the stored bcrypt hash
                    if (BCrypt.checkpw(password, storedHash)) {
                        return true; // login success
                    } else {
                        return false; // wrong password
                            }
                    } else {
                        return false; // no such user
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } 
        
        public static boolean setUserResetCode(String email, String code) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "UPDATE UsersTbl SET reset_code=?, reset_expiry=? WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, code);
                ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis() + (15 * 60 * 1000))); // 15 mins expiry
                ps.setString(3, email);
                return ps.executeUpdate() > 0;
            } 
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        public static boolean verifyUserResetCode(String email, String code) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "SELECT reset_code, reset_expiry FROM UsersTbl WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String dbCode = rs.getString("reset_code");
                    Timestamp expiry = rs.getTimestamp("reset_expiry");

                    return dbCode != null && dbCode.equals(code) && expiry.after(new java.sql.Timestamp(System.currentTimeMillis()));
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        return false;
        }
        
        public static boolean updateUserPassword(String email, String newPassword) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "UPDATE UsersTbl SET password=?, reset_code=NULL, reset_expiry=NULL WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, newPassword);
                ps.setString(2, email);
                return ps.executeUpdate() > 0;
            } 
            catch (Exception e) {
                e.printStackTrace();
            return false;
            }
        }
        
        public static boolean setCounsellorResetCode(String email, String code) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "UPDATE counsellorsTbl SET reset_code=?, reset_expiry=? WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, code);
                ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis() + (15 * 60 * 1000))); // 15 mins expiry
                ps.setString(3, email);
                return ps.executeUpdate() > 0;
            } 
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        public static boolean verifyCounsellorResetCode(String email, String code) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "SELECT reset_code, reset_expiry FROM counsellorsTbl WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String dbCode = rs.getString("reset_code");
                    Timestamp expiry = rs.getTimestamp("reset_expiry");

                    return dbCode != null && dbCode.equals(code) && expiry.after(new java.sql.Timestamp(System.currentTimeMillis()));
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        return false;
        }
        
        public static boolean updateCounsellorPassword(String email, String newPassword) {
            try (Connection con = ConnectionProvider.getCon()) {
                String query = "UPDATE counsellorsTbl SET password=?, reset_code=NULL, reset_expiry=NULL WHERE email=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, newPassword);
                ps.setString(2, email);
                return ps.executeUpdate() > 0;
            } 
            catch (Exception e) {
                e.printStackTrace();
            return false;
            }
        }        
        
}
