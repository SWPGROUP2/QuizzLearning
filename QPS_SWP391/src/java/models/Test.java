package models;

public class Test {
    private int testId;
    private int subjectId;
    private String testName;
    private int duration;
    private int classId;
    private int questionTypeId;
    private int questionCount;
    private String subjectName;
        private int classID;
    private String className;
    

    public Test(int testId, int subjectId, String testName, int duration, int classId, int questionTypeId, int questionCount, String subjectName) {
        this.testId = testId;
        this.subjectId = subjectId;
        this.testName = testName;
        this.duration = duration;
        this.classId = classId;
        this.questionTypeId = questionTypeId;
        this.questionCount = questionCount;
        this.subjectName = subjectName;
    }
    
        public Test(int testId, int subjectId, String testName, int duration, int classId, String subjectName) {
        this.testId = testId;
        this.subjectId = subjectId;
        this.testName = testName;
        this.duration = duration;
        this.classId = classId;
        this.subjectName = subjectName;
    }

    public Test(int testId, int subjectId, String testName, int duration, int classId, int questionCount) {
        this.testId = testId;
        this.subjectId = subjectId;
        this.testName = testName;
        this.duration = duration;
        this.classId = classId;
        this.questionCount = questionCount;
    }

    public Test(int testId, String testName, int subjectId, String subjectName, int classId, int duration, String className) {
        this.testId = testId;
        this.testName = testName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.classID = classId;
        this.duration = duration;
        this.className = className;
    }

    public int getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }
    
    public Test(int testId, int subjectId, String testName, int duration, int classId) {
        this.testId = testId;
        this.subjectId = subjectId;
        this.testName = testName;
        this.duration = duration;
        this.classId = classId;
    }
    
        public Test() {
    }

    public int getTestId() {
        return testId;
    }

    public void setTestID(int testId) {
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
    
    public int getQuestionCount() {
        return questionCount;
    }
     
    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
    
        public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}