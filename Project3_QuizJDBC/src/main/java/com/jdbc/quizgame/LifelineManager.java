package com.jdbc.quizgame;

import java.util.Random;

public class LifelineManager {
    private boolean used5050 = false;
    private boolean usedAudience = false;
    private boolean usedPhone = false;
    private boolean usedSkip = false;

    public boolean isUsed5050() {
        return used5050;
    }

    public boolean isUsedAudience() {
        return usedAudience;
    }

    public boolean isUsedPhone() {
        return usedPhone;
    }

    public boolean isUsedSkip() {
        return usedSkip;
    }

    public String use5050(Question question) {
        if (used5050) {
            return "You have already used 50-50.";
        }

        used5050 = true;
        StringBuilder message = new StringBuilder("50-50 Lifeline Activated!\nRemaining Options:\n");
        String[] options = question.getOptions();
        int correctIndex = question.getCorrectAnswerIndex();
        Random rand = new Random();

        int removed = 0;
        while (removed < 2) {
            int i = rand.nextInt(4);
            if (i != correctIndex && options[i] != null) {
                options[i] = null;
                removed++;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (options[i] != null) {
                message.append((i + 1)).append(". ").append(options[i]).append("\n");
            }
        }

        return message.toString();
    }

    public String useAudiencePoll(Question question) {
        if (usedAudience) {
            return "You have already used Audience Poll.";
        }

        usedAudience = true;
        StringBuilder result = new StringBuilder("Audience Poll Results:\n");
        Random rand = new Random();
        int correct = question.getCorrectAnswerIndex();

        for (int i = 0; i < 4; i++) {
            if (question.getOptions()[i] != null) {
                int percent = (i == correct) ? rand.nextInt(50) + 40 : rand.nextInt(30);
                result.append((i + 1)).append(". ").append(question.getOptions()[i]).append(" → ").append(percent).append("%\n");
            }
        }

        return result.toString();
    }

    public String usePhoneAFriend(Question question) {
        if (usedPhone) {
            return "You have already used Phone a Friend.";
        }

        usedPhone = true;

       
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
             
        }

        return "Your friend thinks the answer is option " + (question.getCorrectAnswerIndex() + 1);
    }

    public String useSkipQuestion() {
        if (usedSkip) {
            return "You have already used Skip Question.";
        }

        usedSkip = true;
        return "Question skipped successfully!";
    }
}
