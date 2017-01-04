/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.support.igd.PortMappingListener;
import org.fourthline.cling.support.model.PortMapping;
import voice.client.ChatClient;

/**
 *
 * @author branc
 */
public class setup extends javax.swing.JFrame {

    camera Cm;
    ChatClient cc = new ChatClient(true);
    boolean flip = true;

    /**
     * Creates new form setup
     */
    public setup() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            InetAddress IP = InetAddress.getLocalHost();
            jip.setText(getadapter());
            System.out.println(getadapter());
        } catch (Exception e) {
        }
        text_be();
        readdolgok();
    }

    String getadapter() {
        String chain = "";
        local_ips.clear();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                chain += displayInterfaceInformation(netint);
            }
        } catch (Exception e) {
        }
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine(); //you get the IP as a String
            chain += "\nDefault public IP: " + ip;
        } catch (Exception e) {
        }
        return chain;
    }
    ArrayList<String> local_ips = new ArrayList<>();

    String displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        int a = 0;
        String localip = "Adapter name: " + netint.getDisplayName() + "\n";
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            if (inetAddress.toString().contains(".") && !inetAddress.isLoopbackAddress()) {
                a++;
                localip += "Internal IP: " + inetAddress.toString().replace("/", "") + "\n";
                local_ips.add(inetAddress.toString().replace("/", ""));
            }
        }
        if (a == 0) {
            localip = "";
        }
        return localip;
    }

    public void text_be() {
        try {
            Scanner in = new Scanner(new FileReader("ip.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("txt tartalom: " + kecske);
                    if (a == 1) {
                        jcam1.setText(kecske.split(":")[0]);
                        jcport1.setText(kecske.split(":")[1]);
                        jvcport1.setText(kecske.split(":")[2]);
                    } else if (a == 2) {
                        jcam2.setText(kecske.split(":")[0]);
                        jcport2.setText(kecske.split(":")[1]);
                        jvcport2.setText(kecske.split(":")[2]);
                    } else if (a == 3) {
                        jcam3.setText(kecske.split(":")[0]);
                        jcport3.setText(kecske.split(":")[1]);
                        jvcport3.setText(kecske.split(":")[2]);
                    }
                    a++;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
        try {
            Scanner in = new Scanner(new FileReader("port.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("txt tartalom: " + kecske);
                    jcportfiled.setText(kecske.split(":")[0]);
                    jvcportfiled.setText(kecske.split(":")[1]);
                    a++;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    void readdolgok() {
        try {
            Scanner in = new Scanner(new FileReader("dolgok.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    if (a == 1) {
                        jname.setText(kecske.trim());
                    } else if (a == 2) {

                    } else if (a == 3) {

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

    void flip_flop_cam_horiz_read() {
        try {
            Scanner in = new Scanner(new FileReader("camera_horiz.txt"));
            int a = 1;
            while (in.hasNext()) {
                String kecske = in.nextLine();
                if (!kecske.isEmpty()) {
                    System.out.println("txt tartalom: " + kecske);
                    flip = kecske.equals("true");
                    a++;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    public void kiir() {
        server_setup_ir();
        cam_feed_ir();
        name_ir();
        this.dispose();
    }

    public void server_setup_ir() {
        try (PrintWriter iro = new PrintWriter(new File("ip.txt"))) {
            if (!jcam1.getText().trim().isEmpty()) {
                iro.println(jcam1.getText().trim() + ":" + jcport1.getText().trim() + ":" + jvcport1.getText().trim());
            }
            if (!jcam2.getText().trim().isEmpty()) {
                iro.println(jcam2.getText().trim() + ":" + jcport2.getText().trim() + ":" + jvcport2.getText().trim());
            }
            if (!jcam3.getText().trim().isEmpty()) {
                iro.println(jcam3.getText().trim() + ":" + jcport3.getText().trim() + ":" + jvcport3.getText().trim());
            }
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter iro = new PrintWriter(new File("port.txt"))) {
            if (!jcportfiled.getText().trim().isEmpty()) {
                iro.println(jcportfiled.getText().trim() + ":" + jvcportfiled.getText().trim());
            }
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cam_feed_ir() {
        String ip = ipip.getText().trim();
        if (ip.isEmpty()) {
            System.out.println("valami hiba van");
        } else {
            try {
                Cm.camera_feed(new URL(ip));
            } catch (Exception e) {
            }
        }
    }

    void name_ir() {
        try (PrintWriter iro = new PrintWriter(new File("dolgok.txt"))) {
            if (!jname.getText().trim().isEmpty()) {
                iro.println(jname.getText().trim() + "\n");
            }
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UpnpService u; //when upnp is enabled, this points to the upnp service

    public void test_upnp() {
        System.out.println("test upnp");
        String message = "";
        for (int i = 0; i < local_ips.size(); i++) {
            System.out.println("test upnp: " + i);
            if (open_upnp(local_ips.get(i))) {
                message += "Opend Port On: " + local_ips.get(i) + "\n";
            } else {
                message += "Can't Open Port On: " + local_ips.get(i) + "\n";
            }
        }
        JOptionPane.showConfirmDialog(null, message, "UPnP status", JOptionPane.PLAIN_MESSAGE);
    }

    boolean open_upnp(String ip) {
        boolean open = false;
        try {
            u = new UpnpServiceImpl(new PortMappingListener(new PortMapping(Integer.parseInt(jcportfiled.getText().trim()), ip, PortMapping.Protocol.TCP, "LedWall TWIN project Zalaegerszeg (video)")));
            u.getControlPoint().search();
            u = new UpnpServiceImpl(new PortMappingListener(new PortMapping(Integer.parseInt(jvcportfiled.getText().trim()), ip, PortMapping.Protocol.TCP, "LedWall TWIN project Zalaegerszeg (audio and chat)")));
            u.getControlPoint().search();
            return u.getRouter().isEnabled();
        } catch (Exception e) {
            System.out.println("UPnP: " + e.toString());
            return false;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jcam1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jcam2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jcam3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jip = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcport1 = new javax.swing.JTextField();
        jcport2 = new javax.swing.JTextField();
        jcport3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcportfiled = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jvcport1 = new javax.swing.JTextField();
        jvcport2 = new javax.swing.JTextField();
        jvcport3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jvcportfiled = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        ipip = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jname = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("wall1 ip:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcam1, gridBagConstraints);

        jLabel2.setText("wall2 ip:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcam2, gridBagConstraints);

        jLabel3.setText("wall3 ip:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcam3, gridBagConstraints);

        jip.setEditable(false);
        jip.setColumns(20);
        jip.setRows(5);
        jip.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jip);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 357;
        gridBagConstraints.ipady = 128;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel4.setText(":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setText(":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setText(":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel6, gridBagConstraints);

        jcport1.setText("6666");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcport1, gridBagConstraints);

        jcport2.setText("6666");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcport2, gridBagConstraints);

        jcport3.setText("6666");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcport3, gridBagConstraints);

        jLabel7.setText("Camera server port:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel7, gridBagConstraints);

        jcportfiled.setText("6666");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 88;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jcportfiled, gridBagConstraints);

        jLabel8.setText("camera");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setText("voice and chat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jLabel9, gridBagConstraints);

        jvcport1.setText("6969");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jvcport1, gridBagConstraints);

        jvcport2.setText("6969");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jvcport2, gridBagConstraints);

        jvcport3.setText("6969");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel1.add(jvcport3, gridBagConstraints);

        jLabel10.setText("Voice and chat port");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel1.add(jLabel10, gridBagConstraints);

        jvcportfiled.setText("6969");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jvcportfiled, gridBagConstraints);

        jButton1.setText("Test UPnP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jButton1, gridBagConstraints);

        jCheckBox1.setText("UPnP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        jPanel1.add(jCheckBox1, gridBagConstraints);

        jTabbedPane1.addTab("ServerSetup", jPanel1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Usb camera");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(jRadioButton1, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Ip camera");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        jPanel2.add(jRadioButton2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(ipip, gridBagConstraints);

        jLabel12.setText("Feed link:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel2.add(jLabel12, gridBagConstraints);

        jLabel11.setText("IP camera");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel25.setText("setup");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel25, gridBagConstraints);

        jLabel26.setText("name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel2.add(jLabel26, gridBagConstraints);

        jLabel27.setText("weathercam:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel2.add(jLabel27, gridBagConstraints);

        jLabel28.setText("weatherforecast:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 5);
        jPanel2.add(jLabel28, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jname, gridBagConstraints);

        jTextField2.setText("jTextField2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jTextField2, gridBagConstraints);

        jTextField3.setText("jTextField3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 95;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jTextField3, gridBagConstraints);

        jLabel13.setText("Other things:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel13, gridBagConstraints);

        jTabbedPane1.addTab("Webcam and Audio setup", jPanel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jButton7.setText("Save and continue");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton7, gridBagConstraints);

        jButton8.setText("Close");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(jButton8, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        Cm.camera_feed(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        Cm.camera_feed(true);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        kiir();
        new kiiras().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        test_upnp();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField ipip;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jcam1;
    private javax.swing.JTextField jcam2;
    private javax.swing.JTextField jcam3;
    private javax.swing.JTextField jcport1;
    private javax.swing.JTextField jcport2;
    private javax.swing.JTextField jcport3;
    private javax.swing.JTextField jcportfiled;
    private javax.swing.JTextArea jip;
    private javax.swing.JTextField jname;
    private javax.swing.JTextField jvcport1;
    private javax.swing.JTextField jvcport2;
    private javax.swing.JTextField jvcport3;
    private javax.swing.JTextField jvcportfiled;
    // End of variables declaration//GEN-END:variables
}
