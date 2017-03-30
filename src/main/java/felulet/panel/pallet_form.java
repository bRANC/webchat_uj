/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet.panel;

import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author User-I3
 */
public class pallet_form extends javax.swing.JPanel {

    /**
     * Creates new form weather_form
     */
    public String location = "", weather_api_key = "", google_api_key = "", sc_logo = "";
    weather wet;
    ArrayList<weather_panel> wp = new ArrayList<>();

    public String name = "";
    int elore = 2;

    public pallet_form(String location, String weather_api_key, String google_api_key, String sc_logo, String sc_name) {
        this.location = location;
        this.weather_api_key = weather_api_key;
        this.google_api_key = google_api_key;

        wet = new weather(location, weather_api_key, google_api_key);
        initComponents();
        weather_cam.setText(location);//késöbb törölni kell
        try {
            if (!sc_logo.isEmpty()) {
                set_custom_image(school_icon, sc_logo);
            }
            if (!sc_name.isEmpty()) {
                school_name.setText(sc_name);
            }
        } catch (Exception e) {
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        GridBagLayout layout = (GridBagLayout) jPanel1.getLayout();

        for (int i = 0; i < elore; i++) {
            wp.add(new weather_panel());
            gbc.gridx = i;
            jPanel1.add(wp.get(i));
            //System.out.println(i + " : " + wet.get_summary(i));
            if (i == 0) {
                wp.get(i).w_summary.setText(wet.summary + "  " + wet.temperature);
                //wp.get(i).w_temp.setText("");
                wp.get(i).feels_like.setText(wet.apparenttemp);
                wp.get(i).w_max_temp.setText(wet.get_temp_max(i));
                wp.get(i).w_min_temp.setText(wet.get_temp_min(i));
                wp.get(i).weather.setText("");
                set_wet_image(wp.get(i).weather, wet.icon);
            } else {
                wp.get(i).w_summary.setText(wet.get_summary(i));
                //wp.get(i).w_temp.setText("");
                wp.get(i).weather.setText("");
                wp.get(i).jLabel1.setText("");
                wp.get(i).feels_like.setText("");
                wp.get(i).w_max_temp.setText(wet.get_temp_max(i));
                wp.get(i).w_min_temp.setText(wet.get_temp_min(i));
                set_wet_image(wp.get(i).weather, wet.get_icon(i));
            }
            layout.addLayoutComponent(wp.get(i), gbc);

        }

//        set_wet_image(weather, wet.icon);
//        w_summary.setText(wet.summary);
//        w_temp.setText(wet.temperature);
        // set_wet_image(weather1, wet.icon_n);
        // w_summary1.setText(wet.summary_n);
        // w_temp1.setText(wet.temperature_n);
    }

    public void set_location(String location) {
        this.location = location;
    }

    public void set_sc_name(String sc_name) {

        if (!sc_name.isEmpty()) {
            school_name.setText(sc_name);
        }
    }

    public void set_address(String address) {
        if (!address.isEmpty()) {
            location = address;
            weather_cam.setText(address);
            wet_update();
        }
    }

    public void set_sc_icon(String sc_logo) {
        System.out.println("set_sc_icon: " + sc_logo);
        if (!sc_logo.isEmpty()) {
            set_custom_image(school_icon, sc_logo);
        }
    }

    public void wet_update() {
        wet = new weather(location, weather_api_key, google_api_key);
        for (int i = 0; i < elore; i++) {
            if (i == 0) {
                wp.get(i).w_summary.setText(wet.summary + "  " + wet.temperature);
                wp.get(i).feels_like.setText(wet.apparenttemp);
                wp.get(i).w_max_temp.setText(wet.get_temp_max(i));
                wp.get(i).w_min_temp.setText(wet.get_temp_min(i));
                wp.get(i).weather.setText("");
                set_wet_image(wp.get(i).weather, wet.icon);
            } else {
                wp.get(i).w_summary.setText(wet.get_summary(i));
                wp.get(i).weather.setText("");
                wp.get(i).jLabel1.setText("");
                wp.get(i).feels_like.setText("");
                wp.get(i).w_max_temp.setText(wet.get_temp_max(i));
                wp.get(i).w_min_temp.setText(wet.get_temp_min(i));
                set_wet_image(wp.get(i).weather, wet.get_icon(i));
            }
        }
    }

    public void set_wet_image(JLabel be, String icon_name) {
        try {
            be.setText("");
            ImageIcon img = new ImageIcon("lib/images/" + icon_name.replace("\"", "") + ".png");
            //System.out.println(img.getImageLoadStatus() + "  " + img.getIconHeight() + " kecske: " + "lib/images/" + icon_name.replace("\"", "") + ".png");
            be.setIcon(getScaledImage(img.getImage(), 64, 64));
            be.setVerticalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            System.out.println("set_wet_image: " + e);
        }
    }

    public void school_icon() {
        FileFilter filter = new FileNameExtensionFilter("Images", "png", "jpg", "jpeg", "bmp");
        JFileChooser filec = new JFileChooser();
        filec.setFileFilter(filter);
        int result = filec.showOpenDialog(new JFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            copy_to_images_scicon(filec.getSelectedFile());
        }
    }

    public void copy_to_images_scicon(File src) {
        String extension = FilenameUtils.getExtension(src.getAbsolutePath());

        System.out.println(extension);
        //File source = new File("H:\\work-temp\\file");
        File dest = new File("lib/images/" + location + "_schoolimg." + extension);//letárolás
        //inn.fel(update helyi where location = nation value ) "lib/images/" + location + "_schoolimg." + extension
        try {
            FileUtils.copyFile(src, dest);
            set_custom_image(school_icon, "lib/images/" + location + "_schoolimg." + extension);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void set_custom_image(JLabel be, String uri) {
        try {
            be.setText("");
            ImageIcon img = new ImageIcon(uri);
            be.setIcon(getScaledImage(img.getImage(), 128, 128));
            be.setVerticalAlignment(JLabel.CENTER);
        } catch (Exception e) {
            System.out.println("set_custom_image: " + e);
        }
    }

    ImageIcon getScaledImage(Image srcImg, int bound_width, int bound_height) {
        int original_width = srcImg.getWidth(this);
        int original_height = srcImg.getWidth(this);
        int new_width = original_width;
        int new_height = original_height;

        if (original_width > bound_width) {
            new_width = bound_width;
            new_height = (new_width * original_height) / original_width;
        }
        if (new_height > bound_height) {
            new_height = bound_height;
            new_width = (new_height * original_width) / original_height;
        }

        BufferedImage resizedImg = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //System.out.println("new_width: " + new_width + "  new_height: " + new_height);
        g2.drawImage(srcImg, 0, 0, new_width, new_height, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
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

        school_icon = new javax.swing.JLabel();
        school_name = new javax.swing.JLabel();
        weather_cam = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        school_icon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        school_icon.setForeground(new java.awt.Color(255, 255, 255));
        school_icon.setText("Icon");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(school_icon, gridBagConstraints);

        school_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        school_name.setForeground(new java.awt.Color(255, 255, 255));
        school_name.setText("School name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(school_name, gridBagConstraints);

        weather_cam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        weather_cam.setForeground(new java.awt.Color(255, 255, 255));
        weather_cam.setText("Address");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(weather_cam, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel school_icon;
    private javax.swing.JLabel school_name;
    private javax.swing.JLabel weather_cam;
    // End of variables declaration//GEN-END:variables
}
