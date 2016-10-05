/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import java.net.InetSocketAddress;
import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author branc
 */
public class main {

    Webcam webcam = Webcam.getDefault();

    public main() {
        start();
        // Dimension dimension = new Dimension(320, 240);
        // webcam.setViewSize(dimension);
    }

    public BufferedImage getcam() {
        return webcam.getImage();
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
