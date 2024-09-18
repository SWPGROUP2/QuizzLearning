
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.User;

/**
 *
 * @author ADMIN
 */
public class TeacherDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private PreparedStatement ps;
    private ResultSet rs;

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
