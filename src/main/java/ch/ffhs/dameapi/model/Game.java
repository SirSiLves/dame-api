package ch.ffhs.dameapi.model;


public class Game {

    private final String sessionId;
    private final Board board;
    private final Validity validity;
    private final PossibleHopMoves possibleHopMoves;
    private final PossibleSimpleMoves possibleSimpleMoves;
    private boolean isBotEnabled;

    public Game(String sessionId) {
        this.sessionId = sessionId;
        this.board = new Board();
        this.validity = new Validity();
        this.possibleHopMoves = new PossibleHopMoves();
        this.possibleSimpleMoves = new PossibleSimpleMoves();
        this.isBotEnabled = false;
    }


    public PossibleHopMoves getPossibleHopMoves() {
        return possibleHopMoves;
    }

    public PossibleSimpleMoves getPossibleSimpleMoves() {
        return possibleSimpleMoves;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Validity getValidity() {
        return validity;
    }

    public Board getBoard() {
        return board;
    }

    public void setBotStatus(boolean isBotEnabled) {
        this.isBotEnabled = isBotEnabled;
    }

    public boolean getBotStatus() {
        return isBotEnabled;
    }
}
