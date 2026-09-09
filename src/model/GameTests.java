package model;
 
public class GameTests {
 
    private static int passed = 0;
    private static int failed = 0;
 
    public static void main(String[] args) {
        System.out.println("=== Team Tests ===");
        testTeam_validName();
        testTeam_blankName();
        testTeam_nullName();
        testTeam_addScore();
        testTeam_resetScore();
        testTeam_setName();
        testTeam_setNameBlank();
 
        System.out.println("\n=== ScoringPlay Tests ===");
        testScoringPlay_touchdown();
        testScoringPlay_fieldGoal();
        testScoringPlay_safety();
        testScoringPlay_extraPoint();
        testScoringPlay_invalidPoints();
        testScoringPlay_sideIsCorrect();
 
        System.out.println("\n=== Game Tests ===");
        testGame_setTeamNames_home();
        testGame_setTeamNames_away();
        testGame_setTeamNames_blankName();
        testGame_setTeamNames_nullName();
        testGame_setTeamNames_invalidSide();
        testGame_namesAreSet_false();
        testGame_namesAreSet_onlyHomeSet();
        testGame_namesAreSet_true();
        testGame_scoreHome();
        testGame_scoreAway();
        testGame_scoreBeforeNames();
        testGame_undoHome();
        testGame_undoAway();
        testGame_undoEmpty();
        testGame_undoMultiple();
        testGame_canUndo();
        testGame_clearResetsScores();
        testGame_clearKeepsNames();
        testGame_clearResetsHistory();
        testGame_getLastPlayDescription_empty();
        testGame_getLastPlayDescription_afterScore();
        testGame_getLastPlayDescription_afterUndo();
        testGame_getPlayLog_order();
        testGame_updateHomeName_midGame();
        testGame_updateAwayName_midGame();
 
        System.out.println("\n─────────────────────────────────");
        System.out.println("Results: " + passed + " passed, " + failed + " failed.");
        if (failed == 0) System.out.println("ALL TESTS PASS ✓");
    }
 
    private static void testTeam_validName() {
        try {
            Team t = new Team("Aggies");
            check("Team: valid name stored correctly", t.getName().equals("Aggies"));
            check("Team: initial score is 0",          t.getScore() == 0);
        } catch (Exception e) { check("Team: valid name — unexpected exception", false); }
    }
 
    private static void testTeam_blankName() {
        try {
            new Team("   ");
            check("Team: blank name should throw", false);
        } catch (IllegalArgumentException e) {
            check("Team: blank name throws IAE", true);
        }
    }
 
    private static void testTeam_nullName() {
        try {
            new Team(null);
            check("Team: null name should throw", false);
        } catch (IllegalArgumentException e) {
            check("Team: null name throws IAE", true);
        }
    }
 
    private static void testTeam_addScore() {
        Team t = new Team("Aggies");
        t.addScore(6);
        check("Team: addScore +6",            t.getScore() == 6);
        t.addScore(3);
        check("Team: addScore +3 cumulative", t.getScore() == 9);
        t.addScore(-6);
        check("Team: addScore -6 (undo)",     t.getScore() == 3);
    }
 
    private static void testTeam_resetScore() {
        Team t = new Team("Aggies");
        t.addScore(21);
        t.resetScore();
        check("Team: resetScore returns to 0", t.getScore() == 0);
    }
 
    private static void testTeam_setName() {
        Team t = new Team("Aggies");
        t.setName("Longhorns");
        check("Team: setName updates name", t.getName().equals("Longhorns"));
    }
 
    private static void testTeam_setNameBlank() {
        Team t = new Team("Aggies");
        try {
            t.setName("");
            check("Team: setName blank should throw", false);
        } catch (IllegalArgumentException e) {
            check("Team: setName blank throws IAE", true);
        }
    }

    private static void testScoringPlay_touchdown() {
        ScoringPlay p = new ScoringPlay(ScoringPlay.Side.HOME, 6, "Aggies");
        check("ScoringPlay: touchdown points == 6",                p.getPoints() == 6);
        check("ScoringPlay: touchdown description has team name",   p.getDescription().contains("Aggies"));
        check("ScoringPlay: touchdown description has 'touchdown'", p.getDescription().contains("touchdown"));
    }
 
    private static void testScoringPlay_fieldGoal() {
        ScoringPlay p = new ScoringPlay(ScoringPlay.Side.AWAY, 3, "Gamecocks");
        check("ScoringPlay: field goal points == 3",         p.getPoints() == 3);
        check("ScoringPlay: field goal description correct", p.getDescription().contains("field goal"));
    }
 
    private static void testScoringPlay_safety() {
        ScoringPlay p = new ScoringPlay(ScoringPlay.Side.HOME, 2, "Aggies");
        check("ScoringPlay: safety points == 2",         p.getPoints() == 2);
        check("ScoringPlay: safety description correct", p.getDescription().contains("safety"));
    }
 
