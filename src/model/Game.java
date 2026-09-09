package model;
 
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ArrayList;
 
public class Game {
 
    private Team home;
    private Team away;
    private final Deque<ScoringPlay> history = new ArrayDeque<>();
 
    public void setTeamNames(String side, String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Team names must not be blank.");
        if(!(side.equals("home") || side.equals("away")))
            throw new IllegalArgumentException("Side must be home or away");

        if(side.equals("home")){
            if (home == null) {
                home = new Team(name);
            } else {
                home.setName(name);
            }
        } else if(side.equals("away")){
            if (away == null) {
                away = new Team(name);
            } else {
                away.setName(name);
            }
        }
    }

    public boolean namesAreSet() {
        return home != null && away != null;
    }
 
    public String getHomeName() { return home != null ? home.getName() : ""; }
    public String getAwayName() { return away != null ? away.getName() : ""; }
    public int    getHomeScore() { return namesAreSet() ? home.getScore() : 0; }
    public int    getAwayScore() { return namesAreSet() ? away.getScore() : 0; }
 
    public void scoreHome(int points) {
        validateReady();
        ScoringPlay play = new ScoringPlay(ScoringPlay.Side.HOME, points, home.getName());
        home.addScore(points);
        history.push(play);
    }
 
    public void scoreAway(int points) {
        validateReady();
        ScoringPlay play = new ScoringPlay(ScoringPlay.Side.AWAY, points, away.getName());
        away.addScore(points);
        history.push(play);
    }
 
    public void undoLast() {
        if (history.isEmpty())
            throw new IllegalStateException("No actions left to undo.");
        ScoringPlay last = history.pop();
        if (last.getSide() == ScoringPlay.Side.HOME) {
            home.addScore(-last.getPoints());
        } else {
            away.addScore(-last.getPoints());
        }
    }
 
    public boolean canUndo() { return !history.isEmpty(); }
 
    public String getLastPlayDescription() {
        return history.isEmpty() ? "" : history.peek().getDescription();
    }
 
    public List<String> getPlayLog() {
        List<ScoringPlay> plays = new ArrayList<>(history); 
        java.util.Collections.reverse(plays);               
        List<String> descriptions = new ArrayList<>();
        for (ScoringPlay p : plays) {
            descriptions.add(p.getDescription());
        }
        return descriptions;
    }
 
    public void clearGame() {
        if (home != null) home.resetScore();
        if (away != null) away.resetScore();
        history.clear();
    }
 
    private void validateReady() {
        if (!namesAreSet())
            throw new IllegalStateException("Team names must be set before scoring.");
    }
}
