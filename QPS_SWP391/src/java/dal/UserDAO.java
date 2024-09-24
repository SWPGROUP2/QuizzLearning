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
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            ResultSet rs = statement.executeQuery();
            return rs.next(); 
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking phone number existence", e);
            throw e; 
        }
    }

    public boolean isUserCodeExists(String userCode) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userCode = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userCode);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking user code existence", e);
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
        List<User> user = new ArrayList<>();
        try {
            String sqlQuery = "SELECT u.UserID, u.UserName, u.RoleID , u.Email, u.Password,r.Role\n"
                    + "FROM Users u, Roles r\n"
                    + "WHERE u.RoleID = r.RoleID";
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
                user.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public List<User> getUserByClassId(int classId) throws Exception {
        List<User> user = new ArrayList<>();
        try {
            String sqlQuery = "select u.UserId, u.UserName, u.DoB, u.PhoneNumber, u.PlaceWork, u.UserCode from Users u, Classmembers c where u.UserID = c.UserID and c.isApproved = 1 and  c.ClassID = " + classId;
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setDob(rs.getDate("DoB"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setPlace(rs.getString("PlaceWork"));
                u.setUserCode(rs.getString("UserCode"));
                user.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public List<User> getStdWantToJoin(int classId) throws Exception {
        List<User> user = new ArrayList<>();
        try {
            String sqlQuery = "select u.UserId, u.UserName, u.DoB, u.PhoneNumber, u.PlaceWork, u.UserCode from Users u, Classmembers c where u.UserID = c.UserID and c.isApproved = 0 and  c.ClassID = " + classId;
            PreparedStatement stm = connection.prepareStatement(sqlQuery);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("UserID"));
                u.setUserName(rs.getString("UserName"));
                u.setDob(rs.getDate("DoB"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setPlace(rs.getString("PlaceWork"));
                u.setUserCode(rs.getString("UserCode"));
                user.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
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
        String query = "SELECT UserID, UserName, RoleID, Email, Password, PhoneNumber, Avatar, FullName, DoB, PlaceWork, UserCode "
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
                    user.setPlace(rs.getString("PlaceWork"));
                    user.setUserCode(rs.getString("UserCode"));
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
       try { String sql = "UPDATE Users SET Password = ? WHERE UserID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newPassword);
        statement.setInt(2, userId);
         int rowsAffected = statement.executeUpdate(); // Execute the update
        return rowsAffected > 0;
    }  catch (Exception ex) {
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
            stm.setString(5,placeWork);
            stm.setString(6,userCode);
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
    


    public static void main(String[] args) {
        LocalDateTime time= LocalDateTime.now();
        System.out.println(time);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        String formatted= time.format(myFormatObj);
        System.out.println(formatted);
        
        UserDAO u= new UserDAO();
        User user= u.getUserById(15);
        System.out.println(user.toString());
        
        
        
        String str2= "                       abc                          ";
        String str1= "abc";
        System.out.println("["+str2.trim()+"]");
        System.out.println(str1.equals(str2.trim()));
    }
}

