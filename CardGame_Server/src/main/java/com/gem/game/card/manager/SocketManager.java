package com.gem.game.card.manager;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.gem.game.card.model.ChatEventModel;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(SocketManager.class);

    public static Map<String, SocketIOClient> listConnects;

    public static SocketIOServer socketIOServer;

    @Autowired
    private Gson gson;

    @Value("${socketio.port}")
    private int PORT_SOCKET;
    private String CHAT_GROUP = "CHAT_GROUP";


    @PostConstruct
    public void init() {
        LOG.info("Start socket");
        listConnects = new HashMap<>();
        Configuration config = new Configuration();
        config.setPort(PORT_SOCKET);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.addConnectListener(socketIOClient ->
                LOG.info("onConnect " + socketIOClient.getRemoteAddress().toString())
        );
        socketIOServer.addDisconnectListener(this::disconnect);
        socketIOServer.addEventListener("SETUP_USER_CONNECT", String.class, this::startConnect);
        socketIOServer.addEventListener("SEND_CHAT_EVENT", String.class, this::chatEvent);
        socketIOServer.start();
    }

    private void startConnect(SocketIOClient socketIOClient, String userId, AckRequest ackRequest) {
        try {
            listConnects.put(userId, socketIOClient);
            socketIOClient.sendEvent("CONNECT_SUCCESS", userId);
            socketIOClient.joinRoom(CHAT_GROUP);
            LOG.info("connected " + socketIOClient.getRemoteAddress().toString() + "UserId: " + userId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void chatEvent(SocketIOClient socketIOClient, String json, AckRequest ackRequest) {
        try {
            socketIOServer.getRoomOperations(CHAT_GROUP).sendEvent("CHAT_EVENT_LISTENER", socketIOClient, json);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void disconnect(SocketIOClient socketIOClient) {
        try {
            socketIOClient.leaveRoom(CHAT_GROUP);
            String idDisconnect = "";
            for (String userId : listConnects.keySet()) {
                if (listConnects.get(userId) == socketIOClient) {
                    idDisconnect = userId;
                    break;
                }
            }
            if (!idDisconnect.equals("")) {
                listConnects.remove(idDisconnect);
            }
            LOG.info("onDisconnect " + socketIOClient.getRemoteAddress().toString());
        } catch (Exception e) {
            LOG.info("Có thiết bị đột ngột ngắt kết nối đến socket");
        }
    }

}
