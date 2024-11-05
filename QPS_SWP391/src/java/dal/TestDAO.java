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
        public int countAllTests() {
        String xSql = "SELECT COUNT(*) FROM Tests";
        int count = 0;
        try (PreparedStatement ps = con.prepareStatement(xSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countTestsByName(String testName) {
        String xSql = "SELECT COUNT(*) FROM Tests WHERE TestName LIKE ?";
        int count = 0;
        try (PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setString(1, "%" + testName + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countQuestionsInTest(int testId) {
        int questionCount = 0;
        String xSql = "SELECT COUNT(*) AS QuestionCount FROM Test_Questions WHERE TestID = ?";

        try (PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    questionCount = rs.getInt("QuestionCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questionCount;
    }

    public List<Test> getTestsBySubjectId(int subjectId) {
        String xSql = "SELECT *, (SELECT COUNT(*) FROM TestQuestions WHERE TestQuestions.testID = Tests.TestID) AS questionCount FROM Tests WHERE subjectId = ?";
        List<Test> testList = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setInt(1, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int testID = rs.getInt("testID");
                    String testName = rs.getString("testName");
                    int duration = rs.getInt("duration");
                    int classID = rs.getInt("classID");
                    int questionCount = rs.getInt("questionCount");
                    testList.add(new Test(testID, subjectId, testName, duration, classID, questionCount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testList;
    }

    public List<Question> getQuestionsByTestId(int testId, int subjectId) {
        String xSql = "SELECT q.*, CASE WHEN tq.testID IS NOT NULL THEN 1 ELSE 0 END AS isInTest "
                + "FROM Questions q LEFT JOIN TestQuestions tq ON q.QuestionID = tq.QuestionID AND tq.testID = ? "
                + "WHERE q.subjectId = ?";
        List<Question> qlist = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setInt(1, testId);
            ps.setInt(2, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int xQuestionID = rs.getInt("QuestionID");
                    int xChapterId = rs.getInt("chapterId");
                    String xQuestion = rs.getString("Question");
                    int xQuestionTypeId = rs.getInt("QuestionTypeId");
                    String xQuestionTypeName = rs.getString("QuestionTypeName");
                    boolean isInTest = rs.getInt("isInTest") == 1;
                    qlist.add(new Question(xQuestionID, subjectId, xChapterId, xQuestion, xQuestionTypeId, xQuestionTypeName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qlist;
    }

public List<Test> getAllTests(String searchQuery, String sortBy, String sortOrder) {
    List<Test> tests = new ArrayList<>();
    String sql = "SELECT t.TestID, t.TestName, t.Duration, t.ClassID, COUNT(tq.QuestionID) AS questionCount " +
                 "FROM Tests t LEFT JOIN Test_Questions tq ON t.TestID = tq.TestID " +
                 "WHERE t.TestName LIKE ? " + 
                 "GROUP BY t.TestID, t.TestName, t.Duration, t.ClassID";

    if (sortBy != null && sortOrder != null) {
        sql += " ORDER BY " + sortBy + " " + sortOrder;
    }

    System.out.println("Executing SQL: " + sql); 

    try (
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, "%" + searchQuery + "%"); 

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Test test = new Test();
            test.setTestID(rs.getInt("TestID"));
            test.setTestName(rs.getString("TestName"));
            test.setDuration(rs.getInt("Duration"));
            test.setClassId(rs.getInt("ClassID"));
            test.setQuestionCount(rs.getInt("questionCount"));
            tests.add(test);
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    System.out.println("Number of tests found: " + tests.size()); 
    return tests;
}

        public List<Test> getTestsPaginated(int page, int testsPerPage, String sortBy, String sortOrder) {
        List<Test> testList = new ArrayList<>();
        String sortColumn = "TestID";
        if ("questionCount".equals(sortBy)) {
            sortColumn = "(SELECT COUNT(*) FROM TestQuestions WHERE TestQuestions.testID = Tests.TestID)";
        }
        String order = "ASC".equalsIgnoreCase(sortOrder) ? "ASC" : "DESC";
        int offset = (page - 1) * testsPerPage;
        String xSql = "SELECT * FROM Tests ORDER BY " + sortColumn + " " + order + " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setInt(1, testsPerPage);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Test test = new Test();
                    test.setTestID(rs.getInt("TestID"));
                    test.setTestName(rs.getString("TestName"));
                    test.setDuration(rs.getInt("Duration"));
                    test.setSubjectId(rs.getInt("SubjectID"));
                    test.setClassId(rs.getInt("ClassID"));
                    testList.add(test);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testList;
    }

    public boolean addTest(Test test) {
        String sql = "INSERT INTO Tests (SubjectID, TestName, Duration, ClassID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, test.getSubjectId());
            ps.setString(2, test.getTestName());
            ps.setInt(3, test.getDuration());
            ps.setInt(4, test.getClassId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTestId(String testName, int subjectId, int classId, int duration) {
        int testId = -1;
        String sql = "SELECT TestID FROM Tests WHERE testName = ? AND subjectId = ? AND classId = ? AND duration = ? ORDER BY TestID DESC LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, testName);
            ps.setInt(2, subjectId);
            ps.setInt(3, classId);
            ps.setInt(4, duration);
            try (ResultSet rs = ps.executeQuery()) {
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

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
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
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question();
                    question.setQuestionID(rs.getInt("questionID"));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    
    public void updateTest(Test test) {
        String sql = "UPDATE Tests SET testName = ?, duration = ?, classId = ?, subjectId = ? WHERE testId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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
