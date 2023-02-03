/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame;

import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.obj.CardType;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gem
 */
public class CardManager {
    private List<CardObj> cards;
    
    
    public CardManager () {
        init();
        
    }
    
    private void init() {
        cards = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            Image roImage = Utils.getInstance().getImage("cards/Ro_" + i +".png");
            Image coImage = Utils.getInstance().getImage("cards/Co_" + i +".png");
            Image tepImage = Utils.getInstance().getImage("cards/Tep_" + i +".png");
            Image bichImage = Utils.getInstance().getImage("cards/Bich_" + i +".png");
            cards.add(new CardObj(i, CardType.RO, roImage));
            cards.add(new CardObj(i, CardType.CO, coImage));
            cards.add(new CardObj(i, CardType.TEP, tepImage));
            cards.add(new CardObj(i, CardType.BICH, bichImage));
        }
        Image upImage = Utils.getInstance().getImage("back_card.png");
        cards.add(new CardObj(0, CardType.RO, upImage));
    }
    
//    public void 
    
    
}
