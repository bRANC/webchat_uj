/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.net.InetSocketAddress;
import com.github.sarxos.webcam.Webcam;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
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

    public void set_camera(Webcam wb) {
        if (!webcam.getName().equals(wb.getName())) {
            System.out.println("set new camera");
            webcam = wb;
        }
        System.out.println("same camera selected");
    }

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
    boolean flip = true;

    void flip_flop_cam_horiz_write() {
        flip = !flip;
        try (PrintWriter iro = new PrintWriter(new File("camera_horiz.txt"))) {
            iro.println(flip);
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
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
                    if (kecske.equals("true")) {
                        flip = true;
                    } else {
                        flip = false;
                    }
                    a++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("not configured");
        }
    }

    public ImageIcon getcam_icon() {
        if (flip) {
            return new ImageIcon(flipHoriz(getcam()));
        } else {
            return new ImageIcon(getcam());
        }
    }

    BufferedImage flipHoriz(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth() - (image.getWidth() / 4), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gg = newImage.createGraphics();
        gg.drawImage(image, image.getHeight(), 0, -image.getWidth() + (image.getWidth() / 4), image.getHeight(), null);
        gg.dispose();
        return newImage;
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
