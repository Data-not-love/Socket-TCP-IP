package org.example.socket_network_programming.TCP.Client_Server;

import java.io.*;
import java.net.Socket;

public abstract class Files_Handling {
    public void receiveFile(Socket socket, String savePath) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        String fileName = dataInputStream.readUTF();
        long fileSize = dataInputStream.readLong();

        FileOutputStream fileOutputStream = new FileOutputStream(savePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        long totalRead = 0;

        while (totalRead < fileSize && (bytesRead = dataInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, bytesRead);
            totalRead += bytesRead;
        }

        fileOutputStream.close();
        System.out.println("File received: " + savePath);
    }


    public void sendFile(Socket socket,String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(file.getName());
        dataOutputStream.writeLong(file.length());


        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
            dataOutputStream.write(buffer, 0, bytesRead);
        }

        fileInputStream.close();
        System.out.println("File sent: " + filePath);


    }

}
