/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet;

import felulet.webcam.own.videopanelhandler;
import felulet.webcam.own.wcamera;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import felulet.screensaver.ScreenSaver;
import base.client.ChatClient;
import base.server.ChatServer;
import felulet.panel.pallet_form;
import felulet.panel.weather_panel;
import webcam.agent.StreamServerAgent;
import sqlite.sqlite;

/**
 *
 * @author branc
 */
public class kiiras extends javax.swing.JFrame {

    /**
     * Creates new form kiiras
     */
    wcamera cam = new wcamera();
    Boolean int_cam_update = true, send = false;
    Chatstartup Ct = new Chatstartup();

    public kiiras() {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("Dsun.java2d.d3d", "True");
        System.setProperty("Dsun.java2d.accthreshold", "0");
        initComponents();
        // server_start();  
        //VolatileImage <-- video memóriában is leképződő image   -Dsun.java2d.accthreshold=0

//        local_things();
        //varas(1000);
        for (int i = 0; i < 4; i++) {
            vph.add(new videopanelhandler());
        }
        sqlscan();
        setup_receiv();
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
        cam.stop_camera_listener();
    }

    ArrayList<videopanelhandler> vph = new ArrayList<>();
    ArrayList<pallet_form> lch = new ArrayList<>();
    ArrayList<JPanel> panelcam = new ArrayList<>();

