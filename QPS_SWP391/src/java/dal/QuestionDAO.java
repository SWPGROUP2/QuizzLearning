package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
