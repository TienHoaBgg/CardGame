/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.model.CardModel;
import com.gem.cardgame.model.CardResult;
import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.objenum.CardType;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.model.UserModel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gem
 */
public class CardManager {

    private List<CardObj> cards;
    private List<UserModel> users;
    private CardObj blackCard;
    private CardObj centerCard;
    private CardCallBack callBack;
    private int currentIndex;
    private boolean isAnimation;
    private List<CardModel> myCards;
    
    private int animationRepeatCount = 0;

    public CardManager() {
        init();
    }

    public void setCallBack(CardCallBack callBack) {
        this.callBack = callBack;
    }

    private void init() {
        cards = new ArrayList<>();
        myCards = new ArrayList<>();
        Image upImage = Utils.getInstance().getImage("cards/back_card.png");
        blackCard = new CardObj(0, CardType.UP, upImage);
        centerCard = new CardObj(0, CardType.UP, upImage);
    }

    public CardObj getBlackCard() {
        Image upImage = Utils.getInstance().getImage("cards/back_card.png");
        return new CardObj(0, CardType.UP, upImage);
    }
    
    public void addMyCard(List<CardModel> cardIds) {
        if (isAnimation) {
            myCards = cardIds;
        } else {
            setCardToUser(cardIds, CurrentSessionUtils.USER_ID);
        }
    }

    public void updateCardOtherUser(List<CardResult> results) {
        for (CardResult result : results) {
            setCardToUser(result.getCards(), result.getUserId());
        }
    }

    public void setCardToUser(List<CardModel> cards, String userId) {
        for(UserModel user : users) {
            if (user.getUserId().equals(userId)) {
                List<CardObj> cardObjs = new ArrayList<>();
                for (CardModel model : cards) {
                    Image image = Utils.getInstance().getImage(model.getUrl());
                    cardObjs.add(new CardObj(model.getValue(), model.getType(), image));
                }
                user.setCards(cardObjs);
                break;
            }
        }
    }
    
    public void setupAnimationCard() {
        isAnimation = true;
        currentIndex = 0;
        blackCard.setX(centerCard.getX());
        blackCard.setY(centerCard.getY());
        users.forEach(user -> {
            if (user.getCards().isEmpty()) {
                user.getCards().add(getBlackCard());
            }
        });
    }

    public void cardAnimation() {
        UserModel userObj = users.get(currentIndex);
        float userCardX = userObj.getCardPosition().getX();
        float userCardY = userObj.getCardPosition().getY();
        float spaceX;
        float spaceY;
        spaceX = (userCardX - centerCard.getX()) / 15;
        spaceY = (userCardY - centerCard.getY()) / 15;
        float x = blackCard.getX() + spaceX;
        float y = blackCard.getY() + spaceY;
        blackCard.setPosition(new PositionObj(x, y));
        if ((x > userCardX - 5 && x < userCardX + 5) && (y < userCardY + 5 && y > userCardY - 5)) {
            if (currentIndex < users.size() - 1) {
                currentIndex++;
                blackCard.setX(centerCard.getX());
                blackCard.setY(centerCard.getY());
            } else {
                animationRepeatCount++;
                if (animationRepeatCount < 3) {
                    setupAnimationCard();
                } else {
                    isAnimation = false;
                    callBack.CardAnimDone();
                    animationRepeatCount = 0;
                    if (!myCards.isEmpty()) {
                        setCardToUser(myCards, CurrentSessionUtils.USER_ID);
                    }
                }
            }
        }
    }

    public void drawAll(Graphics2D g2, SizeObj screenSize, List<UserModel> users, boolean isGameStarted) {
        this.users = users;
        for (UserModel user : users) {
            int cardCount = user.getCards().size();
            switch (user.getPositionEnum()) {
                case BOTTOM -> {
                    float width = screenSize.getWidth() / 10;
                    float height = width / 0.7f;
                    float xCenter = screenSize.getWidth() / 2;
                    float yCard = screenSize.getHeight() - height;
                    user.setCardPosition(new PositionObj(xCenter - width / 2, yCard));
                    float widthCards = cardCount * (width * 2 / 3);
                    xCenter -= widthCards / 2;
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = user.getCards().get(i);
                        card.setSize(width, height);
                        card.setPosition(xCenter, yCard);
                        card.draw(g2);
                        xCenter += width * 2 / cardCount;
                    }
                }
                case TOP -> {
                    float width = screenSize.getWidth() / 17;
                    float height = width / 0.7f;
                    float xCenter = screenSize.getWidth() / 2;
                    float yCard = user.getY() + user.getHeight() + 24;
                    user.setCardPosition(new PositionObj(xCenter - width / 2, yCard));
                    int cardNumber = cardCount - 1;
                    float widthCards = (Math.max(cardNumber, 0)) * (width * 2/3) + width;
                    xCenter -= widthCards / 2;
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = user.getCards().get(i);
                        card.setSize(width, height);
                        card.setPosition(xCenter, yCard);
                        card.draw(g2);
                        xCenter += width * 2 / cardCount;
                    }
                    user.drawResult(g2);
                }
                case LEFT, BOTTOMLEFT, TOPLEFT -> {
                    float width = screenSize.getWidth() / 17;
                    float height = width / 0.7f;
                    float xCard = user.getX() + user.getWidth() + 20;
                    float yCard = user.getY();
                    user.setCardPosition(new PositionObj(xCard, yCard));
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = user.getCards().get(i);
                        card.setSize(width, height);
                        card.setPosition(xCard, yCard);
                        card.draw(g2);
                        xCard += width * 2 / cardCount;
                    }
                    user.drawResult(g2);
                }
                case RIGHT, TOPRIGHT, BOTTOMRIGHT -> {
                    float width = screenSize.getWidth() / 17;
                    float height = width / 0.7f;
                    float xCard = user.getX() - 24;
                    float yCard = user.getY();
                    user.setCardPosition(new PositionObj(xCard, yCard));
                    int cardNumber = cardCount - 1;
                    float widthCards = (Math.max(cardNumber, 0)) * (width * 2/3) + width;
                    xCard -= widthCards;
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = user.getCards().get(i);
                        card.setSize(width, height);
                        card.setPosition(xCard, yCard);
                        card.draw(g2);
                        xCard += width * 2/3;
                    }
                    user.drawResult(g2);
                }
                default -> {
                }
            }
        }
        drawBlackCard(g2, screenSize);
    }

    private void drawBlackCard(Graphics2D g2, SizeObj screenSize) {
        float width = screenSize.getWidth() / 17;
        float height = width / 0.7f;
        float xCenter = screenSize.getWidth() / 2 - width / 2;
        float yCenter = screenSize.getHeight() / 2 - height / 2;
        centerCard.setPosition(new PositionObj(xCenter, yCenter));
        centerCard.setSize(width, height);
        centerCard.draw(g2);
        if (isAnimation) {
            blackCard.setSize(width, height);
            blackCard.draw(g2);
        }
    }

    public interface CardCallBack {
        void CardAnimDone();
    }

}
