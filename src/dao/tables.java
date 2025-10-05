/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import javax.swing.JOptionPane;
/**
 *
 * @author Admin
 */
public class tables {
    public static void main(String[] args){
        try{
            String userTable = "create table user(id int AUTO_INCREMENT primary key,name VARCHAR(100), studentNumber VARCHAR(100),mobileNumber VARCHAR(15), email VARCHAR(100), username VARCHAR(100), password VARCHAR(100), status VARCHAR(20), UNIQUE (email,studentNumber))";
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
