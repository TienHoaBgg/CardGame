/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.obj;

import com.gem.cardgame.objenum.CardType;
import java.awt.Image;

/**
 *
 * @author gem
 */
public class CardObj extends Obj2D {
    private int value;
    private CardType type;
    private boolean isUp;
    private boolean isOpen;

    public CardObj(int value, CardType type) {
        this.value = value;
        this.type = type;
    }

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

    public boolean isIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
}
