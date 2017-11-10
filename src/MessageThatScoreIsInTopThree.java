package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MessageThatScoreIsInTopThree extends JFrame
{
    private JButton okButton;
    private JTextField nickInput;

    public MessageThatScoreIsInTopThree()
    {
        createAndShowMessage();
    }

    private void createAndShowMessage()
    {
        setMessageOptions();

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gridBagLayout);

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        JPanel textPanel = new JPanel(gridBagLayout);
        textPanel.setPreferredSize(new Dimension(250, 50));

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(10,0,0,0);
        textPanel.add(new JLabel("Twój wynik znajdzie się w top 3."), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        textPanel.add(new JLabel("Podaj swój nick:"), gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        add(textPanel, gbc);

        JPanel inputPanel = new JPanel(gridBagLayout);
        inputPanel.setPreferredSize(new Dimension(200, 40));

        nickInput = new JTextField();
        nickInput.setDocument(new JTextFieldLimit(12));
        nickInput.setPreferredSize(new Dimension(100,20));

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(10,0,0,0);
        inputPanel.add(nickInput, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,0,0);
        add(inputPanel, gbc);

        okButton = new JButton("Ok");
        okButton.setPreferredSize(new Dimension(56, 26));
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.insets = new Insets(0,0,10,0);
        add(okButton, gbc);
        pack();
        setVisible(true);
    }

    private void setMessageOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - 125), (dimension.height / 2 - 108));
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