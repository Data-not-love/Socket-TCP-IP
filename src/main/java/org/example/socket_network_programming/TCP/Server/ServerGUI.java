package org.example.socket_network_programming;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.socket_network_programming.TCP.Client_Server.Client.Observable_Client;
import org.example.socket_network_programming.TCP.Client_Server.Server.Server;
import java.io.IOException;

public class ServerGUI extends Application {

    private ObservableList<Observable_Client> clientData = FXCollections.observableArrayList();
    private Thread serverThread;
    private boolean isServerRunning = false;
    private Server server;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Server Side");

        Button startServer = new Button("Start Server");
        Label labelStatus = new Label("Server is Closed");


        startServer.setOnAction(event -> {
            if (!isServerRunning) {
                try {
                    server = new Server();
                    labelStatus.setText("Server is Open");
                    startServer.setText("Stop Server");
                    isServerRunning = true;

                    server.setClientListener(observableClient -> {
                        clientData.add(observableClient);
                    });

                    serverThread = new Thread(server::receiveFileFromClient);
                    serverThread.setDaemon(true);
                    serverThread.start();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                server.stopServer();
                labelStatus.setText("Server is Closed");
                startServer.setText("Start Server");
                isServerRunning = false;
            }
        });


        HBox firstRow = new HBox(10);
        firstRow.getChildren().addAll(startServer, labelStatus);
        firstRow.setStyle("-fx-alignment: center-left;");

        Label fileSentFromClient = new Label("Receive File From Clients");
        TableView<Observable_Client> tableView = new TableView<>();

        TableColumn<Observable_Client, String> clientID = new TableColumn<>("Client ID");
        clientID.setCellValueFactory(cellData -> cellData.getValue().clientIDProperty());

        TableColumn<Observable_Client, String> clientOS = new TableColumn<>("Client OS");
        clientOS.setCellValueFactory(cellData -> cellData.getValue().clientOSProperty());

        TableColumn<Observable_Client, String> clientIP = new TableColumn<>("Client IP");
        clientIP.setCellValueFactory(cellData -> cellData.getValue().clientIPProperty());



        clientID.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        clientOS.prefWidthProperty().bind(tableView.widthProperty().divide(3));
        clientIP.prefWidthProperty().bind(tableView.widthProperty().divide(3));

        
        tableView.setItems(clientData);
        tableView.getColumns().addAll(clientID, clientOS,clientIP);

        VBox layout = new VBox(15, firstRow, fileSentFromClient, tableView);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");
        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
