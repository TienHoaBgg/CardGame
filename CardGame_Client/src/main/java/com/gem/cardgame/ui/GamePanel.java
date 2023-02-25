/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gem.cardgame.ui;

import com.gem.cardgame.CardManager;
import com.gem.cardgame.CardManager.CardCallBack;
import com.gem.cardgame.Constants;
import com.gem.cardgame.CurrentSessionUtils;
import com.gem.cardgame.UserManager;
import com.gem.cardgame.Utils;
import com.gem.cardgame.model.GameEventModel;
import com.gem.cardgame.obj.SizeObj;
import com.gem.cardgame.objenum.PlayerStateEnum;
import com.google.gson.Gson;
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
    private final Gson gson;
    // Tien Ga
    private int totalAmount = 0;
    // Tien nguoi dung to
    private int userUpperAmount = 0;
    // Tien cua ban da choi trong van
    private int yourAmount = 0;
    
    public GamePanel(Socket socket) {
        this.socket = socket;
        gson = new Gson();
        initPNButton();
        bgImage = Utils.getInstance().getImage("background_board.jpg");
        userManager = new UserManager();
        cardManager = new CardManager();
        cardManager.setCallBack(this);
    }
    
    public void gameStarted() {
        gameStarted = true;
        startGameAnimation();
        startButton.setVisible(false);
        totalAmount = 0;
        userUpperAmount = 0;
        yourAmount = 0;
    }
    
    public void endGameEvent() {
        System.out.println("==>> Total: "+ totalAmount);
        System.out.println("==>> Your Amount: "+ yourAmount);
        System.out.println("==>> Upper : "+ userUpperAmount);
    }
    
    public void yourTurn() {
        pnButtons.setVisiableButton(CurrentSessionUtils.IS_YOUR_TURN);
        
    }
    
    public void setVisiablePlayButton() {
        startButton.setVisible(CurrentSessionUtils.IS_HOST);
    }
    
    public void updateTotalAmount(int amount) {
        totalAmount = amount;
        repaint();
    }
    
    public void upperEvent(int value) {
        userUpperAmount = value;
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
                int value = Constants.AMOUNT_DEFAULT;
                if (userUpperAmount > yourAmount) {
                    value = userUpperAmount - yourAmount;
                }
                int max = value + 50;
                sliderDialog.setValueTo(value, max);
                sliderDialog.setVisible(true);
            }

            @Override
            public void theoClickAction() {
                int value = Constants.AMOUNT_DEFAULT;
                if (userUpperAmount > yourAmount) {
                    value = userUpperAmount - yourAmount;
                }
                yourAmount += value;
                GameEventModel model = new GameEventModel(value, PlayerStateEnum.FOLLOW);
                socket.emit("PLAYER_FOLLOW_EVENT", gson.toJson(model));
            }

            @Override
            public void boClickAction() {
                GameEventModel model = new GameEventModel(0, PlayerStateEnum.CANCEL);
                socket.emit("PLAYER_CANCEL_EVENT", gson.toJson(model));
            }
        });
        sliderDialog.setCallBack((int value, boolean isUp) -> {
            yourAmount += value;
            if (isUp) {
                GameEventModel model = new GameEventModel(value, PlayerStateEnum.UPPER);
                socket.emit("PLAYER_UPPER_EVENT", gson.toJson(model));
            } else {
                GameEventModel model = new GameEventModel(value, PlayerStateEnum.FOLLOW);
                socket.emit("PLAYER_FOLLOW_EVENT", gson.toJson(model));
            }
        });
        pnButtons.setVisiableButton(false);
    }
    
    private void initStartButton() {
        startButton = new JButton("Bắt đầu");
        startButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 32));
        startButton.setBounds(this.getWidth()/2 - 100, this.getHeight()/2 - 35, 200, 69);
        startButton.setBackground(new Color(225, 77, 45));
        startButton.setForeground(Color.WHITE);
        startButton.setVisible(false);
        add(startButton);
        startButton.addActionListener((ActionEvent e) -> {
            socket.emit("START_GAME", CurrentSessionUtils.USER_ID);
        });
    }
    
    private void updateU(SizeObj screenSize) {
        pnButtons.setBounds((int)screenSize.getWidth() - 206, (int)screenSize.getHeight() - 76, 206, 76);
        startButton.setBounds(this.getWidth()/2 - 100, this.getHeight()/2 - 35, 200, 69);
        pnButtons.updateView();
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
        if (gameStarted) {
            drawTotalAmount(graphics2D);
        }
        updateU(screenSize);
    }

    private void drawTotalAmount(Graphics2D g2) {
        String totalAmountStr = "" + totalAmount + "K";
        int widthPrice = g2.getFontMetrics().stringWidth(totalAmountStr);
        g2.setFont(new Font("Tahoma", Font.BOLD, 36));
        g2.setColor(new Color(254, 128, 41));
        float pX = this.getWidth()/2 - widthPrice/2;
        float pY = this.getHeight()/2 - 20;
        g2.drawString(totalAmountStr, pX, pY);
    }
    
    public void startGameAnimation() {
        isRunning = true;
        cardManager.setupAnimationCard();
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
    public void CardAnimDone() {
        isRunning = false;
        pnButtons.setVisiableButton(CurrentSessionUtils.IS_YOUR_TURN);
    }
    
}
