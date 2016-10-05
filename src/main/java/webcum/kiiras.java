/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 *
 * @author branc
 */
public class kiiras extends javax.swing.JFrame {

    /**
     * Creates new form kiiras
     */
    main cam;
    Boolean int_cam_update = true, send = false;

    public kiiras() {
        initComponents();
        client[0] = new send();
        client[1] = new send();
        client[2] = new send();
        server = new Server(6666);
    }
    send[] client = new send[3];
    Server server;

    void update() {
        while (int_cam_update) {
            cam0.setIcon(resize(cam0, cam.getcam_icon()));
            //cam0.setIcon(cam.getcam_icon());
            for (int i = 0; i < server.befele.size(); i++) {
                if (server.befele.get(i).wall == 1) {
                    cam1.setIcon(resize(cam1, server.befele.get(i).cam));
                } else if (server.befele.get(i).wall == 2) {
                    cam2.setIcon(resize(cam2, server.befele.get(i).cam));
                } else if (server.befele.get(i).wall == 2) {
                    cam3.setIcon(resize(cam3, server.befele.get(i).cam));
                }
            }
            varas(16);
        }
    }

    ImageIcon resize(JButton a, ImageIcon b) {
        return new ImageIcon(b.getImage().getScaledInstance(a.getWidth() - 50, a.getHeight() - 50, 2));
    }

    void video_send() {
        while (send) {
            BufferedImage im = cam.getcam();
            for (int i = 0; i < client.length; i++) {
                client[i].kuld(im);
            }
            varas(16);
        }
    }


    void varas(int ido) {
        try {
            Thread.sleep(ido);
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    class internal_cam extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            int_cam_update = true;
            update();
            return null;
        }

        @Override
        protected void done() {
            cam.stop();
            int_cam_update = false;
        }
    }

    class external_cam_send extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            video_send();
            return null;
        }

        @Override
        protected void done() {
        }

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        cam0 = new javax.swing.JButton();
        cam1 = new javax.swing.JButton();
        cam2 = new javax.swing.JButton();
        cam3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton1, gridBagConstraints);

        jButton2.setText("stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton2, gridBagConstraints);

        jButton3.setText("stream");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(cam0, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(cam1, gridBagConstraints);

        cam2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cam2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(cam2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(cam3, gridBagConstraints);

        jButton4.setText("Setup");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton4, gridBagConstraints);

        jMenu1.setText("Camera setup");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Wall setup");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    internal_cam int_cam = new internal_cam();
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cam = new main();

        int_cam.execute();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int_cam.done();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        send = !send;
        server_setup();
        /*client[0].setup(1, "127.0.0.1", 6666);
        client[1].setup(2, "127.0.0.1", 6666);
        client[2].setup(3, "127.0.0.1", 6666);*/
        System.out.println(send);
        new external_cam_send().execute();
    }//GEN-LAST:event_jButton3ActionPerformed

    void server_setup() {
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            String kecske = in.nextLine();
            client[0].setup(Integer.parseInt(kecske.split(":")[0]), kecske.split(":")[1], 6666);
            kecske = in.nextLine();
            client[1].setup(Integer.parseInt(kecske.split(":")[0]), kecske.split(":")[1], 6666);
            kecske = in.nextLine();
            client[2].setup(Integer.parseInt(kecske.split(":")[0]), kecske.split(":")[1], 6666);
            //client[2].setup(3, "127.0.0.1", 6666);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    private void cam2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cam2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cam2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //setup

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        new webcam_settings(cam).setVisible(true);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        new server_settings().setVisible(true);
    }//GEN-LAST:event_jMenu2MouseClicked
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
            java.util.logging.Logger.getLogger(kiiras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kiiras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kiiras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kiiras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kiiras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cam0;
    private javax.swing.JButton cam1;
    private javax.swing.JButton cam2;
    private javax.swing.JButton cam3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
