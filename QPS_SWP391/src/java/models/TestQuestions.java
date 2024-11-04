/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class TestQuestions {
    private int testQuestionsId; 
    private int testId;           
    private int questionId; 

    public TestQuestions() {
    }

    public TestQuestions(int testQuestionsId, int testId, int questionId) {
        this.testQuestionsId = testQuestionsId;
        this.testId = testId;
        this.questionId = questionId;
    }

    public int getTestQuestionsId() {
        return testQuestionsId;
    }

    public void setTestQuestionsId(int testQuestionsId) {
        this.testQuestionsId = testQuestionsId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    
    
}
