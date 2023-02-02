/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.ui;

import com.gem.cardgame.Utils;
import static com.gem.cardgame.Utils.logErr;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author gem
 */
public class GamePanel extends JPanel {

    private Image bgImage;
    
    public GamePanel() {
        try {
            bgImage = ImageIO.read(new File("/resource/avt.jpg"));
//
//        bgImage = imgIcon.getImage();
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setBackground(Color.red);

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        
//        graphics2D.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), null);
        
        
        
        
    }
    
    
    
}
