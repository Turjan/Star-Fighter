package com.arkhon.spaceships.logic.game;

import com.arkhon.spaceships.gui.ErrorDialog;
import com.arkhon.spaceships.gui.GameTable;
import com.arkhon.spaceships.gui.MainMenu;
import static com.arkhon.spaceships.logic.game.Game.Option.*;
import com.arkhon.spaceships.logic.game.exceptions.WrongDataException;
import com.arkhon.spaceships.logic.machines.ships.Condor;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game {
    
    public enum Option{ MENU,NEW,EXIT,CONTINUE,WIN, ERROR }
    
    private ArrayList<Level> levels;
    private MainMenu mainMenu;
    private GameTable gameTable;
    private Logic logic;
    private int levelIndex;
    private Option nextOption;
    
    private Game(){};
    
    
    public static Game GameFactory(){
        
        Game game = new Game();
        
        try{
            game.mainMenu = new MainMenu();
            game.levels = new ArrayList<>();
            game.nextOption = MENU;
            game.levelIndex = 0;
            game.setLevels();
        }
        catch(OverTheMaxCargoSpaceException|WrongDataException e){ 
            displayErrorDialog("Wrong data in file: \n" + e.getMessage());
            game.nextOption = ERROR;
        }
        catch(FileNotFoundException e){ 
            displayErrorDialog("Can not read file \n" + e.getMessage());
            game.nextOption = ERROR;
        }
        return game;
    }
    
    public final void setLevels() throws OverTheMaxCargoSpaceException,FileNotFoundException,WrongDataException{ //loading all the levels
        File levelFiles = new File("src/main/resources/files/levels");
        for(File file:levelFiles.listFiles()){
            levels.add(Level.levelFactory(file));
        }
    }
    
    public void run(){
        
        while(nextOption != EXIT) {
            System.out.println("TestWhile");
            switch(nextOption){
            
                case ERROR: System.out.println("Error"); return;
                
                case MENU: System.out.println("MENU"); nextOption = mainMenu.run(); break;
                    
                case NEW:
                    System.out.println("NEW");
                    levelIndex = 0;
                    startLevel(0);
                    break;
                    
                case WIN:
                    System.out.println("Victory!");
                    startLevel(++levelIndex);
                    break;
                    
                case EXIT: return;
                  
            }
        }    
    }
    
    private void startLevel(int levelIndex) {
        System.out.println(levelIndex + ":" + levels.size());
        if(levelIndex > levels.size()-1) { 
            System.out.println("Total Victory!");
            nextOption = EXIT;
            return; 
        }
        
        Ship player = new Condor(null, Direction.NORTH);
        
        Position startPosition = new Position(
                            levels.get(levelIndex).levelWidth/2-player.width/2, 
                            levels.get(levelIndex).levelHeight-player.height);
        
        player.setPosition(startPosition);
        
        logic = new Logic(levels.get(levelIndex), player);
        gameTable = new GameTable(logic);
        gameTable.run();
        nextOption = gameTable.getOption();
    }
    
    private static void displayErrorDialog(String message){
        ErrorDialog e = new ErrorDialog(message); 
    }
}
