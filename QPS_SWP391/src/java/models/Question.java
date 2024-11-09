package models;

import java.util.List;
import models.Option;

public class Question {

    private int questionID;
    private int subjectId;
    private int chapterId;
    private int questionTypeId;
    private String question;
    private String questionTypeName;
    private List<Option> options;
    private String subjectName;

    // No-argument constructor if needed
    public Question() {
    }

    public Question(int questionID, int chapterId, String question, String questionTypeName, String subjectName) {
        this.questionID = questionID;
        this.chapterId = chapterId;
        this.question = question;
        this.questionTypeName = questionTypeName;
        this.subjectName = subjectName;  
    }
public Question(int questionID, int subjectId, int chapterId, String question, int questionTypeId, String questionTypeName, String subjectName) {
    this.questionID = questionID;
    this.subjectId = subjectId;
    this.chapterId = chapterId;
    this.question = question;
    this.questionTypeId = questionTypeId;
    this.questionTypeName = questionTypeName;
    this.subjectName = subjectName;
}
public Question(int questionID, int subjectId, int chapterId, String question, int questionTypeId, String questionTypeName) {
    this.questionID = questionID;
    this.subjectId = subjectId;
    this.chapterId = chapterId;
    this.question = question;
    this.questionTypeId = questionTypeId;
    this.questionTypeName = questionTypeName;
}
public Question(int questionID, int subjectId, int chapterId, String question, String subjectName,int questionTypeId) {
    this.questionID = questionID;
    this.subjectId = subjectId;
    this.chapterId = chapterId;
    this.question = question;
        this.subjectName = subjectName;
    this.questionTypeId = questionTypeId;

}
    

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getQuestionTypeName() {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName) {
        this.questionTypeName = questionTypeName;
    }

    public List<Option> getOptions() { 
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
