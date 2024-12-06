package model;

import java.util.Date;

public class Student {
    private String studentName;
    private Date dateOfBirth;
    private String id;
    private String phoneNumber;
    private String gmail;
    private String parentNumber;

    // Constructor
    public Student() {
    }

    public Student(String studentName, Date dateOfBirth, String id, String phoneNumber, String gmail, String parentNumber) {
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.gmail = gmail;
        this.parentNumber = parentNumber;
    }

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gmail='" + gmail + '\'' +
                ", parentNumber='" + parentNumber + '\'' +
                '}';
    }
}
