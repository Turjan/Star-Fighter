package com.arkhon.spaceships.gui;

import com.arkhon.spaceships.logic.game.Game.Option;
import static com.arkhon.spaceships.logic.game.Game.Option.EXIT;
import static com.arkhon.spaceships.logic.game.Game.Option.NEW;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JDialog{
    
    private final static int mainMenuSize = 450;
    private final static int buttonHight = 40;
    private final static int buttonWidth = 150;
    
    private final JPanel buttonPanel;
    private final JButton newGameButton;
    private final JButton continueButton;
    private final JButton exitButton;
    private Option returnOption;
    
    public MainMenu(){
    
        setLayout(new GridBagLayout());
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        
        newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(buttonWidth, buttonHight));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                returnOption = NEW;
                dispose();
            }
        });
        
        continueButton = new JButton("Continue");
        continueButton.setPreferredSize(new Dimension(buttonWidth, buttonHight));
        continueButton.setEnabled(false); //TODO
        
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHight));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                returnOption = EXIT;
                dispose();
            }
        });
        
        buttonPanel.add(newGameButton);
        buttonPanel.add(continueButton);
        buttonPanel.add(exitButton);
        
        setModal(true);
        setSize(mainMenuSize,mainMenuSize);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("SpaceShip 0.1a");
        add(buttonPanel);
    }

    public Option run() {
        returnOption = EXIT;
        setLocationRelativeTo(null);
        setVisible(true);
        return returnOption;
    }
    
}
