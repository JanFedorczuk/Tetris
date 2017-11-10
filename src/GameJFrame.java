package TetrisGame;

import javax.swing.*;
import java.awt.*;

public class GameJFrame extends JFrame
{
    private GameJFrame gameJFrame = this;
    private JPanel mainPanel;
    private JPanel matrixPanel;
    private JPanel textPanel;
    private JLabel scoreValue;

    private int time = 1100;
    private Timer timer = new Timer(time, null);

    public GameJFrame()
    {
        createGameJFrame();
        startGame();
    }

    private void setGameJFrameOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - 201), (dimension.height / 2 - 301));
        this.setTitle("Tetris");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(407, 629));
    }

    private void createGameJFrame()
    {
        setGameJFrameOptions();

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        matrixPanel = new JPanel(gridBagLayout);
        matrixPanel.setPreferredSize(new Dimension(301, 601));

        textPanel = new JPanel(gridBagLayout);
        textPanel.setPreferredSize(new Dimension(100, 601));

        gbc.anchor = GridBagConstraints.CENTER;

        JLabel scoreSign = new JLabel();
        scoreSign.setText("Wynik: ");
        scoreValue = new JLabel();
        scoreValue.setText("0");

        gbc.gridy = 0;
        textPanel.add(scoreSign, gbc);

        gbc.gridy = 1;
        textPanel.add(scoreValue, gbc);

        mainPanel = new JPanel(gridBagLayout);
        mainPanel.setPreferredSize(new Dimension(401, 601));

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        mainPanel.add(matrixPanel);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.insets = new Insets(0,0,0,0);
        mainPanel.add(textPanel);

        gbc.gridy = 0;
        gbc.gridx = 0;

        gbc.insets = new Insets(0,0,0,0);
        add(mainPanel, gbc);
    }

    private void startGame()
    {
        Tetris tetris = new Tetris(gameJFrame, matrixPanel, time, timer, scoreValue, textPanel);
        tetris.start();
    }
}

