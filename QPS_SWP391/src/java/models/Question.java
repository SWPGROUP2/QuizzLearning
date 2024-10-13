package models;

public class Question {
    private int questionID;
    private int subjectId;
    private String question;
    private String definition;

    // Constructor có tham số
    public Question(int questionID, int subjectId, String question, String definition) {
        this.questionID = questionID;
        this.subjectId = subjectId;
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
}