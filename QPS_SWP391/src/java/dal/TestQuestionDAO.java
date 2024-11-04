package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestQuestionDAO extends MyDAO {

    public void saveTestQuestions(int testId, String[] questionIds) throws SQLException {
        if (questionIds == null || questionIds.length == 0) {
            return;
        }

        String deleteSql = "DELETE FROM Test_Questions WHERE TestID = ?";
        try ( PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
            pstmt.setInt(1, testId);
            pstmt.executeUpdate();
        }

        String insertSql = "INSERT INTO Test_Questions (TestID, QuestionID) VALUES (?, ?)";
        try ( PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            for (String questionId : questionIds) {
                pstmt.setInt(1, testId);
                pstmt.setInt(2, Integer.parseInt(questionId));
                pstmt.executeUpdate();
            }
        }
    }

    public List<Integer> getQuestionIdsByTestId(int testId) {
        List<Integer> questionIds = new ArrayList<>();
        String sql = "SELECT questionId FROM Test_Questions WHERE testId = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, testId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                questionIds.add(rs.getInt("questionId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionIds;
    }
}
