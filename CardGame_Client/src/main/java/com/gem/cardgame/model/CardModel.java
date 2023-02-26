package com.gem.cardgame.model;

import com.gem.cardgame.objenum.CardType;

public class CardModel {
    private int value;
    private CardType type;
    private String url;

    public CardModel(int value, CardType type, String url) {
        this.value = value;
        this.type = type;
        this.url = url;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
