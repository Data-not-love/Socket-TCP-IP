package org.example.socket_network_programming.TCP.Server;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.socket_network_programming.TCP.Client.Client;
import org.example.socket_network_programming.TCP.Client.Observable_Client;
import org.example.socket_network_programming.TCP.Files_Handling;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Add this at the top
import java.util.function.Consumer;

public class Server extends Files_Handling {
    private final String portNumber = Dotenv.configure().filename(".env").load().get("PORT");
    private final ServerSocket serverSocket;
    private Consumer<Observable_Client> clientListener;
    private volatile boolean running = false;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(Integer.parseInt(portNumber));
        serverSocket.setSoTimeout(1000000);
    }

    public void setClientListener(Consumer<Observable_Client> listener) {
        this.clientListener = listener;
    }
    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("\u001B[31mServer stopped.\u001B[0m");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void receiveFileFromClient() {
        while (true) {
            System.out.println("\u001B[33m" + "Waiting for client..." + "\u001B[0m");
            try (Socket clientSocket = serverSocket.accept();
                 DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream())) {

                String fileName = inputStream.readUTF();
                long fileSize = inputStream.readLong();

                if (fileName == null || fileName.isBlank()) {
                    System.err.println("\u001B[31m" + "Received empty or invalid filename. Skipping." + "\u001B[0m");
                    continue;}

                System.out.println("\u001B[32m" + "Receiving file: " + fileName + " (" + fileSize + " bytes)"  + "\u001B[0m");
                File saveFile = new File(System.getProperty("user.home") + "/Downloads", fileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalRead = 0;

                    while (totalRead < fileSize && (bytesRead = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        totalRead += bytesRead;
                    }
                }
                System.out.println("File received and saved to: " + saveFile.getAbsolutePath());

                //  Notify GUI (on the JavaFX Application Thread)
                if (clientListener != null) {
                    Client client = new Client(); // real one
                    Observable_Client observableClient = new Observable_Client(client.getId(), client.getOS(),client.getIP());

                    javafx.application.Platform.runLater(() -> clientListener.accept(observableClient));
                }

            } catch (IOException e) {
                System.err.println("\u001B[31m" + "Error during file transfer: " + e.getMessage() + "\u001B[0m");
                e.printStackTrace();
                break;
            }
        }
    }
}
