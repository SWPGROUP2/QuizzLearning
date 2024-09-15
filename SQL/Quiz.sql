CREATE DATABASE SWPQuiz

USE SWPQuiz

CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    Role NVARCHAR(255)
);

INSERT INTO Roles (Role) VALUES ('student');	--id 1
INSERT INTO Roles (Role) VALUES ('admin');	--id 2
INSERT INTO Roles (Role) VALUES ('teacher');	--id 3

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

CREATE TABLE [dbo].[Subject](
	[subjectId] [int] IDENTITY(1,1) NOT NULL,
	[subjectName] [nvarchar](255) NULL,
	[categoryId] [int] NULL,
	[status] [bit] NULL,
	[tagLine] [int] NULL,
	[title] [nvarchar](255) NULL,
	[thumbnail] [nvarchar](255) NULL,
	[description] [nvarchar](255) NULL,
 CONSTRAINT [PK_Subject] PRIMARY KEY CLUSTERED 
(
	[subjectId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]