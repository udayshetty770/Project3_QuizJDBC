package com.jdbc.quizgame;

import java.sql.*;
import java.util.ArrayList;

public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;
    public static ArrayList<Question> questions = new ArrayList<>();

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public static void loadQuestionsFromDB() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz_app", "root", "Bighouse$770" );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                String qText = rs.getString("question_text");
                String[] opts = {
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4")
                };
                int correct = rs.getInt("correct_option") - 1;
                questions.add(new Question(qText, opts, correct));
            }

            con.close();
        } catch (Exception e) {
            
            System.out.println("Error loading questions from DB: " + e.getMessage());
        }
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public boolean isCorrectAnswer(int userAnswer) {
        return userAnswer - 1 == correctAnswerIndex;
    }
}
