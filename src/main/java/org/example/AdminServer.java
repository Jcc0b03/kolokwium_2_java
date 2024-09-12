package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AdminServer {
    ServerSocket serverSocket;
    private ArrayList<ClientThread> clients = new ArrayList<>();

    public AdminServer(int port){
        try{
            this.serverSocket = new ServerSocket(port);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void listen(){
        System.out.println("Oczekiwanie...");
        while(true){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Nowy klient dołączył!");
                ClientThread thread = new ClientThread(clientSocket, this);
                clients.add(thread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
    }


}
