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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    
    public enum Option{ MENU,NEW,EXIT,CONTINUE,WIN, ERROR } //these define what the game will do next
    
    private static final String DBFile = "src/main/resources/SQL/gameDB.db";
    private static final File levelFiles = new File("src/main/resources/files/levels");  //there will be the files of the different levels, while the game is runnig
    
    private DBController controller; //used for interactions with the database
    private Logic logic;            //the logic of the game
    private MainMenu mainMenu;      //first part of the gui, the menu of the game
    private GameTable gameTable;    //second part of the gui, the window where the game plays
    private int levelIndex;         //shows the current level on wich the player is
    private Option nextOption;      //show what will the game do next
    private ArrayList<Level> levels;//store the levels
    
    private Game(){};
    
    public static Game GameFactory(){
        
        Game game = new Game();
        
        try{
            game.mainMenu = new MainMenu();
            game.levels = new ArrayList<>();
            game.nextOption = MENU;
            game.levelIndex = 0;
            game.controller = DBController.DBControllerFactory(DBFile);
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
        catch (SQLException | ClassNotFoundException e) {
            displayErrorDialog("Error with the database connection \n" + e.getMessage());
            game.nextOption = ERROR;
        }
        //write every exception handling over this
        catch(Exception e) {  
            displayErrorDialog("Unknown Error! \n" + e.getMessage());
            game.nextOption = ERROR;
        }
        return game;
    }
    
    public final void setLevels() throws OverTheMaxCargoSpaceException,FileNotFoundException,WrongDataException, SQLException, ClassNotFoundException{ //loading all the levels
        controller.createLevelFiles(levelFiles);                        //creating the files from the db
        for(File file:levelFiles.listFiles()){                          //upload the leveldata 
            levels.add(Level.levelFactory(file));
        }
        Collections.sort(levels);
    }
    
    public void run(){
        
        while(true) {
            System.out.println(nextOption);
            switch(nextOption){
                
                case ERROR: shutDown(); return;
                
                case MENU: nextOption = mainMenu.run(); break;
                    
                case NEW:
                    levelIndex = 0;
                    startLevel(0);
                    break;
                    
                case WIN:
                    startLevel(++levelIndex);
                    break;
                    
                case EXIT: shutDown(); return;
                  
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
    
    private void shutDown() {
        try                     { controller.shutDown(); }
        catch(SQLException e)   { displayErrorDialog("Error with the database connection \n" + e.getMessage()); }
        finally{ 
            for (File file : levelFiles.listFiles()) { file.delete(); }
        }
    }
    
    private static void displayErrorDialog(String message){
        ErrorDialog e = new ErrorDialog(message); 
    }
}
