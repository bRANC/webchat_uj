/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet.sqlite;

import java.awt.Font;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author branc
 */
public class sqlite {

    validalos valid = new validalos();

    Connection c = null;
    String be = "";
    ScheduledExecutorService service;

    public sqlite(String file) {
        String decodedPath = "";
        try {
            int vissza = 3;
            do {
                String path = felulet.kiiras.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                decodedPath = URLDecoder.decode(path, "UTF-8");
                for (int i = 0; i < vissza; i++) {
                    decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/"));
                }
                decodedPath += "/";
                vissza--;
            //} while (conn(decodedPath + file)); //RELATIV ELÉRÉST KAPOTT A PORTOLHATÓSÁG MIATT
            } while (conn(file));
        } catch (Exception e) {
            System.out.println("decode hiba framecontainer");
        }
        //System.out.println(decodedPath+"ellen.db3");
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public Boolean isconn() {
        try {
            return !c.isClosed();
        } catch (Exception e) {
        }
        return false;
    }

    public String sqlite(String file) {
        String decodedPath = "";
        try {
            String path = felulet.kiiras.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            decodedPath = URLDecoder.decode(path, "UTF-8");
            for (int i = 0; i < 3; i++) {
                decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf("/"));
            }
            decodedPath += "/";
        } catch (Exception e) {
            System.out.println("decode hiba framecontainer");
        }
        //System.out.println(decodedPath+"ellen.db3");

        //return (decodedPath + file);//RELATIV ELÉRÉST KAPOTT A PORTOLHATÓSÁG MIATT
        return (file);
    }
    boolean waiting_to_close = false;

    void dc_timer() {
        service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(new Runnable() {
            @Override
            public void run() {
                if (!waiting_to_close) {
                    waiting_to_close = true;
                    dc();
                }
            }
        }, 10, TimeUnit.SECONDS);
    }

    public Boolean conn(String file) {
        try {
            if (c == null || c.isClosed()) {
                if (file.trim().isEmpty()) {
                    System.out.println("üres a bejövő file");
                } else {
                    System.out.println("trying to open database: " + file);
                }
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + file);
                System.out.println("Opened database successfully.");
                be = file;
            }
            dc_timer();
            return c.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    public void dc() {
        try {
            if (!c.isClosed()) {
                c.close();
                //System.out.println("closed database successfully");
                waiting_to_close = false;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void fel(String sql) {
        conn(be);
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            //stmt.close();
        } catch (Exception e) {
            if (e.toString().contains("UNIQUE constraint failed")) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                JLabel label = new JLabel("Ez már szerepel az adatbázisban!");
                label.setFont(new Font("Arial", Font.BOLD, 15));
                JOptionPane.showMessageDialog(null, label);
            }
            System.out.println(e);
            e.printStackTrace();
            java.awt.Toolkit.getDefaultToolkit().beep();
            JLabel label = new JLabel("Hiba: " + e + "");
            label.setFont(new Font("Arial", Font.BOLD, 11));
            JOptionPane.showMessageDialog(null, label);
        }

    }

    public ResultSet le(String sql) {
        conn(be);
        try {
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();
            return rs;
        } catch (Exception e) {
            System.out.println("rs:null  || " + e);
        }
        return null;
    }
}
