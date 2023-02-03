/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.obj.SizeObj;
import java.awt.Graphics2D;


/**
 *
 * @author gem
 */
public class ManagePlay {
    
    public UserManager userManager;
    
    public ManagePlay() {
        userManager = new UserManager();
       userManager.addUser("sss", "User Random");
    }
    
    public void addUser(String userId, String userName) {
        userManager.addUser(userId, userName);
        
    }
    
    public void drawAll(Graphics2D g2, SizeObj size) {
        userManager.drawAll(g2, size);
        
    }
    
    
}
