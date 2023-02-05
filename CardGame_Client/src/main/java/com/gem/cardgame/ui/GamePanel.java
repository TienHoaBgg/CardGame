/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.ui;

import com.gem.cardgame.ManagePlay;
import com.gem.cardgame.Utils;
import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.obj.SizeObj;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author gem
 */
public class GamePanel extends JPanel {

    private Image bgImage;
    public ManagePlay manage;
    private List<CardObj> cards;
     
     
    public GamePanel() {
        bgImage = Utils.getInstance().getImage("background_board.jpg");
        manage = new ManagePlay();
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        // background image
        graphics2D.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), null);
        manage.drawAll(graphics2D, new SizeObj(this.getWidth(), this.getHeight()));
        
    }
    
    
}
