package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class HighestScoresJFrame extends JFrame
{
    private JFrame highestScoresJFrame = this;
    private JPanel mainPanel;
    private JButton resetButton;

    private String firstNick;
    private String secondNick;
    private String thirdNick;

    private int firstScore;
    private int secondScore;
    private int thirdScore;

    private  static final String NICK = "nick";
    private  static final String SCORE = "score";
    
    private  static final String NICK1 = "nick1";
    private  static final String SCORE1 = "score1";
    
    private  static final String NICK2 = "nick2";
    private  static final String SCORE2 = "score2";
    
    private  static final String NICK3 = "nick3";
    private  static final String SCORE3 = "score3";

    private Properties properties = new Properties();
    private File propertiesFile = new File("Properties.txt");

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
        this.setLocation((dimension.width / 2 - (int)(dimension.width / 5.3 / 2)), (dimension.height / 2 -
                (int)(dimension.width / 10.6 + dimension.width / 53 + dimension.width / 63.6) / 2));
        this.setTitle("Top 3");
        this.setResizable(false);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowEvent)
            {
                windowEvent.getWindow().dispose();
                new MainMenuJFrame().setVisible(true);
            }
        });

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
                    highestScoresJFrame.dispose();
                    new MainMenuJFrame().setVisible(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_R)
                {
                    resetScores();
                    repaintMainJPanel();
                    setVisible(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S ||
                    e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    resetButton.requestFocus();
                }
            }
        });
    }

    private void createMainPanelAndResetButton()
    {
        checkCorrectAndSetHighestScores();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        gbc.weightx = 1;

        JPanel firstPanel = new ScoreJPanel     (new JLabel("1:"),
                new JLabel(firstNick), new JLabel(Integer.toString(firstScore)));
        JPanel secondPanel = new ScoreJPanel   (new JLabel("2:"),
                new JLabel(secondNick), new JLabel(Integer.toString(secondScore)));
        JPanel thirdPanel = new ScoreJPanel    (new JLabel("3:"),
                new JLabel(thirdNick), new JLabel(Integer.toString(thirdScore)));

        mainPanel = new JPanel(new GridBagLayout());

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = dimension.width;
        if ((x / 133) < 12)
        {
            x = 1596;
        }
        int mainPanelX = (int) (x / 5.3);
        int mainPanelY = (int) (x / 10.6);

        mainPanel.setPreferredSize(new Dimension(mainPanelX, mainPanelY));
        mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        gbc.gridy = 0;
        gbc.gridx = 0;
        mainPanel.add(firstPanel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(secondPanel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(thirdPanel, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        highestScoresJFrame.add(mainPanel, gbc);

        resetButton = new JButton("Reset Scores");
        resetButton.setPreferredSize(new Dimension(mainPanelX / 2, mainPanelX / 12));
        resetButton.setFont(new Font("Arial", Font.BOLD, x / 133));
        resetButton.addActionListener(new resetButtonListener());

        resetButton.setFocusable(true);
        resetButton.addKeyListener(new KeyListener()
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
                    highestScoresJFrame.dispose();
                    new MainMenuJFrame().setVisible(true);
                }
                if (e.getKeyCode() == KeyEvent.VK_R || e.getKeyCode() == KeyEvent.VK_ENTER )
                {
                    resetScores();
                    repaintMainJPanel();
                    setVisible(true);
                }
            }
        });

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(mainPanelX / 20,0,mainPanelX / 20,0);
        highestScoresJFrame.add(resetButton, gbc);

        pack();
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
        resetPropertiesFile();
        resetPropertiesVariables();
    }

    private void resetPropertiesFile()
    {
        try
        {
            properties.put(NICK1, "------------");
            properties.put(NICK2, "------------");
            properties.put(NICK3, "------------");
            properties.put(SCORE1, "0");
            properties.put(SCORE2, "0");
            properties.put(SCORE3, "0");

            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
            properties.store(fileOutputStream, "");
        }
        catch (Exception e)
        {
        }
    }

    private void resetPropertiesVariables()
    {
        firstNick = properties.getProperty(NICK1);
        secondNick = properties.getProperty(NICK2);
        thirdNick = properties.getProperty(NICK3);

        firstScore = Integer.parseInt(properties.getProperty(SCORE1));
        secondScore = Integer.parseInt(properties.getProperty(SCORE2));
        thirdScore = Integer.parseInt(properties.getProperty(SCORE3));
    }

    private void restorePropertiesFile()
    {
        try
        {
            if(firstNick == null && secondNick == null && thirdNick == null)
            {
                resetPropertiesFile();
            }
            else
            {
                if(firstNick != null)
                {
                    properties.put(NICK1, firstNick);
                    properties.put(SCORE1, String.valueOf(firstScore));
                }
                if(secondNick != null)
                {
                    properties.put(NICK2, secondNick);
                    properties.put(SCORE2, String.valueOf(secondScore));
                }
                if(thirdNick != null)
                {
                    properties.put(NICK3, thirdNick);
                    properties.put(SCORE3, String.valueOf(thirdScore));
                }

                FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                properties.store(fileOutputStream, "");
            }
        }
        catch (Exception e)
        {
        }
    }

    private boolean checkPropertiesConditions(int number)
    {
        if((!properties.containsKey(NICK + number))                        ||
            !properties.containsKey(SCORE + number)                        ||
             properties.getProperty(NICK + number).equals("")              ||
             properties.getProperty(SCORE + number).equals("")             ||
            !properties.getProperty(SCORE + number).matches("\\d+") ||
             properties.getProperty(NICK + number).length() > 12          ||
             properties.getProperty(SCORE + number).length() > 6)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void checkCorrectAndSetHighestScores()
    {
        if(!propertiesFile.exists())
        {
            try(PrintWriter writer = new PrintWriter(propertiesFile, "UTF-8");)
            {
                restorePropertiesFile();
            }
            catch (Exception e)
            {
            }
        }
        else
        {
            try(FileInputStream inputStream = new FileInputStream(propertiesFile))
            {
                properties.load(inputStream);

                if(checkPropertiesConditions(1))
                {
                    if(firstNick == null)
                    {
                        properties.put(NICK1, "------------");
                        properties.put(SCORE1, "0");
                    }
                    else
                    {
                        properties.put(NICK1,  firstNick);
                        properties.put(SCORE1, String.valueOf(firstScore));
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                    properties.store(fileOutputStream, "");

                }

                if(checkPropertiesConditions(2))
                {
                    if(secondNick == null)
                    {
                        properties.put(NICK2, "------------");
                        properties.put(SCORE2, "0");
                    }
                    else
                    {
                        properties.put(NICK2, secondNick);
                        properties.put(SCORE2, String.valueOf(secondScore));
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                    properties.store(fileOutputStream, "");

                }

                if(checkPropertiesConditions(3))
                {
                    if(thirdNick == null)
                    {
                        properties.put(NICK3, "------------");
                        properties.put(SCORE3, "0");
                    }
                    else
                    {
                        properties.put(NICK3, thirdNick);
                        properties.put(SCORE3, String.valueOf(thirdScore));
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                    properties.store(fileOutputStream, "");

                }
            }
            catch (Exception e)
            {
            }
        }

        if(!(Integer.parseInt(properties.getProperty(SCORE1)) >= Integer.parseInt(properties.getProperty(SCORE2)) &&
             Integer.parseInt(properties.getProperty(SCORE2)) >= Integer.parseInt(properties.getProperty(SCORE3))))
        {
            Record firstRecord = new Record(1, Integer.parseInt(properties.getProperty(SCORE1)), properties.getProperty(NICK1));
            Record secondRecord = new Record(2, Integer.parseInt(properties.getProperty(SCORE2)), properties.getProperty(NICK2));
            Record thirdRecord = new Record(3, Integer.parseInt(properties.getProperty(SCORE3)), properties.getProperty(NICK3));

            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(Integer.parseInt(properties.getProperty(SCORE1)));
            arrayList.add(Integer.parseInt(properties.getProperty(SCORE2)));
            arrayList.add(Integer.parseInt(properties.getProperty(SCORE3)));
            Collections.sort(arrayList, Collections.reverseOrder());

            firstRecord.updateRecords(arrayList);
            secondRecord.updateRecords(arrayList);
            thirdRecord.updateRecords(arrayList);

            ArrayList<Record> recordArrayList = new ArrayList<>();
            recordArrayList.add(new Record(0,0,""));
            recordArrayList.add(new Record(0,0,""));
            recordArrayList.add(new Record(0,0,""));

            recordArrayList.set((firstRecord.getPosition() - 1), firstRecord);
            recordArrayList.set((secondRecord.getPosition() - 1), secondRecord);
            recordArrayList.set((thirdRecord.getPosition() - 1), thirdRecord);

            try(FileInputStream inputStream = new FileInputStream(propertiesFile);)
            {
                properties.load(inputStream);

                properties.setProperty(NICK1, recordArrayList.get(0).getNick());
                properties.setProperty(NICK2, recordArrayList.get(1).getNick());
                properties.setProperty(NICK3, recordArrayList.get(2).getNick());

                properties.setProperty(SCORE1, Integer.toString(recordArrayList.get(0).getScore()));
                properties.setProperty(SCORE2, Integer.toString(recordArrayList.get(1).getScore()));
                properties.setProperty(SCORE3, Integer.toString(recordArrayList.get(2).getScore()));

                FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
                properties.store(fileOutputStream, "");

            }
            catch (Exception e)
            {
            }

        }

        try(FileInputStream inputStream = new FileInputStream(propertiesFile))
        {
            properties.load(inputStream);

            firstScore = Integer.parseInt(properties.getProperty(SCORE1));
            secondScore = Integer.parseInt(properties.getProperty(SCORE2));
            thirdScore = Integer.parseInt(properties.getProperty(SCORE3));

            firstNick = properties.getProperty(NICK1);
            secondNick = properties.getProperty(NICK2);
            thirdNick = properties.getProperty(NICK3);
        }
        catch (Exception e)
        {
        }

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

    public String getFirstNick()
    {
        return firstNick;
    }

    public String getSecondNick()
    {
        return secondNick;
    }

    public String getThirdNick()
    {
        return thirdNick;
    }

    public void setNickAndScore(String newNick, int newScore, int number)
    {
        if(!propertiesFile.exists())
        {
            try(PrintWriter writer = new PrintWriter(propertiesFile, "UTF-8"))
            {
                restorePropertiesFile();
            }
            catch (Exception e)
            {
            }
        }
        try(FileInputStream fileInputStream = new FileInputStream(propertiesFile))
        {
            //properties.load(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
            properties.put(NICK + number, newNick);
            properties.put(SCORE + number, Integer.toString(newScore));

            properties.store(fileOutputStream, null);
        }
        catch (Exception e)
        {
        }
    }

    public int getFirstScore()
    {
        return firstScore;
    }

    public int getSecondScore()
    {
        return secondScore;
    }

    public int getThirdScore()
    {
        return thirdScore;
    }
}
