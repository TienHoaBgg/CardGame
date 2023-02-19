/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.ui;

import com.gem.cardgame.CardManager;
import com.gem.cardgame.CardManager.CardCallBack;
import com.gem.cardgame.CurrentSessionUtils;
import com.gem.cardgame.UserManager;
import com.gem.cardgame.Utils;
import com.gem.cardgame.obj.SizeObj;
import io.socket.client.Socket;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author gem
 */
public class GamePanel extends JPanel implements CardCallBack {
    private final Socket socket;
    private final Image bgImage;
    public UserManager userManager;
    public CardManager cardManager;
    private boolean isRunning;
    private ButtonsPanel pnButtons; 
    private SliderDialog sliderDialog;
    private JButton startButton;
    private boolean gameStarted = false;
    
    
    public GamePanel(Socket socket) {
        this.socket = socket;
        initPNButton();
        bgImage = Utils.getInstance().getImage("background_board.jpg");
        userManager = new UserManager();
        cardManager = new CardManager();
        cardManager.setCallBack(this);
    }
    
    public void gameStarted() {
        gameStarted = true;
        startButton.setVisible(false);
        
    }
    
    private void initPNButton() {
        initStartButton();
        pnButtons = new ButtonsPanel();
        sliderDialog = new SliderDialog(null, true);
        add(pnButtons);
        repaint();
        pnButtons.setCallBack(new ButtonsPanel.IButtonCallBack() {
            @Override
            public void toClickAction() {
                sliderDialog.setValueTo(0, 100);
                sliderDialog.setVisible(true);
                
            }

            @Override
            public void theoClickAction() {
                sliderDialog.setValueTo(100, 200);
                sliderDialog.setVisible(true);
            }

            @Override
            public void boClickAction() {
                
            }
        });
        sliderDialog.setCallBack((int value) -> {
            System.out.println("Tố: " + value);
        });
        
    }
    
    private void initStartButton() {
        startButton = new JButton("Bắt đầu");
        startButton.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 32));
        startButton.setBounds(this.getWidth()/2 - 100, this.getHeight()/2 - 40, 200, 80);
        startButton.setBackground(new Color(243, 185, 45));
        startButton.setForeground(Color.WHITE);
        add(startButton);
        startButton.addActionListener((ActionEvent e) -> {
            socket.emit("START_GAME", CurrentSessionUtils.USER_ID);
            gameStarted = true;
            startCard();
        });
    }
    
    private void updateU(SizeObj screenSize) {
        pnButtons.setBounds((int)screenSize.getWidth() - 206, (int)screenSize.getHeight() - 76, 206, 76);
        startButton.setBounds(this.getWidth()/2 - 100, this.getHeight()/2 - 40, 200, 80);
        pnButtons.updateView();
    }
    
    public void startCard() {
        isRunning = true;
        cardManager.startGame();
        new Thread((() -> {
            while (isRunning) {
                try {
                    cardManager.cardAnimation();
                    repaint();
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Utils.logErr(ex.getLocalizedMessage());
                }
            }
        })).start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        SizeObj screenSize = new SizeObj(this.getWidth(), this.getHeight());
        
        // background image
        graphics2D.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), null);
        userManager.drawAll(graphics2D, screenSize);
        cardManager.drawAll(graphics2D, screenSize, userManager.getUsers(), gameStarted);
        updateU(screenSize);
    }

    @Override
    public void CardAnimDone() {
        isRunning = false;
    }

    
}
