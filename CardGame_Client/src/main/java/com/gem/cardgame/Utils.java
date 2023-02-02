/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author gem
 */
public class Utils {
    
    private static Utils instance;
    
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }
    
    private Utils() { }
     
    
    
    public ImageIcon getImageIcon(String name) {
        Image img = getImage(name);
        if (img != null) {
            return new ImageIcon(img);
        }
        return null;
    }
    
    public Image getImage(String name) {
        String path = "./resource/" + name;
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            logErr(e.getLocalizedMessage());
        }
        return null;
    }
    
    public static void logErr(String err) {
        System.out.println("ERROR: " + err);
    }
    
}
