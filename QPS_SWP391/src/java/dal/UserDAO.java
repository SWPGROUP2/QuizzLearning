package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public User getUserWithStatus(String email, String password) {
        User user = null;
        String query = "SELECT u.UserID, "
                + "u.UserName, "
                + "u.RoleID, "
                + "u.Email, "
                + "u.Password, "
                + "r.Role, "
                + "u.StartDate, "
                + "u.EndDate, "
                + "CASE "
                + "WHEN u.StartDate > CURDATE() OR (u.EndDate IS NOT NULL AND u.EndDate < CURDATE()) THEN 'Inactive' "
                + "ELSE u.Status "
                + "END AS Status "
                + "FROM Users u "
                + "JOIN Roles r ON u.RoleID = r.RoleID "
                + "WHERE u.Email = ? AND u.Password = ?";

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setUserName(rs.getString("UserName"));
                    user.setRoleId(rs.getInt("RoleID"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setRole(rs.getString("Role"));
                    user.setStartDate(rs.getDate("StartDate"));
                    user.setEndDate(rs.getDate("EndDate"));
                    user.setStatus(rs.getString("Status"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error executing query", ex);
        }
        return user;
    }

    public void updateUserStatus(int userId, String status) {
        String query = "UPDATE Users SET Status = ? WHERE UserID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception
        }
    }

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

    public User getUserById(int id) {
        User user = null;
        String query = "SELECT UserID, UserName, RoleID, Email, Password, PhoneNumber, Avatar, FullName, "
                + "DoB, Status, StartDate, EndDate "
                + "FROM Users "
                + "WHERE UserID = ?";
        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
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
                    user.setStatus(rs.getString("Status"));
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

    public void updateUserById(String fullName, String userName, String phoneNumber, String dob, int id, String placeWork, String userCode, Date startDate, Date endDate) {
        String sql = "UPDATE Users\n"
                + "SET FullName=?,\n"
                + "UserName=?,\n"
                + "PhoneNumber=?,\n"
                + "DoB=?,\n"
                + "PlaceWork=?,\n"
                + "UserCode=?,\n"
                + "StartDate=?,\n"
                + "EndDate=?\n"
                + "WHERE UserID=?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, fullName);
            stm.setString(2, userName);
            stm.setString(3, phoneNumber);
            stm.setString(4, dob);
            stm.setString(5, placeWork);
            stm.setString(6, userCode);

            // Set the start and end dates if not null
            if (startDate != null) {
                stm.setDate(7, new java.sql.Date(startDate.getTime()));
            } else {
                stm.setNull(7, java.sql.Types.DATE);
            }

            if (endDate != null) {
                stm.setDate(8, new java.sql.Date(endDate.getTime()));
            } else {
                stm.setNull(8, java.sql.Types.DATE);
            }

            // Set the user ID for the WHERE clause
            stm.setInt(9, id);

            // Execute the update
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

    public List<User> getAllTeachers() {
        List<User> teachers = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE RoleID = 3";

        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User teacher = new User();
                teacher.setUserId(rs.getInt("UserID"));
                teacher.setFullName(rs.getString("FullName"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }
    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (UserName, RoleID, Email, Password, PhoneNumber, " +
                    "FullName, DoB, StartDate, EndDate, Status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, user.getRoleId());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getFullName());
            stmt.setDate(7, user.getDob());
            stmt.setDate(8, user.getStartDate());
            stmt.setDate(9, user.getEndDate());
            stmt.setString(10, user.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }
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
