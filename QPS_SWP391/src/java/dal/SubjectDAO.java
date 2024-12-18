/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SubjectDAO extends MyDAO {

    public subject getSubjectById(int subjectId) {
        subject subject = null;
        String sql = "SELECT * FROM Subject WHERE SubjectID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subject = new subject();
                subject.setSubjectId(rs.getInt("SubjectID"));
                subject.setSubjectName(rs.getString("SubjectName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public List<subject> getAllSubject() {
        xSql = "select * from Subject";
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

    public int addSubject(subject subject) {
        String sql = "INSERT INTO Subject (subjectName, title, thumbnail) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getTitle());
            ps.setString(3, subject.getThumbnail());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // Trả về subjectId vừa được sinh
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
        return 0;
    }

    public boolean updateSubject(subject subject) {
        String sql = "UPDATE Subject SET SubjectName = ?, Title = ? WHERE SubjectID = ?";

        try ( PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getTitle());
            ps.setInt(3, subject.getSubjectId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSubject(int subjectId) {
        String sql = "DELETE FROM Subject WHERE subjectId = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, subjectId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
        return false;
    }

    public String getSubjectNameById(int subjectId) {
        String subjectName = null;
        try ( PreparedStatement statement = connection.prepareStatement("SELECT SubjectName FROM Subject WHERE SubjectID = ?")) {
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                subjectName = resultSet.getString("SubjectName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectName;
    }

    public void assignTeacherToSubject(int subjectId, int teacherId) {
        String sql = "INSERT IGNORE INTO TeacherSubjects (SubjectID, UserID) VALUES (?, ?)";

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isTeacherAssignedToSubject(int subjectId, int teacherId) {
        String query = "SELECT COUNT(*) FROM TeacherSubjects WHERE SubjectID = ? AND UserID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, subjectId);
            ps.setInt(2, teacherId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> getAssignedTeacherIds(int subjectId) {
        List<Integer> assignedTeacherIds = new ArrayList<>();
        String query = "SELECT UserID FROM TeacherSubjects WHERE SubjectID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                assignedTeacherIds.add(rs.getInt("teacherId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignedTeacherIds;
    }

    public List<subject> getAssignedSubjects(int userId) {
        List<subject> subjects = new ArrayList<>();
        String query = "SELECT s.SubjectID AS SubjectID, s.SubjectName AS SubjectName "
                + "FROM TeacherSubjects ts "
                + "JOIN Subject s ON ts.SubjectID = s.SubjectID "
                + "WHERE ts.UserID = ?";

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subject subject = new subject();
                    subject.setSubjectId(rs.getInt("SubjectID"));
                    subject.setSubjectName(rs.getString("SubjectName"));
                    // Set thêm các thông tin khác của subject nếu cần
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }
}
