package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    public List<Question> getQuestionsBySubjectIdQuestionTypeIdSorted(int subjectId, String sort, String sortOrder) {
    String sql = "SELECT * FROM Questions WHERE subjectId = ?";
    
    if ("questionType".equals(sort)) {
        sql += " ORDER BY QuestionTypeId " + ("desc".equals(sortOrder) ? "DESC" : "ASC");
    }
    
    List<Question> qlist = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, subjectId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            qlist.add(new Question(
                rs.getInt("QuestionID"),
                subjectId,
                rs.getInt("chapterId"),
                rs.getString("Question"),
                rs.getInt("QuestionTypeId")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return qlist;
    }
    
    public List<Question> getQuestionsBySubjectIdChapterIdSorted(int subjectId, String chapterId, String sort, String sortOrder) {
    String sql = "SELECT * FROM Questions WHERE subjectId = ?";
    if (chapterId != null && !chapterId.isEmpty()) {
        sql += " AND chapterId = ?";
    }
    if ("chapterId".equals(sort)) {
        sql += " ORDER BY chapterId " + ("desc".equals(sortOrder) ? "desc" : "asc");
    } else if ("questionTypeId".equals(sort)) {
        sql += " ORDER BY questionTypeId " + ("desc".equals(sortOrder) ? "desc" : "asc");
    }
    List<Question> qlist = new ArrayList<>();
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, subjectId);
        if (chapterId != null && !chapterId.isEmpty()) {
            ps.setString(2, chapterId);
        }     
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            qlist.add(new Question(
                rs.getInt("QuestionID"),
                subjectId,
                rs.getInt("chapterId"),
                rs.getString("Question"),
                rs.getInt("QuestionTypeId")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return qlist;
    }

    public void updateQuestion(Question question) {
            String sql = "UPDATE Questions SET subjectId = ?, chapterId = ?, question = ?, questionTypeId = ? WHERE questionID = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, question.getSubjectId());
                ps.setInt(2, question.getChapterId());
                ps.setString(3, question.getQuestion());
                ps.setInt(4, question.getQuestionTypeId());
                ps.setInt(5, question.getQuestionID());

                int rowsAffected = ps.executeUpdate();
                LOGGER.info("Update Question rows affected: " + rowsAffected);
            } catch (SQLException e) {
                LOGGER.severe("Error updating question: " + e.getMessage());
            }
        }

    public boolean deleteQuestion(int QuestionID) {
        String deleteQuery = "DELETE FROM Questions WHERE QuestionID = ?";

        try ( PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, QuestionID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Option> getOptionsByQuestionId(int questionId) throws SQLException {
    List<Option> options = new ArrayList<>();
    String sql = "SELECT * FROM Options WHERE QuestionID = ?";
    
    ps = con.prepareStatement(sql);
    ps.setInt(1, questionId);
    rs = ps.executeQuery();
    
    while (rs.next()) {
        int optionId = rs.getInt("OptionID");
        String optionText = rs.getString("OptionText");
        boolean isCorrect = rs.getBoolean("IsCorrect");
        options.add(new Option(optionId, questionId, optionText, isCorrect));
    }
    
    rs.close();
    ps.close();
    
    return options;
}
    
    public boolean questionExists(int subjectId, String questionText) {
        String query = "SELECT COUNT(*) FROM Questions WHERE subjectId = ? AND question = ?";
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

       
        String query = "SELECT * FROM Questions WHERE subjectId = ? LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM Questions WHERE subjectId = ?";


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

public Set<Integer> getUniqueChapters(int subjectId) {
    Set<Integer> chapterSet = new HashSet<>();

    // Correctly obtaining the database connection
      // Use this.getConnection() to get the connection
        String query = "SELECT DISTINCT chapterId FROM Questions WHERE subjectId = ?"; // Ensure table name is correct
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                chapterSet.add(result.getInt("chapterId")); // Add unique chapter IDs
            }
        }
    catch (SQLException e) { // Catch SQLException instead of a general Exception
        e.printStackTrace();
    }

    return chapterSet;
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
    
    public List<Question> getQuestionsByChapter(int subjectId, String chapterId) {
    List<Question> questions = new ArrayList<>();
    String sql = "SELECT * FROM Questions WHERE subjectId = ? AND chapterId = ?"; 

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, subjectId);
        stmt.setString(2, chapterId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
     
            Question question = new Question();
            question.setQuestionID(rs.getInt("questionID"));
            question.setSubjectId(rs.getInt("subjectId"));
            question.setChapterId(rs.getInt("chapterId"));
            question.setQuestion(rs.getString("question"));
            question.setQuestionTypeId(rs.getInt("questionTypeId"));
            questions.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return questions;
}

   public Question getQuestionById(int questionId) throws SQLException {
    Question question = null;
    String sql = "SELECT * FROM Questions WHERE QuestionID = ?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, questionId);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            int subjectId = rs.getInt("subjectId");
            int chapterId = rs.getInt("chapterId");
            int questionTypeId = rs.getInt("QuestionTypeId");
            String questionText = rs.getString("Question");
            
       
            List<Option> options = getOptionsByQuestionId(questionId);
            
            question = new Question(questionId, subjectId, chapterId, questionText, questionTypeId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
    }
    return question;
}
}

