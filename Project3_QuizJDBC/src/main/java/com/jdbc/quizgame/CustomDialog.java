package com.jdbc.quizgame;

import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {

    public CustomDialog(JFrame parent, String message, String title, Color bgColor, Color textColor) {
        super(parent, title, true);
        setSize(420, 200);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, bgColor.darker(), getWidth(), getHeight(), bgColor.brighter());
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(textColor, 3));
        panel.setBackground(bgColor);

        JLabel label = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>");
        label.setFont(new Font("Consolas", Font.BOLD, 16));
        label.setForeground(textColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        panel.add(label, BorderLayout.CENTER);
        add(panel);

        new Timer(3000, e -> dispose()).start();
    }

    public static void showMessage(JFrame parent, String message, String title, Color bg, Color fg) {
        CustomDialog dialog = new CustomDialog(parent, message, title, bg, fg);
        dialog.setVisible(true);
    }
}
