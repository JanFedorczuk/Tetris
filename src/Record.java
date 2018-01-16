package TetrisGame;

import java.util.ArrayList;

public class Record
{
    private int position;
    private int score;
    private String nick;

    public Record(int position, int score, String nick)
    {
        this.position = position;
        this.score = score;
        this.nick = nick;
    }

    public void updateRecords(ArrayList<Integer> arrayList)
    {
        setPosition(arrayList.indexOf(score));
    }

    private void setPosition(int newPosition)
    {
        this.position = newPosition + 1;
    }

    public int getPosition()
    {
        return position;
    }

    public String getNick()
    {
        return nick;
    }

    public int getScore()
    {
        return score;
    }
}
