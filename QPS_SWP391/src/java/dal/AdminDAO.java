/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
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
 * @author Admin
 */
public class AdminDAO extends DBContext {

    public List<User> searchUser(String name, int roleId) {
        List<User> user = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * \n"
                    + "FROM Users u \n"
                    + "INNER JOIN Roles r ON r.RoleID = u.RoleID \n"
                    + "WHERE (-1 = ? OR u.RoleID = ?)\n"
                    + "AND ('' = ? OR u.UserName LIKE ?)\n"
                    + "ORDER BY u.UserID";
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            stm.setInt(1, roleId);
            stm.setInt(2, roleId);
            stm.setString(3, "%" + name + "%");
            stm.setString(4, "%" + name + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setRoleId(rs.getInt("RoleID"));
                u.setEmail(rs.getString("Email"));
                u.setPassword(rs.getString("Password"));
                u.setRole(rs.getString("Role"));
                u.setDob(rs.getDate("DoB"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setAvatar(rs.getString("Avatar"));
                u.setPlace(rs.getString("PlaceWork"));
                u.setFullName(rs.getString("FullName"));
                u.setUserCode(rs.getString("UserCode"));
                user.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    public List<User> getListByPage(ArrayList<User> list,
            int start, int end) {
        ArrayList<User> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();
        List<User> user = null;
        try {
            user = dao.searchUser("", 2);
            System.out.println(user.toString());
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
