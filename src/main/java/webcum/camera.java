/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.net.InetSocketAddress;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author branc
 */
public class camera {

    Webcam webcam = Webcam.getDefault();

    public camera() {
    }
    Boolean ip = false;
    URL ip_addres;

    public void camera_feed(Boolean a) {
        ip = a;
    }

    public void camera_feed(URL a) {
        ip_addres = a;
    }

    public BufferedImage getcam() {
        if (ip) {
            try {
                return ImageIO.read(ip_addres);
            } catch (Exception e) {
            }
        } else {
            return webcam.getImage();
        }
        return null;
    }

    public ImageIcon getcam_icon() {
        return new ImageIcon(getcam());
    }

    public void start() {
        webcam.open();
    }

    public void stop() {
        webcam.close();
    }

    public void strem() {

    }
}
