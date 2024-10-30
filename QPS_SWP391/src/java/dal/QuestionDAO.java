package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.Question;

public class QuestionDAO extends MyDAO {

    public List<Question> getQuestionsBySubjectId(int subjectId) {
        String sql = "SELECT q.*, qt.QuestionTypeName " +
                     "FROM Questions q " +
                     "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID " +
                     "WHERE q.SubjectID = ?";
        List<Question> qlist = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    qlist.add(new Question(
                        rs.getInt("QuestionID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("ChapterID"),
                        rs.getString("Question"),
                        rs.getInt("QuestionTypeID"),
                        rs.getString("QuestionTypeName")  // Retrieve questionTypeName
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qlist;
    }

     public List<Question> getQuestionsBySubjectIdChapterIdSorted(int subjectId, String chapterId, String sort, String sortOrder) {
        String sql = "SELECT q.*, qt.QuestionTypeName " +
                     "FROM Questions q " +
                     "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID " +
                     "WHERE q.SubjectID = ?";
        if (chapterId != null && !chapterId.isEmpty()) {
            sql += " AND q.ChapterID = ?";
        }
        sql += " ORDER BY " + sort + " " + (sortOrder.equals("desc") ? "DESC" : "ASC");

        List<Question> qlist = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            if (chapterId != null && !chapterId.isEmpty()) {
                ps.setString(2, chapterId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                qlist.add(new Question(
                    rs.getInt("QuestionID"),
                    subjectId,
                    rs.getInt("ChapterID"),
                    rs.getString("Question"),
                    rs.getInt("QuestionTypeID"),
                    rs.getString("QuestionTypeName")  
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qlist;
    }
    
    public List<Question> getQuestionsBySubjectIdQuestionTypeIdSorted(int subjectId, String chapterId, String sort, String sortOrder) {
    String sql = "SELECT * FROM Questions WHERE SubjectID = ?";
    
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
                rs.getInt("ChapterID"),
                rs.getString("Question"),
                rs.getInt("QuestionTypeId"),
                rs.getString("QuestionTypeName")
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return qlist;
    }
        
    public boolean deleteQuestion(int questionID) {
        String deleteQuery = "DELETE FROM Questions WHERE QuestionID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, questionID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

public Question getQuestionById(int questionId) {
        String sql = "SELECT q.*, qt.QuestionTypeName FROM Questions q JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID WHERE q.QuestionID = ?";
        Question question = null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    question = new Question(
                        rs.getInt("QuestionID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("ChapterID"),
                        rs.getString("Question"),
                        rs.getInt("QuestionTypeID"),
                        rs.getString("QuestionTypeName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public int addQuestion(Question question) {
        String sql = "INSERT INTO Questions (SubjectID, ChapterID, Question, QuestionTypeID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getSubjectId());
            ps.setInt(2, question.getChapterId());
            ps.setString(3, question.getQuestion());
            ps.setInt(4, question.getQuestionTypeId());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Set<Integer> getUniqueChapters(int subjectId) {
        Set<Integer> chapterSet = new HashSet<>();
        String query = "SELECT DISTINCT ChapterID FROM Questions WHERE SubjectID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    chapterSet.add(result.getInt("ChapterID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapterSet;
    }

    public boolean questionExists(int subjectId, String questionText) {
        String query = "SELECT COUNT(*) FROM Questions WHERE SubjectID = ? AND Question = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            stmt.setString(2, questionText);
            try (ResultSet rs = stmt.executeQuery()) {
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

        String query = "SELECT q.*, qt.QuestionTypeName FROM Questions q JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID WHERE q.SubjectID = ? LIMIT ? OFFSET ?";
        String countQuery = "SELECT COUNT(*) FROM Questions WHERE SubjectID = ?";

        try (
            PreparedStatement countStmt = connection.prepareStatement(countQuery);
            PreparedStatement queryStmt = connection.prepareStatement(query)
        ) {
            countStmt.setInt(1, subjectId);
            try (ResultSet countRs = countStmt.executeQuery()) {
                if (countRs.next()) {
                    result.setTotalCount(countRs.getInt(1));
                }
            }

            int offset = (currentPage - 1) * pageSize;
            queryStmt.setInt(1, subjectId);
            queryStmt.setInt(2, pageSize);
            queryStmt.setInt(3, offset);

            try (ResultSet rs = queryStmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(new Question(
                        rs.getInt("QuestionID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("ChapterID"),
                        rs.getString("Question"),
                        rs.getInt("QuestionTypeID"),
                        rs.getString("QuestionTypeName")
                    ));
                }
            }
            result.setQuestions(questions);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
