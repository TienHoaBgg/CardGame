package com.gem.game.card.model;

import java.util.List;

public class CardResult {
    private String userId;
    private List<CardModel> cards;
    private int score;
    private ResultType type;

    public CardResult(List<CardModel> cards, int score, ResultType type) {
        this.cards = cards;
        this.score = score;
        this.type = type;
    }

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

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }
}
