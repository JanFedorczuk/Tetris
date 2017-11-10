package TetrisGame;

import javax.swing.*;
import java.awt.*;

public class ScoreJPanel extends JPanel
{
    public ScoreJPanel(JLabel position, JLabel nick, JLabel score)
    {
        nick.setPreferredSize(new Dimension(110,12));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        this.add(position, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.insets = new Insets(0,10,0,0);
        this.add(nick, gbc);

        gbc.gridy = 0;
        gbc.gridx = 2;
        gbc.insets = new Insets(0,10,0,0);
        this.add(score, gbc);
    }
}
