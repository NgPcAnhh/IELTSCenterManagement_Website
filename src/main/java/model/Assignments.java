package model;

import java.util.Date;

public class Assignments {
    private String assignmentId;
    private String nameHW;
    private String teacherId;
    private String handOn;
    private Date submitTime;
    private String checkingStatus;
    private String feedbacks;
    private String studentId;
    private String handOnLink;

    // Constructor
    public Assignments(String assignmentId, String nameHW, String teacherId, String handOn,
                       Date submitTime, String checkingStatus, String feedbacks, String studentId) {
        this.assignmentId = assignmentId;
        this.nameHW = nameHW;
        this.teacherId = teacherId;
        this.handOn = handOn;
        this.submitTime = submitTime;
        this.checkingStatus = checkingStatus;
        this.feedbacks = feedbacks;
        this.studentId = studentId;
    }

    // Getters and Setters
    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getNameHW() {
        return nameHW;
    }

    public void setNameHW(String nameHW) {
        this.nameHW = nameHW;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getHandOn() {
        return handOn;
    }

    public void setHandOn(String handOn) {
        this.handOn = handOn;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(String checkingStatus) {
        this.checkingStatus = checkingStatus;
    }

    public String getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getHandOnLink() {
        return handOnLink;
    }

    public void setHandOnLink(String handOnLink) {
        this.handOnLink = handOnLink;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "assignmentId='" + assignmentId + '\'' +
                ", nameHW='" + nameHW + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", handOn='" + handOn + '\'' +
                ", submitTime=" + submitTime +
                ", checkingStatus='" + checkingStatus + '\'' +
                ", feedbacks='" + feedbacks + '\'' +
                ", studentId='" + studentId + '\'' +
                ", handOnLink='" + handOnLink + '\'' +
                '}';
    }
}
