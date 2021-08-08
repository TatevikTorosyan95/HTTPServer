package com.picsart;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HTTPServerFinal implements Runnable{

    static File file = new File("src//index.html");
    static final int portNumber = 5566;

    private static Socket socket;
    private static HTTPServerFinal finalServer;

    public HTTPServerFinal(Socket s) {
        socket = s;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started. \nPort: " + portNumber);

            while (true) {

                finalServer = new HTTPServerFinal(serverSocket.accept());

                if(true) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                Thread thread =  new Thread(finalServer);
                thread.start();

            }

        } catch (IOException e) {
            System.err.println("Server Connection error.");
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new FileReader(file));
            out = new PrintWriter(socket.getOutputStream());

            out.println("HTTP/1.1 200 OK");
            out.println("Date: " + new Date());
            out.println("Server: Java HTTP Server");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + file.length());
            out.println();
            out.flush();

            String lines;

            while ((lines = in.readLine()) != null) {
                out.println(lines);
                if (lines.isEmpty()) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (true) {
                System.out.println("Connection closed");
            }
        }
    }

}