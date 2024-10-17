package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                qlist.add(new Question(xQuestionID, subjectId,  xChapterId, xQuestion, xQuestionTypeId));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return qlist;
    }

    public void addQuestion(int subjectId, String questionText, String definition) {
        String sql = "INSERT INTO Questions (subjectId, Question, Definition) VALUES (?, ?, ?)";
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
                question.setChapterId(rs.getInt("chapterId"));
                question.setQuestion(rs.getString("Question"));
                question.setQuestionTypeId(rs.getInt("QuestionTypeId"));
            }
        }
        return question;
    }

    public boolean questionExists(int subjectId, String questionText) {
        String query = "SELECT COUNT(*) FROM questions WHERE subjectId = ? AND question = ?";
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

    public Question getQuestionsWithCount(int subjectId, int currentPage, int pageSize) {
        Question result = new Question();
        List<Question> questions = new ArrayList<>();
       
        String query = "SELECT * FROM questions WHERE subjectId = ? LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM questions WHERE subjectId = ?";

        try (
             PreparedStatement countStmt = connection.prepareStatement(countQuery);
             PreparedStatement queryStmt = connection.prepareStatement(query)) {
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
                Question question = new Question( rs.getInt("questionID"),
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
}
