/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.model;

/**
 *
 * @author nth
 */
public class UserEventModel {
    private int index;
    private String userID;
    private String userName;

    public UserEventModel(int index, String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
        this.index = index;
    }

    public UserEventModel(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
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
