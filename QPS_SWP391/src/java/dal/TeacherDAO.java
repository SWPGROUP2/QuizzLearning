
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
public class TeacherDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private PreparedStatement ps;
    private ResultSet rs;

    public List<Classes> getClassesById(int id) throws Exception {
        List<Classes> classes = new ArrayList<>();
        try {
            String sqlQuery = "SELECT c.ClassID, c.ClassName, c.ClassCode, c.TeacherId, c.Semester, c.Subject\n"
                    + "FROM Class c where c.TeacherId =" + id;
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Classes c = new Classes();
                c.setClassID(rs.getInt("ClassID"));
                c.setClassName(rs.getString("ClassName"));
                c.setClassCode(rs.getString("ClassCode"));
                c.setTeacherId(rs.getInt("TeacherId"));
                c.setSemester(rs.getString("Semester"));
                c.setSubject(rs.getString("Subject"));
                classes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }
    
     public List<Classes> searchClassByClassName(String nameClass, int id) throws Exception {
        List<Classes> classes = new ArrayList<>();
        try {
            String sqlQuery = "SELECT c.ClassID, c.ClassName, c.ClassCode, c.TeacherId, c.Semester, c.Subject\n"
                    + "FROM Class c where c.TeacherId =" + id +"and c.ClassName LIKE '%"+ nameClass+"%'";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Classes c = new Classes();
                c.setClassID(rs.getInt("ClassID"));
                c.setClassName(rs.getString("ClassName"));
                c.setClassCode(rs.getString("ClassCode"));
                c.setTeacherId(rs.getInt("TeacherId"));
                c.setSemester(rs.getString("Semester"));
                c.setSubject(rs.getString("Subject"));
                classes.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }
    
     public Classes getClassesByIdClass(int id) throws Exception {
        Classes c = new Classes();
        try {
            String sqlQuery = "SELECT c.ClassID, c.ClassName, c.ClassCode, c.TeacherId, c.Semester, c.Subject\n"
                    + "FROM Class c where c.ClassID =" + id;
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                c.setClassID(rs.getInt("ClassID"));
                c.setClassName(rs.getString("ClassName"));
                c.setClassCode(rs.getString("ClassCode"));
                c.setTeacherId(rs.getInt("TeacherId"));
                c.setSemester(rs.getString("Semester"));
                c.setSubject(rs.getString("Subject"));               
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public void addClass(Classes classes) throws Exception {
        try {
            ps = connection.prepareStatement("INSERT INTO Class (ClassName, ClassCode, TeacherId, Semester, Subject) VALUES (?, ?, ?, ?, ?);");
            ps.setString(1, classes.getClassName());
            ps.setString(2, classes.getClassCode());
            ps.setInt(3, classes.getTeacherId());
            ps.setString(4, classes.getSemester());
            ps.setString(5, classes.getSubject());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void deleteClass (int id) throws Exception {
        try {
         
            ps = connection.prepareStatement("delete from ClassMembers where ClassID = ?;"
                    + " delete from Class where ClassID = ?;");
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    
     public void removeStudentInClass (int uid, int cid) throws Exception {
        try {   
            String sql ="delete from ClassMembers where ClassID = ? and UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, uid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
     
      public void AcceptStudentInClass (int uid, int cid) throws Exception {
        try {   
            String sql ="UPDATE ClassMembers SET isApproved = 1 where ClassID = ? and UserID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, uid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}

//class TestConnection {
//
//    private static final Logger LOGGER = Logger.getLogger(TestConnection.class.getName());
//
//    public static void main(String[] args) {
//        try {
//            TeacherDAO dao = new TeacherDAO();
//            List<Classes> c = dao.getClasses();
//            LOGGER.log(Level.INFO, c.get(0).toString());
//        } catch (Exception ex) {
//            LOGGER.log(Level.SEVERE, "Ket noi that bai");
//        }
//    }
//}
