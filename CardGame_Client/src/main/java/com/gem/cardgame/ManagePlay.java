/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.CardManager.CardCallBack;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.obj.UserObj;
import java.awt.Graphics2D;


/**
 *
 * @author gem
 */
public class ManagePlay implements CardCallBack {
    
    public UserManager userManager;
    public CardManager cardManager;
    
    public ManagePlay() {
        userManager = new UserManager();
        cardManager = new CardManager();
        cardManager.setCallBack(this);
        userManager.addUser("sss", "User Random");
    }
    
    public void addUser(String userId, String userName) {
        userManager.addUser(userId, userName);
        
    }
    
    public void test() {
        for(UserObj obj : userManager.users) {
            System.out.println(obj.getUserName() + obj.getPositionEnum());
        }
        
        
    }
    
    public void drawAll(Graphics2D g2, SizeObj size) {
        userManager.drawAll(g2, size);
        cardManager.drawAll(g2, size, userManager.users);
    }

    @Override
    public void CardAnimDone() {
        
        
        
        
    }
    
    
}
