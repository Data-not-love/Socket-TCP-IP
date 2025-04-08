package org.example.socket_network_programming.TCP.Client_Server.Client;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Observable_Client {
    private final StringProperty clientID;
    private final StringProperty clientOS;
    private final StringProperty clientIP;

    public Observable_Client(int id, String os, String ip) {
        this.clientID = new SimpleStringProperty(String.valueOf(id));
        this.clientOS = new SimpleStringProperty(os);
        this.clientIP = new SimpleStringProperty(ip);
    }

    // Getters for properties
    public String getClientID() {
        return clientID.get();
    }
    public String getClientOS() {
        return clientOS.get();
    }
    public String getClientIP() {
        return clientIP.get();
    }

    // Setters for properties
    public void setClientID(String clientID) {
        this.clientID.set(clientID);
    }

    public void setClientOS(String clientOS) {
        this.clientOS.set(clientOS);
    }

    public void setClientIP(String clientIP) {
        this.clientIP.set(clientIP);
    }

    public StringProperty clientIDProperty() {
        return clientID;
    }

    public StringProperty clientOSProperty() {
        return clientOS;
    }

    public StringProperty clientIPProperty() {
        return clientIP;
    }

}
