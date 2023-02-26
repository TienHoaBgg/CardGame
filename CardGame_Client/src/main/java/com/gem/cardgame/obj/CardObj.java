/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.obj;

import com.gem.cardgame.Utils;
import com.gem.cardgame.objenum.CardType;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Objects;

/**
 *
 * @author gem
 */
public class CardObj extends Obj2D {
    private int value;
    private CardType type;

    public CardObj(int value, CardType type, Image image) {
        this.value = value;
        this.type = type;
        this.img = image;
        this.x = 0;
        this.y = 0;
        this.width = 60;
        this.height = 90;
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

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
    }

}
