/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class LoginResult {
    private boolean success;
    private int counsellorID;
    
    public LoginResult(boolean success, int counsellorID){
        this.success = success;
        this.counsellorID = counsellorID;
    }
    public boolean isSuccess(){
        return success;
    }
    public int getCounsellorID(){
        return counsellorID;
    }
    
}
