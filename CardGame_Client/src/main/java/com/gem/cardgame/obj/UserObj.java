/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.obj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gem
 */
public class UserObj extends Obj2D {
    private String userId;
    private String userName;
    private int price;
    private boolean isRoot;
    private PositionEnum positionEnum;
    private List<CardObj> cards;
    private boolean cardOpen;
    private PositionObj cardPosition;

    public UserObj(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        cards = new ArrayList<>();
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isIsRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PositionEnum getPositionEnum() {
        return positionEnum;
    }

    public void setPositionEnum(PositionEnum positionEnum) {
        this.positionEnum = positionEnum;
    }

    public List<CardObj> getCards() {
        return cards;
    }

    public void setCards(List<CardObj> cards) {
        this.cards = cards;
    }

    public boolean isCardOpen() {
        return cardOpen;
    }

    public void setCardOpen(boolean cardOpen) {
        this.cardOpen = cardOpen;
    }

    public PositionObj getCardPosition() {
        return cardPosition;
    }

    public void setCardPosition(PositionObj cardPosition) {
        this.cardPosition = cardPosition;
    }
    
    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        g2.setColor(Color.LIGHT_GRAY);
        int widthName = g2.getFontMetrics().stringWidth(userName);
        float nameX = (x + width/2) - (widthName/2);
        g2.drawString(userName, nameX, y);
        
        String priceStr = price + "K";
        int widthPrice = g2.getFontMetrics().stringWidth(priceStr);
        g2.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        g2.setColor(Color.ORANGE);
        float priceX = (x + width/2) - (widthPrice/2);
        g2.drawString(priceStr, priceX, y + height + 12);
    }
    
}
