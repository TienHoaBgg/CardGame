/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gem.cardgame.ui;

/**
 *
 * @author nth
 */
public class ButtonsPanel extends javax.swing.JPanel {

    /**
     * Creates new form ButtonsPanel
     */
    
    private IButtonCallBack callBack;
    
    public ButtonsPanel() {
        initComponents();
    }

    public void updateView() {
        this.getLayout().layoutContainer(this);
    }
    
    public void setCallBack(IButtonCallBack callBack) {
        this.callBack = callBack;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTheo = new javax.swing.JButton();
        btnTo = new javax.swing.JButton();
        btnNan = new javax.swing.JButton();
        btnBo = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.GridLayout(2, 2, 2, 2));

        btnTheo.setBackground(new java.awt.Color(0, 135, 248));
        btnTheo.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnTheo.setForeground(new java.awt.Color(255, 255, 255));
        btnTheo.setText("Theo");
        btnTheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTheoActionPerformed(evt);
            }
        });
        add(btnTheo);

        btnTo.setBackground(new java.awt.Color(229, 150, 52));
        btnTo.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnTo.setForeground(new java.awt.Color(255, 255, 255));
        btnTo.setText("Tố");
        btnTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToActionPerformed(evt);
            }
        });
        add(btnTo);

        btnNan.setBackground(new java.awt.Color(223, 40, 42));
        btnNan.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnNan.setForeground(new java.awt.Color(255, 255, 255));
        btnNan.setText("Nặn Bài");
        btnNan.setToolTipText("Click để xem bài, nhấn giữ để nặn bài");
        btnNan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnNanMousePressed(evt);
            }
        });
        btnNan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNanActionPerformed(evt);
            }
        });
        add(btnNan);

        btnBo.setBackground(new java.awt.Color(102, 102, 102));
        btnBo.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnBo.setForeground(new java.awt.Color(255, 255, 255));
        btnBo.setText("Bỏ");
        btnBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoActionPerformed(evt);
            }
        });
        add(btnBo);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheoActionPerformed
        callBack.theoClickAction();
    }//GEN-LAST:event_btnTheoActionPerformed

    private void btnToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToActionPerformed
        callBack.toClickAction();
    }//GEN-LAST:event_btnToActionPerformed

    private void btnNanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNanMousePressed
        callBack.nanPressAction();
    }//GEN-LAST:event_btnNanMousePressed

    private void btnNanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNanActionPerformed
        callBack.nanClickAction();
    }//GEN-LAST:event_btnNanActionPerformed

    private void btnBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoActionPerformed
        callBack.boClickAction();
    }//GEN-LAST:event_btnBoActionPerformed
    
    interface IButtonCallBack {
        void toClickAction();
        void theoClickAction();
        void nanClickAction();
        void nanPressAction();
        void boClickAction();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBo;
    private javax.swing.JButton btnNan;
    private javax.swing.JButton btnTheo;
    private javax.swing.JButton btnTo;
    // End of variables declaration//GEN-END:variables
}
