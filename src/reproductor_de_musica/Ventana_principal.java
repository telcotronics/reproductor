package reproductor_de_musica;

import Objetos.Info_reproduccion;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import control.Comunicacion_Arduino;
import control.Comunicacion_ArduinoPHtek;
import control.Monitor_control;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import video.PanelYouTube;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.monitor.Monitor;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import jssc.SerialPortException;
import servidorWeb.ServidorHttp;

public class Ventana_principal extends javax.swing.JFrame {

    private lista list = new lista();
    private nodo actual = null;
    private final Zplayer audioPlayer;
    private Short x = 0;
    private DefaultListModel lista_modelo = new DefaultListModel();
    private String ultimaLista = "vacio";
    private boolean cambios = false;
    protected boolean detenido = false;
    
    Comunicacion_Arduino com = new Comunicacion_Arduino();
    //Comunicacion_ArduinoPHtek com2 = new Comunicacion_ArduinoPHtek();
    
    String urlVideo = "https://www.youtube.com/watch?v=MM0lWsvieaE";
    DefaultListModel l;
    String temaActual;

    public Ventana_principal() {
        setTitle("Reproductor de musica mp3");
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconos/icono.png"));
        setIconImage(icon);
        initComponents();
        setLocationRelativeTo(null);
        nombre_can.setEditable(false);
        jSlider1.setEnabled(false);
        jSlider2.setEnabled(false);

        lista_can.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList lista = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = lista.locationToIndex(evt.getPoint());
                    if (index != -1) {
                        actual = list.get_cancion(index);
                        x = 0;
                        playActionPerformed(null);
                    }
                }
            }
        });

        try {
            BufferedReader tec = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\config"));
            String aux = tec.readLine();
            if (aux.equals("Si")) {
                aux = tec.readLine();
                if (!aux.equals("vacio")) {
                    cargarLista(aux);
                }
            } else {
                cargarListaInicio.setSelected(false);
            }
        } catch (Exception e) {
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (!list.IsEmpety() && cambios) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (ultimaLista.equals("vacio")) {
                            ultimaLista = crearArchivoLista();
                        }
                        if (ultimaLista == null) {
                            ultimaLista = "vacio";
                        } else {
                            guardarLista(ultimaLista);
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(
                            new FileWriter(System.getProperty("user.dir") + "\\config"));
                    if (cargarListaInicio.isSelected()) {
                        bw.write("Si\r\n");
                        bw.write(ultimaLista + "\r\n");
                    } else {
                        bw.write("No\r\n");
                    }
                    bw.close();
                } catch (Exception e) {
                }
                System.exit(0);
            }
        });
        audioPlayer = new Zplayer(this);
    }

    public void cargarLista(String ruta) {
        try {
            FileInputStream fis = new FileInputStream(new File(ruta));
            BufferedReader tec = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String input[];
            tec.readLine();

            while (tec.ready()) {
                input = tec.readLine().split("<");
                System.out.println(input[0] + " , " + input[1]);
                list.insertar(input[0], input[1]);
                lista_modelo.addElement(input[0]);
            }
            ultimaLista = ruta;
            cambios = false;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error\nal cargar la lista!!!", "alerta", 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error!!!", "alerta", 1);
        }
        lista_can.setModel(lista_modelo);
    }

    public void guardarLista(String dir) {
        try {
            BufferedWriter tec = new BufferedWriter(new FileWriter(dir));
            tec.write("\r\n");

            nodo aux = list.first;
            while (aux != null) {
                tec.append(aux.nombre + "<" + aux.direccion + "\r\n");
                aux = aux.siguiente;
            }

            tec.close();
            cambios = false;
        } catch (IOException e) {
            System.out.println("reproductor_de_musica.ventana_principal.guardarLista()\n"+e.getMessage());
        }
    }

    public String crearArchivoLista() {
        String n = JOptionPane.showInputDialog("digite el nombre de la lista");
        if (n == null || n.isEmpty()) {
            return null;
        }
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = chooser.showOpenDialog(this);
        File ruta;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            ruta = chooser.getSelectedFile();
        } else {
            return null;
        }
        File save = new File(ruta.getAbsolutePath() + "\\" + n + ".lis");
        if (save.exists()) {
            save.delete();
        }
        return save.getAbsolutePath();
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        detener = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        agregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_can = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        tipo_reproduccion = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        nombre_can = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        jSlider2 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        siguiente = new javax.swing.JButton();
        play = new javax.swing.JButton();
        anterior = new javax.swing.JButton();
        info_spectro = new javax.swing.JLabel();
        info_cancion = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        bt_controlWeb = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        tbt_control = new javax.swing.JToggleButton();
        panel_control = new javax.swing.JPanel();
        tbt_ctrl1 = new javax.swing.JToggleButton();
        tbt_ctrl2 = new javax.swing.JToggleButton();
        tbt_ctrl3 = new javax.swing.JToggleButton();
        tbt_ctrl4 = new javax.swing.JToggleButton();
        tbt_ctrl5 = new javax.swing.JToggleButton();
        tbt_ctrl6 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_consola = new javax.swing.JTextArea();
        panel_equalizador = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        cb_puerto = new javax.swing.JComboBox<>();
        bt_conexionSerial = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        slidereq = new javax.swing.JSlider();
        slidereq1 = new javax.swing.JSlider();
        slidereq2 = new javax.swing.JSlider();
        slidereq3 = new javax.swing.JSlider();
        slidereq4 = new javax.swing.JSlider();
        slidereq5 = new javax.swing.JSlider();
        slidereq6 = new javax.swing.JSlider();
        slidereq7 = new javax.swing.JSlider();
        slidereq8 = new javax.swing.JSlider();
        slidereq9 = new javax.swing.JSlider();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        cargarListaInicio = new javax.swing.JCheckBoxMenuItem();
        guardar_lista = new javax.swing.JMenuItem();
        cargar_lista = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(198, 210, 223));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista Reproduccion"));

        detener.setText("Detener reproducción");
        detener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detenerActionPerformed(evt);
            }
        });

        eliminar.setText("Quitar canción actual");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        agregar.setText("Agregar canción");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lista_can);

        jLabel1.setText("tipo de reproduccion");

        tipo_reproduccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "normal", "inversa", "aleatoria" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(detener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tipo_reproduccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(detener)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipo_reproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(181, 190, 203));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Control Reproduccion"));

        nombre_can.setText("...");
        nombre_can.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jPanel5.setBackground(new java.awt.Color(0, 255, 36));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel3.setText("- Volumen +");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 95, -1, -1));

        jSlider1.setBackground(new java.awt.Color(0, 255, 36));
        jSlider1.setValue(100);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jPanel5.add(jSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 105, 177, 18));

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel4.setText("Der- Balance -Izq");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(255, 95, -1, -1));

        jSlider2.setBackground(new java.awt.Color(0, 255, 36));
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });
        jPanel5.add(jSlider2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 105, 177, 18));

        jLabel2.setFont(new java.awt.Font("LCDMono", 1, 24)); // NOI18N
        jLabel2.setText("00:00:00");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/musica2.gif"))); // NOI18N
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/siguiente.png"))); // NOI18N
        siguiente.setToolTipText("");
        siguiente.setBorderPainted(false);
        siguiente.setContentAreaFilled(false);
        siguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });
        jPanel5.add(siguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play.png"))); // NOI18N
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });
        jPanel5.add(play, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/anterior.png"))); // NOI18N
        anterior.setBorderPainted(false);
        anterior.setContentAreaFilled(false);
        anterior.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anteriorActionPerformed(evt);
            }
        });
        jPanel5.add(anterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));
        jPanel5.add(info_spectro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 515, 120));

        info_cancion.setFont(new java.awt.Font("LCDMono", 0, 16)); // NOI18N
        info_cancion.setText("Tema:");
        jPanel5.add(info_cancion, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 8, 397, -1));

        jButton1.setText("Video");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Config");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        bt_controlWeb.setText("App Web");
        bt_controlWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_controlWebActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Equalizador");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        tbt_control.setText("Control");
        tbt_control.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_controlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_controlWeb, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbt_control, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nombre_can)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)))
                .addGap(1, 1, 1))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(nombre_can, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_controlWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_control, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        panel_control.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        tbt_ctrl1.setText("P1");
        tbt_ctrl1.setEnabled(false);
        tbt_ctrl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl1ActionPerformed(evt);
            }
        });

        tbt_ctrl2.setText("P2");
        tbt_ctrl2.setEnabled(false);
        tbt_ctrl2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl2ActionPerformed(evt);
            }
        });

        tbt_ctrl3.setText("P3");
        tbt_ctrl3.setEnabled(false);
        tbt_ctrl3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl3ActionPerformed(evt);
            }
        });

        tbt_ctrl4.setText("P4");
        tbt_ctrl4.setEnabled(false);
        tbt_ctrl4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl4ActionPerformed(evt);
            }
        });

        tbt_ctrl5.setText("P5");
        tbt_ctrl5.setEnabled(false);
        tbt_ctrl5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl5ActionPerformed(evt);
            }
        });

        tbt_ctrl6.setText("P6");
        tbt_ctrl6.setEnabled(false);
        tbt_ctrl6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbt_ctrl6ActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(1, 1, 1));
        jScrollPane2.setForeground(new java.awt.Color(15, 255, 0));

        txt_consola.setBackground(new java.awt.Color(1, 1, 1));
        txt_consola.setColumns(20);
        txt_consola.setForeground(new java.awt.Color(32, 255, 0));
        txt_consola.setLineWrap(true);
        txt_consola.setRows(5);
        txt_consola.setText("Consola:");
        jScrollPane2.setViewportView(txt_consola);

        javax.swing.GroupLayout panel_controlLayout = new javax.swing.GroupLayout(panel_control);
        panel_control.setLayout(panel_controlLayout);
        panel_controlLayout.setHorizontalGroup(
            panel_controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_controlLayout.createSequentialGroup()
                .addComponent(tbt_ctrl1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbt_ctrl2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbt_ctrl3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbt_ctrl4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbt_ctrl5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbt_ctrl6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_controlLayout.setVerticalGroup(
            panel_controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_controlLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbt_ctrl4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_ctrl5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_ctrl6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_ctrl3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_ctrl1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbt_ctrl2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(203, 212, 221));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Control Volumen"));

        bt_conexionSerial.setText("Conectar");
        bt_conexionSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_conexionSerialActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(cb_puerto, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bt_conexionSerial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(cb_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_conexionSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(187, 203, 215));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Equalizador"));

        slidereq.setMinimum(-100);
        slidereq.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq.setValue(0);
        slidereq.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        slidereq.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereqStateChanged(evt);
            }
        });

        slidereq1.setMinimum(-100);
        slidereq1.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq1.setValue(0);
        slidereq1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq1StateChanged(evt);
            }
        });

        slidereq2.setMinimum(-100);
        slidereq2.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq2.setValue(0);
        slidereq2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq2StateChanged(evt);
            }
        });

        slidereq3.setMinimum(-100);
        slidereq3.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq3.setValue(0);
        slidereq3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq3StateChanged(evt);
            }
        });

        slidereq4.setMinimum(-100);
        slidereq4.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq4.setValue(0);
        slidereq4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq4StateChanged(evt);
            }
        });

        slidereq5.setMinimum(-100);
        slidereq5.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq5.setValue(0);
        slidereq5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq5StateChanged(evt);
            }
        });

        slidereq6.setMinimum(-100);
        slidereq6.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq6.setValue(0);
        slidereq6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq6StateChanged(evt);
            }
        });

        slidereq7.setMinimum(-100);
        slidereq7.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq7.setValue(0);
        slidereq7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq7StateChanged(evt);
            }
        });

        slidereq8.setMinimum(-100);
        slidereq8.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq8.setValue(0);
        slidereq8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq8StateChanged(evt);
            }
        });

        slidereq9.setMinimum(-100);
        slidereq9.setOrientation(javax.swing.JSlider.VERTICAL);
        slidereq9.setValue(0);
        slidereq9.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slidereq9StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(slidereq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slidereq9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slidereq3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slidereq2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_equalizadorLayout = new javax.swing.GroupLayout(panel_equalizador);
        panel_equalizador.setLayout(panel_equalizadorLayout);
        panel_equalizadorLayout.setHorizontalGroup(
            panel_equalizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_equalizadorLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_equalizadorLayout.setVerticalGroup(
            panel_equalizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_equalizadorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("Archivo");

        cargarListaInicio.setSelected(true);
        cargarListaInicio.setText("Cargar ultima lista al abrir");
        jMenu1.add(cargarListaInicio);

        guardar_lista.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        guardar_lista.setText("Guardar lista");
        guardar_lista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_listaActionPerformed(evt);
            }
        });
        jMenu1.add(guardar_lista);

        cargar_lista.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        cargar_lista.setText("Cargar lista");
        cargar_lista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargar_listaActionPerformed(evt);
            }
        });
        jMenu1.add(cargar_lista);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem7.setText("Acerca de");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setText("agregar cancion");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem5.setText("eliminar cancion");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem6.setText("ayuda");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_control, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_equalizador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_equalizador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3", "mp3"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File files[] = fileChooser.getSelectedFiles();
            boolean noMp3 = false, repetidos = false;
            cambios = true;

            for (File file : files) {
                String name = file.getName();
                if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".mp3")) {
                    noMp3 = true;
                    continue;
                }
                if (list.buscar(file.getName(), file.getPath())) {
                    repetidos = true;
                    continue;
                }
                list.insertar(file.getName(), file.getPath());
                System.out.println(file.getName());
                System.out.println(file.getPath());
                lista_modelo.addElement(file.getName());
                lista_can.setModel(lista_modelo);
            }
            if (noMp3) {
                JOptionPane.showMessageDialog(null, "Se encontro archivo(s) no mp3", "alerta", 0);
            }
            if (repetidos) {
                JOptionPane.showMessageDialog(null, "Se encontraron repetidos", "alerta", 0);
            }
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        detenido = true;
        if (list.IsEmpety()) {
            JOptionPane.showMessageDialog(null, "no hay canciones", "alerta", 1);
        } else {
            if (actual == null) {
                actual = list.first;
            }
            try {
                if (x == 0) {
                    audioPlayer.control.open(new URL("file:///" + actual.direccion));
                    audioPlayer.control.play();
                    System.out.println("se inicia");
                    nombre_can.setText(actual.nombre);
                    info_cancion.setText(actual.nombre);
                    temaActual = actual.nombre;
                    jSlider1.setEnabled(true);
                    jSlider2.setEnabled(true);
                    x = 1;
                    play.setIcon(new ImageIcon(getClass().getResource("/iconos/pausa.png")));
                    activarSpectro(true);
                } else {
                    if (x == 1) {
                        audioPlayer.control.pause();
                        activarSpectro(false);
                        System.out.println("se pausa!!!");
                        x = 2;
                        play.setIcon(new ImageIcon(getClass().getResource("/iconos/play.png")));
                    } else {
                        audioPlayer.control.resume();
                        activarSpectro(true);
                        System.out.println("se continua!!!");
                        x = 1;
                        play.setIcon(new ImageIcon(getClass().getResource("/iconos/pausa.png")));
                    }
                }
            } catch (BasicPlayerException ex) {
                JOptionPane.showMessageDialog(null, "error al abrir\nla cancion!!!", "alerta", 1);
                x = 0;
            } catch (MalformedURLException ex) {
                Logger.getLogger(Ventana_principal.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "error al abrir la direccion\nde la cancion!!!", "alerta", 1);
                x = 0;
            }
        }
        detenido = false;
    }//GEN-LAST:event_playActionPerformed

    private void detenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detenerActionPerformed
        detenido = true;
        play.setIcon(new ImageIcon(getClass().getResource("/iconos/play.png")));
        try {
            audioPlayer.control.stop();
            x = 0;
            jSlider1.setEnabled(false);
            jSlider2.setEnabled(false);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Ventana_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        detenido = false;
    }//GEN-LAST:event_detenerActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        try {
            audioPlayer.control.setGain((double) jSlider1.getValue() / 100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Ventana_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider1StateChanged

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        try {
            audioPlayer.control.setPan((float) jSlider2.getValue() / 100);
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Ventana_principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider2StateChanged

    private void slidereqStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereqStateChanged
        audioPlayer.eq[0] = (float) slidereq.getValue() / 100;
    }//GEN-LAST:event_slidereqStateChanged

    private void slidereq1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq1StateChanged
        audioPlayer.eq[1] = (float) slidereq1.getValue() / 100;
    }//GEN-LAST:event_slidereq1StateChanged

    private void slidereq2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq2StateChanged
        audioPlayer.eq[2] = (float) slidereq2.getValue() / 100;
    }//GEN-LAST:event_slidereq2StateChanged

    private void slidereq3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq3StateChanged
        audioPlayer.eq[3] = (float) slidereq3.getValue() / 100;
    }//GEN-LAST:event_slidereq3StateChanged

    private void slidereq4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq4StateChanged
        audioPlayer.eq[4] = (float) slidereq4.getValue() / 100;
    }//GEN-LAST:event_slidereq4StateChanged

    private void slidereq5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq5StateChanged
        audioPlayer.eq[5] = (float) slidereq5.getValue() / 100;
    }//GEN-LAST:event_slidereq5StateChanged

    private void slidereq6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq6StateChanged
        audioPlayer.eq[6] = (float) slidereq6.getValue() / 100;
    }//GEN-LAST:event_slidereq6StateChanged

    private void slidereq7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq7StateChanged
        audioPlayer.eq[7] = (float) slidereq7.getValue() / 100;
    }//GEN-LAST:event_slidereq7StateChanged

    private void slidereq8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq8StateChanged
        audioPlayer.eq[8] = (float) slidereq8.getValue() / 100;
    }//GEN-LAST:event_slidereq8StateChanged

    private void slidereq9StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slidereq9StateChanged
        audioPlayer.eq[9] = (float) slidereq9.getValue() / 100;
    }//GEN-LAST:event_slidereq9StateChanged

    protected void eventoSiguiente(){
        siguienteActionPerformed(null);
    }
    
    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed
        if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            case 1:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        playActionPerformed(evt);
    }//GEN-LAST:event_anteriorActionPerformed

    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed
        if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            case 1:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        playActionPerformed(evt);
    }//GEN-LAST:event_siguienteActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if (list.IsEmpety()) {
            return;
        }
        int q = list.index(actual);
        if (q == -1) {
            JOptionPane.showMessageDialog(null, "ha ocurrido un\nerror inesperado!!!", "alerta", 1);
        } else {
            lista_modelo.remove(q);
            list.borrar(actual);
            detenerActionPerformed(evt);
            if (list.IsEmpety()) {
                actual = null;
                nombre_can.setText("...");
            } else {
                if (list.tam == 1) {
                    actual = list.first;
                } else {
                    if (actual.siguiente == null) {
                        actual = actual.anterior;
                    } else {
                        actual = actual.siguiente;
                    }
                }
                nombre_can.setText(actual.nombre);
            }
        }
        cambios = true;
    }//GEN-LAST:event_eliminarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        agregarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        eliminarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JOptionPane.showMessageDialog(null, "verifique que el nombre de las canciones no tengan\ncaracteres especiales como tildes o apostrofos", "alerta", 1);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        JOptionPane.showMessageDialog(null, "Proyecto final, programacion I\n(estructuras de datos)");
        JOptionPane.showMessageDialog(null, "Con ajustes posteriores");
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void cargar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargar_listaActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("archivo lis", "lis"));
        int seleccion = chooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            detenerActionPerformed(evt);
            list.clear();
            lista_modelo.clear();
            actual = list.first;

            String name = chooser.getSelectedFile().getName();
            if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".lis")) {
                JOptionPane.showMessageDialog(null, "no es una lista", "alerta", 0);
                return;
            }
            cargarLista(chooser.getSelectedFile().getPath());
        }
    }//GEN-LAST:event_cargar_listaActionPerformed

    private void guardar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_listaActionPerformed
        if (list.IsEmpety()) {
            JOptionPane.showMessageDialog(null, "no hay canciones!!!", "alerta", 1);
            return;
        }
        guardarLista(crearArchivoLista());
    }//GEN-LAST:event_guardar_listaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mon.panelYt_init(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void bt_controlWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_controlWebActionPerformed
        mon.panelYt_full(true);
    }//GEN-LAST:event_bt_controlWebActionPerformed

    Monitor_control mon;
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        mon = new Monitor_control();
        mon.init_panel();
        String msg = mon.initServerWeb(this);
        txt_consola.append("\n"+msg);
        initParametrosSerial();
    }//GEN-LAST:event_formWindowOpened

    private void tbt_ctrl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl1ActionPerformed
        if(tbt_ctrl1.isSelected()){
            mon.envioDato("ctrl_p1=1");
        }else{
            mon.envioDato("ctrl_p1=0");
        }
    }//GEN-LAST:event_tbt_ctrl1ActionPerformed

    private void tbt_ctrl2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl2ActionPerformed
        if(tbt_ctrl2.isSelected()){
            mon.envioDato("ctrl_p2=1");
        }else{
            mon.envioDato("ctrl_p2=0");
        }
        //com2.verificaPuertos();
    }//GEN-LAST:event_tbt_ctrl2ActionPerformed

    private void tbt_ctrl3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl3ActionPerformed
        if(tbt_ctrl3.isSelected()){
            mon.envioDato("ctrl_p3=1");
        }else{
            mon.envioDato("ctrl_p3=0");
        }
    }//GEN-LAST:event_tbt_ctrl3ActionPerformed

    private void tbt_ctrl4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl4ActionPerformed
        if(tbt_ctrl4.isSelected()){
            mon.envioDato("ctrl_p4=1");
        }else{
            mon.envioDato("ctrl_p4=0");
        }
    }//GEN-LAST:event_tbt_ctrl4ActionPerformed

    private void tbt_ctrl5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl5ActionPerformed
        if(tbt_ctrl5.isSelected()){
            //mon.envioDato("E");
            mon.envioDato("ctrl_p5=1");
        }else{
            mon.envioDato("ctrl_p5=0");
        }
    }//GEN-LAST:event_tbt_ctrl5ActionPerformed

    private void tbt_ctrl6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_ctrl6ActionPerformed
        if(tbt_ctrl6.isSelected()){
            mon.envioDato("ctrl_p6=1");
        }else{
            mon.envioDato("ctrl_p6=0");
        }
    }//GEN-LAST:event_tbt_ctrl6ActionPerformed

    private void tbt_controlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbt_controlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbt_controlActionPerformed

    private void bt_conexionSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_conexionSerialActionPerformed
        if(bt_conexionSerial.getText().equals("Conectar")){
            if(cb_puerto.getSelectedItem()!=null){
                //initSerial();
                mon.envioDato("ctrl_iniSerial");
                bt_conexionSerial.setText("Desconectar");
                habilita(true);
            }else{
                JOptionPane.showMessageDialog(rootPane, "Selecione un Puerto");
                cb_puerto.requestFocus();
            }
        }else{
            mon.envioDato("ctrl_finSerial");
            bt_conexionSerial.setText("Conectar");
            mon.cierraConexionSerial();
            habilita(false);
        }
    }//GEN-LAST:event_bt_conexionSerialActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        mon.panelYt_verPnl(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        mon.panelYt_full(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String p = cb_puerto.getSelectedItem().toString();
        mon.envioDato("ctrl_port="+p);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void stadoRep(){
        Info_reproduccion info = new Info_reproduccion();
        info.setTema(temaActual);
    }
    
    private void activarSpectro(boolean std){
        if(std){
            URL dirIcon = getClass().getClassLoader().getResource("iconos/spectro.gif");
            ImageIcon ico = new ImageIcon(dirIcon);
            ImageIcon tmpIcon = new ImageIcon(ico.getImage().getScaledInstance(550, 130, Image.SCALE_DEFAULT));
            info_spectro.setIcon(tmpIcon);
            info_spectro.setText("");
        }else{
            info_spectro.setIcon(null);
        }
    }
    private ImageIcon ajustarImagen(String ico){
        ImageIcon tmpIconAux = new ImageIcon(ico);
        //Escalar Imagen
        ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT));
        return tmpIcon;
    }
    
    
    //****COMUNUICACION SERIAL*****//
    public void setError(String error){
        txt_consola.append(error);
    }
    
    
    private void initParametrosSerial(){
        txt_consola.append("");
        //com.buscarPuertos();
        
        for(int i=0;i<mon.ListaPuertos().size();i++){
            cb_puerto.addItem(mon.ListaPuertos().get(i).toString());
        }
        
    }
    private void initSerial(){
        if(cb_puerto.getSelectedItem()!=null){
            String puerto = cb_puerto.getSelectedItem().toString();
            //com2.initComunicacion(puerto, 9600, com2.getListener());
            mon.envioDato(puerto);
        }
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_principal().setVisible(true);
            }
        });
    }
    
    private void habilita(boolean std){
        tbt_ctrl1.setEnabled(std);
        tbt_ctrl2.setEnabled(std);
        tbt_ctrl3.setEnabled(std);
        tbt_ctrl4.setEnabled(std);
        tbt_ctrl5.setEnabled(std);
        tbt_ctrl6.setEnabled(std);
        //tbt_ctrl7.setEnabled(std);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar;
    private javax.swing.JButton anterior;
    private javax.swing.JButton bt_conexionSerial;
    private javax.swing.JButton bt_controlWeb;
    private javax.swing.JCheckBoxMenuItem cargarListaInicio;
    private javax.swing.JMenuItem cargar_lista;
    private javax.swing.JComboBox<String> cb_puerto;
    private javax.swing.JButton detener;
    private javax.swing.JButton eliminar;
    private javax.swing.JMenuItem guardar_lista;
    private javax.swing.JLabel info_cancion;
    private javax.swing.JLabel info_spectro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JList lista_can;
    private javax.swing.JTextField nombre_can;
    private javax.swing.JPanel panel_control;
    private javax.swing.JPanel panel_equalizador;
    private javax.swing.JButton play;
    private javax.swing.JButton siguiente;
    private javax.swing.JSlider slidereq;
    private javax.swing.JSlider slidereq1;
    private javax.swing.JSlider slidereq2;
    private javax.swing.JSlider slidereq3;
    private javax.swing.JSlider slidereq4;
    private javax.swing.JSlider slidereq5;
    private javax.swing.JSlider slidereq6;
    private javax.swing.JSlider slidereq7;
    private javax.swing.JSlider slidereq8;
    private javax.swing.JSlider slidereq9;
    private javax.swing.JToggleButton tbt_control;
    private javax.swing.JToggleButton tbt_ctrl1;
    private javax.swing.JToggleButton tbt_ctrl2;
    private javax.swing.JToggleButton tbt_ctrl3;
    private javax.swing.JToggleButton tbt_ctrl4;
    private javax.swing.JToggleButton tbt_ctrl5;
    private javax.swing.JToggleButton tbt_ctrl6;
    private javax.swing.JComboBox tipo_reproduccion;
    public static javax.swing.JTextArea txt_consola;
    // End of variables declaration//GEN-END:variables
}
