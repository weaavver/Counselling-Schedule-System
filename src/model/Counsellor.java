/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Counsellor {
    private String name;
    private String specialty;
    private String license;
    private String availability;
    private String mobileNumber;
    private String email;
    private String username;
    private String password;   
    
    public Counsellor() {}
    
    public Counsellor(String name, String specialty, String license, String availability, String mobileNumber, String email, String username, String password){
        this.name = name;
        this.specialty = specialty;
        this.license = license;
        this.availability = availability;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSpecialty() {return specialty;}

    public void setSpecialty(String specialty) {this.specialty = specialty;}

    public String getLicense() {return license;}

    public void setLicense(String license) {this.license = license;}

    public String getAvailability() {return availability;}

    public void setAvailability(String availability) {this.availability = availability;}

    public String getMobileNumber() {return mobileNumber;}

    public void setMobileNumber(String mobileNumber) {this.mobileNumber = mobileNumber;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
      
}
