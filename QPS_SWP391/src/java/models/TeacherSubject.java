package models;

public class TeacherSubject {
    private int teacherSubjectID; // Primary key
    private int userID;           // References the teacher's user ID
    private int subjectID;         // References the subject ID

    // Constructor
    public TeacherSubject(int teacherSubjectID, int userID, int subjectID) {
        this.teacherSubjectID = teacherSubjectID;
        this.userID = userID;
        this.subjectID = subjectID;
    }

    // Getters and Setters
    public int getTeacherSubjectID() {
        return teacherSubjectID;
    }

    public void setTeacherSubjectID(int teacherSubjectID) {
        this.teacherSubjectID = teacherSubjectID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    // Optional: Methods for display or logic
    @Override
    public String toString() {
        return "TeacherSubject [teacherSubjectID=" + teacherSubjectID + ", userID=" + userID + ", subjectID=" + subjectID + "]";
    }
}
