package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Integer.valueOf;

public class Tetris
{
    private JPanel matrixPanel;
    private JPanel textPanel;
    private GameJFrame gameJFrame;

    private Object[][] arrays = new Object[220][2];

    private ArrayList<Integer> previousInt = new ArrayList<>();
    private ArrayList<Tetrimino> shape = new ArrayList<>();

    private int time;
    private Timer timer;

    private int booleanIndex = 0;
    private int colorIndex = 1;

    private int score = 0;
    private JLabel scoreValue;
    private ArrayList<Integer> bestScores = new ArrayList<>();

    private int amountOfFullLines = 0;
    private int cellsRemoverValue;
    private int cellsRemoverValue2;
    private Timer timerForCellsRemover = new Timer(100, new CellsRemover());
    private ArrayList<Integer> fullRowArrayList = new ArrayList<>();

    private TetrisActionListener tetrisActionListener = new TetrisActionListener();

    private int matrixPanelX;
    private int y;

    public Tetris(GameJFrame gameJFrame, JPanel matrixPanel, int time, Timer timer, JLabel scoreValue, JPanel textPanel,
    int matrixPanelX, int y)
    {
        this.gameJFrame = gameJFrame;
        this.matrixPanel = matrixPanel;
        this.time = time;
        this.timer = timer;
        this.scoreValue = scoreValue;
        this.textPanel = textPanel;
        this.matrixPanelX = matrixPanelX;
        this.y = y;
    }

