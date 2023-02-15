package com.gem.game.card.manager;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.gem.game.card.model.UserEventModel;
import com.gem.game.card.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(SocketManager.class);

    public static Map<String, UserModel> playerUsers;
    public static Map<String, UserModel> waitingUsers;

    public static SocketIOServer socketIOServer;
    private boolean gameIsRunning = false;

    @Autowired
    private Gson gson;

    @Value("${socketio.port}")
    private int PORT_SOCKET;
    private String CHAT_GROUP = "CHAT_GROUP";
    private String GAME_GROUP = "GAME_GROUP";


    @PostConstruct
    public void init() {
        LOG.info("Start socket");
        playerUsers = new HashMap<>();
        waitingUsers = new HashMap<>();
        Configuration config = new Configuration();
        config.setPort(PORT_SOCKET);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.addConnectListener(socketIOClient ->
                LOG.info("onConnect " + socketIOClient.getRemoteAddress().toString())
        );
        socketIOServer.addDisconnectListener(this::disconnect);
        socketIOServer.addEventListener("SETUP_USER_CONNECT", String.class, this::startConnect);
        socketIOServer.addEventListener("SEND_CHAT_EVENT", String.class, this::chatEvent);
        socketIOServer.addEventListener("JOIN_GAME_REQUEST", String.class, this::joinGameEvent);
        socketIOServer.start();
    }

    private void startConnect(SocketIOClient socketIOClient, String userId, AckRequest ackRequest) {
        socketIOClient.sendEvent("CONNECT_SUCCESS", userId);
        socketIOClient.joinRoom(CHAT_GROUP);
        LOG.info("connected " + socketIOClient.getRemoteAddress().toString() + " - UserId: " + userId);
    }

    private void joinGameEvent(SocketIOClient socketIOClient, String jsonString, AckRequest ackRequest) {
        UserEventModel model = gson.fromJson(jsonString, UserEventModel.class);
        pushCurrentPlayer(socketIOClient);
        UserModel userModel = new UserModel(model, socketIOClient);
        if (gameIsRunning) {
            waitingUsers.put(model.getUserID(), userModel);
            LOG.info("Waiting user " + socketIOClient.getRemoteAddress().toString() + " UserId: " + model.getUserID());
        } else {
            playerUsers.put(model.getUserID(), userModel);
            socketIOClient.joinRoom(GAME_GROUP);
            socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("JOIN_GAME_EVENT", jsonString);
            LOG.info("Join game user " + socketIOClient.getRemoteAddress().toString() + " UserId: " + model.getUserID());
        }
    }

    private void pushCurrentPlayer(SocketIOClient socketIOClient) {
        List<UserEventModel> userPlayer = new ArrayList<>();
        for (UserModel userModel : playerUsers.values()) {
            userPlayer.add(userModel.toUserEvent());
        }
        if (userPlayer.size() > 0) {
            Type listType = new TypeToken<List<UserEventModel>>() {}.getType();
            socketIOClient.sendEvent("CURRENT_PLAYERS", gson.toJson(userPlayer, listType));
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
            removeUserDisconnect(playerUsers, true, socketIOClient);
            removeUserDisconnect(waitingUsers, false, socketIOClient);
            LOG.info("onDisconnect " + socketIOClient.getRemoteAddress().toString());
        } catch (Exception e) {
            LOG.info("Có thiết bị đột ngột ngắt kết nối đến socket");
        }
    }

    private void removeUserDisconnect(Map<String, UserModel> users, boolean isPlayer, SocketIOClient socketIOClient) {
        String idDisconnect = "";
        for (String userId : users.keySet()) {
            if (users.get(userId).getSocketIOClient() == socketIOClient) {
                idDisconnect = userId;
                break;
            }
        }
        if (!idDisconnect.equals("")) {
            if (isPlayer) {
                playerUsers.remove(idDisconnect);
            } else {
                waitingUsers.remove(idDisconnect);
            }
        }
    }

}
