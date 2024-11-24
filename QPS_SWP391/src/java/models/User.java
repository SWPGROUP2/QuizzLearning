package models;

import java.sql.Date;

public class User {

    private int userId;
    private String userName;
    private int roleId;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String avatar;
    private String fullName;
    private Date Dob;
    private Date startDate;  
    private Date endDate;    
    private String status;
    private int classId;
    private String className;
    private String userCode;

    public User() {
    }

    public User(int userId, String userName, int roleId, String email, String password, String role, String phoneNumber, String avatar, String fullName, Date Dob, Date startDate, Date endDate, String status, int classId, String className, String userCode) {
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.fullName = fullName;
        this.Dob = Dob;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.classId = classId;
        this.className = className;
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    
    public User(String userName, int roleID, String email, String password, String role) {
        this.userName = userName;
        this.roleId = roleID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String userName, int roleID, String email, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleID;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String userName, int roleId, String email, String password, String role, String phoneNumber, String avatar, String fullName, Date Dob, Date startDate, Date endDate) {
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.fullName = fullName;
        this.Dob = Dob;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User(int userId, String userName, int roleId, String role, String email, String phoneNumber, String avatar, String fullName, Date Dob, Date startDate, Date endDate, String status,int classId, String className) {
        this.userId = userId;
        this.userName = userName;
        this.roleId = roleId;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.fullName = fullName;
        this.Dob = Dob;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.classId = classId;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return Dob;
    }

    public void setDob(Date Dob) {
        this.Dob = Dob;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
