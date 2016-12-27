package com.arkhon.spaceships.logic.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

public class DBController {
    
    private Connection connection;
    
    private DBController(){}
    
    public static DBController DBControllerFactory(String DBPath) throws SQLException{
        
        DBController dbc;
        try{
            if(! new File(DBPath).exists()){ throw new FileNotFoundException(DBPath); }
            dbc = new DBController();
            Class.forName("org.sqlite.JDBC");
            dbc.connection = DriverManager.getConnection("jdbc:sqlite:"+DBPath);
        } 
        catch (ClassNotFoundException | SQLException | FileNotFoundException ex) { 
            throw new SQLException(ex.getClass().getName() + ": "+ ex.getMessage());
        }
        return dbc;
    }
    
    public void createLevelFiles(File parent) throws SQLException, FileNotFoundException, ClassNotFoundException{

        parent.mkdirs();  //making sure that the parent directories existing
        Statement stm = connection.createStatement();

        ResultSet rs = stm.executeQuery("SELECT* FROM LEVELS;");
        while (rs.next()) {

            int levelNumber = rs.getInt("LEVELNUMBER");
            String name = rs.getString("NAME");
            String backgroundName = rs.getString("BACKGROUND");

            PrintWriter pw = new PrintWriter(parent + "/" + name);
            pw.println(levelNumber);
            pw.println(name);
            pw.println(backgroundName);

            Statement innerStatement = connection.createStatement();
            ResultSet shipSet = innerStatement.executeQuery("SELECT* FROM ENEMIES WHERE LEVELNUMBER=" + levelNumber);
            while (shipSet.next()) {

                pw.print(shipSet.getString("TYPENAME") + " ");
                pw.print(shipSet.getInt("X") + " ");
                pw.print(shipSet.getInt("Y") + " ");
                pw.print(shipSet.getInt("DELAY"));
                pw.print("\n");
            }
            pw.close();
            innerStatement.close();
        }
        stm.close();
    }
    
    public void shutDown() throws SQLException{
    
        connection.close();
    }
    
    
}
