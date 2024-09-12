package org.example;

import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.ArrayList;

@Controller
public class DatabaseController {
    Connection databaseConnection;

    DatabaseController() {
        try {
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable() {
        try {
            Statement createTableStatement = databaseConnection.createStatement();
            createTableStatement.execute("CREATE TABLE IF NOT EXISTS entry (token TEXT NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, color TEXT NOT NULL, timestamp TEXT NOT NULL);");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    void addPixelData(String token, int x, int y, String color){
        long timestamp = System.currentTimeMillis();
        String query = String.format("INSERT INTO entry (token, x, y, color, timestamp) VALUES(\"%s\", %d, %d, \"%s\", %d);", token, x, y, color, timestamp);

        try{
            Statement insertPixelStatement = databaseConnection.createStatement();
            insertPixelStatement.execute(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    ArrayList<ImageController.PixelRequestBody> getPixelsData(){
        ArrayList<ImageController.PixelRequestBody> pixelsData = new ArrayList<ImageController.PixelRequestBody>();

        try{
            Statement queryPixelStatement = databaseConnection.createStatement();
            ResultSet pixelResults = queryPixelStatement.executeQuery("SELECT token, x, y, color FROM entry ORDER BY timestamp;");

            while(pixelResults.next()){
                String token = pixelResults.getString("token");
                int x = pixelResults.getInt("x");
                int y = pixelResults.getInt("y");
                String color = pixelResults.getString("color");

                pixelsData.add(new ImageController.PixelRequestBody(token, x, y, color));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return pixelsData;
    }


}
