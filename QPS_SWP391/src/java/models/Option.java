/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author LENOVO
 */
public class Option {
     private int OptionId;
    private int QuestionId;
    private String OptionText;
    private Boolean IsCorrect;

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int OptionId) {
        this.OptionId = OptionId;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int QuestionId) {
        this.QuestionId = QuestionId;
    }

    public String getOptionText() {
        return OptionText;
    }

    public void setOptionText(String OptionText) {
        this.OptionText = OptionText;
    }

    public Boolean getIsCorrect() {
        return IsCorrect;
    }

    public void setIsCorrect(Boolean IsCorrect) {
        this.IsCorrect = IsCorrect;
    }
    
    
    
    
}