    private static void testScoringPlay_extraPoint() {
        ScoringPlay p = new ScoringPlay(ScoringPlay.Side.HOME, 1, "Aggies");
        check("ScoringPlay: extra point points == 1",         p.getPoints() == 1);
        check("ScoringPlay: extra point description correct", p.getDescription().contains("extra point"));
    }
 
    private static void testScoringPlay_invalidPoints() {
        try {
            new ScoringPlay(ScoringPlay.Side.HOME, 0, "Aggies");
            check("ScoringPlay: 0 points should throw", false);
        } catch (IllegalArgumentException e) {
            check("ScoringPlay: 0 points throws IAE", true);
        }
        try {
            new ScoringPlay(ScoringPlay.Side.HOME, -3, "Aggies");
            check("ScoringPlay: negative points should throw", false);
        } catch (IllegalArgumentException e) {
            check("ScoringPlay: negative points throws IAE", true);
        }
    }
 
    private static void testScoringPlay_sideIsCorrect() {
        ScoringPlay home = new ScoringPlay(ScoringPlay.Side.HOME, 6, "Aggies");
        ScoringPlay away = new ScoringPlay(ScoringPlay.Side.AWAY, 3, "Gamecocks");
        check("ScoringPlay: HOME side stored correctly", home.getSide() == ScoringPlay.Side.HOME);
        check("ScoringPlay: AWAY side stored correctly", away.getSide() == ScoringPlay.Side.AWAY);
    }
 
    private static void testGame_setTeamNames_home() {
        Game g = new Game();
        g.setTeamNames("home", "Aggies");
        check("Game: setTeamNames home sets name", g.getHomeName().equals("Aggies"));
    }
 
    private static void testGame_setTeamNames_away() {
        Game g = new Game();
        g.setTeamNames("away", "Gamecocks");
        check("Game: setTeamNames away sets name", g.getAwayName().equals("Gamecocks"));
    }
 
    private static void testGame_setTeamNames_blankName() {
        try {
            new Game().setTeamNames("home", "  ");
            check("Game: blank name should throw", false);
        } catch (IllegalArgumentException e) {
            check("Game: blank name throws IAE", true);
        }
    }
 
    private static void testGame_setTeamNames_nullName() {
        try {
            new Game().setTeamNames("home", null);
            check("Game: null name should throw", false);
        } catch (IllegalArgumentException e) {
            check("Game: null name throws IAE", true);
        }
    }
 
    private static void testGame_setTeamNames_invalidSide() {
        try {
            new Game().setTeamNames("middle", "Aggies");
            check("Game: invalid side should throw", false);
        } catch (IllegalArgumentException e) {
            check("Game: invalid side throws IAE", true);
        }
    }
 
    private static void testGame_namesAreSet_false() {
        check("Game: namesAreSet false on new game", !new Game().namesAreSet());
    }
 
    private static void testGame_namesAreSet_onlyHomeSet() {
        Game g = new Game();
        g.setTeamNames("home", "Aggies");
        check("Game: namesAreSet false when only home is set", !g.namesAreSet());
    }
 
    private static void testGame_namesAreSet_true() {
        check("Game: namesAreSet true after both teams set", ready().namesAreSet());
    }
 
    private static void testGame_scoreHome() {
        Game g = ready();
        g.scoreHome(6);
        check("Game: scoreHome +6",            g.getHomeScore() == 6);
        check("Game: away score unaffected",   g.getAwayScore() == 0);
        g.scoreHome(3);
        check("Game: scoreHome +3 cumulative", g.getHomeScore() == 9);
    }
 
    private static void testGame_scoreAway() {
        Game g = ready();
        g.scoreAway(3);
        check("Game: scoreAway +3",           g.getAwayScore() == 3);
        check("Game: home score unaffected",  g.getHomeScore() == 0);
    }
 
    private static void testGame_scoreBeforeNames() {
        Game g = new Game();
        try {
            g.scoreHome(6);
            check("Game: scoreHome before names should throw", false);
        } catch (IllegalStateException e) {
            check("Game: scoreHome before names throws ISE", true);
        }
        try {
            g.scoreAway(3);
            check("Game: scoreAway before names should throw", false);
        } catch (IllegalStateException e) {
            check("Game: scoreAway before names throws ISE", true);
        }
    }
 
    private static void testGame_undoHome() {
        Game g = ready();
        g.scoreHome(6);
        g.undoLast();
        check("Game: undo reverts home score", g.getHomeScore() == 0);
    }
 
    private static void testGame_undoAway() {
        Game g = ready();
        g.scoreAway(3);
        g.undoLast();
        check("Game: undo reverts away score", g.getAwayScore() == 0);
    }
 
