/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.obj;

import java.awt.Image;

/**
 *
 * @author gem
 */
public class CardObj extends Obj2D {
    private int value;
    private CardType type;

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
        this.width = 50;
        this.height = 80;
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
    
}
