package model;

import java.util.Date;

public class STUNoti {
    private String id;
    private String notiName;
    private Date time;
    private String content;
    private String picture;
    private String writerName;

    public STUNoti() {}

    public STUNoti(String id, String notiName, Date time, String content, String picture, String writerName) {
        this.id = id;
        this.notiName = notiName;
        this.time = time;
        this.content = content;
        this.picture = picture;
        this.writerName = writerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotiName() {
        return notiName;
    }

    public void setNotiName(String notiName) {
        this.notiName = notiName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }
}