    private static void testGame_undoEmpty() {
        try {
            ready().undoLast();
            check("Game: undo on empty history should throw", false);
        } catch (IllegalStateException e) {
            check("Game: undo on empty history throws ISE", true);
        }
    }
 
    private static void testGame_undoMultiple() {
        Game g = ready();
        g.scoreHome(6);   // home = 6
        g.scoreAway(3);   // away = 3
        g.scoreHome(1);   // home = 7
        g.undoLast();     // reverts home +1 → home = 6
        check("Game: undo reverts only the last action", g.getHomeScore() == 6);
        check("Game: undo does not touch away score",    g.getAwayScore() == 3);
        g.undoLast();     // reverts away +3 → away = 0
        check("Game: second undo reverts away score",    g.getAwayScore() == 0);
        g.undoLast();     // reverts home +6 → home = 0
        check("Game: third undo reverts home score",     g.getHomeScore() == 0);
        check("Game: nothing left to undo",              !g.canUndo());
    }
 
    private static void testGame_canUndo() {
        Game g = ready();
        check("Game: canUndo false before scoring",  !g.canUndo());
        g.scoreHome(6);
        check("Game: canUndo true after scoring",     g.canUndo());
        g.undoLast();
        check("Game: canUndo false after full undo",  !g.canUndo());
    }
 
    private static void testGame_clearResetsScores() {
        Game g = ready();
        g.scoreHome(6);
        g.scoreAway(3);
        g.clearGame();
        check("Game: clearGame resets home score", g.getHomeScore() == 0);
        check("Game: clearGame resets away score", g.getAwayScore() == 0);
    }
 
    private static void testGame_clearKeepsNames() {
        Game g = ready();
        g.clearGame();
        check("Game: clearGame keeps home name", g.getHomeName().equals("Aggies"));
        check("Game: clearGame keeps away name", g.getAwayName().equals("Gamecocks"));
    }
 
    private static void testGame_clearResetsHistory() {
        Game g = ready();
        g.scoreHome(6);
        g.clearGame();
        check("Game: clearGame clears undo history",          !g.canUndo());
        check("Game: clearGame clears last play description",  g.getLastPlayDescription().isEmpty());
    }
 
    private static void testGame_getLastPlayDescription_empty() {
        check("Game: lastPlayDescription empty on new game", ready().getLastPlayDescription().isEmpty());
    }
 
    private static void testGame_getLastPlayDescription_afterScore() {
        Game g = ready();
        g.scoreHome(6);
        String desc = g.getLastPlayDescription();
        check("Game: lastPlayDescription contains team name",   desc.contains("Aggies"));
        check("Game: lastPlayDescription contains 'touchdown'", desc.contains("touchdown"));
    }
 
    private static void testGame_getLastPlayDescription_afterUndo() {
        Game g = ready();
        g.scoreHome(6);
        g.scoreAway(3);
        g.undoLast(); // undo away FG, last play should now be home TD
        check("Game: lastPlayDescription after undo shows previous play",
              g.getLastPlayDescription().contains("Aggies"));
    }
 
    private static void testGame_getPlayLog_order() {
        Game g = ready();
        g.scoreHome(6);
        g.scoreAway(3);
        g.scoreHome(1);
        java.util.List<String> log = g.getPlayLog();
        check("Game: play log has 3 entries",              log.size() == 3);
        check("Game: play log first entry is oldest play", log.get(0).contains("touchdown"));
        check("Game: play log last entry is newest play",  log.get(2).contains("extra point"));
    }
 
    private static void testGame_updateHomeName_midGame() {
        Game g = ready();
        g.scoreHome(6);
        g.setTeamNames("home", "Longhorns");
        check("Game: home name updated mid-game",       g.getHomeName().equals("Longhorns"));
        check("Game: home score preserved after rename", g.getHomeScore() == 6);
        check("Game: away name unaffected by home rename", g.getAwayName().equals("Gamecocks"));
    }
 
    private static void testGame_updateAwayName_midGame() {
        Game g = ready();
        g.scoreAway(3);
        g.setTeamNames("away", "Sooners");
        check("Game: away name updated mid-game",       g.getAwayName().equals("Sooners"));
        check("Game: away score preserved after rename", g.getAwayScore() == 3);
        check("Game: home name unaffected by away rename", g.getHomeName().equals("Aggies"));
    }
 
    private static Game ready() {
        Game g = new Game();
        g.setTeamNames("home", "Aggies");
        g.setTeamNames("away", "Gamecocks");
        return g;
    }
 
    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("  PASS  " + testName);
            passed++;
        } else {
            System.out.println("  FAIL  " + testName);
            failed++;
        }
    }
}