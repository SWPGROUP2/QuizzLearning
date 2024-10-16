package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Question;

public class QuestionDAO extends MyDAO {

    // Lấy danh sách câu hỏi dựa trên subjectId
    public List<Question> getQuestionsBySubjectId(int subjectId) {
        xSql = "SELECT * FROM Questions WHERE subjectId = ?";
        List<Question> qlist = new ArrayList<>();

        int xQuestionID;
        String xQuestion;
        String xDefinition;

        try {
            // Chuẩn bị câu lệnh SQL
            ps = con.prepareStatement(xSql);
            ps.setInt(1, subjectId); // Gán subjectId vào câu truy vấn

            // Thực thi câu lệnh
            rs = ps.executeQuery();

            // Duyệt kết quả truy vấn
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                xQuestionID = rs.getInt("QuestionID");
                xQuestion = rs.getString("Question");
                xDefinition = rs.getString("Definition");

                // Thêm đối tượng Question vào danh sách
                qlist.add(new Question(xQuestionID, subjectId, xQuestion, xDefinition));
            }

            // Đóng ResultSet và PreparedStatement
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qlist;
    }

    public void addQuestion(int subjectId, String questionText, String definition) {
        String sql = "INSERT INTO Questions (subjectId, Question, Definition) VALUES (?, ?, ?)";

        // Using try-with-resources to ensure the resources are closed
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            stmt.setString(2, questionText);
            stmt.setString(3, definition);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(int questionId, String question, String definition) throws SQLException {
        String sql = "UPDATE Questions SET question = ?, definition = ? WHERE questionId = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, question);
            stmt.setString(2, definition);
            stmt.setInt(3, questionId);
            stmt.executeUpdate();
        }
    }

    public boolean deleteQuestion(int questionID) {
        String deleteQuery = "DELETE FROM questions WHERE questionID = ?";

        try ( PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, questionID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Question getQuestionById(int questionId) throws SQLException {
        Question question = null;
        String sql = "SELECT * FROM Questions WHERE QuestionID = ?";

        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                question = new Question();
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setSubjectId(rs.getInt("subjectId"));
                question.setQuestion(rs.getString("Question"));
                question.setDefinition(rs.getString("Definition"));
            }
        }
        return question;
    }

    public boolean questionExists(int subjectId, String questionText) {
        String query = "SELECT COUNT(*) FROM questions WHERE subject_id = ? AND question_text = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            stmt.setString(2, questionText);

            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
