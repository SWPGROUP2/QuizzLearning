package models;

public class Test {
    private int testID;
    private int subjectId;
    private String testName;
    private String description;

    public Test(int testID, int subjectId, String testName, String description) {
        this.testID = testID;
        this.subjectId = subjectId;
        this.testName = testName;
        this.description = description;
    }

    // Constructor mặc định (nếu cần)
    public Test() {
        // No-argument constructor
    }

    // Getter và Setter cho testID
    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    // Getter và Setter cho subjectId
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    // Getter và Setter cho testName
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    // Getter và Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}