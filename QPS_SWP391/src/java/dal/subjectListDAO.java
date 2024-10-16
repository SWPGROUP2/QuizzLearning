/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.auth.Subject;
import models.subject;

/**
 *
 * @author dungmuahahaha
 */
public class subjectListDAO extends MyDAO {

    
    public List<subject> getAllSubject() {
        xSql = "select * from [Subject]";
        List<subject> slist = new ArrayList<>();

        int xsubjectId;
        String xthumbnail;
        String xcontent;
        String xtitle;
        String xsubjectName;

        boolean xstatus;

        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {

                xsubjectId = rs.getInt("subjectId");
                xsubjectName = rs.getString("subjectName");
                xtitle = rs.getString("title");
                xthumbnail = rs.getString("thumbnail");

                slist.add(new subject(xsubjectId, xsubjectName, xtitle, xthumbnail));

            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slist;
    }


    public List<subject> getListSubjectsByPagging(int page, int PAGE_SIZE_3) {
        List<subject> list = new ArrayList<>();
        int xsubjectId;
        String xthumbnail;
        String xcontent;
        String xtitle;
        String xsubjectName;
        int i = 0;
        try {
            if (con != null) {
                xSql = "with t as (select ROW_NUMBER() over (order by S.subjectId asc) as r,\n"
                        + "S.* from Subject AS S )\n"
                        + "select * from t where r between ?*?-(?-1) and ?*?";
                ps = con.prepareStatement(xSql);
                ps.setInt(1, page);
                ps.setInt(2, PAGE_SIZE_3);
                ps.setInt(3, PAGE_SIZE_3);
                ps.setInt(4, page);
                ps.setInt(5, PAGE_SIZE_3);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    list.add(new subject(xsubjectId, xsubjectName, xtitle, xthumbnail));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getTotalSubject() {
        try {
            if (con != null) {
                String sql = "select distinct count(S.subjectId) from Subject AS S ";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalSubject(String keyword) {
        try {
            if (con != null) {
                xSql = "SELECT COUNT(*) \n"
                        + "FROM Subject AS S \n"
                        + "WHERE S.subjectName LIKE ?;";
                ps = con.prepareStatement(xSql);
                ps.setString(1, "%" + keyword + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public List<subject> searchSubjectsByNameWithPaging(String keyword, int page, int pageSize) {
        List<subject> list = new ArrayList<>();
        int xsubjectId;
        String xthumbnail;
        String xcontent;
        String xtitle;
        String xsubjectName;

        try {
            if (con != null) {
                String sql = "SELECT * \n"
                        + "FROM Subject \n"
                        + "WHERE subjectName LIKE ? \n"
                        + "ORDER BY subjectId ASC \n"
                        + "LIMIT ? OFFSET ?;";

                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ps.setInt(2, pageSize);  
                ps.setInt(3, (page - 1) * pageSize);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");

                    list.add(new subject(xsubjectId, xsubjectName, xtitle, xthumbnail));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
