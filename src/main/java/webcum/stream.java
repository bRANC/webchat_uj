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

class receive {

    receive() {

    }
    public ImageIcon[] cam = new ImageIcon[4];

    void fogad() {
        try {
            ServerSocket sc = new ServerSocket(6666);
            Socket s = sc.accept();

            DataInputStream in = new DataInputStream(s.getInputStream());

            int came = in.readInt();
            int length = in.readInt();
            byte[] buffer;
            BufferedImage img;

            if (length > 0) {
                buffer = new byte[length];
                //  System.out.println(length);
                in.readFully(buffer);
                //  System.out.println(buffer[100]);

                img = ImageIO.read(new ByteArrayInputStream(buffer));
                //System.out.println(buffer.length);
                cam[came] = new ImageIcon(img);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
