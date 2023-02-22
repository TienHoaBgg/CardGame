package com.gem.game.card.model;

public class GameEventModel {
    private int index;
    private String userId;
    private PlayerStateEnum state;
    private int amount;

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
