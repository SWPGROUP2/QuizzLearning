package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Classes;
import models.User;

/**
 *
 * @author ADMIN
 */
public class ClassDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean checkClassAvailable(String code) throws SQLException {
        try {
            String sql = "SELECT * FROM class WHERE ClassCode = ?";
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, code);
                try ( ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean checkIsInClass(int uid, String code) throws Exception {
        try {
            String sql = "SELECT ClassMembers.*\n"
                    + "FROM ClassMembers\n"
                    + "JOIN Class ON ClassMembers.ClassID = Class.ClassID\n"
                    + "JOIN Users ON ClassMembers.UserID = Users.UserID\n"
                    + "WHERE Class.ClassCode = ? \n"
                    + "AND Users.UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.setInt(2, uid);
            ResultSet rs = ps.executeQuery();
            boolean isInClass = !rs.next();
            rs.close();
            return isInClass;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean studentJoinByCode(int uid, String code) throws Exception {
        try {
            String sql = "INSERT INTO ClassMembers (ClassID, UserID, isApproved)\n"
                    + "SELECT c.ClassID, u.UserID, 0\n"
                    + "FROM Class c, Users u\n"
                    + "WHERE ClassCode = ?\n"
                    + "AND UserID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, code);
            ps.setInt(2, uid);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean acceptAll(int cid) throws Exception {
        try {
            String sql = "UPDATE ClassMembers\n"
                    + "SET isApproved = 1\n"
                    + "WHERE ClassID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Classes> getClassesByUserID(int userID) throws SQLException {
        List<Classes> classesList = new ArrayList<>();
        String query = "SELECT cm.ClassID, cm.UserID, c.ClassName, c.Semester, c.TeacherId, u.FullName, u.Avatar\n"
                + "FROM ClassMembers cm\n"
                + "JOIN Class c ON c.ClassID = cm.ClassID\n"
                + "JOIN Users u ON u.UserID = c.TeacherId\n"
                + "WHERE cm.UserID = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Classes classes = new Classes();
                classes.setClassID(rs.getInt("ClassID"));
                classes.setTeacherId(rs.getInt("TeacherId"));
                classes.setClassName(rs.getString("ClassName"));
                classes.setSemester(rs.getString("Semester"));
                User teacher = new User();
                teacher.setFullName(rs.getString("FullName"));
                teacher.setAvatar(rs.getString("Avatar"));
                // Gán đối tượng User vào đối tượng Classes
                classes.setTeacher(teacher);
                classesList.add(classes);
            }
        }
        return classesList;
    }

    public Classes getClassesByClassID(int classID) {
        String query = "SELECT ClassID, ClassName, ClassCode , TeacherId, Semester, Subject FROM Class where ClassID = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, classID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Classes cl = new Classes();
                cl.setClassID(rs.getInt("ClassID"));
                cl.setClassName(rs.getString("ClassName"));
                cl.setClassCode(rs.getString("ClassCode"));
                cl.setTeacherId(rs.getInt("TeacherId"));
                cl.setSemester(rs.getString("Semester"));
                cl.setSubject(rs.getString("Subject"));
                return cl;
                // Gán đối tượng User vào đối tượng Classes

            }
        } catch (Exception e) {

        }
        return null;
    }

    public List<Classes> getTop3ClassesByUserID(int userID) throws SQLException {
        List<Classes> classesList = new ArrayList<>();
        String query = "SELECT TOP 3 cm.ClassID, cm.UserID, c.ClassName, c.Semester, c.Subject, c.TeacherId, u.FullName, u.Avatar\n"
                + "FROM ClassMembers cm\n"
                + "JOIN Class c ON c.ClassID = cm.ClassID\n"
                + "JOIN Users u ON u.UserID = c.TeacherId\n"
                + "WHERE cm.UserID = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Classes classes = new Classes();
                classes.setClassID(rs.getInt("ClassID"));
                classes.setTeacherId(rs.getInt("TeacherId"));
                classes.setClassName(rs.getString("ClassName"));
                classes.setSemester(rs.getString("Semester"));
                classes.setSubject(rs.getString("Subject"));
                User teacher = new User();
                teacher.setFullName(rs.getString("FullName"));
                teacher.setAvatar(rs.getString("Avatar"));
                teacher.setUserId(rs.getInt("UserID"));
                // Gán đối tượng User vào đối tượng Classes
                classes.setTeacher(teacher);
                classesList.add(classes);
            }
        }
        return classesList;
    }
    


    public static void main(String[] args) throws SQLException {
        ClassDAO dao = new ClassDAO();
        List<Classes> cl= dao.getTop3ClassesByUserID(4);
        try {
            System.out.println(cl);
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
