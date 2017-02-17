/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum.sqlite;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author branc
 */
public class validalos {
//________________________Elenőrzendő szöveg, Hiba kiírás, Nem lehet üres = true, Csak szám = true, String karakterek vesszővel elválasztva

    public boolean txtHibaEll(JTextField txt, JLabel label, Boolean uresLehet_e, Boolean szamLehet_e, String tiltott) {
        //System.out.println("Run Ell");
        boolean hiba = false;
        boolean pont = false;
        String[] tiltottKarakterek = tiltott.split(",");
        ArrayList<String> chLista = new ArrayList<>();
        for (int i = 0; i < tiltottKarakterek.length; i++) {
            chLista.add(tiltottKarakterek[i]);
            //System.out.println("Tch: " + tiltottKarakterek[i]);
        }
        String be = txt.getText().trim();
        if (!be.isEmpty()) {
            if (be.substring(0, 1).contains(".")) {
                //System.out.println("str: " + be.substring(0, 1));
                //System.out.println("be: " + be);
                hiba = false;
                pont = false;
                txt.setBackground(Color.PINK);
                label.setForeground(Color.red);
                label.setText("Nem lehet pont az elején!");
            } else {
                txt.setBackground(Color.WHITE);
                label.setForeground(Color.black);
                label.setText(" ");
                hiba = true;
                pont = true;
            }
        }
        if (!be.isEmpty()) {
            if (szamLehet_e) {
                try {
                    Double.parseDouble(be);
                    if (be.contains("f") || be.contains("d")) {
                        txt.setBackground(Color.PINK);
                        hiba = false;
                        label.setForeground(Color.red);
                        label.setText("Csak számok lehetnek!");
                    } else if (hiba) {
                        label.setForeground(Color.black);
                        label.setText("");
                    }
                } catch (Exception e) {
                    if (!pont) {
                        txt.setBackground(Color.PINK);
                        label.setForeground(Color.red);
                        label.setText("Nem lehet pont az elején!");
                        hiba = false;
                    } else {
                        txt.setBackground(Color.PINK);
                        label.setForeground(Color.red);
                        label.setText("Csak számok lehetnek!");
                        hiba = false;
                    }
                }
            } else if (!be.isEmpty()) {
                String[] be2 = be.split("");
                boolean ell = true; //kell e tovább futtatni while-t
                int j = 0;
                while (j < chLista.size() && ell) {
                    asd:
                    for (int i = 0; i < be.length(); i++) {
                        Boolean tart = be2[i].contains(chLista.get(j));
                        //System.out.println("tart: " + tart);
                        if (tart) {
                            ell = false;

                            hiba = false;
                            txt.setBackground(Color.PINK);
                            label.setForeground(Color.red);
                            label.setText("Illegális karakter: \" " + be2[i] + " \"!");
                            break asd; //Kell!
                        } else if (!pont) {
                            label.setForeground(Color.red);
                            label.setText("Nem lehet pont az elején!");
                            hiba = false;
                        } else {
                            txt.setBackground(Color.WHITE);
                            label.setForeground(Color.black);
                            label.setText(" ");
                        }
                    }//for//for
                    j++;
                }//While
            }
        } else if (!uresLehet_e) {
            txt.setBackground(Color.WHITE);
            label.setForeground(Color.black);
            label.setText(" ");
            hiba = true;
        } else {
            txt.setBackground(Color.PINK);
            label.setForeground(Color.red);
            label.setText("Nem lehet üres!");
        }
        System.out.println("hiba: " + hiba);
        return hiba;
    }

    public boolean txtAreaHibaEll(JTextArea txt, JLabel label, Boolean uresLehet_e, Boolean szamLehet_e, String tiltott) {
        //System.out.println("Run Ell");
        boolean hiba = false;
        boolean pont = false;
        String[] tiltottKarakterek = tiltott.split(",");
        ArrayList<String> chLista = new ArrayList<>();
        for (int i = 0; i < tiltottKarakterek.length; i++) {
            chLista.add(tiltottKarakterek[i]);
            //System.out.println("Tch: " + tiltottKarakterek[i]);
        }
        String be = txt.getText().trim();
        if (!be.isEmpty()) {
            if (be.substring(0, 1).contains(".")) {
                //System.out.println("str: " + be.substring(0, 1));
                //System.out.println("be: " + be);
                hiba = false;
                pont = false;
                txt.setBackground(Color.PINK);
                label.setForeground(Color.red);
                label.setText("Nem lehet pont az elején!");
            } else {
                txt.setBackground(Color.WHITE);
                label.setForeground(Color.black);
                label.setText(" ");
                hiba = true;
                pont = true;
            }
        }
        if (!be.isEmpty()) {
            if (szamLehet_e) {
                try {
                    Double.parseDouble(be);
                    if (be.contains("f") || be.contains("d")) {
                        hiba = false;
                        txt.setBackground(Color.PINK);
                        label.setForeground(Color.red);
                        label.setText("Csak számok lehetnek!");
                    } else if (hiba) {
                        label.setForeground(Color.black);
                        label.setText("");
                    }
                } catch (Exception e) {
                    if (!pont) {
                        txt.setBackground(Color.PINK);
                        label.setForeground(Color.red);
                        label.setText("Nem lehet pont az elején!");
                        hiba = false;
                    } else {
                        txt.setBackground(Color.PINK);
                        label.setForeground(Color.red);
                        label.setText("Csak számok lehetnek!");
                        hiba = false;
                    }
                }
            } else if (!be.isEmpty()) {
                String[] be2 = be.split("");
                boolean ell = true; //kell e tovább futtatni while-t
                int j = 0;
                while (j < chLista.size() && ell) {
                    asd:
                    for (int i = 0; i < be.length(); i++) {
                        Boolean tart = be2[i].contains(chLista.get(j));
                        //System.out.println("tart: " + tart);
                        if (tart) {
                            ell = false;

                            hiba = false;
                            txt.setBackground(Color.PINK);
                            label.setForeground(Color.red);
                            label.setText("Illegális karakter: \" " + be2[i] + " \"!");
                            break asd; //Kell!
                        } else if (!pont) {
                            label.setForeground(Color.red);
                            label.setText("Nem lehet pont az elején!");
                            hiba = false;
                        } else {
                            txt.setBackground(Color.WHITE);
                            label.setForeground(Color.black);
                            label.setText(" ");
                        }
                    }//for//for
                    j++;
                }//While
            }
        } else if (!uresLehet_e) {
            txt.setBackground(Color.WHITE);
            label.setForeground(Color.black);
            label.setText(" ");
            hiba = true;
        } else {
            txt.setBackground(Color.PINK);
            label.setForeground(Color.red);
            label.setText("Nem lehet üres!");
        }
        System.out.println("hiba: " + hiba);
        return hiba;
    }

    public boolean szam_lehet(String be) {
        try {
            int szam = Integer.parseInt(be.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public int szama(String be) {
        int szam = 0;
        try {
            szam = Integer.parseInt(be.trim());
        } catch (Exception e) {
        }
        return szam;
    }

    public Double szama_d(String be) {
        Double szam = 0.0;
        try {
            szam = Double.parseDouble(be.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return szam;
    }

    public boolean nemlehetures(String be) {
        if (be.length() < 1 || be.equals("") || be.equals(" ")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean csakszam(String be) {
        try {
            Integer.parseInt(be);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
