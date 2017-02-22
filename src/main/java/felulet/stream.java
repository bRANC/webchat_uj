/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
/**
 *
 * @author branc
 */
public class stream {

}

class send {

    Socket s;//hova megy, connect
    DataOutputStream out;

    String ip = "";
    int port = 0;

    send() {

    }

    void setup(String ip_, int port_) {
        ip = ip_;
        port = port_;
        cummection();
    }

    void cummection() {
        try {
            System.out.println("socket connect start");
            s = new Socket(ip, port);
            out = new DataOutputStream(s.getOutputStream());
            System.out.println("output setup done");
            hiba = false;
        } catch (IOException ex) {
            System.out.println("Socket open error! ip: " + ip);
            hiba = true;
        }
    }

    void discoverlocalhost() {

    }

    Boolean hiba = false;

    public void kuld(BufferedImage im) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(im, "jpg", baos);
            byte[] buffer = baos.toByteArray();
            out.writeInt(buffer.length);
            out.write(buffer);
            out.flush();
        } catch (Exception e) {
            hiba = true;
            new reconnect().execute();
            e.printStackTrace();
        }
    }

    class reconnect extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            wait(5000);
            cummection();
            return null;
        }

        @Override
        protected void done() {
            if (hiba) {
                System.out.println("failed to reconnect!: " + ip + ":" + port);
            }
        }
    }

}

class Server {

    ServerSocket sc;
    public ArrayList<Socket> connections = new ArrayList<Socket>();
    public ArrayList<receiv> befele = new ArrayList<receiv>();
    public ImageIcon lol;

    void close() {
        try {
            sc.close();
        } catch (Exception e) {
            System.out.println("ServerSocket closed");
        }
    }

    Server(int port) {

        try {
            sc = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("Server socket open error!");
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("waiting connection");
                        Socket a = sc.accept();
                        System.out.println("connected: " + a.getInetAddress() + ":" + a.getPort());
                        connections.add(a);   // a cooncetion-t átadom egy socketnek
                        befele.add(new receiv(a));
                        befele.get(befele.size() - 1).start();
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t.start();
    }
}

class receiv extends Thread {

    DataInputStream in;
    Socket be;
    public ImageIcon cam = new ImageIcon();
    Boolean fut = true;
    String ip = "";

    @Override
    public void run() {
        while (fut) {
            try {
                int length = in.readInt();
                byte[] buffer;
                BufferedImage img;

                if (length > 0) {
                    buffer = new byte[length];
                    in.readFully(buffer);
                    img = ImageIO.read(new ByteArrayInputStream(buffer));
                    cam = new ImageIcon(img);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("szál le állsítása ip: " + ip);
                fut = false;
            }
        }
    }

    receiv(Socket be) {
        try {
            this.be = be;
            in = new DataInputStream(be.getInputStream());
            ip = be.getInetAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
