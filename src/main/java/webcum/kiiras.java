/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import com.github.sarxos.webcam.WebcamResolution;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import webcum.agent.StreamClient;
import webcum.agent.StreamClientAgent;
import webcum.agent.StreamServerAgent;
import webcum.agent.ui.VideoPanel;
import webcum.handler.StreamFrameListener;

/**
 *
 * @author branc
 */
public class kiiras extends javax.swing.JFrame {

    /**
     * Creates new form kiiras
     */
    camera cam = new camera();
    Boolean int_cam_update = true, send = false;
    ArrayList<send> client = new ArrayList<>();

    public kiiras() {
        initComponents();
        // server_start();
        setup_receiv();
    }
    Server server;

    ArrayList<videopanelhandler> vph = new ArrayList<>();
    ArrayList<JPanel> panelcam = new ArrayList<>();

    void setup_receiv() {
        panelcam.add(panelcam0);
        panelcam.add(panelcam1);
        panelcam.add(panelcam2);
        panelcam.add(panelcam3);
        for (int i = 0; i < 4; i++) {
            vph.add(new videopanelhandler());
        }

        //gridbaglayout beállítások
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1.0;
        //automata méretezés madafaka

        //videoPannel.setPreferredSize(dimension);
        //Dimension dm = new Dimension(600, 600);
        for (int i = 0; i < vph.size(); i++) {
            GridBagLayout layout = (GridBagLayout) panelcam.get(i).getLayout();
            panelcam.get(i).add(vph.get(i).get_vp());
            layout.addLayoutComponent(vph.get(i).get_vp(), gbc);
        }

        this.pack();

    }

    void server_start() {
        try {
            server.sc.close();
            server.close();
        } catch (Exception e) {
        }
        server = new Server(6666);
    }

    void update() {
        while (int_cam_update) {
            varas(30);
//            cam0.setIcon(resize(cam0, cam.getcam_icon()));
            for (int i = 0; i < server.befele.size(); i++) {
                try {
                    if ((i + 1) == 1) {
                        //                      cam1.setIcon(resize(cam1, server.befele.get(i).cam));
                    } else if ((i + 1) == 2) {
                        //                    cam2.setIcon(resize(cam2, server.befele.get(i).cam));
                    } else if ((i + 1) == 3) {
                        //                  cam3.setIcon(resize(cam3, server.befele.get(i).cam));
                    }
                } catch (Exception e) {
                    //   e.printStackTrace();
                }
            }
        }
    }

    ImageIcon resize(JButton a, ImageIcon b
    ) {
        return new ImageIcon(b.getImage().getScaledInstance(a.getWidth() - 50, a.getHeight() - 50, 2));
    }

    void video_send() {
        while (send) {
            //BufferedImage im = cam.getcam();
            for (int i = 0; i < client.size(); i++) {
                if (!client.get(i).hiba) {
                    client.get(i).kuld(cam.getcam());
                }
            }
        }
    }

    void varas(int ido
    ) {
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
//            cam.start();

            /* Platform.runLater(() -> {
                WebView webView = new WebView();
                jfxPanel.setScene(new Scene(webView));
                //webView.getEngine().load(vlc.get(0).formatHttpStream("127.0.0.1", 5555));
                webView.getEngine().load("http://youtube.com");
            });*/
            //vlc.get(0).play("127.0.0.1", 5555);
//update();
            return null;
        }

        @Override
        protected void done() {
            //cam.stop();
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
        jButton4 = new javax.swing.JButton();
        panelcam0 = new javax.swing.JPanel();
        panelcam2 = new javax.swing.JPanel();
        panelcam1 = new javax.swing.JPanel();
        panelcam3 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Camera on");
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

        jButton3.setText("connect");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton3, gridBagConstraints);

