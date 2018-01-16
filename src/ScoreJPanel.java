package TetrisGame;

import javax.swing.*;
import java.awt.*;

public class ScoreJPanel extends JPanel
{
    public ScoreJPanel(JLabel position, JLabel nick, JLabel score)
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = dimension.width;
        if ((x / 133) < 12)
        {
            x = 1596;
        }
        int nickJLabelX = x/ 9;
        int scoreJLabelX = x / 29;
        int jLabelY = x / 133;
        int distance = x / 160;

        position.setFont(new Font("Arial", Font.BOLD, jLabelY));

        nick.setPreferredSize(new Dimension(nickJLabelX, jLabelY));
        nick.setFont(new Font("Arial", Font.BOLD, jLabelY));

        score.setPreferredSize(new Dimension(scoreJLabelX, jLabelY));
        score.setFont(new Font("Arial", Font.BOLD, jLabelY));

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        this.add(position, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.insets = new Insets(0, distance,0,0);
        this.add(nick, gbc);

        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.insets = new Insets(0, distance,0,0);
        this.add(score, gbc);
    }
}
