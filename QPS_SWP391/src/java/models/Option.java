
package models;

public class Option {

    private int questionID;
    private int optionId;
    private String optionText;
    private int isCorrect;

    public Option() {
    }

    public Option(int questionID, int optionId, String optionText, int isCorrect) {
        this.questionID = questionID;
        this.optionId = optionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
      
}
    
