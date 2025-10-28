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
    
//    public static boolean AddtoAppointments(String ID){
//       return 1;
//    }
}
