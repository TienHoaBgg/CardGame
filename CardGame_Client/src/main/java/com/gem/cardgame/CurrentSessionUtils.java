/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

/**
 *
 * @author nth
 */
public class CurrentSessionUtils {
    
    public static String USER_ID;
    public static String USER_NAME;
    public static boolean IS_HOST;
    public static boolean IS_YOUR_TURN;
    
    
    
    private static CurrentSessionUtils instaSessionUtils;
    
    public static CurrentSessionUtils getInstance() {
        if (instaSessionUtils == null) {
            instaSessionUtils = new CurrentSessionUtils();
        }
        return instaSessionUtils;
    }
    
    private CurrentSessionUtils() { }
    
}
