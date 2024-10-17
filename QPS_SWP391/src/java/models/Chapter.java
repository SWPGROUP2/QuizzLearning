/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class Chapter {
    private int chapterEntryId;
    private int subjectId;
    private int chapterId;
    private String chapterName;
    public Chapter(int chapterEntryId, int subjectId, int chapterId, String chapterName) {
        this.chapterEntryId = chapterEntryId;
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }

    public int getChapterEntryId() {
        return chapterEntryId;
    }

    public void setChapterEntryId(int chapterEntryId) {
        this.chapterEntryId = chapterEntryId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    
}
