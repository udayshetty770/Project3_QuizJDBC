package com.jdbc.quizgame;

public class Player {
    private String name;
    private String city;
    private String state;

    public Player(String name, String city, String state) {
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    
    public String getWelcomeMessage() {
        return "Welcome " + name + " from " + city + ", " + state + "!";
    }

    public String getRulesMessage() {
        return "<html>Rules of the Game:<br/>"
             + "• You can use 50-50<br/>"
             + "• You can use Audience Poll<br/>"
             + "• You can use Phone a Friend<br/>"
             + "• You can also Skip the Question</html>";
    }
}
