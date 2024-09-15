/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
public class Classes {
    int ClassID;
    int TeacherId;
    String ClassName;
    String ClassCode;
    String Semester;
    String Subject;
    User teacher;

    public Classes() {
    }

    public Classes(int ClassID, int TeacherId, String ClassName, String ClassCode, String Semester, String Subject, User teacher) {
        this.ClassID = ClassID;
        this.TeacherId = TeacherId;
        this.ClassName = ClassName;
        this.ClassCode = ClassCode;
        this.Semester = Semester;
        this.Subject = Subject;
        this.teacher = teacher;
    }
    
    public Classes(int ClassID, int TeacherId, String ClassName, String ClassCode, String Semester, String Subject) {
        this.ClassID = ClassID;
        this.TeacherId = TeacherId;
        this.ClassName = ClassName;
        this.ClassCode = ClassCode;
        this.Semester = Semester;
        this.Subject = Subject;
    }
 public Classes(int TeacherId, String ClassName, String ClassCode, String Semester, String Subject) {
        this.TeacherId = TeacherId;
        this.ClassName = ClassName;
        this.ClassCode = ClassCode;
        this.Semester = Semester;
        this.Subject = Subject;
    }
    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int ClassID) {
        this.ClassID = ClassID;
    }

    public int getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(int TeacherId) {
        this.TeacherId = TeacherId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String ClassCode) {
        this.ClassCode = ClassCode;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Classes{" + "ClassID=" + ClassID + ", TeacherId=" + TeacherId + ", ClassName=" + ClassName + ", ClassCode=" + ClassCode + ", Semester=" + Semester + ", Subject=" + Subject + ", teacher=" + teacher + '}';
    }


    
}
