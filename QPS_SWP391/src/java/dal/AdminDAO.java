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

public List<User> getFilteredUsers(String roleId, String status, String classId, int currentPage, int usersPerPage, String fullNameSearch) {
    List<User> userList = new ArrayList<>();
    
    // Directly include the role filter for roleId = 1 or roleId = 3
    StringBuilder query = new StringBuilder("SELECT u.UserID, u.UserName, u.RoleID, r.Role, u.Email, u.PhoneNumber, u.Avatar, "
            + "u.FullName, u.DoB, u.StartDate, u.EndDate, u.Status, c.ClassID, c.ClassName "
            + "FROM Users u "
            + "JOIN Roles r ON u.RoleID = r.RoleID "
            + "LEFT JOIN Class c ON u.UserID = c.UserID "
            + "WHERE 1=1 "
            + "AND (u.RoleID = 1 OR u.RoleID = 3) "); // Directly put the condition for roleID 1 or 3

    if (roleId != null && !roleId.isEmpty()) {
        query.append(" AND u.RoleID = ? ");
    }
    if (status != null && !status.isEmpty()) {
        query.append(" AND u.Status = ? ");
    }
    if (classId != null && !classId.isEmpty()) {
        query.append(" AND c.ClassID = ? ");
    }
    if (fullNameSearch != null && !fullNameSearch.trim().isEmpty()) {
        query.append(" AND u.FullName LIKE ? ");
    }

    query.append(" LIMIT ?, ?");

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
        int index = 1;

        // Set roleId condition only if it's provided
        if (roleId != null && !roleId.isEmpty()) {
            stmt.setString(index++, roleId);
        }
        if (status != null && !status.isEmpty()) {
            stmt.setString(index++, status);
        }
        if (classId != null && !classId.isEmpty()) {
            stmt.setString(index++, classId);
        }
        if (fullNameSearch != null && !fullNameSearch.trim().isEmpty()) {
            stmt.setString(index++, "%" + fullNameSearch.trim() + "%");
        }

        stmt.setInt(index++, (currentPage - 1) * usersPerPage); // Pagination offset
        stmt.setInt(index, usersPerPage); // Pagination limit

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            User user = new User(
                    rs.getInt("UserID"),
                    rs.getString("UserName"),
                    rs.getInt("RoleID"),
                    rs.getString("Role"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Avatar"),
                    rs.getString("FullName"),
                    rs.getDate("DoB"),
                    rs.getDate("StartDate"),
                    rs.getDate("EndDate"),
                    rs.getString("Status"),
                    rs.getInt("ClassID"),
                    rs.getString("ClassName")
            );
            userList.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return userList;
}

}
