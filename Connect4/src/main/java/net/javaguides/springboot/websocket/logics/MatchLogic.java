package net.javaguides.springboot.websocket.logics;


import net.javaguides.springboot.websocket.models.Disc;
import net.javaguides.springboot.websocket.models.Match;
import net.javaguides.springboot.websocket.models.Player;
import net.javaguides.springboot.websocket.models.Point;

import java.util.ArrayList;

public class MatchLogic {
    private static MatchLogic INSTANCE;

    private MatchLogic() {
        this.matches = new ArrayList<>();
        this.openMatchAvailable = false;
        this.openMatch = null;
        this.checkWinLogic = CheckWinLogic.getInstance();
    }

    public static MatchLogic getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MatchLogic();
        }

        return INSTANCE;
    }

    private ArrayList<Match> matches;
    private boolean openMatchAvailable;
    private Match openMatch;
    private CheckWinLogic checkWinLogic;

    public Match searchMatch(Player player){
        if (openMatchAvailable){
            joinOpenMatch(player);
            return openMatch;
        }

        newMatch(player);
        return null;
    }

    private void newMatch(Player player){
        Match newMatch = new Match(player);
        openMatch = newMatch;
        openMatchAvailable = true;
    }

    private void joinOpenMatch(Player player){
        openMatch.addPlayer(player);
        openMatchAvailable = false;
    }

    public void placeDisc(int lobbyId, Point point){
        Match match = findMatch(lobbyId);
        Disc disc = match.placeDisc(point);
        checkWin(match, disc);
    }

    private boolean checkWin(Match match, Disc disc){
        if(match.getPlayers().get(match.getTurn()).getDiscCount() < 18){
            return checkWinLogic.checkWin(match.getGrid(), disc);
        }
        return false;
    }

    private Match findMatch(int lobbyId){
        return matches.stream().filter(m -> m.getId() == lobbyId).findAny().get();
    }
}
