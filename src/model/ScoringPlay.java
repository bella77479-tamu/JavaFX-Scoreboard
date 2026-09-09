package model;
 
public class ScoringPlay {
 
    public enum Side { HOME, AWAY }
 
    private final Side   side;
    private final int    points;
    private final String description;
 
    public ScoringPlay(Side side, int points, String teamName) {
        if (points <= 0)
            throw new IllegalArgumentException("Points must be positive.");
        this.side        = side;
        this.points      = points;
        this.description = buildDescription(teamName, points);
    }
 
    public Side   getSide()        { return side; }
    public int    getPoints()      { return points; }
    public String getDescription() { return description; }
 
    private static String buildDescription(String teamName, int pts) {
        String label = switch (pts) {
            case 1  -> "an extra point!";
            case 2  -> "a safety / 2-pt conversion!";
            case 3  -> "a field goal!";
            case 6  -> "a touchdown!";
            default -> pts + " points!";
        };
        return teamName + " scored " + label;
    }
 
    @Override
    public String toString() {
        return description;
    }
}