package com.gem.game.card.model;

import com.corundumstudio.socketio.SocketIOClient;

public class UserEventModel {
    private int index;
    private String userID;
    private String userName;

    public UserEventModel(int index, String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        this.index = index;
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

}
