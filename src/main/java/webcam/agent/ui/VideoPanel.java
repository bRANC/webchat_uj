package webcam.agent.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import net.coobird.thumbnailator.makers.ScaledThumbnailMaker;

public class VideoPanel extends JPanel {

    boolean megy = true;

    public void stop() {
        updateImage(null);
        megy = false;

    }

    public void megy() {
        megy = true;
        updateImage(null);
    }

    public void set_parent_size(Dimension a) {
        this.setSize(a);
    }
    /**
     *
     */
    private static final long serialVersionUID = -7292145875292244144L;

    protected BufferedImage image;
    protected final ExecutorService worker = Executors.newSingleThreadExecutor();
    protected final ScaledThumbnailMaker scaleUPMaker = new ScaledThumbnailMaker(2);

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //if (megy) { experementel
        if (image == null) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setBackground(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int cx = (getWidth() - 70) / 2;
            int cy = (getHeight() - 40) / 2;

            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(cx, cy, 70, 40, 10, 10);
            g2.setColor(Color.WHITE);
            g2.fillOval(cx + 5, cy + 5, 30, 30);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(cx + 10, cy + 10, 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillOval(cx + 12, cy + 12, 16, 16);
            g2.fillRoundRect(cx + 50, cy + 5, 15, 10, 5, 5);
            g2.fillRect(cx + 63, cy + 25, 7, 2);
            g2.fillRect(cx + 63, cy + 28, 7, 2);
            g2.fillRect(cx + 63, cy + 31, 7, 2);

            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(0, 0, getWidth(), getHeight());
            g2.drawLine(0, getHeight(), getWidth(), 0);

            String str = image == null ? "Initializing" : "No Image";
            FontMetrics metrics = g2.getFontMetrics(getFont());
            int w = metrics.stringWidth(str);
            int h = metrics.getHeight();

            g2.setColor(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.drawString(str, (getWidth() - w) / 2, cy - h);
            w = metrics.stringWidth(str);
            h = metrics.getHeight();
            g2.drawString(str, (getWidth() - w) / 2, cy - 2 * h);
        } else {
            //owner.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            //g2.clearRect(0, 0, image.getWidth(), image.getHeight());

            if (image.getColorModel().equals(gfx_config.getColorModel())) {
                g2.clearRect(0, 0, getWidth(), getHeight());
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            } else {
                BufferedImage new_image = gfx_config.createCompatibleImage(
                        image.getWidth(), image.getHeight(), image.getTransparency());

                // get the graphics context of the new image to draw the old image on
                Graphics2D g2d = (Graphics2D) new_image.createGraphics();

                // actually draw the image and dispose of context no longer needed
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                g2d.dispose();
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
            g2.dispose();
            //setBounds(getBounds().x	, getBounds().y, image.getWidth(), image.getHeight());
            setSize(getWidth(), getHeight());
        }
    } //else {  //this too
    // g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    // }  //eddig

    public void updateImage(final BufferedImage update) {
        worker.execute(() -> {
            try {
                //System.out.println("img: " + update);
            } catch (Exception e) {
                //System.out.println("img_ex: " + e.toString());
            }
            //image = scaleUPMaker.make(update);
            if (megy) {
                image = update;
                repaint();
            } else if (image != null) {
                image = null;
                repaint();
            }
        });
    }
    // obtain the current system graphical settings
    GraphicsConfiguration gfx_config = GraphicsEnvironment.
            getLocalGraphicsEnvironment().getDefaultScreenDevice().
            getDefaultConfiguration();

    public void close() {
        worker.shutdown();
    }

}
