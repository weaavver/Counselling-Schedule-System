/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import model.Request;
/**
 *
 * @author Admin
 */
public class AppointmentAndRequestsDao {
    public static boolean sendRequestAppointment(Request request){
        String sql = "INSERT INTO pending_requests(userID, classification, problemSummary)"
                + "VALUES (?,?,?)";
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, request.getID());
            ps.setString(2, request.getClassification());      
            ps.setString(3, request.getProblemSummary());            
            ps.executeUpdate();
            
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public static ResultSet getAllPendingRequests(){
        try{
            Connection con = ConnectionProvider.getCon();
            String sql = "SELECT requestID, UserID, classification, problemSummary, status, requestDate FROM pending_requests WHERE status = 'pending' ";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public static void confirmRequest(int requestID, int counsellorID, String date, String time) throws Exception {
    Connection con = ConnectionProvider.getCon();
    con.setAutoCommit(false);

    try {
        //Get user info from pending_requests
       String userQuery = "SELECT p.userID, u.name AS userName " +
                   "FROM pending_requests p " +
                   "JOIN userstbl u ON p.userID = u.ID " +
                   "WHERE p.requestID = ?";
        int userID = 0;
        String userName = null;

        try (PreparedStatement psUser = con.prepareStatement(userQuery)) {
            psUser.setInt(1, requestID);
            try (ResultSet rs = psUser.executeQuery()) {
                if (rs.next()) {
                    userID = rs.getInt("userID");
                    userName = rs.getString("userName");
                } else {
                    throw new Exception("No pending request found for requestID " + requestID);
                }
            }
        }

        //Get counsellor name
        String counsellorQuery = "SELECT name FROM counsellorstbl WHERE     ID = ?";
        String counsellorName = null;

        try (PreparedStatement psC = con.prepareStatement(counsellorQuery)) {
            psC.setInt(1, counsellorID);
            try (ResultSet rs = psC.executeQuery()) {
                if (rs.next()) {
                    counsellorName = rs.getString("name");
                } else {
                    throw new Exception("No counsellor found with ID " + counsellorID);
                }
            }
        }

        //Update pending_requests table
        String updateSQL = "UPDATE pending_requests SET status = 'Approved' WHERE requestID = ?";
        try (PreparedStatement ps1 = con.prepareStatement(updateSQL)) {
            ps1.setInt(1, requestID);
            ps1.executeUpdate();
        }

        //Insert into appointments (now complete)
        String insertSQL = "INSERT INTO appointments " +
                "(requestID, userID, userName, counsellorID, counsellorName, appointmentDate, appointmentTime, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'Scheduled')";
        try (PreparedStatement ps2 = con.prepareStatement(insertSQL)) {
            ps2.setInt(1, requestID);
            ps2.setInt(2, userID);
            ps2.setString(3, userName);
            ps2.setInt(4, counsellorID);
            ps2.setString(5, counsellorName);
            ps2.setString(6, date);
            ps2.setString(7, time);
            ps2.executeUpdate();
        }

        con.commit();

    } catch (Exception e) {
        con.rollback();
        throw e;
    } finally {
        con.setAutoCommit(true);
        con.close();
    }
}

    
    public static java.util.Map<String, String> cancelAppointment(int appointmentID) throws Exception {
    String selectSQL = "SELECT u.email, u.name FROM appointments a " +
                       "JOIN userstbl u ON a.userID = u.ID " +
                       "WHERE a.appointmentID = ?";
    String updateSQL = "UPDATE appointments SET status = 'Cancelled' WHERE appointmentID = ? AND status = 'Scheduled'";
    
    String userEmail = null;
    String userName = null;

    try (Connection con = ConnectionProvider.getCon()) {
        //Get the user's info
        try (PreparedStatement psSelect = con.prepareStatement(selectSQL)) {
            psSelect.setInt(1, appointmentID);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    userEmail = rs.getString("email");
                    userName = rs.getString("name");
                } else {
                    throw new Exception("No appointment found with ID: " + appointmentID);
                }
            }
        }

        //Cancels the appointment
        try (PreparedStatement psUpdate = con.prepareStatement(updateSQL)) {
            psUpdate.setInt(1, appointmentID);
            int rowsAffected = psUpdate.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Appointment not found or already cancelled.");
            }
        }
    }

    //Return user info
    java.util.Map<String, String> userInfo = new java.util.HashMap<>();
    userInfo.put("email", userEmail);
    userInfo.put("name", userName);
    return userInfo;
}

   
    
    public static void completeAppointment(int appointmentID) throws Exception {
        String sql = "UPDATE appointments SET status = 'Completed' WHERE appointmentID = ? AND status = 'Scheduled'";
        try (Connection con = ConnectionProvider.getCon();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);
            int rowsAffected = ps.executeUpdate(); 

            if (rowsAffected == 0) {
                throw new Exception("No appointment found with ID: " + appointmentID);
            }
        }
    }


    
    public static ResultSet getAppointmentsByCounsellor(int counsellorID) throws Exception {
        Connection con = ConnectionProvider.getCon();
        String sql = "SELECT * FROM appointments WHERE counsellorID = ? AND status = 'Scheduled'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, counsellorID);
        return ps.executeQuery();
    }
    
    public static ResultSet getAppointmentsByUser(int userID) throws Exception {
        Connection con = ConnectionProvider.getCon();
        String sql = "SELECT * FROM appointments WHERE userID = ? AND status = 'Scheduled'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userID);
        return ps.executeQuery();
    }
    
    
//    public static boolean AddtoAppointments(String ID){
//       return 1;
//    }
}
