package nl.fhict.websocket.logics;


import nl.fhict.websocket.controller.MessageController;
import nl.fhict.websocket.models.Disc;
import nl.fhict.websocket.models.Match;
import nl.fhict.websocket.models.Player;
import nl.fhict.websocket.models.Point;

import java.util.ArrayList;

public class MatchLogic {
    private static MatchLogic INSTANCE;

    private MatchLogic(MessageController controller) {
        this.matches = new ArrayList<>();
        this.openMatchAvailable = false;
        this.openMatch = null;
        this.checkWinLogic = CheckWinLogic.getInstance();
        this.controller = controller;
    }

    public static MatchLogic getInstance(MessageController controller) {
        if(INSTANCE == null) {
            INSTANCE = new MatchLogic(controller);
        }

        return INSTANCE;
    }

    private ArrayList<Match> matches;
    private boolean openMatchAvailable;
    private Match openMatch;
    private CheckWinLogic checkWinLogic;
    private MessageController controller;

    public Match searchMatch(String username){
        Player player = new Player(username);
        if (openMatchAvailable){
            joinOpenMatch(player);
            return openMatch;
        }

        newMatch(player);
        return null;
    }

    private void newMatch(Player player){
        Match newMatch = new Match(player, matches.size());
        matches.add(newMatch);
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
        if(checkWin(match, disc)){
            controller.sendWinMessage(match.getPlayers(), match.getLastPlacedPlayer());
        }else{
            controller.sendPlaceDiscMessage(match, disc, true); // TODO: check if can be placed!!!
        }
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
