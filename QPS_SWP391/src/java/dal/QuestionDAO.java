package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Option;
import models.Question;
import models.subject;
import models.QuestionType;

public class QuestionDAO extends MyDAO {
    
        public List<Question> getQuestionsBySubjectId(int subjectId) {
        String sql = "SELECT q.*, qt.QuestionTypeName "
                + "FROM Questions q "
                + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
                + "WHERE q.SubjectID = ?";
        List<Question> qlist = new ArrayList<>();

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    qlist.add(new Question(
                            rs.getInt("QuestionID"),
                            rs.getInt("SubjectID"),
                            rs.getInt("ChapterID"),
                            rs.getString("Question"),
                            rs.getInt("QuestionTypeID"),
                            rs.getString("QuestionTypeName") // Retrieve questionTypeName
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qlist;
    }

    public List<QuestionType> getAllQuestionTypes() {
        List<QuestionType> questionTypeList = new ArrayList<>();
        String query = "SELECT QuestionTypeId, QuestionTypeName FROM QuestionTypes";
        try ( Statement statement = con.createStatement();  ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int QuestionTypeId = resultSet.getInt("QuestionTypeId");
                String QuestionTypeName = resultSet.getString("QuestionTypeName");
                QuestionType questionType = new QuestionType(QuestionTypeId, QuestionTypeName);
                questionTypeList.add(questionType);
            }

        } catch (SQLException e) {
            e.printStackTrace();  
        }

        return questionTypeList;
    }

    public List<subject> getAllSubjects() {
        List<subject> subjectList = new ArrayList<>();
        String query = "SELECT SubjectID, SubjectName FROM Subjects"; // Replace with your actual table name and column names

        try (
                 Statement statement = con.createStatement();  ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int subjectId = resultSet.getInt("SubjectID");
                String subjectName = resultSet.getString("SubjectName");

                // Create a new Subject object and add it to the list
                subject subject = new subject(subjectId, subjectName);
                subjectList.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions as necessary
        }

        return subjectList;
    }

public List<Question> getFilteredQuestions(String subjectId, String chapterId, String questionTypeId, int currentPage, int questionsPerPage, int roleId, int userId, String questionSearch, String sortOrder) {
    List<Question> questionList = new ArrayList<>();

    if (roleId != 3) {
        return questionList; 
    }

    StringBuilder query = new StringBuilder("SELECT q.QuestionID, s.SubjectName, q.ChapterID, q.Question, qt.QuestionTypeName "
            + "FROM Questions q "
            + "JOIN Subject s ON q.SubjectID = s.SubjectID "
            + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
            + "JOIN TeacherSubjects ts ON q.SubjectID = ts.SubjectID "
            + "WHERE ts.UserID = ?");

    if (subjectId != null && !subjectId.isEmpty()) {
        query.append(" AND q.SubjectID = ? ");
    }
    if (chapterId != null && !chapterId.isEmpty()) {
        query.append(" AND q.ChapterID = ? ");
    }
    if (questionTypeId != null && !questionTypeId.isEmpty()) {
        query.append(" AND q.QuestionTypeID = ? ");
    }
    if (questionSearch != null && !questionSearch.trim().isEmpty()) {
        query.append(" AND q.Question LIKE ? ");
    }

    query.append(" LIMIT ?, ?");

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        int index = 1;
        stmt.setInt(index++, userId);  

        if (subjectId != null && !subjectId.isEmpty()) {
            stmt.setString(index++, subjectId);
        }
        if (chapterId != null && !chapterId.isEmpty()) {
            stmt.setString(index++, chapterId);
        }
        if (questionTypeId != null && !questionTypeId.isEmpty()) {
            stmt.setString(index++, questionTypeId);
        }
        if (questionSearch != null && !questionSearch.trim().isEmpty()) {
            stmt.setString(index++, "%" + questionSearch.trim() + "%");
        }

        stmt.setInt(index++, (currentPage - 1) * questionsPerPage); 
        stmt.setInt(index, questionsPerPage);                      

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Question question = new Question(
                    rs.getInt("QuestionID"),
                    rs.getInt("ChapterID"),
                    rs.getString("Question"),
                    rs.getString("QuestionTypeName"),
                    rs.getString("SubjectName")
            );
            questionList.add(question);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

   

    return questionList;
}

public int getFilteredQuestionCount(String subjectId, String chapterId, String questionTypeId, int userId) {
    int count = 0;
    StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Questions q "
            + "JOIN Subject s ON q.SubjectID = s.SubjectID "
            + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
            + "JOIN TeacherSubjects ts ON q.SubjectID = ts.SubjectID "
            + "WHERE ts.UserID = ?"); 

    if (subjectId != null && !subjectId.isEmpty()) {
        query.append(" AND q.SubjectID = ? ");
    }
    if (chapterId != null && !chapterId.isEmpty()) {
        query.append(" AND q.ChapterID = ? ");
    }
    if (questionTypeId != null && !questionTypeId.isEmpty()) {
        query.append(" AND q.QuestionTypeID = ? ");
    }

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        int index = 1;
        stmt.setInt(index++, userId);  

        if (subjectId != null && !subjectId.isEmpty()) {
            stmt.setString(index++, subjectId);
        }
        if (chapterId != null && !chapterId.isEmpty()) {
            stmt.setString(index++, chapterId);
        }
        if (questionTypeId != null && !questionTypeId.isEmpty()) {
            stmt.setString(index++, questionTypeId);
        }

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);  
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return count;
}


    public boolean deleteQuestion(int questionID) {
        String deleteQuery = "DELETE FROM Questions WHERE QuestionID = ?";
        try ( PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
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
            PreparedStatement ps = con.prepareStatement(sql);
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
        String sql = "SELECT q.QuestionID, q.SubjectID, q.ChapterID, q.Question, q.QuestionTypeID, s.SubjectName " +
                     "FROM Questions q JOIN Subject s ON q.SubjectID = s.SubjectID WHERE q.QuestionID = ?";
        Question question = null;
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {

                int id = rs.getInt("QuestionID");
                int subjectId = rs.getInt("SubjectID");
                int chapterId = rs.getInt("ChapterID");
                String questionText = rs.getString("Question");
                                String subjectName = rs.getString("SubjectName");
                int questionTypeId = rs.getInt("QuestionTypeID");


                question = new Question(id, subjectId, chapterId, questionText, subjectName, questionTypeId);

                List<Option> options = getOptionsForQuestion(questionId);
                question.setOptions(options);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }

    private List<Option> getOptionsForQuestion(int questionId) {
        List<Option> options = new ArrayList<>();
        String sql = "SELECT OptionID, OptionText, IsCorrect FROM Options WHERE QuestionID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int optionId = rs.getInt("OptionID");
                String optionText = rs.getString("OptionText");
                int isCorrect = rs.getInt("IsCorrect");

                Option option = new Option(optionId, optionText, isCorrect);
                options.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return options;
    }

    public int addQuestion(Question question) {
        String sql = "INSERT INTO Questions (SubjectID, ChapterID, Question, QuestionTypeID) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getSubjectId());
            ps.setInt(2, question.getChapterId());
            ps.setString(3, question.getQuestion());
            ps.setInt(4, question.getQuestionTypeId());

            ps.executeUpdate();
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

public Map<Integer, String> getUniqueSubjects(int userId) {
    Map<Integer, String> uniqueSubjects = new LinkedHashMap<>();
    
    String subjectQuery = "SELECT DISTINCT s.SubjectID, s.SubjectName "
                        + "FROM Subject s "
                        + "JOIN TeacherSubjects ts ON s.SubjectID = ts.SubjectID "
                        + "WHERE ts.UserID = ?";
    
    try (PreparedStatement stmt = con.prepareStatement(subjectQuery)) {
        stmt.setInt(1, userId); 
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int subjectId = rs.getInt("SubjectID");
                String subjectName = rs.getString("SubjectName");
                uniqueSubjects.put(subjectId, subjectName);  
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return uniqueSubjects;
}


public Set<Integer> getUniqueChapters() {
    Set<Integer> uniqueChapters = new HashSet<>();
    String query = "SELECT DISTINCT q.ChapterId FROM Questions q";

    try (PreparedStatement stmt = con.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int chapterId = rs.getInt("ChapterId");
            uniqueChapters.add(chapterId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return uniqueChapters;
}
public Map<Integer, String> getUniqueQuestionTypes() {
    Map<Integer, String> uniqueQuestionTypes = new LinkedHashMap<>();
    String query = "SELECT DISTINCT q.QuestionTypeId, qt.QuestionTypeName " +
                   "FROM Questions q " +
                   "JOIN QuestionType qt ON q.QuestionTypeId = qt.QuestionTypeId";

    try (PreparedStatement stmt = con.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int questionTypeId = rs.getInt("QuestionTypeId");
            String questionTypeName = rs.getString("QuestionTypeName");
            uniqueQuestionTypes.put(questionTypeId, questionTypeName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return uniqueQuestionTypes;
}

    public boolean questionExists(int subjectId, String questionText) {
        String query = "SELECT COUNT(*) FROM Questions WHERE SubjectID = ? AND Question = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public int getTotalQuestions(String chapterId) {
        int totalRecords = 0;
        String countQuery = "SELECT COUNT(*) FROM Questions q WHERE (? IS NULL OR q.ChapterID = ?)";

        try ( PreparedStatement stmt = connection.prepareStatement(countQuery)) {
            stmt.setString(1, chapterId);
            stmt.setString(2, chapterId); 

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }
public boolean isQuestionExists(int subjectId, int chapterId, String questionText) throws SQLException {
        String query = "SELECT COUNT(*) FROM Questions WHERE subjectId = ? AND chapterId = ? AND question = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            stmt.setInt(2, chapterId);
            stmt.setString(3, questionText.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
