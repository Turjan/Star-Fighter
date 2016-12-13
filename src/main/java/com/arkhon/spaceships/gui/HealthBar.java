package com.arkhon.spaceships.gui;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class HealthBar extends JProgressBar{
    
    public HealthBar(int maximum){
        super();
       
        setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() { return Color.BLACK; }
            @Override
            protected Color getSelectionForeground() { return Color.WHITE; }
        });
        setForeground(Color.RED);
        setBackground(Color.ORANGE);
        
        setStringPainted(true);
        setMinimum(0);
        setMaximum(maximum);
        setValue(maximum);
        setString(maximum+"/"+maximum);
    }
    
    public void update(int value){
    
        setValue(value);
        setString(value + "/" + getMaximum());
    }
}
