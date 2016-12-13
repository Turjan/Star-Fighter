package com.arkhon.spaceships.gui;

import static com.arkhon.spaceships.gui.GameImage.CONTROLPANEL;
import com.arkhon.spaceships.logic.game.Game.Option;
import static com.arkhon.spaceships.logic.game.Game.Option.*;
import com.arkhon.spaceships.logic.game.Logic;
import com.arkhon.spaceships.logic.game.Position;
import com.arkhon.spaceships.logic.machines.Explosion;
import com.arkhon.spaceships.logic.machines.Projectile;
import com.arkhon.spaceships.logic.machines.Ship;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameTable extends JDialog {
    
    private final Logic logic;
    private Timer timer;
    
    private JPanel background;
    private JPanel dataPanel;
    private HealthBar healthBar;
    private JLabel weaponLabel;
    
    private Option option;
    
    public GameTable(Logic l){
    
        this.logic = l;
        
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);   //setting invisible cursor
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        this.getContentPane().setCursor(blankCursor);
        
        this.timer = new Timer(10,new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logic.update();
                healthBar.update(logic.getPlayer().getHealth());
                weaponLabel.setText("Weapon: " + logic.getPlayer().getActiveWeapon().name);
                background.repaint();
                
                checkOutcome();
                
            }
        });
        
        this.addWindowListener( new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent we) {
               option = EXIT; 
               timer.stop();
            }
        });
        
        this.background = new JPanel(){
            
            @Override 
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(logic.getLevel().backgroundImage.getImage(), 0, 0, logic.getLevel().levelWidth, logic.getLevel().levelHeight, this);
                g.drawImage(logic.getPlayer().image, logic.getPlayer().getPosition().x, logic.getPlayer().getPosition().y, this);
                for(Projectile p: logic.getProjectiles()){
                    g.drawImage(p.image, p.getPosition().x, p.getPosition().y, this);
                }
                for(Ship ship:logic.getActiveShips()){
                    g.drawImage(ship.image, ship.getPosition().x, ship.getPosition().y, this);
                }
                for(Explosion explosion:logic.getExplosions()){
                    g.drawImage(explosion.getCurrentImage(), explosion.getPosition().x, explosion.getPosition().y, this);
                }
            }
        };
        
        background.addMouseMotionListener(new MouseMotionAdapter() {

            private int halfWidth = logic.getPlayer().width/2;  //This is needed, or the model of the ship will be draw out of the panel.
            private int halfHeight = logic.getPlayer().height/2;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                logic.movePlayer(new Position(e.getX()-halfWidth, e.getY()-halfHeight));
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                logic.fireByPlayer();
                logic.movePlayer(new Position(e.getX()-halfWidth, e.getY()-halfHeight));
            }
        });
        
        background.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                logic.fireByPlayer();
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}
        });
        
        background.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode>47 && keyCode < 58){
                    logic.changePlayerWeapon(keyCode-48);
                }    
            }
            
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyReleased(KeyEvent e){}
        });
        
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        background.setFocusable(true); //for the key-events
        background.setPreferredSize(new Dimension(logic.getLevel().levelWidth, logic.getLevel().levelHeight));
        
        
        dataPanel = new JPanel(){
            
            @Override 
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(CONTROLPANEL.getImage(), 0, 0, logic.getLevel().levelWidth, 30, this);
            }
        };
        
        dataPanel.setPreferredSize(new Dimension(logic.getLevel().levelWidth,30));
        dataPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        
        healthBar = new HealthBar(logic.getPlayer().getHealth());
        weaponLabel = new JLabel("Weapon: " + logic.getPlayer().getActiveWeapon().name);
        
        dataPanel.add(new JLabel("Health"));
        dataPanel.add(healthBar);
        dataPanel.add(weaponLabel);
        
        add(background);
        add(dataPanel);
        
        setResizable(false);  //If called after pack(), then glitches. 
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("SpaceShip 0.6a");
        setModal(true);
    }
    
    public void run(){
    
        timer.start();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public Option getOption() { return option; }
    
    private void checkOutcome(){
    
        if(logic.getLost())             { 
            JOptionPane.showMessageDialog(this, "Defeat");
            option = MENU;
            timer.stop(); 
            this.dispose();
        }
        else if(logic.getVictory())     { 
            JOptionPane.showMessageDialog(this, "Victory"); 
            option = WIN;
            timer.stop(); 
            this.dispose();
        }
    }
    
}

    
    

