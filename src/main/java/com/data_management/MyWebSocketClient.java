package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;


public class MyWebSocketClient extends WebSocketClient implements DataReader{
    private DataStorage dataStorage;

    public void readData(DataStorage dataStorage) throws IOException{
        String uri = "ws://localhost:8887";
        dataStorage = new DataStorage();

        try {
            MyWebSocketClient client = new MyWebSocketClient(new URI(uri), dataStorage);
            client.connectBlocking(); // Waits for the connection to be established
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MyWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        String parsedData = parseMessage(message);
        if (parsedData != null) {
            String[] tokens=parsedData.split(":|,|%");
            if (tokens[2].trim().equals("Alert")) {
                dataStorage.addPatientData(Integer.parseInt(tokens[0].trim()), 0, 
                        tokens[2].trim(), Long.parseLong(tokens[1].trim()));
            }else{
                dataStorage.addPatientData(Integer.parseInt(tokens[0].trim()), Double.parseDouble(tokens[3].trim()), 
                      tokens[2].trim(), Long.parseLong(tokens[1].trim()));
            }
            System.out.println(parsedData);
          
        }
            // dataStorage.addPatientData(getConnectionLostTimeout(), getConnectionLostTimeout(), parsedData, getConnectionLostTimeout());
        
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    private String parseMessage(String message) {
        // Here we assume the message is already a valid JSON string.
        // You can add more complex parsing logic if needed.
        return message;
    }

    public static void main(String[] args) {
        String uri = "ws://localhost:8887";
        DataStorage dataStorage = new DataStorage();

        try {
            MyWebSocketClient client = new MyWebSocketClient(new URI(uri), dataStorage);
            client.connectBlocking(); // Waits for the connection to be established
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
}
