/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voice.ScreenSaver;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import voice.client.ChatClient;
import webcum.kiiras;

/**
 *
 * @author branc
 */
public class ScreenSaver extends javax.swing.JFrame {

    /**
     * Creates new form ScrrenSaver
     */
    kiiras ki;
    public boolean becsuk = false;

    public ScreenSaver(kiiras ki) {
        this.ki = ki;
        fullscreen();
        initComponents();
        txtki.setEditable(false);
        txtki.setFont(new java.awt.Font("Dialog", 0, 18));
        ki.set_text_ki(txtki);
        betext.requestFocus();
        new usercheck().execute();
        new hide().execute();
    }

    ChatClient cc;

    void set_status(JLabel stat) {
        if (stat.equals("scrennsaver")) {

        }
        if (stat.equals("connected")) {

        }
    }

    public void fullscreen() {
//        ez tűnteti el az ablakot
        JFrame frame = this;
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//        eddig
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //   this.setUndecorated(true);
    }

    class hide extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                if (becsuk) {
                    becsuk = false;
                    hide();
                }
            }
        }

        @Override
        protected void done() {
        }

    }

    //timer user check
    class usercheck extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                try {
                    if (cc == null) {
                        cc = ki.get_user();
                    } else {
                        for (int i = 0; i <= cc.SS.size(); i++) {
                            switch (i) {
                                case 0:
                                    user1_name.setText(cc.SS.get(i).name);
                                    user1_status.setText(cc.SS.get(i).status);
                                    break;
                                case 1:
                                    user2_name.setText(cc.SS.get(i).name);
                                    user2_status.setText(cc.SS.get(i).status);
                                    break;
                                case 2:
                                    user3_name.setText(cc.SS.get(i).name);
                                    user3_status.setText(cc.SS.get(i).status);
                                    break;
                                case 3:
                                    user4_name.setText(cc.SS.get(i).name);
                                    user4_status.setText(cc.SS.get(i).status);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        @Override
        protected void done() {
            System.out.println("done usercheck");
        }

    }

    //timer user check end
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        txtki = new javax.swing.JTextArea();
        betext = new javax.swing.JTextField();
        sendtext = new javax.swing.JButton();
        user1_name = new javax.swing.JLabel();
        user1_status = new javax.swing.JLabel();
        user2_name = new javax.swing.JLabel();
        user2_status = new javax.swing.JLabel();
        user3_name = new javax.swing.JLabel();
        user3_status = new javax.swing.JLabel();
        user4_name = new javax.swing.JLabel();
        user4_status = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        hide = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        txtki.setColumns(20);
        txtki.setRows(5);
        jScrollPane1.setViewportView(txtki);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 191;
        gridBagConstraints.ipady = 256;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        betext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                betextKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 230;
        getContentPane().add(betext, gridBagConstraints);

        sendtext.setText("send");
        sendtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendtextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 17;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(sendtext, gridBagConstraints);

        user1_name.setText("user1: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user1_name, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user1_status, gridBagConstraints);

        user2_name.setText("user2:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user2_name, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user2_status, gridBagConstraints);

        user3_name.setText("user3:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user3_name, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user3_status, gridBagConstraints);

        user4_name.setText("user4:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user4_name, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        getContentPane().add(user4_status, gridBagConstraints);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane2, gridBagConstraints);

        hide.setText("hide");
        hide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        getContentPane().add(hide, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendtextActionPerformed
        send(betext.getText());
    }//GEN-LAST:event_sendtextActionPerformed

    private void betextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_betextKeyReleased
        if (evt.getKeyCode() == 10) {
            send(betext.getText());
        }
    }//GEN-LAST:event_betextKeyReleased

    private void hideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_hideActionPerformed

    void send(String uzenet) {
        if (!uzenet.trim().isEmpty()) {
            ki.send_text(betext.getText());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField betext;
    private javax.swing.JButton hide;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton sendtext;
    private javax.swing.JTextArea txtki;
    private javax.swing.JLabel user1_name;
    private javax.swing.JLabel user1_status;
    private javax.swing.JLabel user2_name;
    private javax.swing.JLabel user2_status;
    private javax.swing.JLabel user3_name;
    private javax.swing.JLabel user3_status;
    private javax.swing.JLabel user4_name;
    private javax.swing.JLabel user4_status;
    // End of variables declaration//GEN-END:variables
}
