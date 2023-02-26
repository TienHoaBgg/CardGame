package com.gem.cardgame.model;

import java.util.List;

public class CardResult {
    private String userId;
    private List<CardModel> cards;
    private int score;
    private CardResultType type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CardModel> getCards() {
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public CardResultType getType() {
        return type;
    }

    public void setType(CardResultType type) {
        this.type = type;
    }
}
