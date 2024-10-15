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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, subjectId);
            stmt.setString(2, questionText);
            stmt.setString(3, definition);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    public boolean deleteQuestion(int questionID) {
        String deleteQuery = "DELETE FROM questions WHERE questionID = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
       
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
        
        // Query to fetch the questions with pagination
        String query = "SELECT * FROM questions WHERE subjectId = ? LIMIT ? OFFSET ?";
        // Query to count total questions
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
                                                  rs.getString("question"),
                                                  rs.getString("definition"));
                questions.add(question); 
            }
            result.setQuestions(questions);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return result; 
    }

   
}
