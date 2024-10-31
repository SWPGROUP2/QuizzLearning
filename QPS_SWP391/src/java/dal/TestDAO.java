package dal;

import models.Test;
import models.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAO extends MyDAO {

    public List<Test> getTestsBySubjectId(int subjectId) {
        xSql = "SELECT * FROM Tests WHERE subjectId = ?";
        List<Test> testList = new ArrayList<>();
        int testID;
        String testName;
        int duration;
        int classID;
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                testID = rs.getInt("testID");
                testName = rs.getString("testName");
                duration = rs.getInt("duration");
                classID = rs.getInt("classID");
                testList.add(new Test(testID, subjectId, testName, duration, classID));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }

    public List<Question> getQuestionsByTestId(int testId, int subjectId) {
        xSql = "SELECT q.*, CASE WHEN tq.testID IS NOT NULL THEN 1 ELSE 0 END AS isInTest "
                + "FROM Questions q LEFT JOIN TestQuestions tq ON q.QuestionID = tq.QuestionID AND tq.testID = ? "
                + "WHERE q.subjectId = ?";
        List<Question> qlist = new ArrayList<>();
        int xQuestionID;
        int xChapterId;
        String xQuestion;
        int xQuestionTypeId;
        String xQuestionTypeName;
        boolean isInTest;
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, testId);
            ps.setInt(2, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                xQuestionID = rs.getInt("QuestionID");
                xChapterId = rs.getInt("chapterId");
                xQuestion = rs.getString("Question");
                xQuestionTypeId = rs.getInt("QuestionTypeId");
                xQuestionTypeName = rs.getString("QuestionTypeName");
                isInTest = rs.getInt("isInTest") == 1;
                qlist.add(new Question(xQuestionID, subjectId, xChapterId, xQuestion, xQuestionTypeId, xQuestionTypeName));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qlist;
    }

    public List<Test> getAllTests() {
        List<Test> testList = new ArrayList<>();
        xSql = "SELECT * FROM Tests";
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Test test = new Test();
                test.setTestID(rs.getInt("TestID"));
                test.setTestName(rs.getString("TestName"));
                test.setDuration(rs.getInt("Duration"));
                test.setSubjectId(rs.getInt("SubjectID"));
                test.setClassId(rs.getInt("ClassID"));
                testList.add(test);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }

    public List<Test> searchTestsByName(String testName, int page, int testsPerPage, String sortBy, String sortOrder) {
        List<Test> testList = new ArrayList<>();

        String sortColumn = "TestID";
        if ("questionCount".equals(sortBy)) {
            sortColumn = "(SELECT COUNT(*) FROM TestQuestions WHERE TestQuestions.testID = Tests.TestID)";
        }

        String order = "ASC".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";

        int offset = (page - 1) * testsPerPage;

        xSql = "SELECT * FROM Tests WHERE TestName LIKE ? ORDER BY " + sortColumn + " " + order + " LIMIT ? OFFSET ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + testName + "%");
            ps.setInt(2, testsPerPage);
            ps.setInt(3, offset);
            rs = ps.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                test.setTestID(rs.getInt("TestID"));
                test.setTestName(rs.getString("TestName"));
                test.setDuration(rs.getInt("Duration"));
                test.setSubjectId(rs.getInt("SubjectID"));
                test.setClassId(rs.getInt("ClassID"));
                testList.add(test);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }

    public List<Test> getTestsPaginated(int page, int testsPerPage, String sortBy, String sortOrder) {
        List<Test> testList = new ArrayList<>();
        String sortColumn = "TestID";
        if ("questionCount".equals(sortBy)) {
            sortColumn = "(SELECT COUNT(*) FROM TestQuestions WHERE TestQuestions.testID = Tests.TestID)";
        }
        String order = "ASC".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";

        int offset = (page - 1) * testsPerPage;
        xSql = "SELECT * FROM Tests ORDER BY " + sortColumn + " " + order + " LIMIT ? OFFSET ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, testsPerPage);
            ps.setInt(2, offset);
            rs = ps.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                test.setTestID(rs.getInt("TestID"));
                test.setTestName(rs.getString("TestName"));
                test.setDuration(rs.getInt("Duration"));
                test.setSubjectId(rs.getInt("SubjectID"));
                test.setClassId(rs.getInt("ClassID"));
                testList.add(test);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }

    public int countAllTests() {
        xSql = "SELECT COUNT(*) FROM Tests";
        int count = 0;
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countTestsByName(String testName) {
        xSql = "SELECT COUNT(*) FROM Tests WHERE TestName LIKE ?";
        int count = 0;
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + testName + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countQuestionsInTest(int testId) {
        int questionCount = 0;
        xSql = "SELECT COUNT(*) AS QuestionCount FROM Test_Questions WHERE TestID = ?";

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, testId);
            rs = ps.executeQuery();
            if (rs.next()) {
                questionCount = rs.getInt("QuestionCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionCount;
    }

    public boolean addTest(Test test) {
        String sql = "INSERT INTO Tests (SubjectID, TestName, Duration, ClassID) VALUES (?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, test.getSubjectId());
            ps.setString(2, test.getTestName());
            ps.setInt(3, test.getDuration());
            ps.setInt(4, test.getClassId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {

            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getTestId(String testName, int subjectId, int classId, int duration) {
        int testId = -1;
        String sql = "SELECT TestID FROM Tests WHERE testName = ? AND subjectId = ? AND classId = ? AND duration = ? ORDER BY TestID DESC LIMIT 1";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, testName);
            ps.setInt(2, subjectId);
            ps.setInt(3, classId);
            ps.setInt(4, duration);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    testId = rs.getInt("TestID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return testId;
    }

    public Test getTestById(int testId) {
        Test test = null;
        String sql = "SELECT * FROM Tests WHERE TestID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, testId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    test = new Test();
                    test.setTestID(rs.getInt("TestID"));
                    test.setSubjectId(rs.getInt("subjectId"));
                    test.setClassId(rs.getInt("classId"));
                    test.setTestName(rs.getString("testName"));
                    test.setDuration(rs.getInt("duration"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    public List<Question> getQuestionsByTestId(int testId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Test_Questions WHERE TestID = ?";
        try {PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, testId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionID(rs.getInt("questionID"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    
    public void updateTest(Test test) {
    String sql = "UPDATE Tests SET testName = ?, duration = ?, classId = ?, subjectId = ? WHERE testId = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

        pstmt.setString(1, test.getTestName());
        pstmt.setInt(2, test.getDuration());
        pstmt.setInt(3, test.getClassId());
        pstmt.setInt(4, test.getSubjectId());
        pstmt.setInt(5, test.getTestId());

        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    

}
