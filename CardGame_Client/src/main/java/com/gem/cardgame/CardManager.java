/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

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
    private float xStart;
    private float yStart;
    private CardObj blackCard;
    private CardObj centerCard;
    private CardCallBack callBack;
    private int currentIndex;
    private boolean isAnimation;
    
    
    public CardManager () {
        init();
    }
    
    public void setCallBack(CardCallBack callBack) {
        this.callBack = callBack;
    }
    
    private void init() {
        cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            Image roImage = Utils.getInstance().getImage("cards/Ro_" + i +".png");
            Image coImage = Utils.getInstance().getImage("cards/Co_" + i +".png");
            Image tepImage = Utils.getInstance().getImage("cards/Tep_" + i +".png");
            Image bichImage = Utils.getInstance().getImage("cards/Bich_" + i +".png");
            cards.add(new CardObj(i, CardType.RO, roImage));
            cards.add(new CardObj(i, CardType.CO, coImage));
            cards.add(new CardObj(i, CardType.TEP, tepImage));
            cards.add(new CardObj(i, CardType.BICH, bichImage));
        }
        Image upImage = Utils.getInstance().getImage("cards/back_card.png");
        blackCard = new CardObj(0, CardType.UP, upImage);
        centerCard = new CardObj(0, CardType.UP, upImage);
        cards.add(blackCard);
    }
    
    public void startGame() {
        isAnimation = true;
        currentIndex = 0;
        blackCard.setX(centerCard.getX());
        blackCard.setY(centerCard.getY());
    }
    
    public void cardAnimation() {
        UserModel userObj = users.get(currentIndex);
        float userCardX = userObj.getCardPosition().getX();
        float userCardY = userObj.getCardPosition().getY();
        float spaceX;
        float spaceY;
//        if (userCardX > centerCard.getX()) {
            spaceX = (userCardX - centerCard.getX())/15;
//        } else {
//            spaceX = (centerCard.getX() - userCardX)/10;
//        }
//        if (userCardY > centerCard.getY()) {
            spaceY = (userCardY - centerCard.getY())/15;
//        } else {
//            spaceY = (centerCard.getY() - userCardY)/10;
//        }
        float x = blackCard.getX() + spaceX;
        float y = blackCard.getY() + spaceY;
        blackCard.setPosition(new PositionObj(x, y));
        if ((x > userCardX - 5 && x < userCardX + 5) && (y < userCardY + 5 && y > userCardY - 5)) {
            if (currentIndex < users.size() - 1) {
                currentIndex++;
                blackCard.setX(centerCard.getX());
                blackCard.setY(centerCard.getY());
            } else {
                isAnimation = false;
                callBack.CardAnimDone();
            }
        }
    }
    
    public void drawAll(Graphics2D g2, SizeObj screenSize, List<UserModel> users) {
        this.users = users;
        for (UserModel user : users) {
            int cardCount = user.getCards().size();
            switch (user.getPositionEnum()) {
                case BOTTOM -> {
                    float width = screenSize.getWidth()/10;
                    float height = width/0.7f;
                    float xCenter = screenSize.getWidth()/2;
                    float yCard = screenSize.getHeight() - height;
                    user.setCardPosition(new PositionObj(xCenter - width/2, yCard));
                    if (user.isCardOpen()) {
                        float widthCards = cardCount * (width*2/3);
                        xCenter -= widthCards/2;
                    } else {
                        xCenter -= width/2;
                    }
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (user.isCardOpen()) {
                            card.setPosition(xCenter, yCard);
                            card.draw(g2);
                            xCenter += width*2/3;
                        } else {
                            card.setPosition(xCenter, yCard);
                            card.draw(g2);
                        }
                    }
                }
                case TOP -> {
                    float width = screenSize.getWidth()/17;
                    float height = width/0.7f;
                    float xCenter = screenSize.getWidth()/2;
                    float yCard = user.getY() + user.getHeight() + 24;
                    user.setCardPosition(new PositionObj(xCenter - width/2, yCard));
                    if (user.isCardOpen()) {
                        float widthCards = cardCount * (width*2/3);
                        xCenter -= widthCards/2;
                    } else {
                        xCenter -= width/2;
                    }
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (user.isCardOpen()) {
                            card.setPosition(xCenter, yCard);
                            card.draw(g2);
                            xCenter += width*2/3;
                        } else {
                            card.setPosition(xCenter, yCard);
                            card.draw(g2);
                        }
                    }
                }
                case LEFT, BOTTOMLEFT, TOPLEFT -> {
                    float width = screenSize.getWidth()/17;
                    float height = width/0.7f;
                    float xCard = user.getX() + user.getWidth() + 20;
                    float yCard = user.getY();
                    user.setCardPosition(new PositionObj(xCard, yCard));
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (user.isCardOpen()) {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                            xCard += width*2/cardCount;
                        } else {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                        }
                    }
                }
                case RIGHT, TOPRIGHT, BOTTOMRIGHT -> {
                    float width = screenSize.getWidth()/17;
                    float height = width/0.7f;
                    float xCard = user.getX() - 24;
                    float yCard = user.getY();
                    user.setCardPosition(new PositionObj(xCard, yCard));
                    
                    if (user.isCardOpen()) {
                        float widthCards = 2 * (width*2/cardCount) + width;
                        xCard -= widthCards;
                    } else {
                        xCard -= width;
                    }
                    
                    for (int i = 0; i < cardCount; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (user.isCardOpen()) {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                            xCard += width*2/cardCount;
                        } else {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                        }
                    }
                }
                default -> { }
            }
        }
        drawBlackCard(g2, screenSize);
    }
    
    private void drawBlackCard(Graphics2D g2, SizeObj screenSize) {
        float width = screenSize.getWidth()/17;
        float height = width/0.7f;
        float xCenter = screenSize.getWidth()/2 - width/2;
        float yCenter = screenSize.getHeight()/2 - height/2;
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
