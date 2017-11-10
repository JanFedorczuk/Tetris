package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HighestScoresJFrame extends JFrame
{
    private JFrame highestScoresJFrame = this;
    private JPanel mainPanel;
    private JButton resetButton;

    private String nick1 = "------------------------";
    private String nick2 = "------------------------";
    private String nick3 = "------------------------";

    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;

    public HighestScoresJFrame()
    {
        setHighestScoresOptions();
        createMainPanelAndResetButton();
        setVisible(false);
    }

    private void setHighestScoresOptions()
    {
        this.setLayout(new GridBagLayout());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - 95), (dimension.height / 2 - 100));
        this.setSize(198, 160);
        this.setTitle("Top 3");
        this.setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                windowEvent.getWindow().dispose();
                new MainMenuJFrame().setVisible(true);
            }
        });
    }

    private void createMainPanelAndResetButton()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 1;
        gbc.weightx = 1;

        JPanel firstPanel = new ScoreJPanel    (new JLabel("1:"),
                new JLabel(nick1), new JLabel(Integer.toString(score1)));
        JPanel secondPanel = new ScoreJPanel   (new JLabel("2:"),
                new JLabel(nick2), new JLabel(Integer.toString(score2)));
        JPanel thirdPanel = new ScoreJPanel    (new JLabel("3:"),
                new JLabel(nick3), new JLabel(Integer.toString(score3)));

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(190, 90));
        mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(10,10,0,0);
        mainPanel.add(firstPanel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,10,0,0);
        mainPanel.add(secondPanel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,10,0,0);
        mainPanel.add(thirdPanel, gbc);

        gbc.insets = new Insets(1,1,0,0);
        highestScoresJFrame.add(mainPanel, gbc);

        resetButton = new JButton("Reset Scores");
        resetButton.setPreferredSize(new Dimension(152, 26));
        resetButton.addActionListener(new resetButtonListener());
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(90,0,0,0);
        highestScoresJFrame.add(resetButton, gbc);
    }

    private class resetButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            resetScores();
            repaintMainJPanel();
            setVisible(true);
        }
    }

    private void resetScores()
    {
        nick1 = "------------------------";
        nick2 = "------------------------";
        nick3 = "------------------------";

        score1 = 0;
        score2 = 0;
        score3 = 0;
    }

    public void repaintMainJPanel()
    {
        disposeMainPanelAndResetButton();
        createMainPanelAndResetButton();
        repaint();
    }

    private void disposeMainPanelAndResetButton()
    {
        remove(mainPanel);
        remove(resetButton);
    }

    public String getNick1()
    {
        return nick1;
    }

    public String getNick2()
    {
        return nick2;
    }

    public String getNick3()
    {
        return nick3;
    }

    public void setNick1(String newNick)
    {
        nick1 = newNick;
    }

    public void setNick2(String newNick)
    {
        nick2 = newNick;
    }

    public void setNick3(String newNick)
    {
        nick3 = newNick;
    }

    public int getScore1()
    {
        return score1;
    }

    public int getScore2()
    {
        return score2;
    }

    public int getScore3()
    {
        return score3;
    }

    public void setScore1(int newScore)
    {
        score1 = newScore;
    }

    public void setScore2(int newScore)
    {
        score2 = newScore;
    }

    public void setScore3(int newScore)
    {
        score3 = newScore;
    }
}
