package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameJFrame extends JFrame
{
    private GameJFrame gameJFrame = this;
    private JPanel mainPanel;
    private JPanel matrixPanel;
    private JPanel textPanel;
    private JLabel scoreValue;

    private int time = 1100;
    private Timer timer = new Timer(time, null);

    private int matrixPanelX;
    private int totalPanelX;
    private int y;

    public GameJFrame()
    {
        createGameJFrame();
        startGame();
    }

    private void setGameJFrameOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - totalPanelX / 2), (dimension.height / 2 - y / 2));
        this.setTitle("Tetris");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setFocusable(true);
        this.addKeyListener(new KeyListener()
        {
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
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    gameJFrame.dispose();
                    new MainMenuJFrame().setVisible(true);
                }
            }
        });
    }

    private void createGameJFrame()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = dimension.width / 4;

        matrixPanelX = x * 3 / 4;

        while (matrixPanelX % 10 != 0)
        {
            matrixPanelX = matrixPanelX + 1;
        }

        int textPanelX = matrixPanelX / 3;

        y = matrixPanelX * 2 + 1;

        matrixPanelX = matrixPanelX + 1;

        totalPanelX = matrixPanelX + textPanelX;

        matrixPanel = new JPanel(gridBagLayout);
        matrixPanel.setPreferredSize(new Dimension(matrixPanelX, y));
        matrixPanel.setBackground(Color.BLUE);

        textPanel = new JPanel(gridBagLayout);
        textPanel.setPreferredSize(new Dimension(textPanelX, y));

        gbc.anchor = GridBagConstraints.CENTER;

        JLabel scoreSign = new JLabel();
        scoreSign.setText("Wynik: ");
        scoreSign.setFont(new Font("Arial", Font.BOLD, (int)textPanelX / 8));
        scoreValue = new JLabel();
        scoreValue.setText("0");
        scoreValue.setFont(new Font("Arial", Font.BOLD, (int)textPanelX / 8));

        gbc.gridy = 0;
        textPanel.add(scoreSign, gbc);

        gbc.gridy = 1;
        textPanel.add(scoreValue, gbc);

        mainPanel = new JPanel(gridBagLayout);
        //mainPanel.setPreferredSize(new Dimension(401, 601));
        mainPanel.setPreferredSize(new Dimension(totalPanelX, y));

        gbc.gridy = 0;
        gbc.gridx = 0;
        mainPanel.add(matrixPanel);

        gbc.gridy = 0;
        gbc.gridx = 1;
        mainPanel.add(textPanel);

        add(mainPanel);

        setGameJFrameOptions();

        pack();
    }

    private void startGame()
    {
        Tetris tetris = new Tetris(gameJFrame, matrixPanel, time, timer, scoreValue, textPanel, matrixPanelX, y);
        tetris.start();
    }
}

