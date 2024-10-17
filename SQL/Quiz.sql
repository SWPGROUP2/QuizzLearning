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
    title VARCHAR(255) CHARACTER SET utf8mb4,
    thumbnail VARCHAR(255) CHARACTER SET utf8mb4
);
CREATE TABLE Chapter (
    chapterEntryId INT AUTO_INCREMENT PRIMARY KEY,
    chapterId INT,
    chapterName VARCHAR(255) CHARACTER SET utf8mb4,
    subjectId INT,
    FOREIGN KEY (subjectId) REFERENCES Subject(subjectId)
);

CREATE INDEX idx_chapterId ON Chapter (chapterId);

CREATE TABLE Questions (
    QuestionID INT AUTO_INCREMENT PRIMARY KEY,
    subjectId INT,
    chapterId INT,
    Question VARCHAR(255) CHARACTER SET utf8mb4,
    Definition VARCHAR(1000) CHARACTER SET utf8mb4,
    FOREIGN KEY (subjectId) REFERENCES Subject(subjectId),
	FOREIGN KEY (chapterId) REFERENCES Chapter(chapterId)
);

INSERT INTO `Subject` (subjectId, subjectName, title, thumbnail) 
VALUES 
(1, 'JPT', 'Japanese', 'https://cf.quizizz.com/img/course-assets/title_imgs/2%20-%20Mathematics.png'),
(2, 'IELTS', 'English', 'https://cf.quizizz.com/img/course-assets/title_imgs/1%20-%20English%20and%20Language%20Arts.png'),
(3, 'TOPIK', 'Korean', 'https://cf.quizizz.com/img/course-assets/title_imgs/3%20-%20Social%20Studies.png'),
(4, 'Test', 'World Languages', 'https://cf.quizizz.com/img/course-assets/title_imgs/5-%20World%20Languages.png');

INSERT INTO `Chapter` (subjectId, chapterId, chapterName)
VALUES 
(1, 1, 'JPT 1'),
(1, 2, 'JPT 2'),
(2, 1, 'IELTS 1'),
(2, 2,'IELTS 2');

INSERT INTO `Questions` (subjectId, chapterId, Question, Definition)
VALUES 
(1, 1, 'おはようございます', 'Chào buổi sáng!'),
(1, 1, 'こんにちは', 'Chào buổi trưa!'),
(1, 1, 'こんばんは', 'Chào buổi tối!'),
(1, 2, 'すみません', 'Xin lỗi'),
(1, 2, 'どうも', 'Rất, xin chào, cảm ơn,...'),
(1, 2, 'ありがとう', 'Cảm ơn'),
(1, 2, 'どうもありがとうございます', 'Cảm ơn rất nhiều.'),
(1, 2, 'おやすみなさい', 'Chúc ngủ ngon.'),
(1, 2, 'さようなら', 'Tạm biệt.'),
(1, 2, 'わかりますか', 'Có hiểu không?'),
(1, 2, 'はい、わかりました', 'Vâng, tôi hiểu rồi'),
(1, 2, 'いただきます', 'Lời mời trước khi ăn, uống.'),
(1, 2, 'ごちそうさまでした', 'Cảm ơn sau khi ăn uống.'),
(2, 1, 'What is the synonym for breakfast?', 'brunch'),
(2, 2, 'What else is breakfast also called?', 'morgenmete');