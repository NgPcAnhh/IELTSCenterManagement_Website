package model;

import java.util.Date;

public class Library {
    private String id; // ID của tài liệu
    private String title; // Tên sách hoặc tài liệu
    private String author; // Tác giả
    private String description; // Mô tả
    private String category; // Danh mục
    private Date uploadDate; // Ngày tải lên
    private String filePath; // Đường dẫn file

    // Constructor không tham số
    public Library() {}

    // Constructor đầy đủ tham số
    public Library(String id, String title, String author, String description, String category, Date uploadDate, String filePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.uploadDate = uploadDate;
        this.filePath = filePath;
    }

    // Getter và Setter cho các trường
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Phương thức toString (tùy chọn)
    @Override
    public String toString() {
        return "Library{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", uploadDate=" + uploadDate +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
