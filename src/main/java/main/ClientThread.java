package main;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientThread implements Runnable{

    private Socket socket;
    private BufferedReader input;
    private Scanner scanner;
    private PrintWriter printWriter;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientThread(Socket s) throws IOException {
        socket = s;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Create Input&Outputstreams for the connection
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

        printWriter.println("Hello World! Enter Peace to exit.");
    }
    @Override
    public void run() {
        //Have the server take input from the client and echo it back
        //This should be placed in a loop that listens for a terminator text e.g. bye
        boolean done = false;

        while(!done && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            printWriter.println("Echo from <Your Name Here> main.Server: " + line);

            if(line.toLowerCase().trim().equals("quit")) {
                done = true;
            }
        }
    }
}