        jButton4.setText("StartServer");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButton4, gridBagConstraints);

        panelcam0.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam0, gridBagConstraints);

        panelcam2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam2, gridBagConstraints);

        panelcam1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam1, gridBagConstraints);

        panelcam3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam3, gridBagConstraints);

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
StreamServerAgent serverAgent;
    internal_cam int_cam = new internal_cam();
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        start_local_cam_server();            //VGA

    }//GEN-LAST:event_jButton1ActionPerformed
    void start_local_cam_server() {
        cam.webcam.setAutoOpenMode(true);
        Dimension dimension = new Dimension(320, 240);
        cam.webcam.setViewSize(dimension);
        cam.webcam.setCustomViewSizes(new Dimension[]{WebcamResolution.VGA.getSize(), WebcamResolution.HD720.getSize()});//új felbontás regisztrálása
        if (cam.webcam.getName().contains("HD") || cam.webcam.getName().contains("EasyCamera")) {
            //Cm.webcam.setViewSize(WebcamResolution.HD720.getSize());//be állítása HD

            cam.webcam.setViewSize(WebcamResolution.VGA.getSize());//be állítása VGA
            serverAgent = new StreamServerAgent(cam.webcam, WebcamResolution.VGA.getSize());
        } else if (cam.webcam.getName().contains("VGA") || cam.webcam.getName().contains("")) {
            cam.webcam.setViewSize(WebcamResolution.VGA.getSize());//be állítása VGA
            serverAgent = new StreamServerAgent(cam.webcam, WebcamResolution.VGA.getSize());
        }
        serverAgent.start(new InetSocketAddress("0.0.0.0", port_szam()));
        cam.stream(serverAgent);
        vph.get(0).connect("localhost", port_szam());

    }

    int port_szam() {
        int vissza = 6666;
        try {
            Scanner in = new Scanner(new FileReader("port.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("(port)txt tartalom: " + kecske);
                    try {
                        vissza = Integer.parseInt(kecske);
                    } catch (Exception e) {
                        System.out.println("nem szám a port.txt");
                    }
                    a++;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
        return vissza;
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        /*        int_cam.done();
        server.close();
        server = new Server(6666);*/
    }//GEN-LAST:event_jButton2ActionPerformed
    void connect_to_ips() {
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("txt tartalom: " + kecske);
                    try {
                        if (a == 1) {
                            vph.get(1).connect(kecske.split(":")[0], Integer.parseInt(kecske.split(":")[1]));
                        } else if (a == 2) {
                            vph.get(2).connect(kecske.split(":")[0], Integer.parseInt(kecske.split(":")[1]));
                        } else if (a == 3) {
                            vph.get(3).connect(kecske.split(":")[0], Integer.parseInt(kecske.split(":")[1]));
                        }
                    } catch (Exception e) {
                    }
                    a++;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        connect_to_ips();
    }//GEN-LAST:event_jButton3ActionPerformed

    void server_setup() {
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    send k = new send();
                    k.setup(kecske.split(":")[0], Integer.parseInt(kecske.split(":")[1]));
                    client.add(k);
                }
            }
            System.out.println(client.size());
            //client[2].setup(3, "127.0.0.1", 6666);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        /*        //setup
        if (server.sisClosed()) {
        server = new Server(6666);
        }*/
        // vlc_stream_player vlcsp = new vlc_stream_player();
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel panelcam0;
    private javax.swing.JPanel panelcam1;
    private javax.swing.JPanel panelcam2;
    private javax.swing.JPanel panelcam3;
    // End of variables declaration//GEN-END:variables
}

class PlayerPanel extends JPanel {

    private File vlcInstallPath = new File("C:/Program Files/VideoLAN/VLC");
    private EmbeddedMediaPlayer player;

    public PlayerPanel() {
        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());

        EmbeddedMediaPlayerComponent videoCanvas = new EmbeddedMediaPlayerComponent();
        this.setLayout(new BorderLayout());
        this.add(videoCanvas, BorderLayout.CENTER);
        this.player = videoCanvas.getMediaPlayer();
    }

    public void play(String media, int port) {
        player.prepareMedia(formatHttpStream(media, port));
        player.parseMedia();
        player.play();
    }

    public String formatHttpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#duplicate{dst=std{access=http,mux=ts,");
        sb.append("dst=");
        sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append("}}");
        return sb.toString();
    }
}
