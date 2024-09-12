package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminClient {
    public static void main(String args[]){
        System.out.println("admin client");

        ClientConnection connection = new ClientConnection(4000);
        connection.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try{
            while(true) {
                String data = reader.readLine();
                connection.send(data);
            }
        }catch (IOException e){

        }

    }
}
