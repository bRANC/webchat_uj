/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import com.github.sarxos.webcam.Webcam;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import voice.client.ChatClient;
import webcum.agent.StreamServerAgent;

/**
 *
 * @author branc
 */
public class camera {

    Webcam webcam = Webcam.getDefault();

    public camera() {
        //  for (int i = 0; i < webcam.getWebcamListenersCount(); i++) {
        //      webcam.removeWebcamListener(webcam.getWebcamListeners()[i]);
        //  }
        //flip_flop_cam_horiz_read();
    }
    Boolean ip = false;
    URL ip_addres;

    public void set_camera(Webcam wb) {
        if (!webcam.getName().equals(wb.getName())) {
            System.out.println("set new camera");
            //bcam.
            webcam.close();
            // webcam.shutdown();
            webcam = wb;
        } else {
            System.out.println("same camera selected");
        }
    }

    public void camera_feed(Boolean a) {
        ip = a;
    }

    public void camera_feed(URL a) {
        ip_addres = a;
    }

    BufferedImage last;
    StreamServerAgent serverAgent;

    public void stream(StreamServerAgent sa) {
        serverAgent = sa;
    }

    void flip_flop_cam_horiz_write() {
        serverAgent.flip_flop_cam_horiz_write();
    }
    boolean flip = true;

    public BufferedImage getcam() {
        if (ip) {
            try {
                last = ImageIO.read(ip_addres);
            } catch (Exception e) {
            }
        } else if (webcam.isOpen()) {
            if (flip) {
                last = webcam.getImage();
            } else {
                last = flipHoriz(webcam.getImage());
            }
        }
        return last;
    }

    public ImageIcon getcam_icon() {
        return new ImageIcon(getcam());
    }

    BufferedImage flipHoriz(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth() - (image.getWidth() / 4), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = newImage.createGraphics();
        gg.drawImage(image, image.getHeight(), 0, -image.getWidth() + (image.getWidth() / 4), image.getHeight(), null);
        gg.dispose();
        return newImage;
    }

    public void start() {
        new internal_settings(this,new ChatClient(true)).kamera_res_setup();
        webcam.open();
    }

    public void stop() {
        webcam.close();
    }

    public void strem() {

    }
}
