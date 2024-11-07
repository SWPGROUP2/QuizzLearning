package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    // Get filtered questions with pagination
public List<Question> getFilteredQuestions(String subjectId, String chapterId, String questionTypeId, int currentPage, int questionsPerPage, int roleId, int userId, String questionSearch) {
    List<Question> questionList = new ArrayList<>();

    // Ensure the user is a teacher
    if (roleId != 3) {
        return questionList; // Return an empty list if the user is not a teacher
    }

    StringBuilder query = new StringBuilder("SELECT q.QuestionID, s.SubjectName, q.ChapterID, q.Question, qt.QuestionTypeName "
            + "FROM Questions q "
            + "JOIN Subject s ON q.SubjectID = s.SubjectID "
            + "JOIN QuestionType qt ON q.QuestionTypeID = qt.QuestionTypeID "
            + "JOIN TeacherSubjects ts ON q.SubjectID = ts.SubjectID "
            + "WHERE ts.UserID = ?"); // Ensures questions only for the subjects taught by this teacher

    // Additional filtering
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

    // Apply pagination
    query.append(" LIMIT ?, ?");

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        int index = 1;
        stmt.setInt(index++, userId);  // Use the teacher's user ID

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
            stmt.setString(index++, "%" + questionSearch.trim() + "%"); // Add search keyword with wildcards
        }

        stmt.setInt(index++, (currentPage - 1) * questionsPerPage); // Set offset
        stmt.setInt(index, questionsPerPage);                       // Set limit

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
            + "WHERE ts.UserID = ?"); // Ensures we count only the questions for subjects taught by the teacher

    // Additional filtering for subject, chapter, and question type
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
        stmt.setInt(index++, userId);  // Filter by teacher's userId

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
            count = rs.getInt(1);  // Get the count of matching questions
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return count;
}


    public boolean deleteQuestion(int questionID) {
        String deleteQuery = "DELETE FROM Questions WHERE QuestionID = ?";
        try ( PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
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

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    question = new Question(
                            rs.getInt("QuestionID"),
                            rs.getInt("SubjectID"),
                            rs.getInt("ChapterID"),
                            rs.getString("Question"),
                            rs.getInt("QuestionTypeID"),
                            rs.getString("QuestionTypeName"),
                            rs.getString("SubjectName")
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
    String query = "SELECT DISTINCT s.SubjectName, s.SubjectID "
                 + "FROM Subject s "
                 + "JOIN TeacherSubjects ts ON s.SubjectID = ts.SubjectID "
                 + "WHERE ts.UserID = ?";  // Filter by the current user's ID
    
    try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, userId);  // Set the current user's ID in the query
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int subjectId = rs.getInt("SubjectID");
                String subjectName = rs.getString("SubjectName");
                uniqueSubjects.put(subjectId, subjectName); // Store in map (ID -> Name)
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return uniqueSubjects;
}


public Set<Integer> getUniqueChapters() {
    Set<Integer> uniqueChapters = new HashSet<>();
    String query = "SELECT DISTINCT q.ChapterId FROM Questions q"; // Only select distinct ChapterId from Question table

    try (PreparedStatement stmt = con.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int chapterId = rs.getInt("ChapterId");
            uniqueChapters.add(chapterId); // Add to the HashSet
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
                   "JOIN QuestionType qt ON q.QuestionTypeId = qt.QuestionTypeId"; // Join Question with QuestionType

    try (PreparedStatement stmt = con.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int questionTypeId = rs.getInt("QuestionTypeId");
            String questionTypeName = rs.getString("QuestionTypeName");
            uniqueQuestionTypes.put(questionTypeId, questionTypeName); // Store QuestionTypeId -> QuestionTypeName
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
            stmt.setString(1, chapterId);  // Filter by chapter if provided
            stmt.setString(2, chapterId);  // Filter by chapter if provided

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);  // Get the total count of records
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecords;
    }

}
