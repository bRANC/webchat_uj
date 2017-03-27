/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet.panel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author User-I3
 */
public class pollandconnect {

    JTextArea area;

    public pollandconnect(JTextArea be) {
        area = be;
    }

    String name = "kecske";
    refresh ref = new refresh();
    cmd cm = new cmd();

    public void connect_matrix(Boolean refresh) {
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Register/" + name);
        if (be.contains("Registered") || be.contains("Refreshed")) {
            //System.out.println("done conn");
            if (refresh) {
                ref.execute();
                varas(50);
                cm.execute();
            }
        }
    }

    public void refresh_matrix() {
        connect_matrix(false);
    }

    public void disconnect_matrix() {
        cm.cancel(true);
        ref.cancel(true);
        varas(100);
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Unregister/" + name);
        if (be.contains("Unregistered") || be.contains("Not registered")) {
            System.out.println("done dissconn");
        }
    }

    public void get_command() {
        //http://webledmatrix.azurewebsites.net/
        String be = http("http://webledmatrix.azurewebsites.net/clientApi/Commands/" + name);
        String comand[] = be.split("string");
        String appendus = be.replace(":", "").replace("[", "").replace("]", "").replace("\"", "").trim();
        if (!appendus.isEmpty()) {
            area.append(appendus + "\n");
        }
    }

    public String http(String page) {
        String vissza = "";
        try {
            URL whatismyip = new URL(page);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String hozza = "";
            hozza += in.readLine();
            while (!hozza.equals("null")) {
                vissza += hozza + "\n";
                hozza = "";
                hozza += in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vissza;
    }

    public void varas(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
        }
    }

    class refresh extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                refresh_matrix();
                varas(3000);
            }
            return null;
        }

    }

    class cmd extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            while (!this.isCancelled()) {
                get_command();
                varas(100);
            }
            return null;
        }

    }
}
