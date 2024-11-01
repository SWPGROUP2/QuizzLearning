DROP DATABASE IF EXISTS SWPQuiz;
CREATE DATABASE SWPQuiz;

USE SWPQuiz;


CREATE TABLE Roles (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO Roles (Role) VALUES ('student'), ('admin'), ('teacher');

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
    SubjectID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectName VARCHAR(255) CHARACTER SET utf8mb4,
    Title VARCHAR(255) CHARACTER SET utf8mb4,
    Thumbnail VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO Subject (SubjectName, Title, Thumbnail) 
VALUES 
('JPT', 'Japanese', 'https://cf.quizizz.com/img/course-assets/title_imgs/2%20-%20Mathematics.png'),
('IELTS', 'English', 'https://cf.quizizz.com/img/course-assets/title_imgs/1%20-%20English%20and%20Language%20Arts.png'),
('TOPIK', 'Korean', 'https://cf.quizizz.com/img/course-assets/title_imgs/3%20-%20Social%20Studies.png'),
('Test', 'World Languages', 'https://cf.quizizz.com/img/course-assets/title_imgs/5-%20World%20Languages.png');

CREATE TABLE QuestionType (
    QuestionTypeID INT AUTO_INCREMENT PRIMARY KEY,	
    QuestionTypeName VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO QuestionType (QuestionTypeName) VALUES ('Multiple-choice'), ('Short-answer');

CREATE TABLE Questions (
    QuestionID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectID INT,
    ChapterID INT,
    QuestionTypeID INT,
    Question VARCHAR(255) CHARACTER SET utf8mb4,
    FOREIGN KEY (subjectID) REFERENCES Subject(subjectID) ON DELETE CASCADE
);

INSERT INTO Questions (SubjectID, ChapterID, QuestionTypeID, Question) 
VALUES 
(1, 1, 2, 'Match the capital city to its country.'),
(1, 2, 1, 'What is the capital of Japan?');

CREATE TABLE Options (
    OptionID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionID INT,
    OptionText VARCHAR(1000) CHARACTER SET utf8mb4,
    IsCorrect BIT,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID) ON DELETE CASCADE
);

INSERT INTO Options (QuestionID, OptionText, IsCorrect) 
VALUES 
(1, 'Paris - France', 1), 
(2, 'Tokyo', 1),         
(2, 'Kyoto', 0),
(2, 'Osaka', 0),
(2, 'Nagoya', 0);

CREATE TABLE Class (
    ClassID INT AUTO_INCREMENT PRIMARY KEY,
    ClassName VARCHAR(255) CHARACTER SET utf8mb4,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

INSERT INTO Class (ClassName, UserID) VALUES ('Class A', 1), ('Class B', 2), ('Class C', 3);

CREATE TABLE Tests (
    TestID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectID INT,
    TestName VARCHAR(255) CHARACTER SET utf8mb4,
    Duration INT,
    ClassID INT,
    FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID)
);

INSERT INTO Tests (SubjectId, TestName, Duration, ClassID) 
VALUES 
(1, 'Japanese Test 1', 60, 1),
(2, 'English Test 1', 45, 2),
(3, 'Korean Test 1', 30, 3);

CREATE TABLE Test_Questions (
    TestQuestionsID INT AUTO_INCREMENT PRIMARY KEY,
    TestID INT,
    QuestionID INT,
    FOREIGN KEY (TestID) REFERENCES Tests(TestID),
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID)
);

INSERT INTO Test_Questions (TestID, QuestionID) 
VALUES 
(1, 1), 
(1, 2),
(2, 2);

CREATE TABLE TermSets (
    TermSetID INT AUTO_INCREMENT PRIMARY KEY,
    TermSetName VARCHAR(255) CHARACTER SET utf8mb4,
	TermSetDescription VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO TermSets (TermSetName, TermSetDescription) VALUES
('Chào hỏi trong tiếng Nhật', 'Một số câu chào hỏi cơ bản thường ngày trong tiếng Nhật');

CREATE TABLE Terms (
    TermID INT auto_increment PRIMARY KEY,
    TermSetID INT,
	Term VARCHAR(1000) CHARACTER SET utf8mb4, 
    Definition VARCHAR(1000) CHARACTER SET utf8mb4,
	FOREIGN KEY (TermSetID) REFERENCES TermSets(TermSetID)
);

INSERT INTO Terms (TermSetID, Term, Definition)
VALUES 
(1, 'おはようございます', 'Chào buổi sáng!'),
(1, 'こんにちは', 'Chào buổi trưa!'),
(1, 'こんばんは', 'Chào buổi tối!'),
(1, 'すみません', 'Xin lỗi'),
(1, 'どうも', 'Rất, xin chào, cảm ơn,...'),
(1, 'ありがとう', 'Cảm ơn'),
(1, 'どうもありがとうございます', 'Cảm ơn rất nhiều.'),
(1, 'おやすみなさい', 'Chúc ngủ ngon.'),
(1, 'さようなら', 'Tạm biệt.'),
(1, 'わかりますか', 'Có hiểu không?'),
(1, 'はい、わかりました', 'Vâng, tôi hiểu rồi'),
(1, 'いただきます', 'Lời mời trước khi ăn, uống.'),
(1, 'ごちそうさまでした', 'Cảm ơn sau khi ăn uống.');
