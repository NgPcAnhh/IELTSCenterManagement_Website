package model;

import java.util.Date;

public class MockTest {
    private float reading;
    private float listening;
    private float writing;
    private float speaking;
    private float overall;
    private String feedback_r;
    private String feedback_l;
    private String feedback_w;
    private String feedback_s;
    private Date time;// đổi testDate thành time
    private String id;


    public MockTest() {

    }


    // Getters and Setters
    public float getReading() { return reading; }
    public void setReading(float reading) { this.reading = reading; }

    public float getListening() { return listening; }
    public void setListening(float listening) { this.listening = listening; }

    public float getWriting() { return writing; }
    public void setWriting(float writing) { this.writing = writing; }

    public float getSpeaking() { return speaking; }
    public void setSpeaking(float speaking) { this.speaking = speaking; }

    public float getOverall() { return overall; }
    public void setOverall(float overall) { this.overall = overall; }

    public Date getTime() { return time; }  // đổi testDate thành time
    public void setTime(Date time) { this.time = time; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFeedback_r() { return feedback_r; }
    public void setFeedback_r(String feedback_r) { this.feedback_r = feedback_r; }

    public String getFeedback_l() { return feedback_l; }
    public void setFeedback_l(String feedback_l) { this.feedback_l = feedback_l; }

    public String getFeedback_w() { return feedback_w; }
    public void setFeedback_w(String feedback_w) { this.feedback_w = feedback_w; }

    public String getFeedback_s() { return feedback_s; }
    public void setFeedback_s(String feedback_s) { this.feedback_s = feedback_s; }
}
