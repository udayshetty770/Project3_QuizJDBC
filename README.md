# 🎯 Quiz Application using JDBC

A simple **Java Quiz Application** that interacts with a MySQL database using **JDBC (Java Database Connectivity)**. The project allows users to take a quiz and administrators to manage questions.

## 📌 Features

### 👤 User
- Take a multiple-choice quiz
- View score after completing the quiz

---

## 🛠 Technologies Used

- Java (JDK 8+)
- JDBC
- MySQL
- Command Line Interface (CLI)

---

## 📁 Project Structure
`````
Project3_QuizJDBC/
│
├── ConnectionProvider.java # Establishes DB connection
├── LoginPage.java # Entry point of the application
├── Quiz.java # Quiz logic for users
├── Question.java # Model class for questions
├── AdminPanel.java # Admin interface to manage quiz
└── quiz.sql # SQL script to create and setup the DB
`````
---

## 🧑‍💻 Getting Started

### ✅ Prerequisites

- Java JDK installed
- MySQL installed and running
- Basic understanding of command line

### 🚀 Setup Instructions

#### 1. Clone the repository

git clone https://github.com/udayshetty770/Project3_QuizJDBC.git
cd Project3_QuizJDBC/Project3_QuizJDBC
2. Set up the MySQL database
Open quiz.sql in MySQL Workbench or any client

Run the script to create the database and table:

CREATE DATABASE quiz;
USE quiz;

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    correct_answer VARCHAR(255)
);
Insert some sample questions manually or via your code.

3. Update DB credentials
Edit ConnectionProvider.java:

String url = "jdbc:mysql://localhost:3306/quiz";
String user = "your_mysql_username";
String password = "your_mysql_password";
▶️ Run the Application
Compile all .java files:

javac *.java
Run the app:

java LoginPage
📧 Author
Udaya Kumar Shetty
📍 Bangalore, India
📧 udayshetty465@gmail.com
