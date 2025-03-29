package org.example.socket_network_programming.TCP.Client_Server;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.*;
import java.net.Socket;

public class Client extends Files_Handling{
    private String portNumber;
    private String serverAddress;

    public Client (){
        this.serverAddress = Dotenv.configure().filename(".env").load().get("SERVER_ADDRESS");
        this.portNumber = Dotenv.configure().filename(".env").load().get("PORT");
    }

    public void checkConnection () throws IOException {
        System.out.println("Client SYN : " + "\u001B[35m" + getServerAddress() + "\u001B[0m"+ " port " + "\u001B[33m" + getPortNumber() + "\u001B[0m");
        Socket clientSocket = new Socket(getServerAddress(), Integer.parseInt(getPortNumber()));

        OutputStream outputStreamtoServer = clientSocket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outputStreamtoServer);
        out.writeUTF("\u001B[32m" + "Client IP Address : " + clientSocket.getLocalSocketAddress()+ "\u001B[0m");

        InputStream inputStreamFromServer = clientSocket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStreamFromServer);
        System.out.println("Server replies : " + dataInputStream.readUTF());
            clientSocket.close();
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.checkConnection();
    }

    public String getPortNumber() {
        return portNumber;
    }
    public String getServerAddress() {
        return serverAddress;
    }
//    @Override
//    public void receiveFile(Socket socket) throws IOException {
//
//    }
//
//    @Override
//    public void sendFile(Socket socket, File file) throws IOException {
//
//    }
}

