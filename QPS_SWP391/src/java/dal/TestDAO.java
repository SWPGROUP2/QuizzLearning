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

    public List<Test> getAllTestTeacher(int userId, String subjectId, String classId, String searchQuery, int page, int testsPerPage) {
        List<Test> tests = new ArrayList<>();

            StringBuilder query = new StringBuilder("SELECT t.TestID, t.TestName, t.SubjectID, s.SubjectName, t.ClassID,t.Duration, c.ClassName "
                + "FROM Tests t "
                + "JOIN TeacherSubjects ts ON t.SubjectID = ts.SubjectID "
                + "JOIN Subject s ON t.SubjectID = s.SubjectID "
                + "JOIN Class c ON t.ClassID = c.ClassID "
                + "WHERE ts.UserID = ?");

        if (subjectId != null && !subjectId.isEmpty()) {
            query.append(" AND t.SubjectID = ?");
        }
        if (classId != null && !classId.isEmpty()) {
            query.append(" AND t.ClassID = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query.append(" AND t.TestName LIKE ?");
        }

        query.append(" LIMIT ? OFFSET ?");

        try ( PreparedStatement stmt = con.prepareStatement(query.toString())) {
            int index = 1;
            stmt.setInt(index++, userId);

            if (subjectId != null && !subjectId.isEmpty()) {
                stmt.setString(index++, subjectId);
            }
            if (classId != null && !classId.isEmpty()) {
                stmt.setString(index++, classId);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                stmt.setString(index++, "%" + searchQuery.trim() + "%");
            }

            stmt.setInt(index++, testsPerPage);
            stmt.setInt(index, (page - 1) * testsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Test test = new Test(
                        rs.getInt("TestID"),
                        rs.getString("TestName"),
                        rs.getInt("SubjectID"),
                        rs.getString("SubjectName"),
                        rs.getInt("ClassID"),
                        rs.getInt("Duration"),
                        rs.getString("ClassName")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

public List<Test> getAllTestStudent(int userId, String subjectId, String classId, String searchQuery, int currentPage, int testsPerPage) {
    List<Test> tests = new ArrayList<>();

    StringBuilder query = new StringBuilder("SELECT "
            + "t.TestID, "
            + "t.SubjectID, "
            + "t.TestName, "
            + "t.Duration, "
            + "t.ClassID, "
            + "t.QuestionTypeID, "
            + "s.SubjectName, "
            + "qt.QuestionTypeName, "
            + "c.ClassName "
            + "FROM Tests t "
            + "JOIN Class c ON t.ClassID = c.ClassID "
            + "JOIN Users u ON u.UserID = c.UserID "
            + "JOIN Subject s ON t.SubjectID = s.SubjectID "
            + "JOIN QuestionType qt ON t.QuestionTypeID = qt.QuestionTypeID "
            + "WHERE u.UserID = ? "
            + "AND u.RoleID = 1");  // Ensure we only select tests for students (roleID = 1)

    if (subjectId != null && !subjectId.isEmpty()) {
        query.append(" AND t.SubjectID = ?");
    }
    if (classId != null && !classId.isEmpty()) {
        query.append(" AND t.ClassID = ?");
    }
    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        query.append(" AND t.TestName LIKE ?");
    }

    // Pagination
    query.append(" LIMIT ? OFFSET ?");

    try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
        int index = 1;
        stmt.setInt(index++, userId);  // Set the user ID for the logged-in student

        if (subjectId != null && !subjectId.isEmpty()) {
            stmt.setString(index++, subjectId);  // Set subject filter
        }
        if (classId != null && !classId.isEmpty()) {
            stmt.setString(index++, classId);  // Set class filter
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            stmt.setString(index++, "%" + searchQuery.trim() + "%");  // Set search query filter for TestName
        }

        // Set pagination parameters
        stmt.setInt(index++, testsPerPage);  // Set limit (tests per page)
        stmt.setInt(index, (currentPage - 1) * testsPerPage);  // Set offset based on current page and tests per page

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Test test = new Test(
                        rs.getInt("TestID"),
                        rs.getString("TestName"),
                        rs.getInt("SubjectID"),
                        rs.getString("SubjectName"),
                        rs.getInt("ClassID"),
                        rs.getInt("Duration"),
                        rs.getString("ClassName")
                );
                tests.add(test);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Handle exceptions
    }

    return tests;  // Return the list of tests
}

    public int countTotalTests(int userId, String subjectId, String classId, String searchQuery) {
        int count = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) AS total FROM Tests t "
                + "JOIN TeacherSubjects ts ON t.SubjectID = ts.SubjectID "
                + "WHERE ts.UserID = ?");

        if (subjectId != null && !subjectId.isEmpty()) {
            query.append(" AND t.SubjectID = ?");
        }
        if (classId != null && !classId.isEmpty()) {
            query.append(" AND t.ClassID = ?");
        }
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query.append(" AND t.TestName LIKE ?");
        }

        try ( PreparedStatement stmt = con.prepareStatement(query.toString())) {
            int index = 1;
            stmt.setInt(index++, userId);
            if (subjectId != null && !subjectId.isEmpty()) {
                stmt.setString(index++, subjectId);
            }
            if (classId != null && !classId.isEmpty()) {
                stmt.setString(index++, classId);
            }
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                stmt.setString(index++, "%" + searchQuery.trim() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
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
        String sql = "INSERT INTO Tests (SubjectID, TestName, Duration, ClassID, test_startTime, test_endTime, test_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, test.getSubjectId());
            ps.setString(2, test.getTestName());
            ps.setInt(3, test.getDuration());
            ps.setInt(4, test.getClassId());
            ps.setTimestamp(5, test.getTestStartTime());
            ps.setTimestamp(6, test.getTestEndTime());
            ps.setInt(7, 1);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
                    test.setTestName(rs.getString("TestName"));
                    test.setDuration(rs.getInt("Duration"));
                    test.setClassId(rs.getInt("ClassID"));
                    test.setSubjectId(rs.getInt("SubjectID"));
                    test.setTestStartTime(rs.getTimestamp("test_startTime"));
                    test.setTestEndTime(rs.getTimestamp("test_endTime"));
                    test.setStatus(rs.getInt("test_status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return test;
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

    public void updateTest(Test test) {
        String sql = "UPDATE Tests SET TestName = ?, Duration = ?, ClassID = ?, test_startTime = ?, test_endTime = ? WHERE TestID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, test.getTestName());
            ps.setInt(2, test.getDuration());
            ps.setInt(3, test.getClassId());
            ps.setTimestamp(4, test.getTestStartTime());
            ps.setTimestamp(5, test.getTestEndTime());
            ps.setInt(6, test.getTestId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                question.setOptions(getOptionsByQuestionId(questionID));

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
}
