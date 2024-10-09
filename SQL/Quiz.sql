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

INSERT INTO `Subject` (`subjectId`, `subjectName`, `categoryId`, `status`, `tagLine`, `title`, `thumbnail`, `description`) 
VALUES 
(1, 'Math', 1, 1, 20, 'Mathematics', 'https://cf.quizizz.com/img/course-assets/title_imgs/2%20-%20Mathematics.png', 'About this Specialization For a lot of higher level courses in Machine Learning and Data Science, you find you need to freshen up on the basics in mathematics - stuff you may have studied before in school or university.'),
(2, 'English', 1, 1, 20, 'English and Language Arts', 'https://cf.quizizz.com/img/course-assets/title_imgs/1%20-%20English%20and%20Language%20Arts.png', 'This Specialization helps you improve your professional communication in English for successful business interactions. Each course focuses on a particular area of communication in English.'),
(3, 'Social Studies', 1, 1, 20, 'Social Studies', 'https://cf.quizizz.com/img/course-assets/title_imgs/3%20-%20Social%20Studies.png', 'In this course, you will learn how social workers in the United States engage in creating change and supporting the resilience of individuals, families and communities in this new era.'),
(4, 'World Languages', 1, 1, 20, 'World Languages', 'https://cf.quizizz.com/img/course-assets/title_imgs/5-%20World%20Languages.png', 'Through this course, you can understand Korean more deeply and get advanced Korean language skills. This course is for advanced Korean learners who are interested in Korean language and culture.'),
(5, 'Science', 1, 1, 20, 'Science', 'https://cf.quizizz.com/img/course-assets/title_imgs/4%20-%20Science.png', 'The course aims to explain the scientific principles and techniques behind the work of forensic scientists and will be illustrated with numerous case studies from Singapore and around the world.'),
(6, 'Computer Science and Skills', 1, 1, 20, 'Computer Science and Skills', 'https://cf.quizizz.com/img/course-assets/title_imgs/8%20-%20Computer%20Science.png', 'This Specialization is intended for anyone seeking to learn basic computer skills. Through 3 courses, you will study computer hardware, software, and data safety.'),
(7, 'Career and Technical Education', 1, 1, 20, 'Career and Technical Education', 'https://cf.quizizz.com/img/course-assets/title_imgs/lifeskills_s.png', 'This course is the first of a series that aims to prepare you for a role as an entry-level IT Support Specialist. In this course, you’ll be introduced to the world of Information Technology, or IT.'),
(8, 'Creative Arts', 1, 1, 20, 'Creative Arts', 'https://cf.quizizz.com/img/course-assets/title_imgs/6%20-%20Creative%20Arts.png', 'Graphic design is all around us, in a myriad of forms, both on screen and in print, yet it is always made up of images and words to create a communication goal.'),
(9, 'Health and Physical Education', 1, 1, 20, 'Health and Physical Education', 'https://cf.quizizz.com/img/course-assets/title_imgs/7-%20PE%26Health.png', 'The vital signs – heart rate, blood pressure, body temperature, respiration rate, and pain – communicate important information about the physiological status of the human body.');
