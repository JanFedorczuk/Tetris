package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuJFrame extends JFrame
{
    private MainMenuJFrame mainMenu = this;

    private JButton startGameButton = new JButton("Rozpocznij grę");
    private JButton highestScoresButton = new JButton("Najlepsze wyniki");
    private JButton exitButton = new JButton("Wyjście");

    static HighestScoresJFrame highestScoresJFrame = new HighestScoresJFrame();

    public MainMenuJFrame()
    {
        createAndShowMainMenu();
    }

    private void createAndShowMainMenu()
    {
        setMainMenuOptions();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        buttonPanel.add(startGameButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(30,0,0,0);
        buttonPanel.add(highestScoresButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(30,0,0,0);
        buttonPanel.add(exitButton, gbc);

        gbc.insets = new Insets(30,0,0,0);
        this.add(buttonPanel, gbc);

        this.setVisible(true);
    }

    private void setMainMenuOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - 125), (dimension.height / 2 - 115));

        this.setSize(250, 230);
        this.setTitle("Tetris");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startGameButton.setPreferredSize(new Dimension(152, 26));
        highestScoresButton.setPreferredSize(new Dimension(152, 26));
        exitButton.setPreferredSize(new Dimension(152, 26));

        startGameButton.addActionListener(new startNewGame());
        highestScoresButton.addActionListener(new showHighestScores());
        exitButton.addActionListener(new exitGame());
    }

    private class showHighestScores implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            mainMenu.dispose();
            highestScoresJFrame.setVisible(true);
        }
    }

    private class startNewGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            mainMenu.dispose();
            new GameJFrame().setVisible(true);
        }
    }

    private class exitGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }
}