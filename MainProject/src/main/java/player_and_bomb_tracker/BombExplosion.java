package player_and_bomb_tracker;

public class BombExplosion
{
    private int rowIndex;
    private int colIndex;
    private int duration = 5;

    public BombExplosion(int rowIndex, int colIndex)
    {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getDuration() {
        return duration;
    }

    public synchronized void setDuration(final int duration) {
        this.duration = duration;
    }
}
