package models;

import java.util.List;

public class Question {
    private int questionID;
    private int subjectId;
    private int chapterId;
    private String question;
    private String definition;

    public Question(int questionID, int subjectId, int chapterId, String question, String definition) {
        this.questionID = questionID;
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.question = question;
        this.definition = definition;
    }

    // Constructor mặc định (nếu cần)
    public Question() {
        // No-argument constructor
    }

    // Các getter và setter cho các thuộc tính (nếu cần)
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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
      private List<Question> questions; // To hold the list of questions
    private int totalCount; // To hold the total count of questions

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}