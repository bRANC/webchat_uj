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
import java.awt.Color;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JRootPane;
import javax.swing.UIManager;
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
    user_watcher uw = new user_watcher();

    public kiiras() {
        fullscreen();

        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("Dsun.java2d.d3d", "True");
        System.setProperty("Dsun.java2d.accthreshold", "0");
        UIManager.put("Button.focus", Color.red);
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
        uw.execute();
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
                disconnect_matrix();
                System.exit(0);
            }
        }
        );
        connect_matrix(true);
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

    public void getadapter() {
        String chain = "";
        local_ips.clear();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                chain += displayInterfaceInformation(netint);
            }
        } catch (Exception e) {
        }
        chain += outterip();
        //return chain;
    }
    ArrayList<String> local_ips = new ArrayList<>();

    String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        int a = 0;
        String localip = "Adapter name: " + netint.getDisplayName() + "\n";
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        //netint.get
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if (inetAddress.toString().contains(".") && !inetAddress.isLoopbackAddress()) {
                a++;
                localip += "Internal IP: " + inetAddress.toString().replace("/", "") + "\n";
                local_ips.add(inetAddress.toString().replace("/", ""));
                System.out.println(localip);
            }
        }
        if (a == 0) {
            localip = "";
        } else {
            System.out.println("localip numbers: " + a);
        }
        return localip;
    }

    class Chatstartup extends SwingWorker<Void, Void> {

        Chat_and_voice_server_start cas;

        @Override
        protected Void doInBackground() throws Exception {
            cc.elso = 0;
            String localip = "";
            if (tryhard == 0) {
                cas = new Chat_and_voice_server_start();
                cas.execute();
                varas(200);
                getadapter();
                for (int i = 0; i < local_ips.size(); i++) {
                    System.out.println(local_ips.size() + "   " + !cc.isConnected());
                    if (!cc.isConnected()) {
                        try {
                            cc.connect(local_ips.get(i), ip.get(0).port_jvc + "");
                            if (cc.isConnected()) {
                                localip = local_ips.get(i);
                                varas(100);
                                cc.set_nickname(ip.get(0).name);
                                varas(100);
                                cc.set_status("innerip;" + localip);
                            }
                        } catch (Exception e) {
                        }
                    }
                }

            } else {
                if (ip.size() > 0) {
                    for (int j = 0; j < tryhard; j++) {
                        for (int i = 0; i < ip.size(); i++) {
                            if (!cc.isConnected()) {
                                if (!ip.get(i).ip.isEmpty()) {
                                    cc.connect(ip.get(i).ip, ip.get(i).port_jvc + "");
                                }
                            }
                        }
                    }
                }
                //cc.connect("localhost", port_szam(1) + "");
                if (!cc.isConnected()) {
                    System.out.println("not an any server starting one");
                    cas = new Chat_and_voice_server_start();
                    cas.execute();
                    varas(200);
                    for (int i = 0; i < local_ips.size(); i++) {
                        System.out.println(local_ips.size() + "   " + !cc.isConnected());
                        if (!cc.isConnected()) {
                            try {
                                if (!ip.get(i).ip.isEmpty()) {
                                    cc.connect(local_ips.get(i), ip.get(0).port_jvc + "");
                                    if (cc.isConnected()) {
                                        localip = local_ips.get(i);
                                        //varas(100);
                                        cc.set_nickname(ip.get(0).name);
                                        //varas(100);
                                        cc.set_status("innerip;" + localip);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                } else {

                }
            }
            while (!cc.nickEntered) {
                if (localip.isEmpty()) {
                    cc.set_nickname(ip.get(0).name);
                }
            }
            //varas(100);
            cc.set_status("ip;" + outterip());
            //varas(100);
            cc.set_status("address;" + lch.get(0).location);
            //varas(100);
            cc.set_status("camera;off");
            //varas(100);
            cc.set_status("sc_name;" + sc_name);
            //varas(100);
            //System.out.println("shouldip: " + Inet4Address.getLocalHost().getHostAddress());
            if (localip.isEmpty()) {
                cc.set_status("innerip;" + cc.getconn_ip());
            }

            //varas(100);
            cc.send_img(new ImageIcon(logo_name).getImage(), logo_name);

            cc.elso++;
            cc.elso++;
            return null;
        }

    }

    class user_watcher extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                if (cc.turncamera()) {
                    start_local_cam_server();
                }
                for (int i = 0; i < cc.SS.size(); i++) {
                    if (cc.SS.get(i).get_should_con()) {
                        //System.out.println("cc.SS.get(i).get_should_con(): " + cc.SS.get(i).get_should_con());
                        for (int j = 0; j < ip.size(); j++) {
                            if (!ip.get(j).ip.isEmpty()) {
                                if (ip.get(j).ip.equals(cc.SS.get(i).ip) || ip.get(j).ip.equals(cc.SS.get(i).innerip)) {
                                    lch.get(j).name = cc.SS.get(i).name;
                                    System.out.println("find inner: " + cc.SS.get(i).innerip + " or ip: " + cc.SS.get(i).ip);
                                    lch.get(j).set_sc_name(cc.SS.get(j).sc_name);
                                    lch.get(j).set_address(cc.SS.get(j).addres);
                                    if (cc.SS.get(i).conn_cam()) {
                                        System.out.println("cummect: " + cc.SS.get(i).innerip);
                                        vph.get(j).connect(ip.get(j).ip, ip.get(j).port_jv);
                                        varas(20);
                                        set_cam(j);
                                    } else {
                                        set_weat(j);
                                    }
                                }
                            }
                        }
                    }
                    if (cc.get_should_write()) {
                        sqlite_write_cc();
                    }
                }
                if (cc.get_new_pic()) {
                    for (int j = 0; j < cc.SS.size(); j++) {
                        for (int k = 0; k < lch.size(); k++) {
                            if (cc.SS.get(j).name.equals(lch.get(k).name)) {
                                lch.get(k).set_sc_icon(cc.SS.get(j).pic_hely);
                            }
                        }
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
                if (!ip.get(i).ip.isEmpty()) {
                    if (ip.get(i).ip.equals(cc.SS.get(j).ip)) {
                        try {
                            System.out.println("update nation set "
                                    + " addres ='" + cc.SS.get(j).addres + "',"
                                    + " name ='" + cc.SS.get(j).name + "',"
                                    + " sc_logo ='" + cc.SS.get(j).pic_hely + "',"
                                    + " sc_name ='" + cc.SS.get(j).name + "'"
                                    + " where ip = " + ip.get(i).ip + ";");
                            inn.fel("update nation set "
                                    + " addres ='" + cc.SS.get(j).addres + "',"
                                    + " name ='" + cc.SS.get(j).name + "',"
                                    + " sc_logo ='" + cc.SS.get(j).pic_hely + "',"
                                    + " sc_name ='" + cc.SS.get(j).name + "'"
                                    + " where ip = " + ip.get(i).ip + ";");
                        } catch (Exception e) {
                            System.out.println("sqlite_write_cc: " + e.toString());
                        }
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
            cc.set_status("camera;on");
            set_cam(0);
        } else {
            //serverAgent.vait();
//            for (int i = 0; i < vph.size(); i++) {
//                dc(i);
//            }
            jButton1.setText("Camera on");
            cam_bool = true;
            cc.set_status("camera;off");
            set_weat(0);
//            for (int i = 0; i < vph.size(); i++) {
//                set_weat(i);
//            }
        }
        cc.handsfree(true);
        cc.talk();
        jButton2.setText("talk " + cc.is_talking());

    }

    class connect_agent extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
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
            return null;
        }

        @Override
        protected void done() {
        }

    }

    boolean is_full_sc = true;

    public void fullscreen() {

//        ez tűnteti el az ablakot
        JFrame frame = this;
//        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//        eddig
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        is_full_sc = true;
        //   this.setUndecorated(true);
    }

    public void scmall_Screen() {
        this.setExtendedState(JFrame.ICONIFIED);
        is_full_sc = false;
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

    String logo_name = "", sc_name = "";

    void sqlscan() {
        ip = new ArrayList();
        try {
            ResultSet rs = inn.le("select * from nation;");
            ResultSet rs1 = inn.le("select * from api;");
            rs1.next();
            String weather = rs1.getString("weather_api_key"), geo = rs1.getString("geo_api_key");
            String helyi = "";
            String sc_name = "";
            String sc_logo = "";
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

                        try {
                            logo_name = rs.getString("sc_logo");
                        } catch (Exception e) {
                        }
                        try {
                            this.sc_name = rs.getString("sc_name");
                        } catch (Exception e) {
                        }

                        lch.add(new pallet_form(rs.getString("addres"), weather, geo, logo_name, this.sc_name));
                        //lch.get(0).set_address(rs.getString("addres"));

                        break;
                    case 2:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            try {
                                sc_logo = rs.getString("sc_logo");
                            } catch (Exception e) {
                            }
                            try {
                                sc_name = rs.getString("sc_name");
                            } catch (Exception e) {
                            }
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo, sc_logo, sc_name));
                        } catch (Exception e) {
                        }
//                        jcam1.setText(rs.getString("ip"));
//                        jcport1.setText(rs.getString("jcport"));
//                        jvcport1.setText(rs.getString("jvcport"));
                        break;
                    case 3:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            try {
                                sc_logo = rs.getString("sc_logo");
                            } catch (Exception e) {
                            }
                            try {
                                sc_name = rs.getString("sc_name");
                            } catch (Exception e) {
                            }
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo, sc_logo, sc_name));
                        } catch (Exception e) {
                        }
//                        jcam2.setText(rs.getString("ip"));
//                        jcport2.setText(rs.getString("jcport"));
//                        jvcport2.setText(rs.getString("jvcport"));
                        break;
                    case 4:
                        try {
                            ip.add(new ip(rs.getString("ip"), Integer.parseInt(rs.getString("jcport")), Integer.parseInt(rs.getString("jvcport"))));
                            try {
                                sc_logo = rs.getString("sc_logo");
                            } catch (Exception e) {
                            }
                            try {
                                sc_name = rs.getString("sc_name");
                            } catch (Exception e) {
                            }
                            lch.add(new pallet_form(rs.getString("addres"), weather, geo, sc_logo, sc_name));
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
            //System.out.println(vph.size() + "  lch: " + lch.size());
            if (lch.size() != vph.size()) {
                for (int i = lch.size(); i < vph.size(); i++) {
                    lch.add(new pallet_form(helyi, weather, geo, "", ""));
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
//        connect_to_ips();
//        try {
//            con.cancel(true);
//        } catch (Exception e) {
//        }
//        con = new connect_agent();
//        cc.send_img(new ImageIcon(logo_name).getImage(), logo_name);
//        con.execute();
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

    refresh ref = new refresh();
    cmd cm = new cmd();
    int button = 1;

    public void connect_matrix(Boolean refresh) {
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Register/" + ip.get(0).name);
        if (be.contains("Registered") || be.contains("Refreshed")) {
            //System.out.println("done conn");
            if (refresh) {
                ref.execute();
                varas(50);
                cm.execute();
            }
        }
    }

    public void refresh_matrix() {
        connect_matrix(false);
    }

    public void disconnect_matrix() {
        cm.cancel(true);
        ref.cancel(true);
        varas(100);
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Unregister/" + ip.get(0).name);
        if (be.contains("Unregistered") || be.contains("Not registered")) {
            System.out.println("done dissconn");
        }
    }

    public void get_command() {
        //http://webledmatrix.azurewebsites.net/
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Commands/" + ip.get(0).name);
        String comand[] = be.split("string");
        String appendus = be.replace(":", "").replace("[", "").replace("]", "").replace("\"", "").trim();
        if (!appendus.isEmpty()) {
            System.out.println(button + " : " + be);
            if (!is_full_sc) {
                fullscreen();
            }
            if (appendus.contains("Left")) {
                button--;
                if (button == 0) {
                    button = 4;
                }
            } else if (appendus.contains("Right")) {
                button++;
                if (button == 5) {
                    button = 1;
                }
            }
            if (appendus.contains("Exit")) {
                if (ss.isVisible()) {
                    ss.setVisible(false);
                } else if (is_full_sc) {
                    scmall_Screen();
                } else {
                    fullscreen();
                } //this.setVisible(false);
            }
            if (ss.isVisible()) {
                send_text(appendus);
            }
            if (appendus.contains("OK")) {
                switch (button) {
                    case 1:
                        start_local_cam_server();            //VGA
                        break;
                    case 2:
                        //semmi
                        break;
                    case 3:
                        screensaver();
                        break;
                    case 4:
                        cc.handsfree(true);
                        cc.talk();
                        jButton2.setText("talk " + cc.is_talking());
                        break;
                    default:
                        break;
                }
            }

            switch (button) {
                case 1:
                    jButton1.requestFocusInWindow();         //VGA
                    break;
                case 2:
                    //semmi
                    jButton3.requestFocusInWindow();
                    break;
                case 3:
                    jButton4.requestFocusInWindow();
                    break;
                case 4:
                    jButton2.requestFocusInWindow();
                    break;
                default:
                    break;
            }

//            area.append(appendus + "\n");
        }
    }

    public String http(String page) {
        String vissza = "";
        try {
            URL whatismyip = new URL(page);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String hozza = "";
            hozza += in.readLine();
            while (!hozza.equals("null")) {
                vissza += hozza + "\n";
                hozza = "";
                hozza += in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vissza;
    }

    class refresh extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                refresh_matrix();
                varas(3000);
            }
            return null;
        }

    }

    class cmd extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                get_command();
                varas(100);
            }
            return null;
        }

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
