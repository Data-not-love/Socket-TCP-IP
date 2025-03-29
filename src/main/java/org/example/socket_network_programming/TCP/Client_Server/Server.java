package org.example.socket_network_programming.TCP.Client_Server;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Files_Handling{
    private String portNumber = Dotenv.configure().filename(".env").load().get("PORT");
    private ServerSocket serverSocket;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(Integer.parseInt(getPortNumber()));
        serverSocket.setSoTimeout(1000000);
    }

    public void serverConnection () {
        while (true){
        try {
            System.out.println("\u001B[33m" + "Waiting for Client ..." + "\u001B[0m");
            Socket server = serverSocket.accept();
            System.out.println("Server SYN - ACK : port " + Integer.parseInt(getPortNumber()));
            System.out.println("Connected to : " + server.getRemoteSocketAddress());
            DataInputStream inputStream = new DataInputStream(server.getInputStream());
            System.out.println(inputStream.readUTF());

            DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
            dataOutputStream.writeUTF("Thanks for connecting to " + server.getLocalSocketAddress());
            server.close();

           // System.out.println("Client ACK : Receiving Files");
        } catch (SocketTimeoutException s){
            System.out.println("\u001B[31m" + "Socket timed out!" + "\u001B[0m");
            break;

        } catch (Exception e) {
            e.printStackTrace();
            break;}
        }
}


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.serverConnection();
    }
    public String getPortNumber() {
        return portNumber;
    }
//    @Override
//    public void receiveFile(Socket socket) throws IOException{
//
//    }
//
//    @Override
//    public void sendFile(Socket socket,File file) throws IOException{
//
//    }
}
