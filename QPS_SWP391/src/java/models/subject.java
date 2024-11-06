package models;

public class subject {
    private int subjectId;
    private String subjectName;
    private String title;
    private String thumbnail;

    // Default constructor
    public subject() {
    }

    // Constructor for full details
    public subject(int subjectId, String subjectName, String title, String thumbnail) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    // Constructor with only subjectId and subjectName
    public subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    // Getters and Setters
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
