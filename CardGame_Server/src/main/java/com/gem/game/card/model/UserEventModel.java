package com.gem.game.card.model;

import com.corundumstudio.socketio.SocketIOClient;

public class UserEventModel {
    private int index;
    private String userID;
    private String userName;
    private boolean isHost;

    public UserEventModel(int index, String userID, String userName, boolean isHost) {
        this.userID = userID;
        this.userName = userName;
        this.index = index;
        this.isHost = isHost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
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

}
