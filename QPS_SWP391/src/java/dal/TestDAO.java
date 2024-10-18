package dal;

import models.Test;
import models.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestDAO extends MyDAO {

    // Lấy danh sách các test theo subjectId
    public List<Test> getTestsBySubjectId(int subjectId) {
        xSql = "SELECT * FROM Tests WHERE subjectId = ?";
        List<Test> testList = new ArrayList<>();
        int testID;
        String testName;
        String description;

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                testID = rs.getInt("testID");
                testName = rs.getString("testName");
                description = rs.getString("description");

                // Tạo đối tượng Test và thêm vào danh sách
                testList.add(new Test(testID, subjectId, testName, description));
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
                isInTest = rs.getInt("isInTest") == 1;

                // Thêm câu hỏi vào danh sách và đánh dấu câu hỏi đã có trong test
                qlist.add(new Question(xQuestionID, subjectId, xChapterId, xQuestion, xQuestionTypeId));
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
                test.setDescription(rs.getString("Description"));
                test.setSubjectId(rs.getInt("SubjectID"));
                testList.add(test);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testList;
    }

    public List<Test> searchTestsByName(String testName) {
        List<Test> testList = new ArrayList<>();
        xSql = "SELECT * FROM Tests WHERE TestName LIKE ?"; // Sử dụng LIKE cho tìm kiếm

        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, "%" + testName + "%"); // Thêm ký tự % để tìm kiếm chuỗi bất kỳ
            rs = ps.executeQuery();

            while (rs.next()) {
                Test test = new Test();
                test.setTestID(rs.getInt("TestID"));
                test.setTestName(rs.getString("TestName"));
                test.setDescription(rs.getString("Description"));
                test.setSubjectId(rs.getInt("SubjectID"));

                testList.add(test);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return testList;
    }
}
