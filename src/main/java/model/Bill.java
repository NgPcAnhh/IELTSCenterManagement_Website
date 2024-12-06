// src/model/Bill.java
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill {
    private String subjectId;
    private String studentId;
    private LocalDateTime time;
    private int price;

    // Getters and setters
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return time.format(formatter);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
