/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.nio.channels.ConnectionPendingException;

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
    
    public boolean isConnected() {
        if (socket != null) {
            return socket.connected();
        }
        return false;
    }
    
    public void connect(String ipString, IConnectCallback callback) {
        try {
            socket = IO.socket("http://" + ipString + ":3868");
            socket.on(Socket.EVENT_CONNECT, (Object... args) -> {
                System.out.println("Socket begin Connect...");
                String userId = CurrentSessionUtils.USER_ID;
                socket.emit("SETUP_USER_CONNECT", userId);
            }).on("CONNECT_SUCCESS", (Object... args) -> {
                String userId = args[0].toString();
                Utils.logInfo("Connected: " + userId);
                callback.connectState(ConnectStateEnum.CONNECTED);
            }).on(Socket.EVENT_CONNECTING, (Object... args) -> {
                System.out.println("Socket CONNECTING....");
                callback.connectState(ConnectStateEnum.CONNECTING);
            }).on(Socket.EVENT_DISCONNECT, (Object... args) -> {
                System.out.println("Socket Disconnected");
                callback.connectState(ConnectStateEnum.DISCONNECTED);
            });
            socket.connect();
        } catch (URISyntaxException ex) {
            System.out.println("ERROR: " + ex.getLocalizedMessage());
        }
    }
    
    public void disconnect() {
        socket.disconnect();
        
    }
    
    public enum ConnectStateEnum {
        CONNECTING, CONNECTED, DISCONNECTED
    }
    
    public interface IConnectCallback {
        void connectState(ConnectStateEnum state);
    }
    
}
