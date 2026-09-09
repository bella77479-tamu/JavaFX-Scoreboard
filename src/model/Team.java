package model;
 
public class Team {
 
    private String name;
    private int score;
 
    public Team(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Team name must not be blank.");
        this.name  = name.trim();
        this.score = 0;
    }
 
    public String getName()  { return name; }
    public int    getScore() { return score; }
 
    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Team name must not be blank.");
        this.name = name.trim();
    }
 
    public void addScore(int points) {
        this.score += points;
    }
 
    public void resetScore() {
        this.score = 0;
    }
 
    @Override
    public String toString() {
        return name + " (" + score + ")";
    }
}
 