    void setup_receiv() {
        panelcam.add(panelcam0);
        panelcam.add(panelcam1);
        panelcam.add(panelcam2);
        panelcam.add(panelcam3);

//        lch.add(new pallet_form("Zalaegerszeg", "363335226d56b6cdec4e85c6c7323e0e", "AIzaSyCK4A-bn35g1EX2Jgm6IpHFioh3Ctb99QI"));
//        lch.add(new pallet_form("Zalaegerszeg", "363335226d56b6cdec4e85c6c7323e0e", "AIzaSyCK4A-bn35g1EX2Jgm6IpHFioh3Ctb99QI"));
//        lch.add(new pallet_form("Zalaegerszeg", "363335226d56b6cdec4e85c6c7323e0e", "AIzaSyCK4A-bn35g1EX2Jgm6IpHFioh3Ctb99QI"));
//        lch.add(new pallet_form("Zalaegerszeg", "363335226d56b6cdec4e85c6c7323e0e", "AIzaSyCK4A-bn35g1EX2Jgm6IpHFioh3Ctb99QI"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 100.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        //automata méretezés madafaka
        for (int i = 0; i < panelcam.size(); i++) {
            GridBagLayout layout = (GridBagLayout) panelcam.get(i).getLayout();
            panelcam.get(i).add(vph.get(i).get_vp());
            layout.addLayoutComponent(vph.get(i).get_vp(), gbc);

        }
        //gbc.weightx = gbc.weighty = 1.0;
        //gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        for (int i = 0; i < lch.size(); i++) {
            GridBagLayout layout = (GridBagLayout) panelcam.get(i).getLayout();
            //layout.
            panelcam.get(i).add(lch.get(i));
            layout.addLayoutComponent(lch.get(i), gbc);
        }
        this.pack();
        fullscreen();
        //videoPannel.setPreferredSize(dimension);
        //Dimension dm = new Dimension(600, 600);
        while (lch.size() < vph.size()) {
            varas(10);
            System.out.println(lch.size() + "  vph: " + vph.size());

        }
        for (int i = 0; i < vph.size(); i++) {
            set_weat(i);
        }
    }

    void varas(int ido) {
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
            cc.set_status("ip|" + outterip());
            varas(100);
            cc.set_status("address|" + lch.get(0).location);
            varas(100);
            cc.set_status("camera|off");
            return null;
        }

        @Override
        protected void done() {
        }

    }

    class user_watcher extends SwingWorker<Void, Void> {

        Chat_and_voice_server_start cas;

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                for (int i = 0; i < cc.SS.size(); i++) {
                    if (cc.SS.get(i).get_should_con()) {
                        for (int j = 0; j < ip.size(); j++) {
                            if (ip.get(j).ip.equals(cc.SS.get(i).ip)) {
                                if (cc.SS.get(i).conn_cam()) {
                                    vph.get(j).connect(ip.get(j).ip, ip.get(j).port_jv);
                                    varas(20);
                                    set_cam(j);
                                } else {
                                    set_weat(j);
                                }
                            }
                        }
                    }
                    if (cc.get_should_write()) {
                        sqlite_write_cc();
                    }
                }
                varas(200);
            }
            return null;
        }
    }

    public void sqlite_write_cc() {
        for (int i = 0; i < ip.size(); i++) {
            for (int j = 0; j < cc.SS.size(); j++) {
                if (ip.get(i).ip.equals(cc.SS.get(j).ip)) {
                    try {
                        inn.fel("update nation set "
                                + " addres ='" + cc.SS.get(j).addres + "',"
                                + " name ='" + cc.SS.get(j).name + "'"
                                + " where ip = " + ip.get(i).ip + ";");
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    String outterip() {
        String publicip = "";
        try {//http://icanhazip.com/
            URL whatismyip1 = new URL("http://icanhazip.com/");
            BufferedReader in1 = new BufferedReader(new InputStreamReader(
                    whatismyip1.openStream()));
            publicip = in1.readLine(); //you get the IP as a String
        } catch (Exception e) {
        }
        return publicip;
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
        gridBagConstraints.gridx = 3;
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton4, gridBagConstraints);

        panelcam0.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam0, gridBagConstraints);

        panelcam2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam2, gridBagConstraints);

        panelcam1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam1, gridBagConstraints);

        panelcam3.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(panelcam3, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
StreamServerAgent serverAgent;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        start_local_cam_server();            //VGA
    }//GEN-LAST:event_jButton1ActionPerformed
    boolean cam_bool = true;

    void start_local_cam_server() {
        if (serverAgent == null) {
            cam.webcam.setAutoOpenMode(true);
            cam.webcam.setCustomViewSizes(new Dimension[]{WebcamResolution.VGA.getSize(), WebcamResolution.QVGA.getSize(), WebcamResolution.HD720.getSize()});//új felbontás regisztrálása
            cam.webcam.setViewSize(WebcamResolution.QVGA.getSize());//be állítása VGA
            serverAgent = new StreamServerAgent(cam.webcam, WebcamResolution.QVGA.getSize());
        }
        if (cam_bool) {
            conn(0);
            if (cam.serverAgent == null) {
                serverAgent.start(new InetSocketAddress("0.0.0.0", ip.get(0).port_jv));
                cam.stream(serverAgent);
                vph.get(0).connect("localhost", ip.get(0).port_jv);
            }
            jButton1.setText("Camera off");
            cam_bool = false;
            cc.set_status("camera|on");
            set_cam(0);
        } else {
            //serverAgent.vait();
            for (int i = 0; i < vph.size(); i++) {
                dc(i);
            }
            jButton1.setText("Camera on");
            cam_bool = true;
            cc.set_status("camera|off");
            for (int i = 0; i < vph.size(); i++) {
                set_weat(i);
            }
        }
        cc.handsfree(true);
        cc.talk();
        jButton2.setText("talk " + cc.is_talking());

    }

    class connect_agent extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                boolean van = false;
                ArrayList<Integer> for_delet = new ArrayList<>();
                if (serverAgent.dc_ips.size() != 0) {
                    System.out.println("serveragentd dc size: " + serverAgent.dc_ips.size());
                }
                for (int i = 0; i < serverAgent.dc_ips.size(); i++) {
                    for (int j = 0; j < ip.size(); j++) {
                        if (ip.get(j).ip.contains(serverAgent.dc_ips.get(i))) {
                            for_delet.add(j);
                            System.out.println("add: " + j);
                        }
                    }
                }
                if (!for_delet.isEmpty()) {
                    System.out.println(" delete ");

                }
                serverAgent.dc_ips.clear();
                varas(300);
            }
        }

        @Override
        protected void done() {
        }

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
//        hfree.setText("handsfree " + cc.handsfree());
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
        ip = new ArrayList();
        try {
            ResultSet rs = inn.le("select * from nation;");
            ResultSet rs1 = inn.le("select * from api;");
            rs1.next();
            String weather = rs1.getString("weather_api_key"), geo = rs1.getString("geo_api_key");
            String helyi = "";
            while (rs.next()) {
                switch (rs.getInt("id")) {
                    case 1:
                        //upnpbool.setSelected(rs.getBoolean("upnp"));
                        //jname.setText(rs.getString("name"));
                        //jcportfiled.setText(rs.getString("jcport"));
                        //jvcportfiled.setText(rs.getString("jvcport"));
                        //jtryhard.setText(rs.getString("tryhard"));
                        ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                        ip.get(ip.size() - 1).name = rs.getString("name");
                        ip.get(ip.size() - 1).upnp = rs.getBoolean("upnp");
                        tryhard = rs.getInt("tryhard");
                        helyi = rs.getString("addres");
                        lch.add(new pallet_form(rs.getString("addres"), weather, geo));
                        break;
                    case 2:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo));
                        } catch (Exception e) {
                        }
