CREATE DATABASE QuizSystem
GO

USE QuizSystem
GO

-- Creating Roles Table with IDENTITY
CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    Role NVARCHAR(255)
);

INSERT INTO Roles (Role) VALUES ('student');	--id 1
INSERT INTO Roles (Role) VALUES ('admin');	--id 2
INSERT INTO Roles (Role) VALUES ('teacher');	--id 3

-- Creating Users Table with IDENTITY
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    UserName NVARCHAR(255),
    RoleID INT,
    Email NVARCHAR(255),
    Password NVARCHAR(255),
	PhoneNumber VARCHAR(15),
	Avatar VARCHAR(MAX) DEFAULT 'https://i.pinimg.com/originals/26/82/bf/2682bf05bc23c0b6a1145ab9c966374b.png',
	FullName VARCHAR(100),
	DoB DATE,
	PlaceWork NVARCHAR(255),
	UserCode NVARCHAR(255),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

INSERT INTO Users (UserName, RoleID, Email, Password, PhoneNumber, FullName, DoB, PlaceWork, UserCode)
VALUES 
('teacher', 3, 'teacher@example.com', '1', '0123456789', 'Nguyen Van A', '1980-01-01', 'Fu-Hoa Lac', 'HE123456'),
('admin', 2, 'admin@example.com', '1', '0987654321', 'Nguyen Van B', '1980-01-01', 'Fu-Hoa Lac', 'HE123456'),
('student', 1, 'student@example.com', '1', '045632789', 'Nguyen Van C', '2000-01-01', 'Fu-Hoa Lac', 'HE123456');

-- Creating Classes Table with IDENTITY
CREATE TABLE Class (
    ClassID INT IDENTITY(1,1) PRIMARY KEY,
    ClassName NVARCHAR(255),
	ClassCode NVARCHAR(255),
	TeacherId int,
	Semester NVARCHAR(255),
	Subject NVARCHAR(255)
);
-- Creating Classes Table with IDENTITY
CREATE TABLE ClassMembers (
    ClassMemberID INT IDENTITY(1,1) PRIMARY KEY,
    ClassID INT,
    UserID INT,
	isApproved int,
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Creating QuestionSets Table with IDENTITY
CREATE TABLE QuestionSets (
    QuestionSetID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT,
    QuestionSetName NVARCHAR(255),
	IsPublic BIT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Creating Questions Table with IDENTITY
CREATE TABLE Questions (
    QuestionID INT IDENTITY(1,1) PRIMARY KEY,
    QuestionSetID INT,
	Question NVARCHAR(1000),
	FOREIGN KEY (QuestionSetID) REFERENCES QuestionSets(QuestionSetID)
);

-- Creating Options Table with IDENTITY
CREATE TABLE Options (
    OptionID INT IDENTITY(1,1) PRIMARY KEY,
    QuestionID INT,
    [Option] NVARCHAR(1000),
    IsCorrect BIT,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID)
);

-- Creating TermSets Table with IDENTITY
CREATE TABLE TermSets (
    TermSetID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT,
    TermSetName NVARCHAR(255),
	TermSetDescription NVARCHAR(255),
	IsPublic BIT,
	AccessTime DATETIME,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

INSERT INTO TermSets (UserID, TermSetName, TermSetDescription, IsPublic) VALUES
(3, N'Chào hỏi trong tiếng Nhật', N'Một số câu chào hỏi cơ bản thường ngày trong tiếng Nhật', 1);

-- Creating Terms Table with IDENTITY
CREATE TABLE Terms (
    TermID INT IDENTITY(1,1) PRIMARY KEY,
    TermSetID INT,
	Term NVARCHAR(1000),
    [Definition] NVARCHAR(1000),
	FOREIGN KEY (TermSetID) REFERENCES TermSets(TermSetID)
);

CREATE TABLE TypingGameScore (
	TypingGameScoreID INT IDENTITY(1,1) PRIMARY KEY,
	Score INT,
	TermSetID INT,
	UserID INT,
	[Level] NVARCHAR(30),
	FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (TermSetID) REFERENCES TermSets(TermSetID)
);


IF OBJECT_ID('UpdateHighScore', 'P') IS NOT NULL 
    DROP PROCEDURE UpdateHighScore;
GO

CREATE PROCEDURE UpdateHighScore
    @score INT,
    @termSetId INT,
    @userId INT,
    @level NVARCHAR(50)
AS
BEGIN
    -- Check if a score for this user, term set, and level already exists
    IF EXISTS (SELECT 1 FROM TypingGameScore WHERE TermSetID = @termSetId AND UserID = @userId AND [Level] = @level AND Score < @score)
    BEGIN
        -- If the new score is higher, update the existing score
        UPDATE TypingGameScore
        SET Score = @score
        WHERE TermSetID = @termSetId AND UserID = @userId AND [Level] = @level
    END
    ELSE IF NOT EXISTS (SELECT 1 FROM TypingGameScore WHERE TermSetID = @termSetId AND UserID = @userId AND [Level] = @level)
    BEGIN
        -- If no score exists, insert a new score
        INSERT INTO TypingGameScore (Score, TermSetID, UserID, [Level])
        VALUES (@score, @termSetId, @userId, @level)
    END
END
GO

--EXEC UpdateHighScore @score = 11, @termSetId = 1, @userId = 15, @level = 'easy'

INSERT INTO Terms (TermSetID, Term, [Definition])
VALUES 
(1, N'おはようございます', N'Chào buổi sáng!'),
(1, N'こんにちは', N'Chào buổi trưa!'),
(1, N'こんばんは', N'Chào buổi tối!'),
(1, N'すみません', N'Xin lỗi'),
(1, N'どうも', N'Rất, xin chào, cảm ơn,...'),
(1, N'ありがとう', N'Cảm ơn'),
(1, N'どうもありがとうございます', N'Cảm ơn rất nhiều.'),
(1, N'おやすみなさい', N'Chúc ngủ ngon.'),
(1, N'さようなら', N'Tạm biệt.'),
(1, N'わかりますか', N'Có hiểu không?'),
(1, N'はい、わかりました', N'Vâng, tôi hiểu rồi'),
(1, N'いただきます', N'Lời mời trước khi ăn, uống.'),
(1, N'ごちそうさまでした', N'Cảm ơn sau khi ăn uống.');

CREATE TABLE Tests (
    TestID INT IDENTITY(1,1) PRIMARY KEY,
	TestName NVARCHAR(255),
    ClassID INT,
    QuestionSetID INT,
    Start_at DATETIME,
	End_at DATETIME,
	duration int,
	isApproved BIT,
    FOREIGN KEY (ClassID) REFERENCES Class(ClassID),
    FOREIGN KEY (QuestionSetID) REFERENCES QuestionSets(QuestionSetID)
);

CREATE TABLE TestAttempts (
    TestAttemptID INT IDENTITY(1,1) PRIMARY KEY,
    TestID INT,
    UserID INT,
    Marks INT,
    Grade DECIMAL(10,2),
    FOREIGN KEY (TestID) REFERENCES Tests(TestID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Folder (
    FolderID INT IDENTITY(1,1) PRIMARY KEY,
    FolderSetName NVARCHAR(255),
	UserID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE FolderTermSets (
    FolderID INT,
    TermSetID INT,
	FOREIGN KEY (TermSetID) REFERENCES TermSets(TermSetID),
    FOREIGN KEY (FolderID) REFERENCES Folder(FolderID)
);
