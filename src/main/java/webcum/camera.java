/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.net.InetSocketAddress;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamStreamer;
import java.awt.Dimension;
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
import webcum.agent.StreamServerAgent;

/**
 *
 * @author branc
 */
public class camera {

    Webcam webcam = Webcam.getDefault();

    public camera() {
        flip_flop_cam_horiz_read();
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

    public void stream() {
        //webcam.open();
        //    WebcamStreamer WS = new WebcamStreamer(5555, webcam, 25, true);
        webcam.setAutoOpenMode(true);
        Dimension dimension = new Dimension(320, 240);
        webcam.setViewSize(dimension);
        if (webcam.getName().contains("HD") || webcam.getName().contains("EasyCamera") || webcam.getName().contains("VGA")) {
            webcam.setCustomViewSizes(new Dimension[]{WebcamResolution.VGA.getSize(), WebcamResolution.HD720.getSize()});//új felbontás regisztrálása
            //Cm.webcam.setViewSize(WebcamResolution.HD720.getSize());//be állítása HD
            //VGA
            webcam.setViewSize(WebcamResolution.HD720.getSize());//be állítása VGA
        }
        serverAgent = new StreamServerAgent(webcam, WebcamResolution.HD720.getSize());
        serverAgent.start(new InetSocketAddress("localhost", 20000));

    }

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
    boolean flip = true;

    void flip_flop_cam_horiz_write() {
        serverAgent.flip_flop_cam_horiz_write();/*
        flip = !flip;
        try (PrintWriter iro = new PrintWriter(new File("camera_horiz.txt"))) {
            iro.println(flip);
            iro.flush();
            iro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
        new webcam_settings(this).kamera_res_setup();
        webcam.open();
    }

    public void stop() {
        webcam.close();
    }

    public void strem() {

    }
}
