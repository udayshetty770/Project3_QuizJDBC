package com.jdbc.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI extends JFrame {
    private Player player;
    private LifelineManager lifelineManager;
    private int currentQuestionIndex = 0;
    private int currentReward = 0;
    private final int[] rewardLevels = { 1000, 2000, 4000, 10000, 20000, 40000, 80000, 160000, 320000, 1000000 };

    private JLabel questionLabel, rewardLabel, timerLabel;
    private JButton[] options = new JButton[4];
    private JButton submitButton, lifelineButton;
    private Timer questionTimer;
    private int timeLeft = 30;
    private int selectedIndex = -1;

    public GameUI(Player player) {
        this.player = player;
        this.lifelineManager = new LifelineManager();
        Question.questions = new java.util.ArrayList<>();
        Question.loadQuestionsFromDB();

        setTitle("Quiz Game - Show Style");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        questionLabel = new JLabel("Question appears here");
        questionLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        for (int i = 0; i < 4; i++) {
            final int index = i;
            options[i] = new JButton("Option");
            options[i].setFont(new Font("Arial", Font.BOLD, 16));
            options[i].setForeground(Color.WHITE);
            options[i].setBackground(new Color(50, 0, 70));
            options[i].setFocusPainted(false);
            options[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            options[i].addActionListener(e -> {
                clearOptionHighlight();
                options[index].setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
                selectedIndex = index;
            });
            optionsPanel.add(options[i]);
        }

        mainPanel.add(optionsPanel, BorderLayout.CENTER);

       
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 0, 50));
        bottomPanel.setLayout(new FlowLayout());

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(0, 153, 0));
        submitButton.setForeground(Color.WHITE);

        lifelineButton = new JButton("Use Lifeline");
        lifelineButton.setBackground(new Color(102, 0, 153));
        lifelineButton.setForeground(Color.WHITE);

        rewardLabel = new JLabel("Reward: Rs. 0");
        rewardLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rewardLabel.setForeground(Color.YELLOW);

        timerLabel = new JLabel("Time Left: 30s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);

        bottomPanel.add(submitButton);
        bottomPanel.add(lifelineButton);
        bottomPanel.add(rewardLabel);
        bottomPanel.add(timerLabel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

     
        submitButton.addActionListener(e -> handleAnswer());
        lifelineButton.addActionListener(e -> useLifeline());

        loadQuestion();
        setVisible(true);
    }

    private void loadQuestion() {
        selectedIndex = -1;
        clearOptionHighlight();

        if (currentQuestionIndex >= Question.questions.size()) {
            displayResult();
            return;
        }

        Question q = Question.questions.get(currentQuestionIndex);
        questionLabel.setText("<html><div style='text-align:center;'>" + q.getQuestionText() + "</div></html>");
        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            options[i].setText((char) ('a' + i) + ") " + (opts[i] != null ? opts[i] : ""));
            options[i].setEnabled(true);
        }

        timeLeft = 30;
        timerLabel.setText("Time Left: 30s");

        if (questionTimer != null && questionTimer.isRunning()) questionTimer.stop();
        questionTimer = new Timer(1000, evt -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");
            if (timeLeft <= 0) {
                questionTimer.stop();
                CustomDialog.showMessage(this, "Time's up!", "Timeout", Color.DARK_GRAY, Color.RED);
                displayResult();
            }
        });
        questionTimer.start();
    }

    private void clearOptionHighlight() {
        for (JButton btn : options) {
            btn.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        }
    }

    private void handleAnswer() {
        if (questionTimer != null) questionTimer.stop();

        if (selectedIndex == -1) {
            CustomDialog.showMessage(this, "Please select an option.", "Warning", Color.BLACK, Color.ORANGE);
            return;
        }

        Question q = Question.questions.get(currentQuestionIndex);
        if (selectedIndex == q.getCorrectAnswerIndex()) {
            currentReward = rewardLevels[Math.min(currentQuestionIndex, rewardLevels.length - 1)];
            rewardLabel.setText("Reward: Rs. " + currentReward);
            CustomDialog.showMessage(this, "Correct! You won Rs. " + currentReward, "Correct",
                    new Color(0, 128, 0), Color.WHITE);
            currentQuestionIndex++;

            if (currentQuestionIndex >= 5 && currentQuestionIndex < rewardLevels.length) {
                int choice = JOptionPane.showConfirmDialog(this, "Do you want to continue?", "Checkpoint",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.NO_OPTION) {
                    displayResult();
                    return;
                }
            }

            loadQuestion();
        } else {
            CustomDialog.showMessage(this, "Wrong answer!", "Game Over", new Color(139, 0, 0), Color.WHITE);
            displayResult();
        }
    }

    private void displayResult() {
        if (questionTimer != null) questionTimer.stop();
        CustomDialog.showMessage(this,
                "<html><b>Congratulations " + player.getName() + " from " + player.getCity() + ", "
                        + player.getState() + "!</b><br/>You won Rs. " + currentReward + "</html>",
                "Result", new Color(30, 0, 60), Color.YELLOW);
        System.exit(0);
    }

    private void useLifeline() {
        if (lifelineManager.isUsed5050() && lifelineManager.isUsedAudience()
                && lifelineManager.isUsedPhone() && lifelineManager.isUsedSkip()) {
            CustomDialog.showMessage(this, "No lifelines left!", "Lifelines",
                    new Color(50, 0, 50), Color.LIGHT_GRAY);
            return;
        }

        Question q = Question.questions.get(currentQuestionIndex);
        String[] choices = { "50-50", "Audience Poll", "Phone a Friend", "Skip Question" };
        String choice = (String) JOptionPane.showInputDialog(this, "Select a lifeline:", "Lifeline",
                JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

        if (choice == null)
            return;

        String message = "";

        switch (choice) {
            case "50-50":
                message = lifelineManager.use5050(q);
                loadQuestion(); break;
            case "Audience Poll":
                message = lifelineManager.useAudiencePoll(q); break;
            case "Phone a Friend":
                message = lifelineManager.usePhoneAFriend(q); break;
            case "Skip Question":
                message = lifelineManager.useSkipQuestion();
                if (message.toLowerCase().contains("skipped")) {
                    currentQuestionIndex++;
                    loadQuestion();
                } break;
        }

        CustomDialog.showMessage(this, message, "Lifeline", new Color(60, 0, 90), Color.YELLOW);
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color c1 = new Color(80, 0, 130);
            Color c2 = new Color(140, 0, 180);
            GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
