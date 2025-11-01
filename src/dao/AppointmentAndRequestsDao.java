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
import model.Request;
import org.mindrot.jbcrypt.BCrypt;
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
            // Update pending_requests status
                String updateSQL = "UPDATE pending_requests SET status = 'Approved' WHERE requestID = ?";
                PreparedStatement ps1 = con.prepareStatement(updateSQL);
                ps1.setInt(1, requestID);
                ps1.executeUpdate();

                // Insert into appointments
                String insertSQL = "INSERT INTO appointments (requestID, counsellorID, appointmentDate, appointmentTime, status) VALUES (?, ?, ?, ?, 'Scheduled')";
                PreparedStatement ps2 = con.prepareStatement(insertSQL);
                ps2.setInt(1, requestID);
                ps2.setInt(2, counsellorID);
                ps2.setString(3, date);
                ps2.setString(4, time);
                ps2.executeUpdate();

                con.commit(); // commit both actions
            } 
            catch (Exception e) {
                con.rollback(); // undo if error
                throw e;
            } 
            finally {
                con.setAutoCommit(true);
                con.close();
            }
    }
    
    public static void cancelAppointment(int appointmentID) throws Exception {
        String sql = "UPDATE appointments SET status = 'Cancelled' WHERE appointmentID = ? AND status = 'Scheduled'";
        try (Connection con = ConnectionProvider.getCon();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);
            int rowsAffected = ps.executeUpdate(); 

            if (rowsAffected == 0) {
                throw new Exception("No appointment found with ID: " + appointmentID);
            }
        }
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
