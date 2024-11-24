DROP DATABASE IF EXISTS SWPQuiz;
CREATE DATABASE SWPQuiz;

USE SWPQuiz;

CREATE TABLE Roles (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    Role VARCHAR(255) CHARACTER SET utf8mb4
);

INSERT INTO Roles (Role) VALUES ('student'), ('admin'), ('teacher');

CREATE TABLE Class (
    ClassID INT AUTO_INCREMENT PRIMARY KEY,
    ClassName VARCHAR(255) CHARACTER SET utf8mb4
);

CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(255) CHARACTER SET utf8mb4,
    RoleID INT,
    Email VARCHAR(255) CHARACTER SET utf8mb4 UNIQUE, 
    Password VARCHAR(255) CHARACTER SET utf8mb4,
    PhoneNumber VARCHAR(15),
    Avatar VARCHAR(5000) DEFAULT 'assets/avatar/default-avatar.png',
    FullName VARCHAR(100) CHARACTER SET utf8mb4,
    DoB DATE,
    StartDate DATE,
    ClassID INT,
    EndDate DATE,    
    Status ENUM('Active', 'Inactive') DEFAULT 'Active',
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID) ON DELETE CASCADE
);

INSERT INTO Users (UserName, RoleID, ClassID, Email, Password, PhoneNumber, FullName, DoB, StartDate, EndDate, Status)
VALUES 
('user1', 2, null, 'admin@example.com', '1', '0987654321', 'Admin One', '1985-05-15', '2024-01-01', '2025-01-01', 'Active');

CREATE TABLE Subject (
    SubjectID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectName VARCHAR(255) CHARACTER SET utf8mb4,
    Title VARCHAR(255) CHARACTER SET utf8mb4,
    Thumbnail VARCHAR(255) CHARACTER SET utf8mb4
);

CREATE TABLE TeacherSubjects (
    TeacherSubjectID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    SubjectID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID)
);

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
    FOREIGN KEY (QuestionTypeID) REFERENCES QuestionType(QuestionTypeID) ON DELETE CASCADE,
    FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID) ON DELETE CASCADE
);

CREATE TABLE Options (
    OptionID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionID INT,
    OptionText VARCHAR(1000) CHARACTER SET utf8mb4,
    IsCorrect BIT,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID) ON DELETE CASCADE
);

CREATE TABLE Tests (
    TestID INT AUTO_INCREMENT PRIMARY KEY,
    SubjectID INT,
    TestName VARCHAR(255) CHARACTER SET utf8mb4,
    Duration INT,
    ClassID INT,
    test_startTime TIMESTAMP,
    test_endTime TIMESTAMP,
    test_status INT DEFAULT 0,
    FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID),
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID) ON DELETE CASCADE
);

CREATE TABLE Test_Questions (
    TestQuestionsID INT AUTO_INCREMENT PRIMARY KEY,
    TestID INT,
    QuestionID INT,
    FOREIGN KEY (TestID) REFERENCES Tests(TestID) ON DELETE CASCADE,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID) ON DELETE CASCADE
);

CREATE TABLE TermSets (
    TermSetID INT AUTO_INCREMENT PRIMARY KEY,
    TermSetName VARCHAR(255) CHARACTER SET utf8mb4,
    TermSetDescription VARCHAR(255) CHARACTER SET utf8mb4,
    CreatedBy INT,
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID)
);

CREATE TABLE Terms (
    TermID INT AUTO_INCREMENT PRIMARY KEY,
    TermSetID INT,
    Term VARCHAR(1000) CHARACTER SET utf8mb4, 
    Definition VARCHAR(1000) CHARACTER SET utf8mb4,
    FOREIGN KEY (TermSetID) REFERENCES TermSets(TermSetID)
);

CREATE TABLE StudentAnswers (
    answer_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    test_id INT,
    question_id INT,
    option_id INT,
    answer_text TEXT,
    answered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (test_id) REFERENCES Tests(TestID),
    FOREIGN KEY (question_id) REFERENCES Questions(QuestionID),
    FOREIGN KEY (option_id) REFERENCES Options(OptionID)
);

CREATE TABLE TestResults (
    result_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    test_id INT,
    score DECIMAL(5, 2),
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (test_id) REFERENCES Tests(TestID)
);