    private class TetrisActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (shape.isEmpty())
            {
                getRandomTetrimino();
                if(shape.get(0).checkIfFigureCanEnterOnMatrix())
                {
                    shape.get(0).pullFirstPartIntoMatrix();
                }
                else
                {
                    timer.stop();
                    showMessage();
                }
            }
            else
            {
                if(!shape.get(0).checkIfWholeFigureIsOnMatrix())
                {
                    if(shape.get(0).checkIfFigureCanEnterOnMatrix())
                    {
                        shape.get(0).pullSecondPartIntoMatrix();
                    }
                    else
                    {
                        timer.stop();
                        showMessage();
                    }
                }
                else
                {
                    if(shape.get(0).moveCellsDown())
                    {
                    }
                    else
                    {
                        shape.clear();
                    }
                }
            }
            matrixPanel.repaint();
        }
    }

    private class CellsRemover implements ActionListener
    {
        public void actionPerformed(ActionEvent actionEvent)
        {
            if (arrays[cellsRemoverValue2 - 9][colorIndex] == Color.LIGHT_GRAY)
            {
                shiftAllCells(cellsRemoverValue2);
                matrixPanel.repaint();
                fullRowArrayList.remove(0);
                if(fullRowArrayList.isEmpty())
                {
                    timerForCellsRemover.stop();
                    changeScore(amountOfFullLines);
                    textPanel.repaint();
                    decreaseTime();
                    timer = new Timer(time, tetrisActionListener);
                    timer.start();
                }
                else
                {
                    decreaseTime();
                    cellsRemoverValue = fullRowArrayList.get(0);
                    cellsRemoverValue2 = fullRowArrayList.get(0);
                }
            }
            else
            {
                arrays[cellsRemoverValue][colorIndex] = Color.LIGHT_GRAY;
                cellsRemoverValue = cellsRemoverValue - 1;
                matrixPanel.repaint();
            }
        }
    }

    class MatrixPainter extends JComponent
    {
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            drawMatrix(g2);
            //drawNumbers(g2);
        }
    }

    private void drawMatrix(Graphics2D g2)
    {
        int i = 10;
        for (int y = 1; y < 21; y++)
        {
            for (int x = 0; x < 10; x++)
            {
                Rectangle2D rect = new Rectangle2D.Double((x * (matrixPanelX / 10)), ((y - 1) * (matrixPanelX / 10)),
                        (matrixPanelX / 10), (matrixPanelX / 10));

                g2.setPaint(Color.BLACK);
                g2.draw(rect);

                g2.setPaint((Color) arrays[i][1]);
                g2.fill(rect);

                g2.setPaint(Color.BLACK);
                g2.draw(rect);
                i++;
            }
        }
    }

    private void drawNumbers(Graphics2D g2)
    {
        int i = 10;
        for (int y = 1; y < 21; y++)
        {
            for (int x = 0; x < 10; x++)
            {
                g2.drawString(String.valueOf(i), (x * 30) + 7, ((y - 1) * 30) + 15);
                i++;
            }
        }
    }

    public void start()
    {
        gameJFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                timer.stop();
                windowEvent.getWindow().dispose();
                new MainMenuJFrame().setVisible(true);
            }
        });

        initialiseArrays();

        MatrixPainter matrixPainter = new MatrixPainter();
        matrixPainter.setPreferredSize(new Dimension(matrixPanelX, y));
        matrixPanel.add(matrixPainter);

        timer.addActionListener(tetrisActionListener);
        matrixPainter.repaint();
        timer.start();
    }

    private void startGameAgain()
    {
        initialiseArrays();
        gameJFrame.repaint();
        timer = new Timer(time, tetrisActionListener);
        timer.start();
    }

    private void initialiseArrays()
    {
        for (int a = 0; a < 210; a++)
        {
            arrays[a][0] = true;
            arrays[a][1] = Color.LIGHT_GRAY;
        }
        for (int a = 210; a < 220; a++)
        {
            arrays[a][0] = false;
            arrays[a][1] = Color.LIGHT_GRAY;
        }
    }

    private void getRandomTetrimino()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 8);
        if (!previousInt.isEmpty())
        {
            while (randomNum == previousInt.get(0))
            {
                randomNum = ThreadLocalRandom.current().nextInt(1, 8);
            }
        }
        previousInt.add(0, randomNum);
        shape.add(new TetriminoFigure(randomNum));
        shape.get(0).setInitialIndexes();
    }

    private void checkIfRowsAreFull()
    {
        for(int i = 19; i <= 209; i = i + 10)
        {
            checkRow(i);
        }
        if(!fullRowArrayList.isEmpty())
        {
            cellsRemoverValue = fullRowArrayList.get(0);
            cellsRemoverValue2 = fullRowArrayList.get(0);
            amountOfFullLines = fullRowArrayList.size();
            timerForCellsRemover.start();
        }
        else
        {
            timer.start();
        }
    }

    private boolean checkRow(int firstRowIndex)
    {
        if(!(Boolean) arrays[firstRowIndex]   [booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -1][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -2][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -3][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -4][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -5][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -6][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -7][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -8][booleanIndex] &&
           !(Boolean) arrays[firstRowIndex -9][booleanIndex])
        {
            fullRowArrayList.add(firstRowIndex);
            return true;
        }
        else
        {
            return false;
        }
    }

    private void shiftAllCells(int firstRowIndex)
    {
        for(int i = firstRowIndex; i >= 10; i--)
        {
            arrays[i][booleanIndex] = arrays[i - 10][booleanIndex];
            arrays[i][colorIndex] = arrays[i - 10][colorIndex];
        }
    }

    private void changeScore(int amountOfFullLines)
    {
        switch (amountOfFullLines)
        {
            case 1:
                score = score + 50;
                scoreValue.setText(Integer.toString(score));
                break;

            case 2:
                score = score + 150;
                scoreValue.setText(Integer.toString(score));
                break;

            case 3:
                score = score + 450;
                scoreValue.setText(Integer.toString(score));
                break;

            case 4:
                score = score + 1350;
                scoreValue.setText(Integer.toString(score));
                break;
        }
    }

    private void decreaseTime()
    {

        if(time > 800)
        {
            time = time - 30;
        }
        else if(800 >= time && time > 550)
        {
            time = time - 25;
        }
        else if(550 >= time && time > 350)
        {
            time = time - 20;
        }
        else if(350 >= time && time > 200)
        {
            time = time - 15;
        }
        else if(200 >= time && time > 100)
        {
            time = time - 10;
        }
    }

    private ArrayList<Boolean> fillBestScores()
    {
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();
        if(bestScores != null)
        {
            bestScores.clear();
        }
        if(MainMenuJFrame.highestScoresJFrame.getFirstScore() != 0)
        {
            bestScores.add(MainMenuJFrame.highestScoresJFrame.getFirstScore());

            if(MainMenuJFrame.highestScoresJFrame.getSecondScore() != 0)
            {
                bestScores.add(MainMenuJFrame.highestScoresJFrame.getSecondScore());

                if(MainMenuJFrame.highestScoresJFrame.getThirdScore() != 0)
                {
                    bestScores.add(MainMenuJFrame.highestScoresJFrame.getThirdScore());
                }
            }
        }

        for(int i = 0; i < 3; i++)
        {
            if(bestScores.size() >= (valueOf(i) + 1))
            {
                if (score > bestScores.get(i))
                {
                    booleanArrayList.add(true);
                }
                else
                {
                    booleanArrayList.add(false);
                }
            }
            else
            {
                booleanArrayList.add(true);
            }
        }

        return booleanArrayList;
    }

    private void showMessage()
    {
        ArrayList<Boolean> booleanArrayList = fillBestScores();
        int trueValueOccurrences = Collections.frequency(booleanArrayList, true);
        MessageThatPlayerLostTheGame secondMessage = createMessageThatPlayerLostTheGameAndAddListeners();
        if ((score == 0) || (trueValueOccurrences == 0))
        {
            secondMessage.setVisible(true);
        }
        else
        {
            MessageThatScoreIsInTopThree firstMessage = createTop3MessageAndAddListeners(booleanArrayList, trueValueOccurrences, secondMessage);
            firstMessage.setVisible(true);
        }
    }

    private MessageThatScoreIsInTopThree createTop3MessageAndAddListeners
            (ArrayList<Boolean> booleanArrayList, int trueValuesOccurrences, MessageThatPlayerLostTheGame secondMessage)
    {
        MessageThatScoreIsInTopThree firstMessage = new MessageThatScoreIsInTopThree();
        firstMessage.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                windowEvent.getWindow().dispose();
                secondMessage.setVisible(true);
            }
        });
        firstMessage.getOkButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(!(firstMessage.getNickInput().getText().replaceAll("\\s","").equals("")))
                {
                    compareAndSetScoresInTop3(firstMessage, secondMessage, booleanArrayList, trueValuesOccurrences);
                }
            }
        });

        firstMessage.setFocusable(true);
        firstMessage.getOkButton().setFocusable(true);
        firstMessage.getNickInput().setFocusable(true);

        firstMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if(!(firstMessage.getNickInput().getText().replaceAll("\\s","").equals("")))
                    {
                        compareAndSetScoresInTop3(firstMessage, secondMessage, booleanArrayList, trueValuesOccurrences);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    firstMessage.getNickInput().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    firstMessage.getOkButton().requestFocus();
                }
            }
        });
        firstMessage.getOkButton().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e)
                {
                }

                @Override
                public void keyPressed(KeyEvent e)
                {
                }

                @Override
                public void keyReleased(KeyEvent e)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        if(!(firstMessage.getNickInput().getText().replaceAll("\\s","").equals("")))
                        {
                            compareAndSetScoresInTop3(firstMessage, secondMessage, booleanArrayList, trueValuesOccurrences);
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                    {
                        firstMessage.getNickInput().requestFocus();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                    {
                        firstMessage.getNickInput().requestFocus();
                    }
                }
            });
        firstMessage.getNickInput().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if(!(firstMessage.getNickInput().getText().replaceAll("\\s","").equals("")))
                    {
                        compareAndSetScoresInTop3(firstMessage, secondMessage, booleanArrayList, trueValuesOccurrences);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    firstMessage.getOkButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    firstMessage.getOkButton().requestFocus();
                }
            }
        });

        return firstMessage;
    }

    private void compareAndSetScoresInTop3(MessageThatScoreIsInTopThree firstMessage,
        MessageThatPlayerLostTheGame secondMessage, ArrayList<Boolean> booleanArrayList, int trueValuesOccurrences)
    {
        String nick = firstMessage.getNickInput().getText();
        if(booleanArrayList.isEmpty() && score != 0)
        {
        }
        else
        {
            if(trueValuesOccurrences == 1)
            {
                MainMenuJFrame.highestScoresJFrame.setNickAndScore(nick, score, 3);
                MainMenuJFrame.highestScoresJFrame.repaintMainJPanel();
            }
            else if(trueValuesOccurrences == 2)
            {
                MainMenuJFrame.highestScoresJFrame.setNickAndScore(MainMenuJFrame.highestScoresJFrame.getSecondNick(),
                MainMenuJFrame.highestScoresJFrame.getSecondScore(), 3);

                MainMenuJFrame.highestScoresJFrame.setNickAndScore(nick, score, 2);
                MainMenuJFrame.highestScoresJFrame.repaintMainJPanel();
            }
            else if(trueValuesOccurrences == 3)
            {
                MainMenuJFrame.highestScoresJFrame.setNickAndScore(MainMenuJFrame.highestScoresJFrame.getSecondNick(),
                        MainMenuJFrame.highestScoresJFrame.getSecondScore(), 3);

                MainMenuJFrame.highestScoresJFrame.setNickAndScore(MainMenuJFrame.highestScoresJFrame.getFirstNick(),
                        MainMenuJFrame.highestScoresJFrame.getFirstScore(), 2);

                MainMenuJFrame.highestScoresJFrame.setNickAndScore(nick, score, 1);

                MainMenuJFrame.highestScoresJFrame.repaintMainJPanel();
            }
        }
        firstMessage.dispose();
        secondMessage.setVisible(true);
    }

    private MessageThatPlayerLostTheGame createMessageThatPlayerLostTheGameAndAddListeners()
    {

        MessageThatPlayerLostTheGame secondMessage = new MessageThatPlayerLostTheGame();

        secondMessage.getMainMenuButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                secondMessage.dispose();
                gameJFrame.dispose();
                new MainMenuJFrame().setVisible(true);
            }
        });

        secondMessage.getTryAgainButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                secondMessage.dispose();
                resetGame();
                startGameAgain();
            }
        });

        secondMessage.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                windowEvent.getWindow().dispose();
            }
        });

        secondMessage.setFocusable(true);
        secondMessage.getMainMenuButton().setFocusable(true);
        secondMessage.getTryAgainButton().setFocusable(true);
        secondMessage.getExitGameButton().setFocusable(true);

        secondMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ||
                    e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    secondMessage.getMainMenuButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A ||
                        e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    secondMessage.getExitGameButton().requestFocus();
                }
            }
        });

        secondMessage.getExitGameButton().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    secondMessage.getTryAgainButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    secondMessage.getMainMenuButton().requestFocus();
                }
                if(e.getKeyCode() ==  KeyEvent.VK_ENTER)
                {
                    System.exit(0);
                }
            }
        });

        secondMessage.getTryAgainButton().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    secondMessage.getMainMenuButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    secondMessage.getExitGameButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    secondMessage.dispose();
                    resetGame();
                    startGameAgain();
                }

            }
        });

        secondMessage.getMainMenuButton().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    secondMessage.getExitGameButton().requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    secondMessage.getTryAgainButton().requestFocus();
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    secondMessage.dispose();
                    gameJFrame.dispose();
                    new MainMenuJFrame().setVisible(true);
                }
            }
        });


        return secondMessage;
    }

    private void resetGame()
    {
        score = 0;
        time = 1100;
        previousInt.clear();
        shape.clear();
        timer.removeActionListener(tetrisActionListener);
        scoreValue.setText("0");
    }

    public class TetriminoFigure implements Tetrimino
    {
        private int firstIndex ;
        private int secondIndex;
        private int thirdIndex ;
        private int fourthIndex;

        private int state = 1;
        private int tetriminoType;
        
        private KeysListener keysListener = new KeysListener();

        private TetriminoFigure(int tetriminoType)
        {
            this.tetriminoType
                    = tetriminoType;
        }

        public void setInitialIndexes()
        {
            switch (tetriminoType)
            {
                case 1:
                    firstIndex  = -6;
                    secondIndex = -5;
                    thirdIndex  = 4;
                    fourthIndex = 5;
                    break;

                case 2:
                    firstIndex  = -6;
                    secondIndex = 3;
                    thirdIndex  = 4;
                    fourthIndex = 5;
                    break;

                case 3:
                    firstIndex  = 3;
                    secondIndex = 4;
                    thirdIndex  = 5;
                    fourthIndex = -5;
                    break;

                case 4:
                    firstIndex  = 5;
                    secondIndex = 4;
                    thirdIndex  = 3;
                    fourthIndex = -7;
                    break;

                case 5:
                    firstIndex  = 3;
                    secondIndex = 4;
                    thirdIndex  = 5;
                    fourthIndex = 6;
                    break;

                case 6:
                    firstIndex  = 3;
                    secondIndex = 4;
                    thirdIndex  = -6;
                    fourthIndex = -5;
                    break;

                case 7:
                    firstIndex  = -7;
                    secondIndex = -6;
                    thirdIndex  = 4;
                    fourthIndex = 5;
                    break;
            }
        }

        private void changeIndexesToTrue()
        {
            arrays[firstIndex][booleanIndex]  = true;
            arrays[secondIndex][booleanIndex] = true;
            arrays[thirdIndex][booleanIndex]  = true;
            arrays[fourthIndex][booleanIndex] = true;
        }

        private void changeIndexesToFalse()
        {
            arrays[firstIndex][booleanIndex]  = false;
            arrays[secondIndex][booleanIndex] = false;
            arrays[thirdIndex][booleanIndex]  = false;
            arrays[fourthIndex][booleanIndex] = false;
        }

        public void changeFigureIndexes(int integer)
        {
            firstIndex  = firstIndex  +  integer;
            secondIndex = secondIndex +  integer;
            thirdIndex  = thirdIndex  +  integer;
            fourthIndex = fourthIndex +  integer;
        }

        private boolean checkBooleanValuesOfLowerCells()
        {
            if((Boolean) arrays[firstIndex +  10][booleanIndex]  &&
               (Boolean) arrays[secondIndex + 10][booleanIndex]  &&
               (Boolean) arrays[thirdIndex +  10][booleanIndex]  &&
               (Boolean) arrays[fourthIndex + 10][booleanIndex])
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private boolean checkBooleanValuesOfRightCells()
        {
            if(checkRightCells())
            {
                if ((Boolean) arrays[firstIndex + 1][booleanIndex ] &&
                    (Boolean) arrays[secondIndex + 1][booleanIndex] &&
                    (Boolean) arrays[thirdIndex + 1][booleanIndex ] &&
                    (Boolean) arrays[fourthIndex + 1][booleanIndex])
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        private boolean checkBooleanValuesOfLeftCells()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex  - 1][booleanIndex ] &&
                   (Boolean) arrays[secondIndex - 1][booleanIndex ] &&
                   (Boolean) arrays[thirdIndex  - 1][booleanIndex ] &&
                   (Boolean) arrays[fourthIndex - 1][booleanIndex ])
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        private boolean checkRightCells()
        {
            if(((firstIndex  + 1) % 10 != 0)  &&
               ((secondIndex + 1) % 10 != 0)  &&
               ((thirdIndex  + 1) % 10 != 0)  &&
               ((fourthIndex + 1) % 10 != 0))
            {
                return true;
            }
            else
            {
                return false;
            }

        }

        private boolean checkLeftCells()
        {
            if((((firstIndex  - 1) % 10 != 9) &&
                ((secondIndex - 1) % 10 != 9) &&
                ((thirdIndex  - 1) % 10 != 9) &&
                ((fourthIndex - 1) % 10 != 9)))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private void changeCellsColorToLightGray()
        {
            arrays[firstIndex][colorIndex] = Color.LIGHT_GRAY;
            arrays[secondIndex][colorIndex] = Color.LIGHT_GRAY;
            arrays[thirdIndex][colorIndex] = Color.LIGHT_GRAY;
            arrays[fourthIndex][colorIndex] = Color.LIGHT_GRAY;
        }

        private void changeCellsColorAccordingToTheirType()
        {
            {
                switch (tetriminoType)
                {
                    case 1:
                        arrays[firstIndex][colorIndex]  = Color.RED;
                        arrays[secondIndex][colorIndex] = Color.RED;
                        arrays[thirdIndex][colorIndex]  = Color.RED;
                        arrays[fourthIndex][colorIndex] = Color.RED;
                        break;

                    case 2:
                        arrays[firstIndex][colorIndex]  = Color.CYAN;
                        arrays[secondIndex][colorIndex] = Color.CYAN;
                        arrays[thirdIndex][colorIndex]  = Color.CYAN;
                        arrays[fourthIndex][colorIndex] = Color.CYAN;
                        break;

                    case 3:
                        arrays[firstIndex][colorIndex]  = Color.YELLOW;
                        arrays[secondIndex][colorIndex] = Color.YELLOW;
                        arrays[thirdIndex][colorIndex]  = Color.YELLOW;
                        arrays[fourthIndex][colorIndex] = Color.YELLOW;
                        break;

                    case 4:
                        arrays[firstIndex][colorIndex]  = Color.BLUE;
                        arrays[secondIndex][colorIndex] = Color.BLUE;
                        arrays[thirdIndex][colorIndex]  = Color.BLUE;
                        arrays[fourthIndex][colorIndex] = Color.BLUE;
                        break;

                    case 5:
                        arrays[firstIndex][colorIndex]  = Color.ORANGE;
                        arrays[secondIndex][colorIndex] = Color.ORANGE;
                        arrays[thirdIndex][colorIndex]  = Color.ORANGE;
                        arrays[fourthIndex][colorIndex] = Color.ORANGE;
                        break;

                    case 6:
                        arrays[firstIndex][colorIndex]  = Color.GREEN;
                        arrays[secondIndex][colorIndex] = Color.GREEN;
                        arrays[thirdIndex][colorIndex]  = Color.GREEN;
                        arrays[fourthIndex][colorIndex] = Color.GREEN;
                        break;

                    case 7:
                        arrays[firstIndex][colorIndex]  = Color.MAGENTA;
                        arrays[secondIndex][colorIndex] = Color.MAGENTA;
                        arrays[thirdIndex][colorIndex]  = Color.MAGENTA;
                        arrays[fourthIndex][colorIndex] = Color.MAGENTA;
                        break;
                }
            }
        }

        public boolean checkIfFigureCanEnterOnMatrix()
        {
            if((Boolean) arrays[firstIndex  + 10][booleanIndex] &&
               (Boolean) arrays[secondIndex + 10][booleanIndex] &&
               (Boolean) arrays[thirdIndex  + 10][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 10][booleanIndex])
            {
                return true;
            }
            else
            {

                return false;
            }
        }

        public boolean checkIfWholeFigureIsOnMatrix()
        {
            if(firstIndex  > 9 &&
               secondIndex > 9 &&
               thirdIndex  > 9 &&
               fourthIndex > 9)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public void pullFirstPartIntoMatrix()
        {
            changeFigureIndexes(10);
            changeCellsColorAccordingToTheirType();
            if (checkIfWholeFigureIsOnMatrix())
            {
                changeIndexesToFalse();
                gameJFrame.addKeyListener(keysListener);
            }
        }

        public void pullSecondPartIntoMatrix()
        {
            changeCellsColorToLightGray();
            changeFigureIndexes(10);
            changeCellsColorAccordingToTheirType();
            changeIndexesToFalse();
            gameJFrame.addKeyListener(keysListener);
        }

        public boolean moveCellsDown()
        {
            changeIndexesToTrue();
            if(checkBooleanValuesOfLowerCells())
            {
                changeCellsColorToLightGray();
                changeFigureIndexes(10);
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                return true;
            }
            else
            {
                changeIndexesToFalse();
                gameJFrame.removeKeyListener(keysListener);
                timer.stop();
                shape.clear();
                startCheckingRows();
                return false;
            }
        }

        private void moveCellsRight()
        {
            changeIndexesToTrue();
            if(checkBooleanValuesOfRightCells())
            {
                changeCellsColorToLightGray();
                changeFigureIndexes(1);
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                matrixPanel.repaint();
            }
            else
            {
                changeIndexesToFalse();
            }
        }
        
        private void moveCellsLeft()
        {
            changeIndexesToTrue();
            if(checkBooleanValuesOfLeftCells())
            {
                changeCellsColorToLightGray();
                changeFigureIndexes(-1);
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                matrixPanel.repaint();
            }
            else
            {
                changeIndexesToFalse();
            }
        }

        private boolean checkITetrimino()
        {
            if(state == 2)
            {
                if((firstIndex + 1 ) % 10 != 0  &&
                   (secondIndex + 1) % 10 != 0  &&
                   (thirdIndex + 1 ) % 10 != 0  &&
                   (fourthIndex + 1) % 10 != 0  &&

                   (firstIndex - 1 ) % 10 != 0  &&
                   (secondIndex - 1) % 10 != 0  &&
                   (thirdIndex - 1 ) % 10 != 0  &&
                   (fourthIndex - 1) % 10 != 0  &&

                   (firstIndex  % 10 != 0)    &&
                   (secondIndex % 10 != 0)    &&
                   (thirdIndex  % 10 != 0)    &&
                   (fourthIndex % 10 != 0))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if((firstIndex + 1 ) % 10 != 0 &&
                   (secondIndex + 1) % 10 != 0 &&
                   (thirdIndex + 1 ) % 10 != 0 &&
                   (fourthIndex + 1) % 10 != 0 &&

                   (firstIndex + 2 ) % 10 != 0 &&
                   (secondIndex + 2) % 10 != 0 &&
                   (thirdIndex + 2 ) % 10 != 0 &&
                   (fourthIndex + 2) % 10 != 0 &&

                   (firstIndex  % 10 != 0)     &&
                   (secondIndex % 10 != 0)     &&
                   (thirdIndex  % 10 != 0)     &&
                   (fourthIndex % 10 != 0))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        private void startCheckingRows()
        {
            checkIfRowsAreFull();
        }

        private void changeTetriminoPositionE()
        {
            switch (tetriminoType)
            {
                case 1:
                    break;

                case 2:
                    changeTPositionE();
                    break;

                case 3:
                    changeLPositionE();
                    break;

                case 4:
                    changeJPositionE();
                    break;
                case 5:
                    changeIPositionE();
                    break;
                case 6:
                    changeSPositionE();
                    break;
                case 7:
                    changeZPositionE();
                    break;
            }
        }

        private void changeTetriminoPositionQ()
        {
            switch (tetriminoType)
            {
                case 1:
                    break;

                case 2:
                    changeTPositionQ();
                    break;

                case 3:
                    changeLPositionQ();
                    break;

                case 4:
                    changeJPositionQ();
                    break;
                case 5:
                    changeIPositionQ();
                    break;
                case 6:
                    changeSPositionQ();
                    break;
                case 7:
                    changeZPositionQ();
                    break;
            }
        }

        //

        private void changeTPositionE()
        {
            switch (state)
            {
                case 1:
                    changeTPositionFrom1To2();
                    break;

                case 2:
                    changeTPositionFrom2To3();
                    break;

                case 3:
                    changeTPositionFrom3To4();
                    break;

                case 4:
                    changeTPositionFrom4To1();
                    break;
            }
        }

        private void changeTPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeTPositionFrom1To4();
                    break;

                case 4:
                    changeTPositionFrom4To3();
                    break;

                case 3:
                    changeTPositionFrom3To2();
                    break;

                case 2:
                    changeTPositionFrom2To1();
                    break;
            }
        }

        private void changeTPositionFrom1To2()
        {
            if ((Boolean) arrays[fourthIndex + 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 11;
                secondIndex = secondIndex - 9;
                fourthIndex = fourthIndex + 9;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeTPositionFrom2To3()
        {
            if(checkLeftCells())
            {
                if ((Boolean) arrays[fourthIndex - 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 9;
                    secondIndex = secondIndex + 11;
                    fourthIndex = fourthIndex - 11;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    gameJFrame.repaint();
                }
            }
        }
        private void changeTPositionFrom3To4()
        {
            if ((Boolean) arrays[fourthIndex - 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 11;
                secondIndex = secondIndex  + 9;
                fourthIndex = fourthIndex - 9;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeTPositionFrom4To1()
        {
            if(checkRightCells())
            {
                if ((Boolean) arrays[fourthIndex + 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 9;
                    secondIndex = secondIndex - 11;
                    fourthIndex = fourthIndex + 11;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        private void changeTPositionFrom1To4()
        {
            if ((Boolean) arrays[secondIndex + 11][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 9;
                secondIndex = secondIndex + 11;
                fourthIndex = fourthIndex - 11;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeTPositionFrom4To3()
        {
            if(checkRightCells())
            {
                if ((Boolean) arrays[secondIndex - 9][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 11;
                    secondIndex = secondIndex - 9;
                    fourthIndex = fourthIndex + 9;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    gameJFrame.repaint();
                }
            }
        }
        private void changeTPositionFrom3To2()
        {
            if ((Boolean) arrays[secondIndex - 11][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 9;
                secondIndex = secondIndex - 11;
                fourthIndex = fourthIndex + 11;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeTPositionFrom2To1()
        {
            if(checkLeftCells())
            {
                if ((Boolean) arrays[secondIndex + 9][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 11;
                    secondIndex = secondIndex + 9;
                    fourthIndex = fourthIndex - 9;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        //

        //

        private void changeLPositionE()
        {
            switch (state)
            {
                case 1:
                    changeLPositionFrom1To2();
                    break;

                case 2:
                    changeLPositionFrom2To3();
                    break;

                case 3:
                    changeLPositionFrom3To4();
                    break;

                case 4:
                    changeLPositionFrom4To1();
                    break;
            }
        }
        private void changeLPositionFrom1To2()
        {
            if((Boolean) arrays[firstIndex - 9][booleanIndex] &&
               (Boolean) arrays[thirdIndex + 9][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 9;
                thirdIndex = thirdIndex + 9;
                fourthIndex = fourthIndex + 20;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeLPositionFrom2To3()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex + 11][booleanIndex] &&
                   (Boolean) arrays[thirdIndex - 11][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 11;
                    thirdIndex = thirdIndex - 11;
                    fourthIndex = fourthIndex - 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeLPositionFrom3To4()
        {
            if((Boolean) arrays[firstIndex + 9][booleanIndex] &&
               (Boolean) arrays[thirdIndex - 9][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 9;
                thirdIndex = thirdIndex - 9;
                fourthIndex = fourthIndex - 20;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeLPositionFrom4To1()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex - 11][booleanIndex] &&
                   (Boolean) arrays[thirdIndex + 11][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 11;
                    thirdIndex = thirdIndex + 11;
                    fourthIndex = fourthIndex + 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }

        }

        private void changeLPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeLPositionFrom1To4();
                    break;

                case 4:
                    changeLPositionFrom4To3();
                    break;

                case 3:
                    changeLPositionFrom3To2();
                    break;

                case 2:
                    changeLPositionFrom2To1();
                    break;
            }
        }
        private void changeLPositionFrom1To4()
        {
            if((Boolean) arrays[firstIndex + 11][booleanIndex] &&
               (Boolean) arrays[thirdIndex - 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 11;
                thirdIndex = thirdIndex - 11;
                fourthIndex = fourthIndex - 2;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeLPositionFrom4To3()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex - 9][booleanIndex] &&
                   (Boolean) arrays[thirdIndex + 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 9;
                    thirdIndex = thirdIndex + 9;
                    fourthIndex = fourthIndex + 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeLPositionFrom3To2()
        {
            if((Boolean) arrays[firstIndex - 11][booleanIndex] &&
               (Boolean) arrays[thirdIndex + 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 11;
                thirdIndex = thirdIndex + 11;
                fourthIndex = fourthIndex + 2;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeLPositionFrom2To1()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex + 9][booleanIndex] &&
                   (Boolean) arrays[thirdIndex - 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 9;
                    thirdIndex = thirdIndex - 9;
                    fourthIndex = fourthIndex - 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }

        }

        //

        //

        private void changeJPositionE()
        {
            switch (state)
            {
                case 1:
                    changeJPositionFrom1To2();
                    break;

                case 2:
                    changeJPositionFrom2To3();
                    break;

                case 3:
                    changeJPositionFrom3To4();
                    break;

                case 4:
                    changeJPositionFrom4To1();
                    break;
            }
        }
        private void changeJPositionFrom1To2()
        {
            if((Boolean) arrays[firstIndex + 9][booleanIndex] &&
               (Boolean) arrays[thirdIndex - 9][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 9;
                thirdIndex = thirdIndex - 9;
                fourthIndex = fourthIndex + 2;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeJPositionFrom2To3()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex - 11][booleanIndex] &&
                        (Boolean) arrays[thirdIndex + 11][booleanIndex] &&
                        (Boolean) arrays[fourthIndex + 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 11;
                    thirdIndex = thirdIndex + 11;
                    fourthIndex = fourthIndex + 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeJPositionFrom3To4()
        {
            if((Boolean) arrays[firstIndex - 9][booleanIndex] &&
               (Boolean) arrays[thirdIndex + 9][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 9;
                thirdIndex = thirdIndex + 9;
                fourthIndex = fourthIndex - 2;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeJPositionFrom4To1()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex + 11][booleanIndex] &&
                        (Boolean) arrays[thirdIndex - 11][booleanIndex] &&
                        (Boolean) arrays[fourthIndex - 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 11;
                    thirdIndex = thirdIndex - 11;
                    fourthIndex = fourthIndex - 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        private void changeJPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeJPositionFrom1To4();
                    break;

                case 4:
                    changeJPositionFrom4To3();
                    break;

                case 3:
                    changeJPositionFrom3To2();
                    break;

                case 2:
                    changeJPositionFrom2To1();
                    break;
            }
        }
        private void changeJPositionFrom1To4()
        {
            if((Boolean) arrays[firstIndex - 11][booleanIndex] &&
               (Boolean) arrays[thirdIndex + 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 11;
                thirdIndex = thirdIndex + 11;
                fourthIndex = fourthIndex + 20;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeJPositionFrom4To3()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex + 9][booleanIndex] &&
                   (Boolean) arrays[thirdIndex  - 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 9;
                    thirdIndex = thirdIndex - 9;
                    fourthIndex = fourthIndex + 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeJPositionFrom3To2()
        {
            if((Boolean) arrays[firstIndex + 11][booleanIndex] &&
               (Boolean) arrays[thirdIndex - 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 11;
                thirdIndex = thirdIndex - 11;
                fourthIndex = fourthIndex - 20;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeJPositionFrom2To1()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex - 9][booleanIndex] &&
                   (Boolean) arrays[thirdIndex + 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 9;
                    thirdIndex = thirdIndex + 9;
                    fourthIndex = fourthIndex - 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        //

        //

        private void changeIPositionE()
        {
            switch (state)
            {
                case 1:
                    changeIPositionFrom1To2();
                    break;

                case 2:
                    changeIPositionFrom2To3();
                    break;

                case 3:
                    changeIPositionFrom3To4();
                    break;

                case 4:
                    changeIPositionFrom4To1();
                    break;
            }
        }
        private void changeIPositionFrom1To2()
        {
            if(firstIndex < 20)
            {
            }
            else
            {
                if((Boolean) arrays[firstIndex - 8][booleanIndex] &&
                   (Boolean) arrays[thirdIndex + 10][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 19][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 8;
                    secondIndex = secondIndex + 1;
                    thirdIndex = thirdIndex  + 10;
                    fourthIndex = fourthIndex + 19;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 2;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeIPositionFrom2To3()
        {
            if(checkITetrimino())
            {
                if((Boolean) arrays[firstIndex + 21][booleanIndex] &&
                   (Boolean) arrays[thirdIndex - 1][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 12][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 21;
                    secondIndex = secondIndex + 10;
                    thirdIndex = thirdIndex  - 1;
                    fourthIndex = fourthIndex - 12;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeIPositionFrom3To4()
        {
            if((Boolean) arrays[firstIndex + 8][booleanIndex] &&
               (Boolean) arrays[thirdIndex - 10][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 19][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex + 8;
                secondIndex = secondIndex - 1;
                thirdIndex = thirdIndex  - 10;
                fourthIndex = fourthIndex - 19;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeIPositionFrom4To1()
        {
            if(checkITetrimino())
            {
                if((Boolean) arrays[firstIndex - 21][booleanIndex] &&
                   (Boolean) arrays[thirdIndex + 1][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 12][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 21;
                    secondIndex = secondIndex - 10;
                    thirdIndex = thirdIndex  + 1;
                    fourthIndex = fourthIndex + 12;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        private void changeIPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeIPositionFrom1To4();
                    break;

                case 4:
                    changeIPositionFrom4To3();
                    break;

                case 3:
                    changeIPositionFrom3To2();
                    break;

                case 2:
                    changeIPositionFrom2To1();
                    break;
            }
        }
        private void changeIPositionFrom1To4()
        {
            if(firstIndex < 20)
            {
            }
            else
            {
                if((Boolean) arrays[firstIndex + 21][booleanIndex] &&
                   (Boolean) arrays[secondIndex + 10][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 12][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 21;
                    secondIndex = secondIndex + 10;
                    thirdIndex = thirdIndex  - 1;
                    fourthIndex = fourthIndex - 12;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 4;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeIPositionFrom4To3()
        {
            if(checkITetrimino())
            {
                if((Boolean) arrays[firstIndex - 8][booleanIndex] &&
                   (Boolean) arrays[secondIndex + 1][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 19][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 8;
                    secondIndex = secondIndex + 1;
                    thirdIndex = thirdIndex  + 10;
                    fourthIndex = fourthIndex + 19;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeIPositionFrom3To2()
        {
            if((Boolean) arrays[firstIndex - 21][booleanIndex] &&
               (Boolean) arrays[secondIndex - 10][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 12][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();

                firstIndex = firstIndex - 21;
                secondIndex = secondIndex - 10;
                thirdIndex = thirdIndex  + 1;
                fourthIndex = fourthIndex + 12;

                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();

                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeIPositionFrom2To1()
        {
            if(checkITetrimino())
            {
                if((Boolean) arrays[firstIndex + 8][booleanIndex] &&
                   (Boolean) arrays[secondIndex - 1][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 19][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 8;
                    secondIndex = secondIndex - 1;
                    thirdIndex = thirdIndex  - 10;
                    fourthIndex = fourthIndex- 19;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        //

        //

        private void changeSPositionE()
        {
            switch (state)
            {
                case 1:
                    changeSPositionFrom1To2();
                    break;

                case 2:
                    changeSPositionFrom2To3();
                    break;

                case 3:
                    changeSPositionFrom3To4();
                    break;

                case 4:
                    changeSPositionFrom4To1();
                    break;
            }
        }
        private void changeSPositionFrom1To2()
        {
            if((Boolean) arrays[thirdIndex + 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex - 9;
                thirdIndex = thirdIndex  + 11;
                fourthIndex = fourthIndex + 20;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 2;
                matrixPanel.repaint();
            }

        }
        private void changeSPositionFrom2To3()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[thirdIndex + 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 11;
                    thirdIndex = thirdIndex  + 9;
                    fourthIndex = fourthIndex - 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeSPositionFrom3To4()
        {
            if((Boolean) arrays[thirdIndex - 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 20][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex + 9;
                thirdIndex = thirdIndex  - 11;
                fourthIndex = fourthIndex - 20;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeSPositionFrom4To1()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[thirdIndex - 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 2][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 11;
                    thirdIndex = thirdIndex  - 9;
                    fourthIndex = fourthIndex + 2;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        private void changeSPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeSPositionFrom1To4();
                    break;

                case 4:
                    changeSPositionFrom4To3();
                    break;

                case 3:
                    changeSPositionFrom3To2();
                    break;

                case 2:
                    changeSPositionFrom2To1();
                    break;
            }
        }
        private void changeSPositionFrom1To4()
        {
            if((Boolean) arrays[firstIndex + 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex + 11;
                thirdIndex = thirdIndex  + 9;
                fourthIndex = fourthIndex - 2;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 4;
                matrixPanel.repaint();
            }

        }
        private void changeSPositionFrom4To3()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex - 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex - 9;
                    thirdIndex = thirdIndex  + 11;
                    fourthIndex = fourthIndex + 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeSPositionFrom3To2()
        {
            if((Boolean) arrays[firstIndex - 11][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 2][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex - 11;
                thirdIndex = thirdIndex  - 9;
                fourthIndex = fourthIndex + 2;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeSPositionFrom2To1()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex + 9][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 20][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();

                    firstIndex = firstIndex + 9;
                    thirdIndex = thirdIndex  - 11;
                    fourthIndex = fourthIndex - 20;

                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();

                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        //

        //

        private void changeZPositionE()
        {
            switch (state)
            {
                case 1:
                    changeZPositionFrom1To2();
                    break;

                case 2:
                    changeZPositionFrom2To3();
                    break;

                case 3:
                    changeZPositionFrom3To4();
                    break;

                case 4:
                    changeZPositionFrom4To1();
                    break;
            }
        }
        private void changeZPositionFrom1To2()
        {
            if((Boolean) arrays[firstIndex + 2][booleanIndex] &&
               (Boolean) arrays[fourthIndex + 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex + 2;
                secondIndex = secondIndex  + 11;
                fourthIndex = fourthIndex + 9;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeZPositionFrom2To3()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex + 20][booleanIndex] &&
                   (Boolean) arrays[fourthIndex - 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();
                    firstIndex = firstIndex + 20;
                    secondIndex = secondIndex  + 9;
                    fourthIndex = fourthIndex - 11;
                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();
                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeZPositionFrom3To4()
        {
            if((Boolean) arrays[firstIndex - 2][booleanIndex] &&
               (Boolean) arrays[fourthIndex - 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex - 2;
                secondIndex = secondIndex  - 11;
                fourthIndex = fourthIndex - 9;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeZPositionFrom4To1()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex - 20][booleanIndex] &&
                   (Boolean) arrays[fourthIndex + 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();
                    firstIndex = firstIndex - 20;
                    secondIndex = secondIndex  - 9;
                    fourthIndex = fourthIndex + 11;
                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();
                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        private void changeZPositionQ()
        {
            switch (state)
            {
                case 1:
                    changeZPositionFrom1To4();
                    break;

                case 4:
                    changeZPositionFrom4To3();
                    break;

                case 3:
                    changeZPositionFrom3To2();
                    break;

                case 2:
                    changeZPositionFrom2To1();
                    break;
            }
        }
        private void changeZPositionFrom1To4()
        {
            if((Boolean) arrays[firstIndex + 20][booleanIndex] &&
               (Boolean) arrays[secondIndex + 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex + 20;
                secondIndex = secondIndex  + 9;
                fourthIndex = fourthIndex - 11;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 4;
                matrixPanel.repaint();
            }
        }
        private void changeZPositionFrom4To3()
        {
            if(checkRightCells())
            {
                if((Boolean) arrays[firstIndex + 2][booleanIndex] &&
                   (Boolean) arrays[secondIndex + 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();
                    firstIndex = firstIndex + 2;
                    secondIndex = secondIndex  + 11;
                    fourthIndex = fourthIndex + 9;
                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();
                    state = 3;
                    matrixPanel.repaint();
                }
            }
        }
        private void changeZPositionFrom3To2()
        {
            if((Boolean) arrays[firstIndex - 20][booleanIndex] &&
               (Boolean) arrays[secondIndex - 9][booleanIndex])
            {
                changeIndexesToTrue();
                changeCellsColorToLightGray();
                firstIndex = firstIndex - 20;
                secondIndex = secondIndex  - 9;
                fourthIndex = fourthIndex + 11;
                changeIndexesToFalse();
                changeCellsColorAccordingToTheirType();
                state = 2;
                matrixPanel.repaint();
            }
        }
        private void changeZPositionFrom2To1()
        {
            if(checkLeftCells())
            {
                if((Boolean) arrays[firstIndex - 2][booleanIndex] &&
                   (Boolean) arrays[secondIndex - 11][booleanIndex])
                {
                    changeIndexesToTrue();
                    changeCellsColorToLightGray();
                    firstIndex = firstIndex - 2;
                    secondIndex = secondIndex  - 11;
                    fourthIndex = fourthIndex - 9;
                    changeIndexesToFalse();
                    changeCellsColorAccordingToTheirType();
                    state = 1;
                    matrixPanel.repaint();
                }
            }
        }

        //

        //

        private class KeysListener implements KeyListener
        {
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_E)
                {
                    changeTetriminoPositionE();
                }
                if (e.getKeyCode() == KeyEvent.VK_Q)
                {
                    changeTetriminoPositionQ();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    moveCellsRight();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    moveCellsLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    timer.start();
                }
                if(e.getKeyCode() == KeyEvent.VK_MINUS)
                {
                    timer.stop();
                }

            }
            public void keyTyped(KeyEvent e)
            {
            }
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    timer.stop();
                    moveCellsDown();
                    matrixPanel.repaint();
                }
            }
        }
    }
}
