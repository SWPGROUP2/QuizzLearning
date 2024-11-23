/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Classes;
import models.subject;

/**
 *
 * @author Admin
 */
public class ClassDAO extends MyDAO {

    public List<Classes> getAllClasses() {
        String sql = "SELECT * FROM Class";
        List<Classes> classList = new ArrayList<>();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int classId = rs.getInt("ClassID");
                String className = rs.getString("ClassName");

                classList.add(new Classes(classId, className));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return classList;
    }
    
public List<Classes> getAllClassesList(String searchKeyword) {
    List<Classes> classList = new ArrayList<>();
    String query = "SELECT ClassID, ClassName FROM Class";

    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
        query += " WHERE ClassName LIKE ?";
    }

    try (PreparedStatement stmt = con.prepareStatement(query)) {
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            stmt.setString(1, "%" + searchKeyword + "%");
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int classID = rs.getInt("ClassID");
                String className = rs.getString("ClassName");

                classList.add(new Classes(classID, className));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return classList;
}


    public String getClassNameById(int classId) {
        String sql = "SELECT ClassName FROM Class WHERE ClassID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, classId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("ClassName");
            }
        } catch (SQLException e) {
            System.out.println("getClassNameById: " + e.getMessage());
        }
        return null;
    }

    public List<Classes> getUniqueClasses(int userId) {
        List<Classes> uniqueClasses = new ArrayList<>();


        String sql = "SELECT DISTINCT c.ClassID, c.ClassName "
                + "FROM Class c "
                + "JOIN Users u ON c.UserID = u.UserID "
                + "WHERE u.UserID = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Integer classID = rs.getInt("ClassID");
                    String className = rs.getString("ClassName");

                    if (className != null) {
                        Classes classObj = new Classes();
                        classObj.setClassID(classID);
                        classObj.setClassName(className);
                        uniqueClasses.add(classObj);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching unique classes: " + e.getMessage());
            e.printStackTrace();
        }

        return uniqueClasses;
    }

    public List<subject> getTeacherSubjects(int userId) {
        List<subject> uniqueSubjects = new ArrayList<>();

        String sql = "SELECT DISTINCT s.SubjectID, s.SubjectName "
                + "FROM Subject s "
                + "JOIN TeacherSubjects ts ON ts.SubjectID = s.SubjectID "
                + "WHERE ts.UserID = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                subject subjectObj = new subject();
                subjectObj.setSubjectId(rs.getInt("SubjectID"));
                subjectObj.setSubjectName(rs.getString("SubjectName"));
                uniqueSubjects.add(subjectObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return uniqueSubjects;
    }

    public List<Classes> getClassesByTeacherId(int userId) {
        List<Classes> classList = new ArrayList<>();
        String sql = "SELECT c.ClassID, c.className FROM Class c JOIN Users u On u.ClassID = c.ClassID WHERE UserID = ?";
                      

        try ( PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Classes cls = new Classes();
                    cls.setClassID(rs.getInt("ClassID"));
                    cls.setClassName(rs.getString("ClassName"));
                    classList.add(cls);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }

    public List<Classes> getUniqueClasses() {
        List<Classes> uniqueClassesList = new ArrayList<>();
        String query = "SELECT MIN(c.ClassID) as ClassID, c.ClassName "
                + "FROM Class c "
                + "GROUP BY c.ClassName "
                + "ORDER BY c.ClassName";

        try ( PreparedStatement ps = connection.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Classes cls = new Classes();
                cls.setClassID(rs.getInt("ClassID"));
                cls.setClassName(rs.getString("ClassName"));
                uniqueClassesList.add(cls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uniqueClassesList;
    }

    public boolean addUserToClass(int userID, int classID) {
        String sql = "INSERT INTO Class (UserID, ClassName) "
                + "SELECT ?, ClassName FROM Class WHERE ClassID = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, classID);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public void updateUserClass(int userId, int classId) {
        String sql = "UPDATE Class SET ClassID = ? WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, classId);
            st.setInt(2, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updateUserClass: " + e.getMessage());
        }
    }

}
