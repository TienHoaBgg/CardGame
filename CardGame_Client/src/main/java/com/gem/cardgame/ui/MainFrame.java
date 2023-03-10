/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gem.cardgame.ui;

import com.gem.cardgame.CurrentSessionUtils;
import com.gem.cardgame.SocketManager;
import com.gem.cardgame.model.*;
import com.gem.cardgame.obj.CardObj;
import com.gem.cardgame.objenum.PlayerStateEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.socket.client.Socket;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author gem
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private final GamePanel gameView;
    private final Socket socket;
    private final Gson gson;

    public MainFrame() {
        initComponents();
        socket = SocketManager.getInstance().getSocket();
        gameView = new GamePanel(socket);
        mainPanel.add(gameView);
        gson = new Gson();
        socketEventListener();
    }

    private void socketEventListener() {
        socket.on("CHAT_EVENT_LISTENER", (args) -> {
            String jsonString = args[0].toString();
            ChatEventModel model = gson.fromJson(jsonString, ChatEventModel.class);
            if (model != null) {
                String message = model.getUserName() + ": " + model.getMessage() + "\n";
                jTextChat.setEditable(true);
                appendToLogger(message, Color.BLUE);
            }
        });
        socket.on("TURN_EVENT_LISTENER", (args) -> {
            String jsonString = args[0].toString();
            String message;
            if (jsonString.equals(CurrentSessionUtils.USER_NAME)) {
                message = "=>> Đến lượt bạn đi" + "\n";
            } else {
                message = "=>> Đến lượt " + jsonString +" đi" + "\n";
            }
            jTextChat.setEditable(true);
            appendToLogger(message, Color.DARK_GRAY);
        });
        UserEventModel user = new UserEventModel(CurrentSessionUtils.USER_ID, CurrentSessionUtils.USER_NAME);
        socket.emit("JOIN_GAME_REQUEST", gson.toJson(user));
        
        socket.on("CURRENT_PLAYERS", (args) -> {
            String json = args[0].toString();
            List<UserEventModel> currentPlayers = gson.fromJson(json, new TypeToken<List<UserEventModel>>() {}.getType());
            gameView.userManager.setUsers(currentPlayers);
            
            gameView.setVisiblePlayButton();
            repaint();
            updateCurrentUser();
        });
        
        socket.on("GAME_STARTED",  (args) -> {
            String hostUserId = args[0].toString();
            if (Objects.equals(hostUserId, CurrentSessionUtils.USER_ID)) {
                CurrentSessionUtils.IS_HOST = true;
                CurrentSessionUtils.IS_YOUR_TURN = true;
            } else {
                CurrentSessionUtils.IS_HOST = false;
                CurrentSessionUtils.IS_YOUR_TURN = false;
            }
            gameView.gameStarted();
        });
        
        socket.on("MY_CARD_EVENT", (args) -> {
            String json = args[0].toString();
            List<CardModel> cards = gson.fromJson(json, new TypeToken<List<CardModel>>() {}.getType());
            gameView.cardManager.addMyCard(cards);
        });

        socket.on("TOTAL_AMOUNT_UPDATED", (args) -> {
            String amountStr = args[0].toString();
            int amount = Integer.parseInt(amountStr);
            gameView.updateTotalAmount(amount);
        });
        
        socket.on("PLAYER_CHANGE_STATE_EVENT", (args) -> {
           String json = args[0].toString();
           GameEventModel event = gson.fromJson(json, GameEventModel.class);
           gameView.userManager.updateStateUser(event);
            if (event.getState() == PlayerStateEnum.UPPER) {
                gameView.upperEvent(event.getTotal());
            }
            if (event.getUserId().equals(CurrentSessionUtils.USER_ID)) {
                CurrentSessionUtils.IS_YOUR_TURN = false;
                gameView.yourTurn();
            }
            repaint();
        });
        
        socket.on("YOUR_TURN_EVENT", (args)  -> {
            CurrentSessionUtils.IS_YOUR_TURN = true;
            gameView.yourTurn();
        });

        socket.on("CARD_OTHER_USER_EVEN", (args) -> {
            String json = args[0].toString();
            List<CardResult> results = gson.fromJson(json, new TypeToken<List<CardResult>>() {}.getType());
            gameView.cardManager.updateCardOtherUser(results);
            gameView.userManager.updateResult(results);
        });

        socket.on("END_GAME_EVENT", (args) -> {
            gameView.endGameEvent();
        });

    }
 
    private void updateCurrentUser() {
        List<UserModel> users = gameView.userManager.getUserInfo();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        if (!users.isEmpty()) {
            Object[] row = new Object[3];
            for(int i = 0; i < users.size(); i ++) {
                row[0] = i + 1;
                row[1] = users.get(i).getUserName();
                row[2] = users.get(i).getPrice();
                model.addRow(row);
            }
        }
    }
    
    private synchronized void appendToLogger(String msg, Color c) {
        java.awt.EventQueue.invokeLater(() -> {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
            aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
            aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
            int len = jTextChat.getDocument().getLength();
            jTextChat.setCaretPosition(len);
            jTextChat.setCharacterAttributes(aset, false);
            jTextChat.replaceSelection(msg);
            jTextChat.setEditable(false);
        });
    }

    private void sendChatMsg(String msg) {
        ChatEventModel model = new ChatEventModel();
        model.setUserId(CurrentSessionUtils.USER_ID);
        model.setUserName(CurrentSessionUtils.USER_NAME);
        model.setMessage(msg);
        socket.emit("SEND_CHAT_EVENT", gson.toJson(model));
        jTextChat.setEditable(true);
        String message = "Bạn: " + msg + "\n";
        appendToLogger(message, Color.BLACK);
        txtChatMessage.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        txtChatMessage = new javax.swing.JTextField();
        sendChatBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextChat = new javax.swing.JTextPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        menuPanel.setMaximumSize(new java.awt.Dimension(300, 32767));
        menuPanel.setPreferredSize(new java.awt.Dimension(300, 470));

        txtChatMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtChatMessageKeyPressed(evt);
            }
        });

        sendChatBtn.setText("SEND");
        sendChatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendChatBtnActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên", "Lãi/Lỗ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(30);
        jTable1.setRowSelectionAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel1.setText("Bảng kết quả");

        jScrollPane3.setViewportView(jTextChat);

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuPanelLayout.createSequentialGroup()
                                .addComponent(txtChatMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendChatBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChatMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendChatBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtChatMessage.getAccessibleContext().setAccessibleName("");

        jPanel1.add(menuPanel, java.awt.BorderLayout.LINE_START);

        mainPanel.setBackground(new java.awt.Color(204, 255, 204));
        mainPanel.setMinimumSize(new java.awt.Dimension(500, 300));
        mainPanel.setPreferredSize(new java.awt.Dimension(500, 524));
        mainPanel.setLayout(new java.awt.BorderLayout());
        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1386, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sendChatBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendChatBtnActionPerformed
        String msg = txtChatMessage.getText();
        if (!"".equals(msg)) {
            sendChatMsg(msg);
        }
    }//GEN-LAST:event_sendChatBtnActionPerformed

    private void txtChatMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChatMessageKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String msg = txtChatMessage.getText();
            if (!"".equals(msg)) {
                sendChatMsg(msg);
            }
        }
    }//GEN-LAST:event_txtChatMessageKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextChat;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JButton sendChatBtn;
    private javax.swing.JTextField txtChatMessage;
    // End of variables declaration//GEN-END:variables
}
