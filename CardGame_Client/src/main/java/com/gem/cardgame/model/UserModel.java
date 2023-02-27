/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.model;

import com.gem.cardgame.CurrentSessionUtils;
import static com.gem.cardgame.model.CardResultType.ANH;
import static com.gem.cardgame.model.CardResultType.LIENG;
import static com.gem.cardgame.model.CardResultType.NONE;
import static com.gem.cardgame.model.CardResultType.SAP;
import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.obj.Obj2D;
import com.gem.cardgame.obj.PositionObj;
import com.gem.cardgame.objenum.PositionEnum;
import com.gem.cardgame.objenum.PlayerStateEnum;
import static com.gem.cardgame.objenum.PositionEnum.BOTTOMLEFT;
import static com.gem.cardgame.objenum.PositionEnum.BOTTOMRIGHT;
import static com.gem.cardgame.objenum.PositionEnum.LEFT;
import static com.gem.cardgame.objenum.PositionEnum.RIGHT;
import static com.gem.cardgame.objenum.PositionEnum.TOP;
import static com.gem.cardgame.objenum.PositionEnum.TOPLEFT;
import static com.gem.cardgame.objenum.PositionEnum.TOPRIGHT;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author gem
 */
public class UserModel extends Obj2D {

    private String userId;
    private String userName;
    private int price;
    private PlayerStateEnum state;
    private boolean isHost;

    private PositionEnum positionEnum;
    private List<CardObj> cards;
    private PositionObj cardPosition;

    private CardResult result;

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

    public PlayerStateEnum getStatus() {
        return state;
    }

    public void setStatus(PlayerStateEnum status) {
        this.state = status;
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

    public CardResult getResult() {
        return result;
    }

    public void setResult(CardResult result) {
        this.result = result;
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setFont(new Font("Tahoma", Font.BOLD, 13));
        g2.setColor(Color.LIGHT_GRAY);
        int widthName = g2.getFontMetrics().stringWidth(userName);
        float nameX = (x + width / 2) - (widthName / 2f);
        g2.drawString(userName, nameX, y);
        String statusString = "";
        if (state != null) {
            switch (state) {
                case NONE ->
                    statusString = "NONE";
                case UPPER -> {
                    statusString = "Đã tố";
                    g2.setColor(Color.GREEN);
                }
                case FOLLOW -> {
                    statusString = "Theo";
                    g2.setColor(new Color(254, 128, 41));
                }
                case CANCEL -> {
                    statusString = "Đã bỏ";
                    g2.setColor(Color.WHITE);
                }
                case WINNER -> {
                    statusString = "WINNER";
                    g2.setColor(Color.RED);
                }
            }
            int widthStatus = g2.getFontMetrics().stringWidth(statusString);
            g2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
            float stateX = (x + width / 2) - (widthStatus / 2f) - 5;
            g2.drawString(statusString, stateX, y + height / 2);
        }
    }

    public void drawResult(Graphics2D g2) {
        if (result != null && !cards.isEmpty() && !Objects.equals(userId, CurrentSessionUtils.USER_ID)) {
            String resultString = "";
            switch (result.getType()) {
                case NONE ->
                    resultString = result.getScore() + " điểm";
                case LIENG ->
                    resultString = "Liêng";
                case SAP ->
                    resultString = "Sáp";
                case ANH ->
                    resultString = "Ảnh";
                default -> {
                }
            }
            g2.setColor(Color.BLUE);
            int widthStatus = g2.getFontMetrics().stringWidth(resultString);
            g2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
            float resultX = cardPosition.getX();
            switch (positionEnum) {
                case TOP ->
                    resultX = cardPosition.getX() - widthStatus / 2;
                case RIGHT, TOPRIGHT, BOTTOMRIGHT ->
                    resultX = cardPosition.getX() - widthStatus;
                case LEFT, BOTTOMLEFT, TOPLEFT ->
                    resultX = cardPosition.getX();
                default -> {
                }
            }
            g2.drawString(resultString, resultX, cardPosition.getY() + 40);
        }
    }

}
