package com.gem.game.card.manager;


import com.corundumstudio.socketio.*;
import com.gem.game.card.model.GameEventModel;
import com.gem.game.card.model.PlayerStateEnum;
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
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class SocketManager {

    private static final Logger LOG = LoggerFactory.getLogger(SocketManager.class);

    public static List<UserModel> playerUsers;

    public static SocketIOServer socketIOServer;
    private boolean gameIsRunning = false;
    private int totalAmount;
    private UserModel hostUser;

    @Autowired
    private Gson gson;

    private Random random;

    @Value("${socketio.port}")
    private int PORT_SOCKET;
    private String CHAT_GROUP = "CHAT_GROUP";
    private String GAME_GROUP = "GAME_GROUP";


    @PostConstruct
    public void init() {
        LOG.info("Start socket");
        random = new Random();
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

        socketIOServer.addEventListener("START_GAME", String.class, this::startGame);
        socketIOServer.addEventListener("PLAYER_FOLLOW_EVENT", String.class, this::followAction);
        socketIOServer.addEventListener("PLAYER_UPPER_EVENT", String.class, this::upperActionEvent);
        socketIOServer.addEventListener("PLAYER_CANCEL_EVENT", String.class, this::cancelActionEvent);
        socketIOServer.start();
    }

    private void startConnect(SocketIOClient socketIOClient, String userId, AckRequest ackRequest) {
        if (playerUsers.size() >= 8) {
            socketIOClient.sendEvent("MAX_CONNECTED");
        } else if (gameIsRunning) {
            socketIOClient.sendEvent("GAME_RUNNING");
        } else {
            socketIOClient.sendEvent("CONNECT_SUCCESS", userId);
            socketIOClient.joinRoom(CHAT_GROUP);
            LOG.info("connected " + socketIOClient.getRemoteAddress().toString() + " - UserId: " + userId);
        }
    }

    // ======================== GAME EVENT ===============================
    private void startGame(SocketIOClient socketIOClient, String userId, AckRequest ackRequest) {
//        gameIsRunning = true;
        List<Integer> cards = new ArrayList<>();
        playerUsers = playerUsers.stream().sorted(Comparator.comparing(UserModel::getIndex)).collect(Collectors.toList());
        playerUsers.forEach((userModel -> {
            userModel.getCards().clear();
            if (userModel.isHost()) {
                hostUser = userModel;
            }
        }));

        for (int l = 0; l < 3; l ++) {
            for (UserModel playerUser : playerUsers) {
                int cardRandom = random.nextInt(52);
                while (cards.contains(cardRandom)) {
                    cardRandom = random.nextInt(52);
                }
                cards.add(cardRandom);
                playerUser.getCards().add(cardRandom);
            }
        }
        socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("GAME_STARTED", hostUser.getUserID());
        // Bai cua ai thi gui cho nguoi day.
        totalAmount = 0;
        for (UserModel playerUser : playerUsers) {
            totalAmount += 10;
            Type listType = new TypeToken<List<Integer>>() {}.getType();
            playerUser.getSocketIOClient().sendEvent("MY_CARD_EVENT", gson.toJson(playerUser.getCards(), listType));
        }
        updateTotalAmount();
    }

    private void followAction(SocketIOClient socketIOClient, String json, AckRequest ackRequest) {
        GameEventModel eventModel = gson.fromJson(json, GameEventModel.class);
        socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("PLAYER_CHANGE_STATE_EVENT", json);
        if (eventModel.getIndex() < playerUsers.size()) {
            playerUsers.get(eventModel.getIndex()).setPlayerState(PlayerStateEnum.FOLLOW);
        }
        UserModel nextUser = getNextUser(eventModel.getIndex());
        if (nextUser.getUserID().equals(eventModel.getUserId())) {
            socketIOClient.sendEvent("YOUR_WIN_EVENT");
        } else if (nextUser.isHost() && checkDoneGame()) {
            socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("END_GAME_EVENT");
        } else {
            nextUser.getSocketIOClient().sendEvent("YOUR_TURN_EVENT");
        }
    }

    private void upperActionEvent(SocketIOClient socketIOClient, String json, AckRequest ackRequest) {
        GameEventModel eventModel = gson.fromJson(json, GameEventModel.class);
        socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("PLAYER_CHANGE_STATE_EVENT", json);
        if (eventModel.getIndex() < playerUsers.size()) {
            playerUsers.get(eventModel.getIndex()).setPlayerState(PlayerStateEnum.UPPER);
            playerUsers.get(eventModel.getIndex()).setHost(true);
        }
        UserModel nextUser = getNextUser(eventModel.getIndex());
        if (nextUser.getUserID().equals(eventModel.getUserId())) {
            socketIOClient.sendEvent("YOUR_WIN_EVENT");
        } else if (nextUser.isHost() && checkDoneGame()) {
            socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("END_GAME_EVENT");
        } else {
            nextUser.getSocketIOClient().sendEvent("YOUR_TURN_EVENT");
        }
    }

    private void cancelActionEvent(SocketIOClient socketIOClient, String json, AckRequest ackRequest) {
        GameEventModel eventModel = gson.fromJson(json, GameEventModel.class);
        socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("PLAYER_CHANGE_STATE_EVENT", json);
        if (eventModel.getIndex() < playerUsers.size()) {
            playerUsers.get(eventModel.getIndex()).setPlayerState(PlayerStateEnum.CANCEL);
        }
        UserModel nextUser = getNextUser(eventModel.getIndex());
        if (nextUser.isHost() && checkDoneGame()) {
            socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("END_GAME_EVENT");
        } else {
            nextUser.getSocketIOClient().sendEvent("YOUR_TURN_EVENT");
        }
    }

    private boolean checkDoneGame() {
        boolean isDone = true;
        for (UserModel userModel : playerUsers) {
            if (userModel.getPlayerState() == PlayerStateEnum.UPPER) {
                isDone = false;
                break;
            }
        }
        return isDone;
    }

    private UserModel getNextUser(int userIndex) {
        int index = userIndex + 1;
        while (true) {
            if (index < playerUsers.size()) {
                UserModel user = playerUsers.get(index);
                if (index == userIndex) {
                    return user;
                }
                if (user.getPlayerState() == PlayerStateEnum.CANCEL) {
                    index += 1;
                } else {
                    return user;
                }
            } else {
                index = 0;
            }
        }
    }

    private void endGameNotification() {

    }

    private void yourWinNotification() {

    }

    private void updateTotalAmount() {
        String totalAmountStr = "" + totalAmount;
        socketIOServer.getRoomOperations(GAME_GROUP).sendEvent("TOTAL_AMOUNT_UPDATED", totalAmountStr);
    }

    // ======================== CONFIG GAME ===============================
    private void joinGameEvent(SocketIOClient socketIOClient, String jsonString, AckRequest ackRequest) {
        UserEventModel model = gson.fromJson(jsonString, UserEventModel.class);
        socketIOClient.joinRoom(GAME_GROUP);
        if (playerUsers.size() < 8) {
            UserModel userModel = new UserModel(playerUsers.size(), model, socketIOClient);
            userModel.setHost(playerUsers.size() == 0);
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
            LOG.info("User: " + userDisconnect.getUserName() + " thoát khỏi game.");
            if (!gameIsRunning) {
                pushCurrentPlayer(socketIOClient);
            }
        }
    }

}
