/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.model;

import com.gem.cardgame.CurrentSessionUtils;
import com.gem.cardgame.objenum.PlayerStateEnum;

/**
 *
 * @author nth
 */
public class GameEventModel {
    private int index;
    private String userId;
    private PlayerStateEnum state;
    private int amount;

    public GameEventModel(int amount, PlayerStateEnum state) {
        this.index = CurrentSessionUtils.USER_INDEX;
        this.userId = CurrentSessionUtils.USER_ID;
        this.amount = amount;
        this.state = state;
    }
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PlayerStateEnum getState() {
        return state;
    }

    public void setState(PlayerStateEnum state) {
        this.state = state;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
