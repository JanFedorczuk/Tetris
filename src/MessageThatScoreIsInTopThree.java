package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MessageThatScoreIsInTopThree extends JDialog
{
    private JButton okButton;
    private JTextField nickInput;
    double x;

    public MessageThatScoreIsInTopThree()
    {
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        createMessage();
    }

    private void createMessage()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        x = dimension.width / 6.4;
        if ((int) (x / 19.2) < 12)
        {
            x = 1480 / 6.4;
        }
        int panelX = (int) x;
        int panelY = panelX / 5;
        int fontSize = (int) (x / 19.2);

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        JPanel textPanel = new JPanel(gridBagLayout);
        textPanel.setPreferredSize(new Dimension(panelX, panelY));

        gbc.gridy = 0;
        gbc.gridx = 0;
        int distance = (int) x / 25;
        gbc.insets = new Insets(distance,0,0,0);
        JLabel firstJlabel = new JLabel("Twój wynik znajdzie się w top 3.");
        firstJlabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        textPanel.add(firstJlabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        JLabel secondJlabel = new JLabel("Podaj swój nick:");
        secondJlabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        textPanel.add(secondJlabel, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        add(textPanel, gbc);

        JPanel inputPanel = new JPanel(gridBagLayout);
        inputPanel.setPreferredSize(new Dimension(panelX, panelY));

        nickInput = new JTextField();
        nickInput.setHorizontalAlignment(SwingConstants.CENTER);
        nickInput.setFont(new Font("Arial", Font.PLAIN, fontSize));
        nickInput.setDocument(new JTextFieldLimit(12));
        nickInput.setPreferredSize(new Dimension(panelX / 2,panelY / 2));

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(distance,0,0,0);
        inputPanel.add(nickInput, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        add(inputPanel, gbc);

        okButton = new JButton("Ok");
        okButton.setPreferredSize(new Dimension(panelX / 4, panelX / 8));
        //okButton.setPreferredSize(new Dimension(56, 26));
        okButton.setFont(new Font("Arial", Font.BOLD, fontSize));
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,distance,0);
        add(okButton, gbc);
        pack();

        setMessageOptions();
    }

    private void setMessageOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - ((int)x / 2)), (dimension.height / 2 - ((int)x / 2)));
        this.setTitle("Gratulacje!");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public JButton getOkButton()
    {
        return okButton;
    }

    public JTextField getNickInput()
    {
        return nickInput;
    }

}