package models;

import dal.QuestionDAO;
import java.util.List;

public class Question {
    private int questionID;
    private int subjectId;
    private int chapterId;
    private int questionTypeId;
    private String question;

    // No-argument constructor if needed
    public Question() {
    }

    // Parameterized constructor
    public Question(int questionID, int subjectId, int chapterId, String question, int questionTypeId) {
        this.questionID = questionID;
        this.subjectId = subjectId;
        this.chapterId = chapterId;
        this.question = question;
        this.questionTypeId = questionTypeId;
    }

    // Getters and Setters
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

    // Method to retrieve a list of questions for a given subjectId
    public static Question getQuestionsWithCount(int subjectId, int currentPage, int pageSize) {
        QuestionDAO questionDAO = new QuestionDAO(); // Create a new instance of QuestionDAO
        return questionDAO.getQuestionsWithCount(subjectId, currentPage, pageSize); // Call the DAO method
    }
}
