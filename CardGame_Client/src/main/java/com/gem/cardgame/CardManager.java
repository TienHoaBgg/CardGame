/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.obj.CardType;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.obj.UserObj;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author gem
 */
public class CardManager {
    
    private List<CardObj> cards;
    private Timer timer;
    private UserObj currentUser;
    private float xStart;
    private float yStart;
    
    private CardObj blackCard;
    
    public CardManager () {
        init();
        timer = new Timer(10, (ActionEvent e) -> {
            
            
        });
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
        cards.add(blackCard);
    }
    
    public void cardAnimation(Graphics2D g2, SizeObj screenSize, UserObj user) {
        timer.stop();
        currentUser = user;
        float width = screenSize.getWidth()/17;
        float height = width/0.7f;
        float xCenter = screenSize.getWidth()/2 - width/2;
        float yCenter = screenSize.getHeight()/2 - height/2;
        
        
        
    }
    
    
    public void drawAll(Graphics2D g2, SizeObj screenSize, List<UserObj> users) {
        drawBlackCard(g2, screenSize);
        for (UserObj user : users) {
            switch (user.getPositionEnum()) {
                case BOTTOM -> {
                    float width = screenSize.getWidth()/10;
                    float height = width/0.7f;
                    float xCenter = screenSize.getWidth()/2;
                    float yCard = screenSize.getHeight() - height;
                    user.setCardPosition(new PositionObj(xCenter - width/2, yCard));
                    if (!user.isCardOpen()) {
                        float widthCards = 3 * (width*2/3);
                        xCenter -= widthCards/2;
                    } else {
                        xCenter -= width/2;
                    }
                    for (int i = 0; i < 3; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (!user.isCardOpen()) {
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
                        float widthCards = 3 * (width*2/3);
                        xCenter -= widthCards/2;
                    } else {
                        xCenter -= width/2;
                    }
                    for (int i = 0; i < 3; i++) {
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
                    for (int i = 0; i < 3; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (!user.isCardOpen()) {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                            xCard += width*2/3;
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
                    
                    if (!user.isCardOpen()) {
                        float widthCards = 2 * (width*2/3) + width;
                        xCard -= widthCards;
                    } else {
                        xCard -= width;
                    }
                    
                    for (int i = 0; i < 3; i++) {
                        CardObj card = cards.get(i);
                        card.setSize(width, height);
                        if (!user.isCardOpen()) {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                            xCard += width*2/3;
                        } else {
                            card.setPosition(xCard, yCard);
                            card.draw(g2);
                        }
                    }
                }
                default -> { }
            }
        }
    }
    
    private void drawBlackCard(Graphics2D g2, SizeObj screenSize) {
        float width = screenSize.getWidth()/17;
        float height = width/0.7f;
        float xCenter = screenSize.getWidth()/2 - width/2;
        float yCenter = screenSize.getHeight()/2 - height/2;
        blackCard.setPosition(new PositionObj(xCenter, yCenter));
        blackCard.setSize(width, height);
        blackCard.draw(g2);
    }
    
}
