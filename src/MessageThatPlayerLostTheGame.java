package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageThatPlayerLostTheGame extends JFrame
{
   private JButton exitGameButton = new JButton("Wyjdź z gry");
   private JButton tryAgainButton = new JButton("Jeszcze raz");
   private JButton mainMenuButton = new JButton("Menu");

   public MessageThatPlayerLostTheGame()
   {
       createMessage();
   }

   private void setMessageOptions()
   {
       Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
       this.setLocation((dimension.width / 2 - 175), (dimension.height / 2 - 100));
       this.setTitle("Przegrana");
       this.setResizable(false);
       this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

       exitGameButton.setPreferredSize(new Dimension(102, 26));
       tryAgainButton.setPreferredSize(new Dimension(102, 26));
       mainMenuButton.setPreferredSize(new Dimension(102, 26));

       exitGameButton.addActionListener(new Exit());
   }

   private void createMessage()
   {
       setMessageOptions();

       GridBagLayout gridBagLayout = new GridBagLayout();
       GridBagConstraints gbc = new GridBagConstraints();
       this.setLayout(gridBagLayout);

       gbc.anchor = GridBagConstraints.NORTH;
       gbc.weighty = 1;
       gbc.weightx = 1;

       JPanel textPanel = new JPanel(gridBagLayout);
       textPanel.setPreferredSize(new Dimension(350, 50));

       gbc.gridy = 0;
       gbc.gridx = 0;
       gbc.insets = new Insets(10,0,0,0);
       textPanel.add(new JLabel("Niestety, przegrałeś."), gbc);

       gbc.gridy = 1;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,0,0,0);
       textPanel.add(new JLabel("Proszę wybrać jedną z opcji:"), gbc);

       gbc.gridy = 0;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,0,0,0);
       add(textPanel, gbc);

       JPanel buttonPanel = new JPanel(gridBagLayout);
       buttonPanel.setPreferredSize(new Dimension(350, 50));

       gbc.anchor = GridBagConstraints.WEST;

       gbc.gridy = 0;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,10,0,0);
       buttonPanel.add(exitGameButton, gbc);

       gbc.gridy = 0;
       gbc.gridx = 1;
       gbc.insets = new Insets(0,0,0,0);
       buttonPanel.add(tryAgainButton, gbc);

       gbc.gridy = 0;
       gbc.gridx = 2;
       gbc.insets = new Insets(0,0,0,0);
       buttonPanel.add(mainMenuButton, gbc);

       gbc.anchor = GridBagConstraints.NORTH;
       gbc.gridy = 1;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,0,5,0);
       add(buttonPanel, gbc);
       pack();
   }

   private class Exit implements ActionListener
   {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
   }

   public JButton getTryAgainButton()
   {
       return tryAgainButton;
   }
   public JButton getMainMenuButton()
   {
       return mainMenuButton;
   }

}
