package dal;

import models.Test;
import models.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Option;
import models.TestResult;

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
        try ( PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setString(1, "%" + testName + "%");
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countQuestionsInTest(int testId) {
        int questionCount = 0;
        xSql = "SELECT COUNT(*) AS QuestionCount FROM Test_Questions WHERE TestID = ?";

        try ( PreparedStatement ps = con.prepareStatement(xSql)) {
            ps.setInt(1, testId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    questionCount = rs.getInt("QuestionCount");
                }
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
        String sql = "SELECT * FROM Tests WHERE TestID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, testId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Test test = new Test();
                test.setTestID(rs.getInt("TestID"));
                test.setSubjectId(rs.getInt("SubjectID"));
                test.setTestName(rs.getString("TestName"));
                test.setDuration(rs.getInt("Duration"));
                test.setClassId(rs.getInt("ClassID"));
                test.setQuestionTypeId(rs.getInt("QuestionTypeID"));
                return test;
            }
        } catch (SQLException e) {
            System.out.println("getTestById: " + e.getMessage());
        }
        return null;
    }

    public List<Question> getQuestionsByTestId(int testId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT q.*, qt.QuestionTypeName "
                + "FROM Questions q "
                + "JOIN Test_Questions tq ON q.QuestionID = tq.QuestionID "
                + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
                + "WHERE tq.TestID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, testId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionID(rs.getInt("QuestionID"));
                q.setSubjectId(rs.getInt("SubjectID"));
                q.setChapterId(rs.getInt("ChapterID"));
                q.setQuestionTypeId(rs.getInt("QuestionTypeID"));
                q.setQuestion(rs.getString("Question"));
                q.setQuestionTypeName(rs.getString("QuestionTypeName"));
                questions.add(q);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
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

    public void updateTest(Test test) {
        String sql = "UPDATE Tests SET testName = ?, duration = ?, classId = ?, subjectId = ? WHERE testId = ?";
        try ( PreparedStatement pstmt = con.prepareStatement(sql)) {
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

    public List<Question> getQuestionsByTestId2(int testId) {
        List<Question> questions = new ArrayList<>();

        String query = "SELECT q.QuestionID, q.SubjectID, q.ChapterID, q.QuestionTypeID, q.Question, "
                + "qt.QuestionTypeName FROM Questions q "
                + "JOIN Test_Questions tq ON q.QuestionID = tq.QuestionID "
                + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
                + "WHERE tq.TestID = ?";

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, testId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int questionID = resultSet.getInt("QuestionID");
                int subjectId = resultSet.getInt("SubjectID");
                int chapterId = resultSet.getInt("ChapterID");
                int questionTypeId = resultSet.getInt("QuestionTypeID");
                String questionText = resultSet.getString("Question");
                String questionTypeName = resultSet.getString("QuestionTypeName");

                Question question = new Question(questionID, subjectId, chapterId, questionText, questionTypeId, questionTypeName);
                question.setOptions(getOptionsByQuestionId(questionID)); // Lấy các tùy chọn cho câu hỏi

                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<Option> getOptionsByQuestionId(int questionId) {
        List<Option> options = new ArrayList<>();

        String query = "SELECT o.OptionID, o.QuestionID, o.OptionText, o.IsCorrect "
                + "FROM Options o WHERE o.QuestionID = ?";

        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int optionId = resultSet.getInt("OptionID");
                String optionText = resultSet.getString("OptionText");
                int isCorrect = resultSet.getInt("IsCorrect");

                Option option = new Option(questionId, optionId, optionText, isCorrect);
                options.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    public boolean isCorrectOption(int questionId, int optionId) {
        String query = "SELECT IsCorrect FROM Options WHERE QuestionID = ? AND OptionID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, questionId);
            statement.setInt(2, optionId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("IsCorrect") == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void saveTestResult(int studentId, int testId, double score) throws SQLException {
        String sql = "INSERT INTO TestResults (student_id, test_id, score) VALUES (?, ?, ?)";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, testId);
            statement.setDouble(3, score);
            statement.executeUpdate();
        }
    }

    public List<TestResult> getTestHistory(int userId) {
        List<TestResult> testHistory = new ArrayList<>();
        String sql = "SELECT t.TestName, r.score FROM Tests t "
                + "JOIN TestResults r ON t.TestID = r.test_id "
                + "WHERE r.student_id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TestResult result = new TestResult();
                result.setTestName(rs.getString("testName"));
                result.setScore(rs.getInt("score"));
                testHistory.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testHistory;
    }

    public void deleteTest(int testId) {
        String sql = "DELETE FROM Tests WHERE testId = ?";
        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, testId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
