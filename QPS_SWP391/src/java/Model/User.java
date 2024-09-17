package model;

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
    private String place;
    private String userCode;

    public User() {
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

    public User(int userId, String userName, int roleId, String email, String password, String role, String phoneNumber, String avatar, String fullName, Date Dob) {
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
    }

    public User(String userName, int roleId, String email, String password, String role, String phoneNumber, Date Dob, String place) {
        this.userName = userName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.Dob = Dob;
        this.place = place;
    }

    public User(String userName, int roleId, String email, String password, String role, String phoneNumber, Date Dob, String place, String userCode) {
        this.userName = userName;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.Dob = Dob;
        this.place = place;
        this.userCode = userCode;
    }

    public User(int userId, String userName, int roleId, String email, String password, String role, String phoneNumber, String avatar, String fullName, Date Dob, String place, String userCode) {
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
        this.place = place;
        this.userCode = userCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userName=" + userName + ", roleId=" + roleId + ", email=" + email + ", password=" + password + ", role=" + role + ", phoneNumber=" + phoneNumber + ", avatar=" + avatar + ", fullName=" + fullName + ", Dob=" + Dob + ", place=" + place + ", userCode=" + userCode + '}';
    }
}
