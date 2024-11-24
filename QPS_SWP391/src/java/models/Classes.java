/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Objects;

public class Classes {
    private int classID;
    private String className;
    private subject subject;

    public Classes() {
    }

    public Classes(int classID, String className) {
        this.classID = classID;
        this.className = className;
    }  


    
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

        public subject getSubject() {
        return subject;
    }

    public void setSubject(subject subject) {
        this.subject = subject;  // This is the setter for Subject
    }
@Override
public int hashCode() {
    return Objects.hash(classID); // Use classID for hash code
}

}
