package models;

public class Test {
    private int testId;
    private int subjectId;
    private String testName;
    private int duration;
    private int classId;

    public Test() {
    }

    public Test(int testId, int subjectId, String testName, int duration, int classId) {
        this.testId = testId;
        this.subjectId = subjectId;
        this.testName = testName;
        this.duration = duration;
        this.classId = classId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    
}