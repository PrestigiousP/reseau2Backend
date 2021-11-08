package main;

import main.Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerConnection extends Thread{
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    Scanner scanner;
    PrintWriter printWriter;
    boolean run;

    public ServerConnection(Socket socket, Server main){
        super("SocketConnectionThread");
        this.socket = socket;

    }

    public void sendStringToClient(String text){

    }

    public void sendStringToAllClients(){

    }

    @Override
    public void run(){
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            printWriter = new PrintWriter(new OutputStreamWriter(dout, StandardCharsets.UTF_8));
            while(run){
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    printWriter.println("Echo from server: " + line);

                    if(line.toLowerCase().trim().equals("quit")) {
                        run = true;
                    }
                }
            }
            din.close();
            dout.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
