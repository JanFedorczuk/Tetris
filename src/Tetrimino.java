package TetrisGame;

public interface Tetrimino
{
    void setInitialIndexes();
    void changeFigureIndexes(int integer);
    boolean checkIfWholeFigureIsOnMatrix();
    boolean checkIfFigureCanEnterOnMatrix();
    void pullFirstPartIntoMatrix();
    void pullSecondPartIntoMatrix();
    boolean moveCellsDown();
}
