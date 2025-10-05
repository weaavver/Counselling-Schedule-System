/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class User {
    private String name;
    private String studentNumber;
    private String mobileNumber;
    private String email;
    private String username;
    private String password;
    
    public User() {}

    public User(String name, String studentNumber, String mobileNumber, String email, String username, String password){
        this.name = name;
        this.studentNumber = studentNumber;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;   
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getStudentNumber() {return studentNumber;}

    public void setStudentNumber(String studentNumber) {this.studentNumber = studentNumber;}

    public String getMobileNumber() {return mobileNumber;}

    public void setMobileNumber(String mobileNumber) {this.mobileNumber = mobileNumber;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
    
}
