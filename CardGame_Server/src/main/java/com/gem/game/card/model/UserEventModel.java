package com.gem.game.card.model;

import com.corundumstudio.socketio.SocketIOClient;

public class UserEventModel {
    private String userID;
    private String userName;

    public UserEventModel(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
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
