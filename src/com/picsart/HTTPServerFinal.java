package com.picsart;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HTTPServerFinal{
    static File file = new File("src//index.html");
    static final int portNumber = 5566;
    private static Socket socket;

    public static void main(String[] args) {
        try {

            HTTPServerFinal finalServer = new HTTPServerFinal();

            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started. \nPort: " + portNumber);

            if (true) {
                socket = serverSocket.accept();
                finalServer.getRequest(socket);
            }
        } catch (IOException e) {
            System.err.println("Server Connection error.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection closed");
        }
    }

    public void getRequest(Socket socket) throws IOException {
        System.out.println("Connecton opened. (" + new Date() + ")");

        BufferedReader in = new BufferedReader(new FileReader(file));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

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

        in.close();
        out.close();
    }
}