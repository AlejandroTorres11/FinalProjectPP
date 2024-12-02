/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.trabajofinalpp;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author alejandro
 */
public class MainFrame extends javax.swing.JFrame implements Runnable{

    /**
     * Creates new form MainFrame
     */
    private Almacen almacen;
    private Cafetera cafetera;
    private List<Horno> listaHornos;
    private List<Repostero> listaReposteros;
    private List<Empaquetador> listaEmpaquetadores;
   
    
    public MainFrame(Almacen almacen, Cafetera cafetera, List<Horno> listaHornos, List<Repostero> listaReposteros, List<Empaquetador> listaEmpaquetadores) {
        initComponents();
        this.almacen = almacen;
        this.cafetera = cafetera;
        this.listaHornos = listaHornos;
        this.listaReposteros = listaReposteros;
        this.listaEmpaquetadores = listaEmpaquetadores;
        makeAllTextFieldsNonEditable();
        h1estadotxt.setText("Inactivo");
        h2estadotxt.setText("Inactivo");
        h3estadotxt.setText("Inactivo");
        
    }
    
    public void iniciarSistema(){
        for (Horno horno : listaHornos) {
            horno.start();
        }
        for (Repostero repostero : listaReposteros) {
            repostero.start();
        }
        for(Empaquetador empaquetador : listaEmpaquetadores){
            empaquetador.start();
        }
    }
    // Método para recorrer todos los JTextField y deshabilitar la edición
    private void makeAllTextFieldsNonEditable() {
        e1estado.setEditable(false);
        e2estado.setEditable(false);
        e3estado.setEditable(false);
        r1estadotxt.setEditable(false);
        r2estadotxt.setEditable(false);
        r3estadotxt.setEditable(false);
        r4estadotxt.setEditable(false);
        r5estadotxt.setEditable(false);
        h1nGalletastxt.setEditable(false);
        h2nGalletastxt.setEditable(false);
        h3nGalletastxt.setEditable(false); 
        cReposteroUsandotxt.setEditable(false);
        cReposterosColatxt.setEditable(false);
    }
    
