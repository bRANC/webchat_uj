/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package felulet.panel;

/**
 *
 * @author User-I3
 */
public class weather_panel extends javax.swing.JPanel {

    /** Creates new form weather_panel */
    public weather_panel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        weather = new javax.swing.JLabel();
        w_summary = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        feels_like = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        w_min_temp = new javax.swing.JLabel();
        w_max_temp = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        weather.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        weather.setForeground(new java.awt.Color(255, 255, 255));
        weather.setText("jLabel4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        add(weather, gridBagConstraints);

        w_summary.setBackground(new java.awt.Color(0, 0, 0));
        w_summary.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        w_summary.setForeground(new java.awt.Color(255, 255, 255));
        w_summary.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        add(w_summary, gridBagConstraints);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Feels: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel1, gridBagConstraints);

        feels_like.setBackground(new java.awt.Color(255, 255, 255));
        feels_like.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        feels_like.setForeground(new java.awt.Color(255, 255, 255));
        feels_like.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(feels_like, gridBagConstraints);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Min: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel2, gridBagConstraints);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Max: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        add(jLabel3, gridBagConstraints);

        w_min_temp.setBackground(new java.awt.Color(0, 0, 0));
        w_min_temp.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        w_min_temp.setForeground(new java.awt.Color(255, 255, 255));
        w_min_temp.setText("jLabel4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(w_min_temp, gridBagConstraints);

        w_max_temp.setBackground(new java.awt.Color(0, 0, 0));
        w_max_temp.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        w_max_temp.setForeground(new java.awt.Color(255, 255, 255));
        w_max_temp.setText("jLabel5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        add(w_max_temp, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel feels_like;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel w_max_temp;
    public javax.swing.JLabel w_min_temp;
    public javax.swing.JLabel w_summary;
    public javax.swing.JLabel weather;
    // End of variables declaration//GEN-END:variables

}
