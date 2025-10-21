/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Request {
    private String classification;
    private String problemSummary;
    private Integer ID;
    
    public Request() {}
    
    public Request(String classification, String problemSummary, Integer ID){ //USER ID
        this.classification = classification;
        this.problemSummary = problemSummary;
        this.ID = ID;
    }

    public String getClassification() {return classification;}

    public void setClassification(String classification) {this.classification = classification;}

    public String getProblemSummary() {return problemSummary;}

    public void setProblemSummary(String problemSummary) {this.problemSummary = problemSummary;}
    
    public Integer getID() {return ID;}

    public void setID(Integer ID) {this.ID = ID;}
    
}
