package TetrisGame;

import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageThatPlayerLostTheGame extends JDialog
{
   private JButton exitGameButton = new JButton("Wyjdź z gry");
   private JButton tryAgainButton = new JButton("Jeszcze raz");
   private JButton mainMenuButton = new JButton("Menu");

   double x;

   public MessageThatPlayerLostTheGame()
   {
       this.setModalityType(ModalityType.APPLICATION_MODAL);
       createMessage();
   }

   private void setMessageOptions()
   {
       Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
       this.setLocation((dimension.width / 2 - (int)x / 2), (dimension.height / 2 - (int)x / 4));
       this.setTitle("Przegrana");
       this.setResizable(false);
       this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
   }

   private void createMessage()
   {
       GridBagLayout gridBagLayout = new GridBagLayout();
       GridBagConstraints gbc = new GridBagConstraints();
       this.setLayout(gridBagLayout);

       Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
       x = dimension.width / 3.7;
       if((int) x / 32 < 12)
       {
           x = 1480 / 3.7;
       }
       int panelX = (int) x;
       int panelY = panelX / 8;
       int fontSize = (int) x / 32;

       JPanel textPanel = new JPanel(gridBagLayout);
       textPanel.setPreferredSize(new Dimension(panelX, panelY));

       gbc.anchor = GridBagConstraints.NORTH;
       gbc.weighty = 1;
       gbc.weightx = 1;

       gbc.gridy = 0;
       gbc.gridx = 0;
       int distance = panelY / 5;
       gbc.insets = new Insets(distance,0,0,0);
       JLabel firstJlabel = new JLabel("Niestety, przegrałeś");
       firstJlabel.setFont(new Font("Arial", Font.BOLD, fontSize));
       textPanel.add(firstJlabel, gbc);

       gbc.gridy = 1;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,0,0,0);
       JLabel secondJlabel = new JLabel("Proszę wybrać jedną z opcji:");
       secondJlabel.setFont(new Font("Arial", Font.BOLD, fontSize));
       textPanel.add(secondJlabel, gbc);

       gbc.gridy = 0;
       gbc.gridx = 0;
       gbc.insets = new Insets(0,0,0,0);
       add(textPanel, gbc);

       JPanel buttonPanel = new JPanel(gridBagLayout);
       buttonPanel.setPreferredSize(new Dimension(panelX, panelY));

       gbc.anchor = GridBagConstraints.WEST;

       gbc.gridy = 0;
       gbc.gridx = 0;
       gbc.insets = new Insets(0, distance,0,0);
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
       gbc.insets = new Insets(0,0,0,0);
       add(buttonPanel, gbc);

       int buttonX = (int) (x / 3.33);
       int buttonY = (int) x / 16;
       exitGameButton.setPreferredSize(new Dimension(buttonX, buttonY));
       exitGameButton.setFont(    new Font("Arial", Font.BOLD, fontSize));

       tryAgainButton.setPreferredSize(new Dimension(buttonX, buttonY));
       tryAgainButton.setFont(    new Font("Arial", Font.BOLD, fontSize));

       mainMenuButton.setPreferredSize(new Dimension(buttonX, buttonY));
       mainMenuButton.setFont(    new Font("Arial", Font.BOLD, fontSize));

       exitGameButton.addActionListener(new Exit());

       setMessageOptions();

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
   public JButton getExitGameButton() { return exitGameButton; }

}
