package com.jdbc.quizgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGUI extends JFrame {

    private JTextField nameField, cityField, stateField;
    private JButton startButton, continueButton;
    private JPanel panel;
    private Player player;

    public QuizGUI() {
        setTitle("Quiz Game - Login");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new GradientPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel nameLabel = new JLabel("Enter your Name:");
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        nameLabel.setForeground(Color.CYAN);

        nameField = new JTextField();
        styleInput(nameField);

        JLabel cityLabel = new JLabel("Enter your City:");
        cityLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        cityLabel.setForeground(Color.CYAN);

        cityField = new JTextField();
        styleInput(cityField);

        JLabel stateLabel = new JLabel("Enter your State:");
        stateLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        stateLabel.setForeground(Color.CYAN);

        stateField = new JTextField();
        styleInput(stateField);

        startButton = new JButton("Start Game");
        startButton.setBackground(new Color(0, 128, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setFocusPainted(false);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(stateLabel);
        panel.add(stateField);
        panel.add(new JLabel()); 
        panel.add(startButton);

        add(panel);
        setVisible(true);

      
        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();

            if (name.isEmpty() || city.isEmpty() || state.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            player = new Player(name, city, state);
            showWelcomeWithContinue();  
        });
    }

    private void showWelcomeWithContinue() {
        panel.removeAll();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

  
        JLabel welcomeMsg = new JLabel(
            "<html><div style='text-align:center;'>" +
            "<h1 style='color:cyan;'>Welcome to GQT Quiz Show!</h1>" +
            "<p style='color:white; font-size:16px;'>Welcome <b>" + player.getName() + "</b> from " +
            player.getCity() + ", " + player.getState() + "!</p></div></html>"
        );
        welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMsg.setFont(new Font("Verdana", Font.PLAIN, 16));

        JLabel rulesLabel = new JLabel(player.getRulesMessage());
        rulesLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        rulesLabel.setForeground(Color.YELLOW);
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);

     
        continueButton = new JButton("Continue to Game");
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.setBackground(new Color(0, 180, 0));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(welcomeMsg);
        centerPanel.add(rulesLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(continueButton);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();

        continueButton.addActionListener(e -> {
            dispose(); 
            SwingUtilities.invokeLater(() -> new GameUI(player)); 
        });
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("Consolas", Font.PLAIN, 14));
        field.setBackground(new Color(240, 240, 255));
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
    }

    class GradientPanel extends JPanel {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGUI());
    }
}
