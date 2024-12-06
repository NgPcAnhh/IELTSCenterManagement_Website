package model;

public class AdminTopOverall {
    private String studentId;
    private String studentName;
    private String phoneNumber;
    private String gmail;
    private float averageOverall;
    private int mockTestCount;

    // Constructor không tham số
    public AdminTopOverall() {}

    // Constructor đầy đủ tham số
    public AdminTopOverall(String studentId, String studentName, String phoneNumber, String gmail, float averageOverall, int mockTestCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.phoneNumber = phoneNumber;
        this.gmail = gmail;
        this.averageOverall = averageOverall;
        this.mockTestCount = mockTestCount;
    }

    // Getters và Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public float getAverageOverall() {
        return averageOverall;
    }

    public void setAverageOverall(float averageOverall) {
        this.averageOverall = averageOverall;
    }

    public int getMockTestCount() {
        return mockTestCount;
    }

    public void setMockTestCount(int mockTestCount) {
        this.mockTestCount = mockTestCount;
    }
}
