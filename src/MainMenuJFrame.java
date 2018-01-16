package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuJFrame extends JFrame
{
    private MainMenuJFrame mainMenu = this;

    private JButton startGameButton = new JButton("Rozpocznij grę");
    private JButton highestScoresButton = new JButton("Top 3");
    private JButton exitButton = new JButton("Wyjście");

    static HighestScoresJFrame highestScoresJFrame = new HighestScoresJFrame();

    double x;

    public MainMenuJFrame()
    {
        createAndShowMainMenu();
    }

    private void createAndShowMainMenu()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        x = dimension.width / 6.4;
        if ((int) (x / 19.2) < 12)
        {
            x = 1480 / 6.4;
        }
        int mainMenuX = (int) x;
        int mainMenuY = (int) x;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setPreferredSize(new Dimension(mainMenuX, mainMenuY));

        double buttonX = x / 1.64;
        double buttonY = x / 9.61;

        startGameButton.setPreferredSize    (new Dimension((int) buttonX, (int) buttonY));
        highestScoresButton.setPreferredSize(new Dimension((int) buttonX, (int) buttonY));
        exitButton.setPreferredSize         (new Dimension((int) buttonX, (int) buttonY));

        double fontSize = x / 19.2;
        startGameButton.setFont(    new Font("Arial", Font.BOLD, (int)fontSize));
        highestScoresButton.setFont(new Font("Arial", Font.BOLD, (int)fontSize));
        exitButton.setFont(         new Font("Arial", Font.BOLD, (int)fontSize));

        double y = x / 5.81;
        int distance = (int) y;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(distance,0,0,0);
        buttonPanel.add(startGameButton, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(2 * distance + (int)buttonY,0,0,0);
        buttonPanel.add(highestScoresButton, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(3 * distance + 2 * (int)buttonY,0,0,0);
        buttonPanel.add(exitButton, gbc);

        gbc.insets = new Insets(0,0,0,0);
        this.add(buttonPanel, gbc);
        pack();

        setMainMenuOptions();

        this.setVisible(true);
    }

    private void setMainMenuOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dimension.width / 2) - ((int) x / 2)), (dimension.height / 2) - ((int) x / 2));

        this.setTitle("Tetris");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startGameButton.addActionListener(new startNewGame());
        highestScoresButton.addActionListener(new showHighestScores());
        exitButton.addActionListener(new exitGame());

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
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ||
                    e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    startGameButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    exitGame();
                }
            }
        });

        startGameButton.setFocusable(true);
        highestScoresButton.setFocusable(true);
        exitButton.setFocusable(true);

        startGameButton.addKeyListener(new KeyListener() {
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
                    startNewGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    highestScoresButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    exitButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    exitGame();
                }
            }
        });
        highestScoresButton.addKeyListener(new KeyListener() {
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
                    showHighestScores();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    exitButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    startGameButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    exitGame();
                }
            }
        });
        exitButton.addKeyListener(new KeyListener() {
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
                    exitGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    startGameButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    highestScoresButton.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    exitGame();
                }
            }
        });
    }

    private class showHighestScores implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            showHighestScores();
        }
    }

    private class startNewGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            startNewGame();
        }
    }

    private class exitGame implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            exitGame();
        }
    }

    private void showHighestScores()
    {
        mainMenu.dispose();
        highestScoresJFrame.repaintMainJPanel();
        highestScoresJFrame.setVisible(true);
    }

    private void startNewGame()
    {
        mainMenu.dispose();
        new GameJFrame().setVisible(true);
    }

    private void exitGame()
    {
        System.exit(0);
    }

}