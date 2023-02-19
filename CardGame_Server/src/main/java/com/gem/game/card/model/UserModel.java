package com.gem.game.card.model;

import com.corundumstudio.socketio.SocketIOClient;

public class UserModel {
    private int index;
    private String userID;
    private String userName;
    private SocketIOClient socketIOClient;

    public UserModel(int index, String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public UserModel(int index, UserEventModel userEventModel, SocketIOClient socketIOClient) {
        this.index = index;
        this.userID = userEventModel.getUserID();
        this.userName = userEventModel.getUserName();
        this.socketIOClient = socketIOClient;
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

    public SocketIOClient getSocketIOClient() {
        return socketIOClient;
    }

    public void setSocketIOClient(SocketIOClient socketIOClient) {
        this.socketIOClient = socketIOClient;
    }

    public UserEventModel toUserEvent() {
        return new UserEventModel(this.index, this.userID, this.userName);
    }

}
