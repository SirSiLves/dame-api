package ch.ffhs.dameapi.model;


public class Stone {

    private final int stoneId;
    private Boolean isAlive, isQueen;
    private final Color stoneColor;

    public Stone(int stoneId, Color stoneColor) {
        this.stoneId = stoneId;
        this.isAlive = true;
        this.isQueen = false;
        this.stoneColor = stoneColor;
    }


    public int getStoneId() {
        return stoneId;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Boolean getQueen() {
        return isQueen;
    }

    public void setQueen(Boolean queen) {
        isQueen = queen;
    }

    public Color getStoneColor() {
        return stoneColor;
    }

}
