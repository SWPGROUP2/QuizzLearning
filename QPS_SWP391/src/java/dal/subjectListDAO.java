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
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;

        boolean xstatus;
        
        try {
            ps = con.prepareStatement(xSql);
            rs = ps.executeQuery();
            while (rs.next()) {

                xsubjectId = rs.getInt("subjectId");
                xsubjectName = rs.getString("subjectName");
                xcategoryId = rs.getInt("categoryId");
                xstatus = rs.getBoolean("status");
                xtagLine = rs.getInt("tagLine");
                xtitle = rs.getString("title");
                xthumbnail = rs.getString("thumbnail");
                xdescription = rs.getString("description");

                slist.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription));

            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slist;
    }

    public List<subject> getListSubjectBySubjectId(int subjectId) {
        ArrayList<subject> list = new ArrayList<>();
        int xsubjectId;
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;
        boolean xstatus;
        float xsalePrice;
        float xprice;
        try {
            if (con != null) {
                xSql = "select s.*,p.price,p.salePrice from Subject s, PricePackage p, SubjectPrice sp\n"
                        + "   where s.subjectId=sp.subjectId and p.priceId=sp.priceId and s.subjectId=?";
                ps = con.prepareStatement(xSql);
                ps.setInt(1, subjectId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xcategoryId = rs.getInt("categoryId");
                    xstatus = rs.getBoolean("status");
                    xtagLine = rs.getInt("tagLine");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    xdescription = rs.getString("description");
                    xsalePrice = rs.getFloat("salePrice");
                    xprice = rs.getFloat("price");

                    list.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription, xsalePrice, xprice));
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

    public List<subject> getListSubjectsByPaggingAndSort(int page, int pageSize, String sortBy) {
        List<subject> list = new ArrayList<>();
        int xsubjectId;
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;
        boolean xstatus;
        Date xdate; // Thêm biến xdate kiểu Timestamp

        try {
            if (con != null) {
                xSql = "  with t as (select ROW_NUMBER() over (order by S.createDate asc) as r, S.* from Subject AS S) \n"
                        + " select * from t where r between  ? * ? - (?-1) and ? * ?";
                ps = con.prepareStatement(xSql);
                ps.setInt(1, page);
                ps.setInt(2, pageSize);
                ps.setInt(3, pageSize);
                ps.setInt(4, page);
                ps.setInt(5, pageSize);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xcategoryId = rs.getInt("categoryId");
                    xstatus = rs.getBoolean("status");
                    xtagLine = rs.getInt("tagLine");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    xdescription = rs.getString("description");
                    xdate = rs.getDate("createDate"); // Lấy giá trị thời gian của trường "date"

                    list.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription, xdate)); // Thêm xdate vào constructor của Subject
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

    public List<subject> sortByDate() {
        List<subject> sortedList = new ArrayList<>();
        int xsubjectId;
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;
        boolean xstatus;
        Date xdate;

        try {
            if (con != null) {
                xSql = "SELECT * FROM Subject ORDER BY createDate ASC";
                ps = con.prepareStatement(xSql);
                rs = ps.executeQuery(xSql);
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xcategoryId = rs.getInt("categoryId");
                    xstatus = rs.getBoolean("status");
                    xtagLine = rs.getInt("tagLine");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    xdescription = rs.getString("description");
                    xdate = rs.getDate("createDate");

                    sortedList.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription, xdate));
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
        return sortedList;
    }

    public List<subject> getListSubjectsByPagging(int page, int PAGE_SIZE_3) {
        List<subject> list = new ArrayList<>();
        int xsubjectId;
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;
        boolean xstatus;
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
                    xcategoryId = rs.getInt("categoryId");
                    xstatus = rs.getBoolean("status");
                    xtagLine = rs.getInt("tagLine");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    xdescription = rs.getString("description");

                    list.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription));
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
                xSql = "select distinct count(S.subjectId)from Subject AS S where S.subjectName like ?";
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

    public List<subject> getListSubjectsByKeywordAndPagging(String keyword, int page, int PAGE_SIZE_3) {
        List<subject> list = new ArrayList<>();
        int xsubjectId;
        int xcategoryId;
        String xthumbnail;
        int xtagLine;
        String xcontent;
        String xdescription;
        String xtitle;
        String xsubjectName;
        boolean xstatus;
        try {
            if (con != null) {
                String sql = "with t as (select ROW_NUMBER() over (order by S.subjectId asc) as r,\n"
                        + "                    S.*from Subject AS S \n"
                        + "                    where S.subjectName like ?) select * from t where r between ?*?-(?-1) and ?*?";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ps.setInt(2, page);
                ps.setInt(3, PAGE_SIZE_3);
                ps.setInt(4, PAGE_SIZE_3);
                ps.setInt(5, page);
                ps.setInt(6, PAGE_SIZE_3);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xsubjectId = rs.getInt("subjectId");
                    xsubjectName = rs.getString("subjectName");
                    xcategoryId = rs.getInt("categoryId");
                    xstatus = rs.getBoolean("status");
                    xtagLine = rs.getInt("tagLine");
                    xtitle = rs.getString("title");
                    xthumbnail = rs.getString("thumbnail");
                    xdescription = rs.getString("description");

                    list.add(new subject(xsubjectId, xsubjectName, xcategoryId, xstatus, xtagLine, xtitle, xthumbnail, xdescription));
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

    public subject getSubjectById(int subjectId) {
        String subjectName;
        int categoryId;
        boolean status;
        int tagLine;
        String title;
        String thumbnail;
        String description;

        try {
            if (con != null) {
                xSql = "select distinct S.* from Subject AS S where S.subjectId = ?";
                ps = con.prepareStatement(xSql);
                ps.setInt(1, subjectId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    subjectId = rs.getInt(1);
                    subjectName = rs.getString(2);
                    categoryId = rs.getInt(3);
                    status = rs.getBoolean(4);
                    tagLine = rs.getInt(5);
                    title = rs.getString(6);
                    thumbnail = rs.getString(7);
                    description = rs.getString(8);
                    return new subject(subjectId, subjectName, categoryId, status, tagLine, title, thumbnail, description);
                }
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

}
