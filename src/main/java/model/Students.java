package model;

import java.util.Date;

public class Students {
    private String student_name;
    private Date date_birth;
    private String id;
    private String phone_number;
    private String gmail;
    private String parent_name;
    private String parent_number;
    private String ma_mon;
    private String ma_mon_1;
    private String ma_mon_2;
    private String ss1;

    // Constructors
    public Students() {
    }

    public Students(String student_name, Date date_birth, String id, String phone_number, String gmail,
                   String parent_name, String parent_number, String ma_mon, String ma_mon_1, String ma_mon_2, String ss1) {
        this.student_name = student_name;
        this.date_birth = date_birth;
        this.id = id;
        this.phone_number = phone_number;
        this.gmail = gmail;
        this.parent_name = parent_name;
        this.parent_number = parent_number;
        this.ma_mon = ma_mon;
        this.ma_mon_1 = ma_mon_1;
        this.ma_mon_2 = ma_mon_2;
        this.ss1 = ss1;
    }

    // Getters and Setters
    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_number() {
        return parent_number;
    }

    public void setParent_number(String parent_number) {
        this.parent_number = parent_number;
    }

    public String getMa_mon() {
        return ma_mon;
    }

    public void setMa_mon(String ma_mon) {
        this.ma_mon = ma_mon;
    }

    public String getMa_mon_1() {
        return ma_mon_1;
    }

    public void setMa_mon_1(String ma_mon_1) {
        this.ma_mon_1 = ma_mon_1;
    }

    public String getMa_mon_2() {
        return ma_mon_2;
    }

    public void setMa_mon_2(String ma_mon_2) {
        this.ma_mon_2 = ma_mon_2;
    }

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_name='" + student_name + '\'' +
                ", date_birth=" + date_birth +
                ", id='" + id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", gmail='" + gmail + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", parent_number='" + parent_number + '\'' +
                ", ma_mon='" + ma_mon + '\'' +
                ", ma_mon_1='" + ma_mon_1 + '\'' +
                ", ma_mon_2='" + ma_mon_2 + '\'' +
                ", ss1='" + ss1 + '\'' +
                '}';
    }
}