/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.model;

import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.obj.Obj2D;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.objenum.PositionEnum;
import com.gem.cardgame.objenum.UserStatusEnum;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gem
 */
public class UserModel extends Obj2D {
    private String userId;
    private String userName;
    private int price;
    private UserStatusEnum status;
    private boolean isHost;
    
    private PositionEnum positionEnum;
    private List<CardObj> cards;
    private PositionObj cardPosition;

    public UserModel(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        cards = new ArrayList<>();
    }

    public UserModel(UserEventModel eventModel) {
        this.userId = eventModel.getUserID();
        this.userName = eventModel.getUserName();
        this.isHost = eventModel.isHost();
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

    public boolean isHost() {
        return isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }
    
    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PositionEnum getPositionEnum() {
        if (positionEnum == null) {
            positionEnum = PositionEnum.BOTTOM;
        }
        return positionEnum;
    }

    public void setPositionEnum(PositionEnum positionEnum) {
        this.positionEnum = positionEnum;
    }

    public List<CardObj> getCards() {
        if (cards == null) {
            cards = new ArrayList<>();
        }
        return cards;
    }

    public void setCards(List<CardObj> cards) {
        this.cards = cards;
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
        String statusString = "";
        if (status != null) {
             switch (status) {
            case READY -> {
                statusString = "";
            }
            case TO -> {
                statusString = "ĐÃ Tố";
            }
            case THEO -> {
                statusString = "THEO";
            }
            case BO -> {
                statusString = "ĐÃ BỎ";
            }
            case WINNER -> { statusString = "WINNER"; }
        }
        int widthPrice = g2.getFontMetrics().stringWidth(statusString);
        g2.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 18));
        g2.setColor(Color.ORANGE);
        float priceX = (x + width/2) - (widthPrice/2);
        g2.drawString(statusString, priceX, y + height + 12);
        }
    }
    
}
