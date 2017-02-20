/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import com.github.sarxos.webcam.WebcamResolution;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import voice.ScreenSaver.ScreenSaver;
import voice.client.ChatClient;
import voice.server.ChatServer;
import webcum.agent.StreamServerAgent;
import webcum.sqlite.sqlite;

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
    Chatstartup Ct = new Chatstartup();

    public kiiras() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("Dsun.java2d.d3d", "True");
        System.setProperty("Dsun.java2d.accthreshold", "0");
        initComponents();
        // server_start();  
        //VolatileImage <-- video memóriában is leképződő image   -Dsun.java2d.accthreshold=0
        setup_receiv();
//        local_things();
        Ct.execute();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cc.close();
                for (int i = 0; i < vph.size(); i++) {
                    vph.get(i).stop();
                }
                try {
                    serverAgent.stop();
                } catch (Exception e) {
                }
                System.exit(0);
            }
        }
        );
        fullscreen();
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

    void varas(int ido
    ) {
        try {
            Thread.sleep(ido);
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    ChatClient cc = new ChatClient();

    public ChatClient get_user() {
        return cc;
    }
    int tryhard = 0;

    class Chatstartup extends SwingWorker<Void, Void> {

        Chat_and_voice_server_start cas;

        @Override
        protected Void doInBackground() throws Exception {
            scan();
            if (tryhard == 0) {
                cas = new Chat_and_voice_server_start();
                cas.execute();
                varas(200);
                cc.connect("localhost", ip.get(0).port_jvc + "");
            } else {
                if (ip.size() > 0) {
                    for (int j = 0; j < tryhard; j++) {
                        for (int i = 0; i < ip.size(); i++) {
                            if (!cc.isConnected()) {
                                cc.connect(ip.get(i).ip, ip.get(i).port_jvc + "");
                            }
                        }
                    }
                }
                //cc.connect("localhost", port_szam(1) + "");
                if (!cc.isConnected()) {
                    cas = new Chat_and_voice_server_start();
                    cas.execute();
                    varas(200);
                    cc.connect("localhost", ip.get(0).port_jvc + "");
                } else {

                }
            }
            cc.set_nickname(ip.get(0).name);
            return null;
        }

        @Override
        protected void done() {
        }

    }
    ChatServer cs;

    class Chat_and_voice_server_start extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            cs = new ChatServer(ip.get(0).port_jvc);
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
        hfree = new javax.swing.JButton();
        spacing = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton1, gridBagConstraints);

        jButton2.setText("talk");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton3, gridBagConstraints);

        jButton4.setText("Screen Saver");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton4, gridBagConstraints);

        panelcam0.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam0, gridBagConstraints);

        panelcam2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam2, gridBagConstraints);

        panelcam1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam1, gridBagConstraints);

        panelcam3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam3, gridBagConstraints);

        hfree.setText("handsfree");
        hfree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hfreeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(hfree, gridBagConstraints);

        spacing.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        getContentPane().add(spacing, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
StreamServerAgent serverAgent;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        start_local_cam_server();            //VGA
    }//GEN-LAST:event_jButton1ActionPerformed

    void start_local_cam_server() {
        cam.webcam.setAutoOpenMode(true);
        Dimension dimension = new Dimension(320, 240);
        cam.webcam.setViewSize(dimension);
        cam.webcam.setCustomViewSizes(new Dimension[]{WebcamResolution.VGA.getSize(), WebcamResolution.QVGA.getSize(), WebcamResolution.HD720.getSize()});//új felbontás regisztrálása
        if (cam.webcam.getName().contains("HD") || cam.webcam.getName().contains("EasyCamera")) {
            //Cm.webcam.setViewSize(WebcamResolution.HD720.getSize());//be állítása HD
            cam.webcam.setViewSize(WebcamResolution.QVGA.getSize());//be állítása VGA
            serverAgent = new StreamServerAgent(cam.webcam, WebcamResolution.QVGA.getSize());
        } else if (cam.webcam.getName().contains("VGA") || cam.webcam.getName().contains("")) {
            cam.webcam.setViewSize(WebcamResolution.QVGA.getSize());//be állítása VGA
            serverAgent = new StreamServerAgent(cam.webcam, WebcamResolution.QVGA.getSize());
        }
        serverAgent.start(new InetSocketAddress("0.0.0.0", ip.get(0).port_jv));
        cam.stream(serverAgent);
        vph.get(0).connect("localhost", ip.get(0).port_jv);
    }

    public void fullscreen() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //    this.setUndecorated(true);
    }

    public void hide() {
        this.setVisible(false);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cc.handsfree(true);
        hfree.setText("handsfree " + cc.handsfree());
        cc.talk();
        jButton2.setText("talk " + cc.is_talking());
    }//GEN-LAST:event_jButton2ActionPerformed
    class ip {

        ip(String ip, int port_v, int port_txt) {
            this.ip = ip;
            this.port_jv = port_v;
            this.port_jvc = port_txt;
        }
        String name = "";
        boolean upnp = false;
        String ip;
        int port_jv;
        int port_jvc;
    }
    ArrayList<ip> ip;

    class dolgok {

        public String nev, weathercamera, forecast;

        dolgok(String nev, String weathercamera, String forecast) {
            this.nev = nev;
            this.weathercamera = weathercamera;
            this.forecast = forecast;
        }
    }
    dolgok dolog;

    sqlite inn = new sqlite("twin.db3");

    void sqlscan() {
        try {
            ResultSet rs = inn.le("selec * from nation;");
            while (rs.next()) {
                switch (rs.getInt("id")) {
                    case 0:
                        //upnpbool.setSelected(rs.getBoolean("upnp"));
                        //jname.setText(rs.getString("name"));
                        //jcportfiled.setText(rs.getString("jcport"));
                        //jvcportfiled.setText(rs.getString("jvcport"));
                        //jtryhard.setText(rs.getString("tryhard"));
                        ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                        ip.get(ip.size()).name = rs.getString("name");
                        ip.get(ip.size()).upnp = rs.getBoolean("upnp");
                        tryhard = rs.getInt("tryhard");
                        break;
                    case 1:
                        ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
//                        jcam1.setText(rs.getString("ip"));
//                        jcport1.setText(rs.getString("jcport"));
//                        jvcport1.setText(rs.getString("jvcport"));
                        break;
                    case 2:
                        ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
//                        jcam2.setText(rs.getString("ip"));
//                        jcport2.setText(rs.getString("jcport"));
//                        jvcport2.setText(rs.getString("jvcport"));
                        break;
                    case 3:
                        ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
//                        jcam3.setText(rs.getString("ip"));
//                        jcport3.setText(rs.getString("jcport"));
//                        jvcport3.setText(rs.getString("jvcport"));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            System.out.print("sqlscan: ");
            e.printStackTrace();
        }

    }

    void scan() {
        ip = new ArrayList();
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("txt tartalom: " + kecske);
                    try {
                        ip.add(new ip(kecske.split(":")[0], Integer.parseInt(kecske.split(":")[1]), Integer.parseInt(kecske.split(":")[2])));
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

    void connect_to_ips() {
        for (int i = 0; i < ip.size(); i++) {
            try {

                System.out.println(ip.get(i).ip + " i:" + i);
                vph.get(i).connect(ip.get(i).ip, ip.get(i).port_jv);

            } catch (Exception e) {
                System.out.println("cti: " + e.toString());
            }
        }
    }

    public void send_text(String text) {
        cc.send_text(text);
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

    ScreenSaver ss = new ScreenSaver(this);
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        screensaver();
    }//GEN-LAST:event_jButton4ActionPerformed

    void screensaver() {
        cc.set_status("scrennsaver");
        ss.setVisible(true);
    }

    public void set_text_ki(JTextArea txtOutput) {
        cc.set_txtout(txtOutput);
    }
    private void hfreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hfreeActionPerformed
        cc.handsfree(!cc.handsfree());
        hfree.setText("handsfree " + cc.handsfree());
        // TODO add your handling code here:
    }//GEN-LAST:event_hfreeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hfree;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel panelcam0;
    private javax.swing.JPanel panelcam1;
    private javax.swing.JPanel panelcam2;
    private javax.swing.JPanel panelcam3;
    private javax.swing.JButton spacing;
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
