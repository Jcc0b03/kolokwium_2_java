package org.example;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    Socket socket;
    AdminServer server;

    PrintWriter writer;

    String password = "P@ssw0rd";

    ClientThread(Socket socket, AdminServer server){
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        Boolean isLogged = false;
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null){
                if(!isLogged){
                    if(message == password){
                        isLogged = true;
                        send("Logged successfully");
                    }else{

                    }
                }

                System.out.println(message);
            }
            System.out.println("closed");
            server.removeClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        writer.println(message);
    }
}
