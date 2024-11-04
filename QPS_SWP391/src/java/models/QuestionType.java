/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author LENOVO
 */
public class QuestionType {
    private int QuestionTypeId;
    private String QuestionTypeName;

    public QuestionType() {
    }

    public QuestionType(int QuestionTypeId, String QuestionTypeName) {
        this.QuestionTypeId = QuestionTypeId;
        this.QuestionTypeName = QuestionTypeName;
    }
    
   

    public int getQuestionTypeId() {
        return QuestionTypeId;
    }

    public void setQuestionTypeId(int QuestionTypeId) {
        this.QuestionTypeId = QuestionTypeId;
    }

    public String getQuestionTypeName() {
        return QuestionTypeName;
    }

    public void setQuestionTypeName(String QuestionTypeName) {
        this.QuestionTypeName = QuestionTypeName;
    }
    
    

}
