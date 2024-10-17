package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Chapter;


public class ChapterDAO extends MyDAO {

    public List<Chapter> getChaptersBySubjectId(int subjectId) {
        xSql = "SELECT * FROM Chapters WHERE subjectId = ?";
        List<Chapter> clist = new ArrayList<>();
        int xChapterId;       
        String xChapterName;   
        try {
            ps = con.prepareStatement(xSql);
            ps.setInt(1, subjectId);
            rs = ps.executeQuery();
            while (rs.next()) {
                xChapterId = rs.getInt("chapterId");
                xChapterName = rs.getString("Chapter Name");
                clist.add(new Chapter(subjectId,xChapterId, xChapterName));
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clist;
    }

}
