
package Server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
import Common.InfoAlmacen;
import Common.InfoHorno;
import Common.InfoRepostero;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author alejandro
 */
public class MainFrame extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form MainFrame
     */
    private Almacen almacen;
    private Cafetera cafetera;
    private List<Horno> listaHornos;
    private List<Repostero> listaReposteros;
    private List<Empaquetador> listaEmpaquetadores;
    private int nGalletasComidas;
    public MainFrame(Almacen almacen, Cafetera cafetera, List<Horno> listaHornos, List<Repostero> listaReposteros, List<Empaquetador> listaEmpaquetadores) {
        initComponents();
        this.almacen = almacen;
        this.cafetera = cafetera;
        this.listaHornos = listaHornos;
        this.listaReposteros = listaReposteros;
        this.listaEmpaquetadores = listaEmpaquetadores;
        this.nGalletasComidas=0;
        makeAllTextFieldsNonEditable();
        h1estadotxt.setText("Inactivo");
        h2estadotxt.setText("Inactivo");
        h3estadotxt.setText("Inactivo");
        this.getContentPane().setBackground(new Color(239, 227, 215));
        
        // Cargar la primera imagen
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image img1 = icon1.getImage(); // Convierte la imagen a tipo Image
        Image resizedImg1 = img1.getScaledInstance(jLabel18.getWidth(), jLabel18.getHeight(), Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(resizedImg1); // Asignar la imagen redimensionada
        jLabel18.setIcon(icon1); // Establecer la imagen en el JLabel

        // Cargar la segunda imagen
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/images/deco1.png"));
        Image img2 = icon2.getImage(); // Convierte la imagen a tipo Image
        Image resizedImg2 = img2.getScaledInstance(jLabel17.getWidth(), jLabel17.getHeight(), Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(resizedImg2); // Asignar la imagen redimensionada
        jLabel17.setIcon(icon2); // Establecer la imagen en el segundo JLabel


    }
    public void colocarImagenes(){
        
    }
    public void iniciarSistema() {
        for (Horno horno : listaHornos) {
            horno.start();
        }
        for (Repostero repostero : listaReposteros) {
            repostero.start();
        }
        for (Empaquetador empaquetador : listaEmpaquetadores) {
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
        almacenNGalletastxt.setEditable(false);
    }

    public void update() {
        updateCafetera();
        updateReposteros();
        updateHornos();
        updateEmpaquetadores();
        updateAlmacen();
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

        } catch (Exception e) {
        }
    }

    public void updateReposteros() {
        JTextField[] reposteroTextFields = {r1estadotxt, r2estadotxt, r3estadotxt, r4estadotxt, r5estadotxt};
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
                if (h.getEstado() == "inactivo") {
                    hprogressBars[index].setValue(0);
                }
            });
        }
        // Cerrar el ExecutorService cuando termine
        executor.shutdown();
    }

    public void updateEmpaquetadores() {
        JButton[] e1lotes = {jButton5, jButton6, jButton4, jButton2, jButton3};
        JButton[] e2lotes = {jButton10, jButton11, jButton9, jButton7, jButton8};
        JButton[] e3lotes = {jButton13, jButton14, jButton12, jButton15, jButton16};
        JButton[][] lotes = {e1lotes, e2lotes, e3lotes};
        JTextField[] eEstadosTextFields = {e1estado, e2estado, e3estado};

        ExecutorService executor = Executors.newFixedThreadPool(listaEmpaquetadores.size());
        for (int i = 0; i < listaEmpaquetadores.size(); i++) {
            int index = i;
            executor.submit(() -> {
                Empaquetador e = listaEmpaquetadores.get(index);

                // Actualizar estado y botones
                SwingUtilities.invokeLater(() -> {
                    // Actualizar estado
                    eEstadosTextFields[index].setText(e.getEstado());

                    // Actualizar botones para lotes
                    for (int j = 0; j < lotes[index].length; j++) {
                        if (j < e.getnLotes()) {
                            lotes[index][j].setBackground(Color.RED); // Lote recogido
                        } else {
                            lotes[index][j].setBackground(Color.GRAY); // Lote no recogido
                        }
                    }
                });
            });
        }
        executor.shutdown();
    }
    public void updateAlmacen(){
        almacenNGalletastxt.setText(almacen.getnGalletasDentro()+"/"+almacen.getCapacidad());
    }
    public void comer(int n){
        almacen.comer(n);
        
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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        comerButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        almacenNGalletastxt = new javax.swing.JTextField();
        nGalletasComidastxt = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(224, 200, 176));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(991, 765));
        setMinimumSize(new java.awt.Dimension(991, 765));
        setResizable(false);

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

        comerButton.setBackground(new java.awt.Color(175, 126, 113));
        comerButton.setText("COMER (100)");
        comerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comerButtonActionPerformed(evt);
            }
        });

        jLabel16.setText("Almacén");

        nGalletasComidastxt.setText("galletas comidas: 0");

        jLabel17.setText("jLabel17");

        jLabel18.setText("jLabel18");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(almacenNGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(nGalletasComidastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cReposteroUsandotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(r1estadotxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(101, 101, 101)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(r2estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(71, 71, 71)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(r3estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(340, 340, 340)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(h3progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(h3nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(h3estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(r5estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(e3estado, javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                                    .addComponent(cReposterosColatxt, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(e1estado, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(h1progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(h1nGalletastxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(h1estadotxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(357, 357, 357)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(h2progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(e2estado, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(h2nGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(h2estadotxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(183, 183, 183)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(r4estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButton6});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton11, jButton7, jButton8, jButton9});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton12, jButton13, jButton14, jButton15, jButton16});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel6)
                                                .addGap(18, 18, 18)
                                                .addComponent(r5estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(77, 77, 77)))
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
                                                    .addComponent(jLabel5))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(r1estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(r3estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(r4estadotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel15))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(344, Short.MAX_VALUE)
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
                                    .addComponent(e3estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(almacenNGalletastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nGalletasComidastxt))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addGap(39, 39, 39))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButton6});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton10, jButton11, jButton7, jButton8, jButton9});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton12, jButton13, jButton14, jButton15, jButton16});

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

    private void comerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comerButtonActionPerformed
        //Si cuando le das a comer se esta introduciendo un paquete hay una espera ya que el empaquetador esta en sleep
// Cambiar el color del botón a naranja
    comerButton.setBackground(Color.ORANGE);
    comerButton.setEnabled(false); // Deshabilitar el botón mientras esperamos

    // Ejecutar la operación de comer en un hilo de fondo
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() throws Exception {
            // Llamar al método comer que realiza la acción
            nGalletasComidas+=almacen.comer(100);
            nGalletasComidastxt.setText("galletas comidas: "+ nGalletasComidas);
            return null;
        }

        @Override
        protected void done() {
            try {
                // Después de que la operación de "comer" ha terminado:
                // Actualizar el valor de galletas en el almacén
                updateAlmacen();
                
                // Volver a poner el color original del botón
                comerButton.setBackground(null); // O el color original que tenía
                

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Volver a habilitar el botón después de que la operación termine
                comerButton.setEnabled(true);
            }
        }
        };
        worker.execute();
        
    }//GEN-LAST:event_comerButtonActionPerformed

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
    private javax.swing.JTextField almacenNGalletastxt;
    private javax.swing.JTextField cReposteroUsandotxt;
    private javax.swing.JTextField cReposterosColatxt;
    private javax.swing.JButton comerButton;
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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel nGalletasComidastxt;
    private javax.swing.JTextField r1estadotxt;
    private javax.swing.JTextField r2estadotxt;
    private javax.swing.JTextField r3estadotxt;
    private javax.swing.JTextField r4estadotxt;
    private javax.swing.JTextField r5estadotxt;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        System.out.println("--------------------------------COMIENZA EL RUN-------------------------------------");
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
