package com.gem.game.card.manager;


import com.corundumstudio.socketio.*;
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
import java.util.List;

@Controller
public class SocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(SocketManager.class);

    public static List<UserModel> playerUsers;

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
        playerUsers = new ArrayList<>();
        Configuration config = new Configuration();
        config.setPort(PORT_SOCKET);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
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
        if (playerUsers.size() >= 8) {
            socketIOClient.sendEvent("MAX_CONNECTED");
        } else {
            socketIOClient.sendEvent("CONNECT_SUCCESS", userId);
            socketIOClient.joinRoom(CHAT_GROUP);
            LOG.info("connected " + socketIOClient.getRemoteAddress().toString() + " - UserId: " + userId);
        }
    }

    private void joinGameEvent(SocketIOClient socketIOClient, String jsonString, AckRequest ackRequest) {
        UserEventModel model = gson.fromJson(jsonString, UserEventModel.class);
        socketIOClient.joinRoom(GAME_GROUP);
        if (playerUsers.size() < 8) {
            UserModel userModel = new UserModel(playerUsers.size(), model, socketIOClient);
            playerUsers.add(userModel);
            LOG.info("Join game user " + socketIOClient.getRemoteAddress().toString() + " UserId: " + model.getUserID());
            pushCurrentPlayer(socketIOClient);
        }
    }

    private void pushCurrentPlayer(SocketIOClient socketIOClient) {
        List<UserEventModel> userPlayer = new ArrayList<>();
        for (UserModel userModel : playerUsers) {
            UserEventModel userEventModel = userModel.toUserEvent();
            userPlayer.add(userEventModel);
            LOG.info("Index: " + userEventModel.getIndex() + " - UserName: "+ userEventModel.getUserName() + " - UserId: " + userEventModel.getUserID());
        }
        if (userPlayer.size() > 0) {
            Type listType = new TypeToken<List<UserEventModel>>() {}.getType();
            socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("CURRENT_PLAYERS", gson.toJson(userPlayer, listType));
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
            removeUserDisconnect(socketIOClient);
            LOG.info("onDisconnect " + socketIOClient.getRemoteAddress().toString());
        } catch (Exception e) {
            LOG.info("Có thiết bị đột ngột ngắt kết nối đến socket");
        }
    }

    private void removeUserDisconnect(SocketIOClient socketIOClient) {
        UserModel userDisconnect = null;
        for (UserModel user : playerUsers) {
            if (user.getSocketIOClient() == socketIOClient) {
                userDisconnect = user;
                break;
            }
        }
        if (userDisconnect != null) {
            for (UserModel playerUser : playerUsers) {
                if (playerUser.getIndex() > userDisconnect.getIndex()) {
                    int index = playerUser.getIndex();
                    playerUser.setIndex(index - 1);
                }
            }
            playerUsers.remove(userDisconnect);
            pushCurrentPlayer(socketIOClient);
        }
    }

}
