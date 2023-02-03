/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.obj;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author gem
 */
public class Obj2D {
    
    protected float x,y;
    protected float width, height;
    protected Image img;
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setPosition(PositionObj position) {
        this.x = position.getX();
        this.y = position.getY();
    }
    
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    public void setSize(SizeObj size) {
        this.width = size.getWidth();
        this.height = size.getHeight();
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(img, (int)x, (int)y, (int)width, (int)height, null);
        
    }
    
}
