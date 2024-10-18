package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Option;
import models.Question;

public class QuestionDAO extends MyDAO {

    public List<Question> getQuestionsBySubjectId(int subjectId) {
        xSql = "SELECT * FROM Questions WHERE subjectId = ?";
        List<Question> qlist = new ArrayList<>();
        int xQuestionID;
        int xChapterId;
        String xQuestion;
        int xQuestionTypeId;

        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                xQuestionID = rs.getInt("QuestionID");
                xChapterId = rs.getInt("chapterId");
                xQuestion = rs.getString("Question");
                xQuestionTypeId = rs.getInt("QuestionTypeId");
                qlist.add(new Question(xQuestionID, subjectId, xChapterId, xQuestion, xQuestionTypeId));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qlist;
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

    public Question getQuestionsWithCount(int subjectId, int currentPage, int pageSize) {
        Question result = new Question();
        List<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM questions WHERE subjectId = ? LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM questions WHERE subjectId = ?";

        try (
                 PreparedStatement countStmt = connection.prepareStatement(countQuery);  PreparedStatement queryStmt = connection.prepareStatement(query)) {
            countStmt.setInt(1, subjectId);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                result.setTotalCount(countRs.getInt(1));
            }
            int offset = (currentPage - 1) * pageSize;
            queryStmt.setInt(1, subjectId);
            queryStmt.setInt(2, pageSize);
            queryStmt.setInt(3, offset);
            ResultSet rs = queryStmt.executeQuery();

            while (rs.next()) {
                Question question = new Question(rs.getInt("questionID"),
                        rs.getInt("subjectId"),
                        rs.getInt("chapterId"),
                        rs.getString("question"),
                        rs.getInt("questionTypeId"));
                questions.add(question);
            }
            result.setQuestions(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    
    public int addQuestion(Question question) {
        String sql = "INSERT INTO Questions (subjectId, chapterId, Question, questionTypeId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getSubjectId());
            ps.setInt(2, question.getChapterId());
            ps.setString(3, question.getQuestion());
            ps.setInt(4, question.getQuestionTypeId());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // Lấy questionId vừa được sinh
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
