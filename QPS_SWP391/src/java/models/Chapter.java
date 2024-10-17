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
    private int chapterId;
    private int subjectId;
    private String chapterName;
    public Chapter(int chapterId, int subjectId, String chapterName) {
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }

       public Chapter() {
        // No-argument constructor
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
    
    private List<Chapter> chapters; // To hold the list of questions
    private int totalCount; // To hold the total count of questions

    public List<Chapter> getChapters() {
        return chapters;
    }
        public void setChapters(List<Chapter> Chapters) {
        this.chapters = chapters;
    }
          public int getTotalCount() {
        return totalCount;
    }
}