   public void update(){
       updateCafetera();
       updateReposteros();
       updateHornos();
   }
   public void updateCafetera() {
        try {
            // Verificar si la cafetera no tiene un repostero actual
            if (cafetera.getReposteroActual() == null) {
                cReposteroUsandotxt.setText(""); // Campo vacío si no hay repostero
            } else {
                // Obtener el ID del repostero actual si existe
                String reposteroActual = cafetera.getReposteroActual().getIdRepostero();
                cReposteroUsandotxt.setText(reposteroActual); // Actualizar con el ID del repostero
            }

            // Construir la cola de reposteros esperando
            String cola = "";
            String delimitador = "";
            for (Repostero r : listaReposteros) {
                if (r.getIdRepostero() != cafetera.getReposteroActual().getIdRepostero() && r.isEsperaCafe()) {
                    cola += delimitador + " " + r.getIdRepostero();
                    delimitador = ",";
                }
            }
            cReposterosColatxt.setText(cola);

        } catch (Exception e) {}
    }
    public void updateReposteros(){
        JTextField[] reposteroTextFields ={ r1estadotxt, r2estadotxt, r3estadotxt, r4estadotxt, r5estadotxt };
       for (int i = 0; i < listaReposteros.size(); i++) {
            Repostero r = listaReposteros.get(i);
            if (i < reposteroTextFields.length) { // Asegurarse de no exceder el número de JTextField
                reposteroTextFields[i].setText(r.getEstado()); // Asigna el estado al JTextField correspondiente
            }
        }
    }
    public void updateHornos() {
        JTextField[] nGalletasTextFields = {h1nGalletastxt, h2nGalletastxt, h3nGalletastxt};
        JLabel[] hEstadoJLabels = {h1estadotxt, h2estadotxt, h3estadotxt};
        JProgressBar[] hprogressBars = {h1progressbar, h2progressbar, h3progressbar};
       
        // Crear un ExecutorService con un número fijo de hilos (uno por horno)
        ExecutorService executor = Executors.newFixedThreadPool(listaHornos.size());

        for (int i = 0; i < listaHornos.size(); i++) {
            int index = i; // Necesario para usar en la lambda
            executor.submit(() -> {
                Horno h = listaHornos.get(index);
                String texto = "";

                SwingUtilities.invokeLater(() -> {
                    nGalletasTextFields[index].setText(texto + h.getnGalletasDentro());
                    hEstadoJLabels[index].setText(h.getEstado());
                });

                if ("Horneando".equals(h.getEstado())) {
                    hprogressBars[index].setValue(h.getProgresoHorneado());
                }
                if(h.getEstado()=="inactivo"){
                    hprogressBars[index].setValue(0);
                }
            });
        }
        // Cerrar el ExecutorService cuando termine
        executor.shutdown();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cReposterosColatxt = new javax.swing.JTextField();
        cReposteroUsandotxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        r1estadotxt = new javax.swing.JTextField();
        r2estadotxt = new javax.swing.JTextField();
        r3estadotxt = new javax.swing.JTextField();
        r4estadotxt = new javax.swing.JTextField();
        r5estadotxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        h1nGalletastxt = new javax.swing.JTextField();
        h2nGalletastxt = new javax.swing.JTextField();
        h3nGalletastxt = new javax.swing.JTextField();
        h1estadotxt = new javax.swing.JLabel();
        h2estadotxt = new javax.swing.JLabel();
        h3estadotxt = new javax.swing.JLabel();
        h1progressbar = new javax.swing.JProgressBar();
        h2progressbar = new javax.swing.JProgressBar();
        h3progressbar = new javax.swing.JProgressBar();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        e1estado = new javax.swing.JTextField();
        e2estado = new javax.swing.JTextField();
        e3estado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Iniciar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Cafetera");

        cReposteroUsandotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cReposteroUsandotxtActionPerformed(evt);
            }
        });

        jLabel2.setText("Repostero 1");

        jLabel3.setText("Repostero 2");

        jLabel4.setText("Repostero 3");

        jLabel5.setText("Repostero 4");

        jLabel6.setText("Repostero 5");

        r1estadotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1estadotxtActionPerformed(evt);
            }
        });

        r2estadotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2estadotxtActionPerformed(evt);
            }
        });

        r3estadotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3estadotxtActionPerformed(evt);
            }
        });

        jLabel7.setText("Horno 1");

        jLabel8.setText("Horno 2");

        jLabel9.setText("Horno 3");

        jLabel10.setText("Numero de galletas");

        jLabel11.setText("Numero de galletas");

        jLabel12.setText("Numero de galletas");

        h1estadotxt.setText("jLabel13");

        h2estadotxt.setText("jLabel13");

        h3estadotxt.setText("jLabel13");

        jLabel13.setText("Empaquetador 1");

        jLabel14.setText("Empaquetador 2");

        jLabel15.setText("Empaquetador 3");

        e1estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e1estadoActionPerformed(evt);
            }
        });

        e2estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e2estadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cReposteroUsandotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r1estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(h1progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(h1nGalletastxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(h1estadotxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(e1estado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(205, 205, 205)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(135, 135, 135)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(h2nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(h2estadotxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(h2progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(e2estado, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(104, 104, 104))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(101, 101, 101)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(r2estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(71, 71, 71)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(r3estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(146, 146, 146)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(r5estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(e3estado, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(h3progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h3nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h3estadotxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(r4estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cReposterosColatxt, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(h3nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(h3estadotxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(h3progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cReposterosColatxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cReposteroUsandotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(r1estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r3estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r4estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(r5estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(r2estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(h1nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(h2nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(h1estadotxt)
                            .addComponent(h2estadotxt))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(h1progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(h2progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14)))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(e1estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(e2estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(e3estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel15)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //boton iniciar
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        iniciarSistema();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void e1estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e1estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e1estadoActionPerformed

    private void e2estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e2estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e2estadoActionPerformed

    private void r3estadotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r3estadotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r3estadotxtActionPerformed

    private void r1estadotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r1estadotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r1estadotxtActionPerformed

    private void r2estadotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r2estadotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r2estadotxtActionPerformed

    private void cReposteroUsandotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cReposteroUsandotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cReposteroUsandotxtActionPerformed

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
        }
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cReposteroUsandotxt;
    private javax.swing.JTextField cReposterosColatxt;
    private javax.swing.JTextField e1estado;
    private javax.swing.JTextField e2estado;
    private javax.swing.JTextField e3estado;
    private javax.swing.JLabel h1estadotxt;
    private javax.swing.JTextField h1nGalletastxt;
    private javax.swing.JProgressBar h1progressbar;
    private javax.swing.JLabel h2estadotxt;
    private javax.swing.JTextField h2nGalletastxt;
    private javax.swing.JProgressBar h2progressbar;
    private javax.swing.JLabel h3estadotxt;
    private javax.swing.JTextField h3nGalletastxt;
    private javax.swing.JProgressBar h3progressbar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField r1estadotxt;
    private javax.swing.JTextField r2estadotxt;
    private javax.swing.JTextField r3estadotxt;
    private javax.swing.JTextField r4estadotxt;
    private javax.swing.JTextField r5estadotxt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        System.out.println("--------------------------------COMIENZA EL RUNEO-------------------------------------");
        while (true) {
            try {
                update();
                Thread.sleep(100); // Pausa para evitar saturar la memoria
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
}