//                        jcam1.setText(rs.getString("ip"));
//                        jcport1.setText(rs.getString("jcport"));
//                        jvcport1.setText(rs.getString("jvcport"));
                        break;
                    case 3:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo));
                        } catch (Exception e) {
                        }
//                        jcam2.setText(rs.getString("ip"));
//                        jcport2.setText(rs.getString("jcport"));
//                        jvcport2.setText(rs.getString("jvcport"));
                        break;
                    case 4:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo));
                        } catch (Exception e) {
                        }
//                        jcam3.setText(rs.getString("ip"));
//                        jcport3.setText(rs.getString("jcport"));
//                        jvcport3.setText(rs.getString("jvcport"));
                        break;
                    default:
                        break;
                }
            }
            System.out.println(vph.size() + "  lch: " + lch.size());
            if (lch.size() != vph.size()) {
                for (int i = lch.size(); i < vph.size(); i++) {
                    lch.add(new pallet_form(helyi, weather, geo));
                }
            }
        } catch (Exception e) {
            System.out.print("sqlscan: ");
            e.printStackTrace();
        }

    }

    void scan() {
        ip = new ArrayList();
        try (Scanner in = new Scanner(new FileReader("ip.txt"))) {
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

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    void connect_to_ips() {
        System.out.println("size: " + ip.size());
        for (int i = 1; i < ip.size(); i++) {
            try {
                if (!ip.get(i).ip.trim().isEmpty()) {
                    System.out.println(ip.get(i).ip + " i:" + i);
                    vph.get(i).connect(ip.get(i).ip, ip.get(i).port_jv);

                }
            } catch (Exception e) {
                System.out.println("cti: " + e.toString());
            }
        }
    }

    public void send_text(String text) {
        cc.send_text(text);
    }
    connect_agent con;
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        connect_to_ips();
        try {
            con.cancel(true);
        } catch (Exception e) {
        }
        con = new connect_agent();
        con.execute();
    }//GEN-LAST:event_jButton3ActionPerformed

    ScreenSaver ss = new ScreenSaver(this);
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        screensaver();
    }//GEN-LAST:event_jButton4ActionPerformed

    public void set_cam(int i) {
        GridBagConstraints gbc;
        GridBagLayout layout = (GridBagLayout) panelcam.get(i).getLayout();
        gbc = layout.getConstraints(lch.get(i));
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        layout.setConstraints(lch.get(i), gbc);
        gbc = layout.getConstraints(vph.get(i).get_vp());
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        //panelcam.get(i).add(vph.get(i).get_vp());
        layout.setConstraints(vph.get(i).get_vp(), gbc);
        //layout.get
        this.pack();
        fullscreen();
        vph.get(i).connect();
    }

    public void conn(int i) {
        vph.get(i).connect();
    }

    public void dc(int i) {
        vph.get(i).dc();
        vph.get(i).clear();
    }

    public void set_weat(int i) {
        vph.get(i).dc();
        vph.get(i).clear();
        //
        GridBagConstraints gbc;
        GridBagLayout layout = (GridBagLayout) panelcam.get(i).getLayout();
        gbc = layout.getConstraints(lch.get(i));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        layout.setConstraints(lch.get(i), gbc);
        //panelcam.get(i).remove(vph.get(i).get_vp());
        gbc = layout.getConstraints(vph.get(i).get_vp());
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        layout.setConstraints(vph.get(i).get_vp(), gbc);
        //layout.get
        this.pack();
        fullscreen();
    }

    void screensaver() {
        cc.set_status("scrennsaver");
        ss.setVisible(true);
    }

    public void set_text_ki(JTextArea txtOutput) {
        cc.set_txtout(txtOutput);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel panelcam0;
    private javax.swing.JPanel panelcam1;
    private javax.swing.JPanel panelcam2;
    private javax.swing.JPanel panelcam3;
    // End of variables declaration//GEN-END:variables
}
