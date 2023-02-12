/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

/**
 *
 * @author nth
 */
public class SocketManager {
    
    private static SocketManager instanceManager;
    
    public static SocketManager getInstance() {
        if (instanceManager == null) {
            instanceManager = new SocketManager();
        }
        return instanceManager;
    }
    
    private Socket socket;
    
    
    private SocketManager() {
        
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public void connect() {
        try {
            socket = IO.socket("http://51.75.187.110:3868");
            socket.on(Socket.EVENT_CONNECT, (Object... args) -> {
                System.out.println("Socket Connected");
            }).on(Socket.EVENT_CONNECTING, (Object... args) -> {
                System.out.println("Socket CONNECTING....");
            }).on(Socket.EVENT_DISCONNECT, (Object... args) -> {
                System.out.println("Socket Disconnected");
            });
            socket.connect();
        } catch (URISyntaxException ex) {
            System.out.println("ERROR: " + ex.getLocalizedMessage());
        }
    }
    
    public void disconnect() {
        socket.disconnect();
        
    }
    
}
