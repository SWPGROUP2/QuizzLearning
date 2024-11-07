package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class UserDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean isPhoneNumberExists(String phoneNumber) throws SQLException {
        String sql = "SELECT * FROM Users WHERE phoneNumber = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking phone number existence", e);
            throw e;
        }
    }


    public void addUser(User user) throws Exception {
        try {
            ps = connection.prepareStatement("INSERT INTO Users (UserName, RoleID, Email, Password, PhoneNumber, DoB, PlaceWork, UserCode, FullName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, user.getUserName());
            ps.setInt(2, user.getRoleId());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhoneNumber());
            ps.setDate(6, user.getDob());
            ps.setString(7, user.getPlace());
            ps.setString(8, user.getUserCode());
            ps.setString(9, user.getFullName());
            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

public List<User> getUser() throws Exception {
    List<User> users = new ArrayList<>();
    try {
        // Updated SQL query to include Status
        String sqlQuery = "SELECT u.UserID, u.UserName, u.RoleID, u.Email, u.Password, r.Role, u.StartDate, u.EndDate, u.Status "
                        + "FROM Users u "
                        + "JOIN Roles r ON u.RoleID = r.RoleID"; // Using JOIN for better readability

        PreparedStatement stm = connection.prepareStatement(sqlQuery);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("UserID"));
            u.setUserName(rs.getString("UserName"));
            u.setRoleId(rs.getInt("RoleID"));
            u.setEmail(rs.getString("Email"));
            u.setPassword(rs.getString("Password"));
            u.setRole(rs.getString("Role"));
            
            // Setting the startDate and endDate
            u.setStartDate(rs.getDate("StartDate"));
            u.setEndDate(rs.getDate("EndDate"));

            // Setting the status
            u.setStatus(rs.getString("Status"));

            users.add(u);
        }
    } catch (SQLException ex) {
        Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return users;
}


    public List<User> getTop5NewestUser() throws Exception {
        List<User> top5UserList = new ArrayList<>();
        try {
            String query = "SELECT TOP (5) UserID,UserName,Avatar\n"
                    + "FROM Users\n"
                    + "WHERE RoleID = 1\n"
                    + "ORDER BY [UserID] DESC;";
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setAvatar(rs.getString("Avatar"));
                top5UserList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return top5UserList;
    }

public User getUserById(int id) {
    User user = null;
    String query = "SELECT UserID, UserName, RoleID, Email, Password, PhoneNumber, Avatar, FullName, "
                 + "DoB, Status, StartDate, EndDate "
                 + "FROM Users "
                 + "WHERE UserID = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUserName(rs.getString("UserName"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAvatar(rs.getString("Avatar"));
                user.setFullName(rs.getString("FullName"));
                user.setDob(rs.getDate("DoB"));
                user.setStatus(rs.getString("Status")); // Assuming Status is a boolean
                user.setStartDate(rs.getDate("StartDate"));
                user.setEndDate(rs.getDate("EndDate"));
            }
        }
    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "Error executing query", ex);
    }
    return user;
}


    public User getUserByEmail(String email) throws Exception {
        String sql = "SELECT u.UserID,  u.UserName, u.RoleID , u.Email, u.Password,r.Role\n"
                + "FROM Users u, Roles r\n"
                + "WHERE Email =  ? AND u.RoleID = r.RoleID";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("userName"),
                        rs.getInt("RoleID"),
                        email,
                        rs.getString("Password"),
                        rs.getString("Role"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        try {
            String sql = "UPDATE Users SET Password = ? WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate(); 
            return rowsAffected > 0;
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean verifyPassword(int userId, String currentPassword) throws SQLException {
        String sql = "SELECT Password FROM Users WHERE UserID = ? AND Password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        statement.setString(2, currentPassword);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    public User getUser(String email, String password) throws Exception {
        String sql = "SELECT u.UserID,  u.UserName, u.RoleID , u.Email, u.Password,r.Role\n"
                + "FROM Users u, Roles r\n"
                + "WHERE Email =  ? AND Password = ? AND u.RoleID = r.RoleID";

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, email);
        stm.setString(2, password);

        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("UserID"),
                    rs.getString("userName"),
                    rs.getInt("RoleID"),
                    email,
                    password,
                    rs.getString("Role"));
        }
        return null;
    }

    public void updateUserById(String fullName, String userName, String phoneNumber, String dob, int id, String placeWork, String userCode) {
        String sql = "update Users\n"
                + "set FullName=?,\n"
                + "UserName=?,\n"
                + "PhoneNumber=?,\n"
                + "DoB=?,\n"
                + "PlaceWork=?,\n"
                + "UserCode=?\n"
                + "where UserID=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, userName);
            stm.setString(3, phoneNumber);
            stm.setString(4, dob);
            stm.setString(5, placeWork);
            stm.setString(6, userCode);
            stm.setInt(7, id);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateAvatarById(String selectedAvatar, int id) {
        String sql = "update Users\n"
                + "set Avatar=?\n"
                + "where UserID=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, selectedAvatar);
            stm.setInt(2, id);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateUserRole(int newRole, int id) {
        String sql = "update Users set RoleID = ? where UserID = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, newRole);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean deleteUser(int subjectId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try {
            ps = connection.prepareStatement(sql);
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
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formatted = time.format(myFormatObj);
        System.out.println(formatted);

        UserDAO u = new UserDAO();
        User user = u.getUserById(15);
        System.out.println(user.toString());

        String str2 = "                       abc                          ";
        String str1 = "abc";
        System.out.println("[" + str2.trim() + "]");
        System.out.println(str1.equals(str2.trim()));
    }
}