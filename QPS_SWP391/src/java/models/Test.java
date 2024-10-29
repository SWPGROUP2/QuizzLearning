package models;

public class Test {
    private int testID;
    private int subjectId;
    private String testName;
    private String description;
    private int questionCount;

    public Test(int testID, int subjectId, String testName, String description) {
        this.testID = testID;
        this.subjectId = subjectId;
        this.testName = testName;
        this.description = description;
    }

    public Test() {
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}