package model;

import java.sql.Timestamp;

public class NotLoginData {
    private String studentID;
    private String studentName;
    private String studentPhone;
    private String parentName;
    private String parentPhone;
    private Timestamp latestLogin;
    private int monthsNotLoggedIn;

    // Constructor
    public NotLoginData(String studentID, String studentName, String studentPhone, String parentName, String parentPhone, Timestamp latestLogin, int monthsNotLoggedIn) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.latestLogin = latestLogin;
        this.monthsNotLoggedIn = monthsNotLoggedIn;
    }

    // Getters và Setters

    public String getstudentID() {
        return studentID;
    }

    public void setstudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public Timestamp getLatestLogin() {
        return latestLogin;
    }

    public void setLatestLogin(Timestamp latestLogin) {
        this.latestLogin = latestLogin;
    }

    public int getMonthsNotLoggedIn() {
        return monthsNotLoggedIn;
    }

    public void setMonthsNotLoggedIn(int monthsNotLoggedIn) {
        this.monthsNotLoggedIn = monthsNotLoggedIn;
    }
}
