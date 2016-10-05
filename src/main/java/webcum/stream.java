/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

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
    int cam;

    send() {

    }

    void setup(String ip_, int port_) {
        ip = ip_;
        port = port_;
        try {
            s = new Socket(ip, port);
            out = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Socket open error!");
        }
    }

    void setup(int cam, String ip_, int port_) {
        ip = ip_;
        port = port_;
        this.cam = cam;
    }

    void setup(int cam) {
        this.cam = cam;
    }

    void discoverlocalhost() {

    }

    public void kuld(BufferedImage im) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(im, "jpg", baos);
            byte[] buffer = baos.toByteArray();
            out.writeInt(cam);
            out.writeInt(buffer.length);
            out.write(buffer);
            out.flush();
        } catch (Exception e) {
        }
    }
}

class Server {

    ServerSocket sc;
    public ArrayList<Socket> connections = new ArrayList<Socket>();
    public ArrayList<receiv> befele = new ArrayList<receiv>();
    public ImageIcon lol;

    Server(int port) {

        try {

            sc = new ServerSocket(port);

        } catch (Exception e) {
            System.out.println("Server socket open error!");
        }
        System.out.println("thread start");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("witing connection");
                        Socket a = sc.accept();
                        System.out.println("connected: " + a.getInetAddress());
                        connections.add(a);   // a cooncetion-t átadom egy socketnek
                        befele.add(new receiv(a));
                        System.out.println("New Connection");
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
    public ImageIcon cam = new ImageIcon();
    public int wall = 0;

    public void run() {
        try {

            int came = in.readInt();
            int length = in.readInt();
            byte[] buffer;
            BufferedImage img;

            if (length > 0) {
                buffer = new byte[length];
                in.readFully(buffer);
                img = ImageIO.read(new ByteArrayInputStream(buffer));
                cam = new ImageIcon(img);
                wall = came;
            }
        } catch (Exception e) {
        }

    }

    receiv(Socket be) {
        try {
            in = new DataInputStream(be.getInputStream());
        } catch (Exception e) {
        }
    }
}
