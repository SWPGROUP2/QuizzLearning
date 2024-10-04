CREATE DATABASE SWPQuiz;
DROP DATABASE IF EXISTS SWPQuiz;
USE SWPQuiz;

CREATE TABLE Roles (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO Roles (Role) VALUES ('student');  -- id 1
INSERT INTO Roles (Role) VALUES ('admin');    -- id 2
INSERT INTO Roles (Role) VALUES ('teacher');  -- id 3

CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(255) CHARACTER SET utf8mb4,
    RoleID INT,
    Email VARCHAR(255) CHARACTER SET utf8mb4,
    Password VARCHAR(255) CHARACTER SET utf8mb4,
    PhoneNumber VARCHAR(15),
    Avatar VARCHAR(5000) DEFAULT 'https://i.pinimg.com/originals/26/82/bf/2682bf05bc23c0b6a1145ab9c966374b.png',
    FullName VARCHAR(100) CHARACTER SET utf8mb4,
    DoB DATE,
    PlaceWork VARCHAR(255) CHARACTER SET utf8mb4,
    UserCode VARCHAR(255) CHARACTER SET utf8mb4,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

INSERT INTO Users (UserName, RoleID, Email, Password, PhoneNumber, FullName, DoB, PlaceWork, UserCode)
VALUES 
('teacher', 3, 'teacher@example.com', '1', '0123456789', 'Nguyen Van A', '1980-01-01', 'Fu-Hoa Lac', 'HE123456'),
('admin', 2, 'admin@example.com', '1', '0987654321', 'Nguyen Van B', '1980-01-01', 'Fu-Hoa Lac', 'HE123456'),
('student', 1, 'student@example.com', '1', '045632789', 'Nguyen Van C', '2000-01-01', 'Fu-Hoa Lac', 'HE123456');

CREATE TABLE Subject (
    subjectId INT AUTO_INCREMENT PRIMARY KEY,
    subjectName VARCHAR(255) CHARACTER SET utf8mb4,
    categoryId INT,
    status BIT,
    tagLine INT,
    title VARCHAR(255) CHARACTER SET utf8mb4,
    thumbnail VARCHAR(255) CHARACTER SET utf8mb4,
    description VARCHAR(255) CHARACTER SET utf8mb4
);
