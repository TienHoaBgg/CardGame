package com.gem.game.card.model;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private int index;
    private String userID;
    private String userName;
    private boolean isHost;
    private boolean yourTurn;
    private PlayerStateEnum playerState;
    private List<Integer> cardIds;
    private SocketIOClient socketIOClient;
    private CardResult cardResult;

    public UserModel(int index, UserEventModel userEventModel, SocketIOClient socketIOClient) {
        this.index = index;
        this.userID = userEventModel.getUserID();
        this.userName = userEventModel.getUserName();
        this.socketIOClient = socketIOClient;
        this.cardIds = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public PlayerStateEnum getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerStateEnum playerState) {
        this.playerState = playerState;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cards) {
        this.cardIds = cards;
    }

    public SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }

    public void setSocketIOClient(SocketIOClient socketIOClient) {
        this.socketIOClient = socketIOClient;
    }

    public CardResult getCardResult() {
        return cardResult;
    }

    public void setCardResult(CardResult cardResult) {
        this.cardResult = cardResult;
    }

    public UserEventModel toUserEvent() {
        return new UserEventModel(this.index, this.userID, this.userName, this.isHost);
    }

}
