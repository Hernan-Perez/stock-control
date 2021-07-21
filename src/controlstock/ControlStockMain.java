/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.awt.Desktop;
import java.awt.Font;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author hernan
 */
public final class ControlStockMain extends javax.swing.JFrame
{
    public static int SIGUIENTE_CODIGO = 1;
    
    private static final String NOMBRE_DB = "db.DB";
    //private static final String NOMBRE_BACKUP_DEFAULT = ".BackupStock00";
    private static final int HASH_BACKUP = 915487632;   
    private static final String TUTORIAL_LINK_YT = "https://youtu.be/23kUevvWyTw";
    
    private final JFileChooser fc;
    //HASH_BACKUP - cuando se guarda un backup, 
    //esto se guarda al principio de todo, 
    //y se usa al cargar un backup para comprobar que el archivo 
    //seleccionado sea realmente un backup de este programa
    
    /**
     * Creates new form ControlStock
     */
    
    private List<Producto> productos;

    public ControlStockMain()
    {
        initComponents();
        setLocationRelativeTo(null);
        
        productos = new ArrayList<>();
        fc = new JFileChooser();
        //prueba
        /*Producto aux = new Producto();
        for (int i = 0; i < 20; i++)
        {
            aux = new Producto();
            aux.setData("PRODUCTO " + i, "DESCRIPCION " + i , 5);
            aux.setStock(i);
            productos.add(aux);
        }
        
        for (int i = 0; i < 20; i++)
        {
            aux = new Producto();
            aux.setData("CAJA " + i, "DESCRIPCION CAJA " + i , 5);
            aux.setStock(i);
            productos.add(aux);
        }
        
        for (int i = 0; i < 12; i++)
        {
            aux = new Producto();
            aux.setData("CAJON " + i, "DESCRIPCION CAJON " + i , 5);
            aux.setStock(i);
            productos.add(aux);
        }
        
        for (int i = 0; i < 15; i++)
        {
            aux = new Producto();
            aux.setData("MADERA " + i, "DESCRIPCION MADERA " + i , 5);
            aux.setStock(i);
            productos.add(aux);
        }*/
        
        CargarBaseDeDatos();
    }
    
    /*
    ESTRUCTURA BASE DE DATOS:
    
    siguiente_codigo INT
    cantidad_productos INT
    
    *********para cada producto***********
    codigo INT
    nombre_size INT
    nombre CHAR[]
    descripcion_size INT
    descripcion CHAR[]
    stock INT
    stock_minimo INT
    **************************************
    
    CADA VEZ QUE SE GUARDA SE REEMPLAZA EL ARCHIVO COMPLETAMENTE
    */
    
    //se llama desde CargarBaseDeDatos() si el archivo no existe
    protected void CrearBaseDeDatosDefault()
    {
        try 
        (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(NOMBRE_DB))) {
 
            outputStream.writeInt(1);
            outputStream.writeInt(0);
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR BASE DE DATOS.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
    }
    
    protected void GuardarBaseDeDatos()
    {
        try 
        (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(NOMBRE_DB))) {
 
            outputStream.writeInt(SIGUIENTE_CODIGO);
            outputStream.writeInt(productos.size());
            Producto aux;
            for (int i = 0; i < productos.size(); i++)
            {
                aux = productos.get(i);
                outputStream.writeInt(aux.getCod());
                outputStream.writeInt(aux.getNombre().length());
                outputStream.writeChars(aux.getNombre());
                outputStream.writeInt(aux.getDescripcion().length());
                outputStream.writeChars(aux.getDescripcion());
                outputStream.writeInt(aux.getStock());
                outputStream.writeInt(aux.getValorStockMin());
            }
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR BASE DE DATOS.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
    }
    
    protected void CargarBaseDeDatos()
    {
        if (Files.notExists(Paths.get(NOMBRE_DB)))
        {
            JOptionPane.showMessageDialog(this, "No se encontró una base de datos, se generó una nueva.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            CrearBaseDeDatosDefault();
            return;
        }
        
        try 
            (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(NOMBRE_DB)))
        {
            Producto aux;
            productos = new ArrayList<>();

            

            SIGUIENTE_CODIGO = inputStream.readInt(); //siguiente codigo
            int cantidad = inputStream.readInt(); //cantidad de productos a cargar
            int cant_aux;
            char[] char_arr;
            for (int i = 0; i < cantidad; i++)
            {
                //CODIGO
                aux = new Producto(inputStream.readInt()); 
                
                //NOMBRE SIZE
                cant_aux = inputStream.readInt();
                //NOMBRE
                char_arr = new char[cant_aux];
                for (int ii = 0; ii < cant_aux; ii++)
                {
                    char_arr[ii] = inputStream.readChar();
                }
                aux.setNombre(String.valueOf(char_arr));
                
                //DESC SIZE
                cant_aux = inputStream.readInt();
                //DESC
                char_arr = new char[cant_aux];
                for (int ii = 0; ii < cant_aux; ii++)
                {
                    char_arr[ii] = inputStream.readChar();
                }
                aux.setDescripcion(String.valueOf(char_arr));
                
                //STOCK
                aux.setStock(inputStream.readInt());
                //STOCK MIN
                aux.setValorStockMin(inputStream.readInt());
                productos.add(aux);
            }
 
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR BASE DE DATOS.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
    }
    
    protected void GuardarBackup()
    {
        int res = fc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        
        File f = fc.getSelectedFile();
        
        if (f.exists())
        {
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, "El archivo ya existe, está seguro que desea reemplazarlo?", "Reemplazar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (showConfirmDialog != JOptionPane.YES_OPTION)
            {
                return;
            }
        }
        
        try 
        (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(f))) {
 
            outputStream.writeInt(HASH_BACKUP);
            outputStream.writeInt(SIGUIENTE_CODIGO);
            outputStream.writeInt(productos.size());
            Producto aux;
            for (int i = 0; i < productos.size(); i++)
            {
                aux = productos.get(i);
                outputStream.writeInt(aux.getCod());
                outputStream.writeInt(aux.getNombre().length());
                outputStream.writeChars(aux.getNombre());
                outputStream.writeInt(aux.getDescripcion().length());
                outputStream.writeChars(aux.getDescripcion());
                outputStream.writeInt(aux.getStock());
                outputStream.writeInt(aux.getValorStockMin());
            }
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR BACKUP.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
    }
    
    protected void CargarBackup()
    {
        int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Está seguro que desea cargar un backup?\nLos datos actuales de la base de datos se reemplazaran por los del backup elegido.", "Cargar backup", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (showConfirmDialog != JOptionPane.YES_OPTION)
        {
            return;
        }
        
        int res = fc.showOpenDialog(this);
        if (res != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        
        File f = fc.getSelectedFile();
        
        
        
        try 
            (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f)))
        {
            Producto aux;
            productos = new ArrayList<>();

            int hash = inputStream.readInt();
            if (hash != HASH_BACKUP)
            {
                throw new EOFException();
            }
            
            SIGUIENTE_CODIGO = inputStream.readInt(); //siguiente codigo
            int cantidad = inputStream.readInt(); //cantidad de productos a cargar
            int cant_aux;
            char[] char_arr;
            for (int i = 0; i < cantidad; i++)
            {
                //CODIGO
                aux = new Producto(inputStream.readInt()); 
                
                //NOMBRE SIZE
                cant_aux = inputStream.readInt();
                //NOMBRE
                char_arr = new char[cant_aux];
                for (int ii = 0; ii < cant_aux; ii++)
                {
                    char_arr[ii] = inputStream.readChar();
                }
                aux.setNombre(String.valueOf(char_arr));
                
                //DESC SIZE
                cant_aux = inputStream.readInt();
                //DESC
                char_arr = new char[cant_aux];
                for (int ii = 0; ii < cant_aux; ii++)
                {
                    char_arr[ii] = inputStream.readChar();
                }
                aux.setDescripcion(String.valueOf(char_arr));
                
                //STOCK
                aux.setStock(inputStream.readInt());
                //STOCK MIN
                aux.setValorStockMin(inputStream.readInt());
                productos.add(aux);
            }
            
            JOptionPane.showMessageDialog(this, "Backup cargado con exito!", "Exito", JOptionPane.INFORMATION_MESSAGE);
 
        } 
        catch (EOFException e)
        {
            JOptionPane.showMessageDialog(this, "Error: el backup elegido no es valido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(this, "ERROR AL CARGAR BACKUP.", "Error", JOptionPane.ERROR_MESSAGE);
            //return;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        tituloLabel = new javax.swing.JLabel();
        salirButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        verProductosButton = new javax.swing.JButton();
        generarReporteButton = new javax.swing.JButton();
        cargaLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        modificarProductosButton = new javax.swing.JButton();
        modificarStockButton = new javax.swing.JButton();
        controlLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        archivoMenu = new javax.swing.JMenu();
        salirMenuItem = new javax.swing.JMenuItem();
        backupMenu = new javax.swing.JMenu();
        crearBackupMenuItem = new javax.swing.JMenuItem();
        restaurarBackupMenuItem = new javax.swing.JMenuItem();
        ayudaMenu = new javax.swing.JMenu();
        verAyudaMenuItem = new javax.swing.JMenuItem();
        acercaDeMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Control De Stock");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        tituloLabel.setFont(new java.awt.Font("BankGothic Lt BT", 0, 36)); // NOI18N
        tituloLabel.setForeground(new java.awt.Color(255, 102, 0));
        tituloLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloLabel.setText("Control De Stock");

        salirButton.setBackground(new java.awt.Color(255, 51, 51));
        salirButton.setText("Salir");
        salirButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                salirButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        verProductosButton.setText("Listar productos");
        verProductosButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                verProductosButtonActionPerformed(evt);
            }
        });

        generarReporteButton.setText("Generar reporte");
        generarReporteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                generarReporteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(verProductosButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generarReporteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(verProductosButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(generarReporteButton))
        );

        cargaLabel.setFont(new java.awt.Font("BankGothic Lt BT", 0, 18)); // NOI18N
        cargaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cargaLabel.setText("Carga");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        modificarProductosButton.setText("Modificar productos");
        modificarProductosButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                modificarProductosButtonActionPerformed(evt);
            }
        });

        modificarStockButton.setText("Cargar stock");
        modificarStockButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                modificarStockButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modificarProductosButton, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(modificarStockButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(modificarProductosButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modificarStockButton))
        );

        controlLabel.setFont(new java.awt.Font("BankGothic Lt BT", 0, 18)); // NOI18N
        controlLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        controlLabel.setText("Control");

        archivoMenu.setText("Archivo");

        salirMenuItem.setText("Salir");
        salirMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                salirMenuItemActionPerformed(evt);
            }
        });
        archivoMenu.add(salirMenuItem);

        jMenuBar1.add(archivoMenu);

        backupMenu.setText("Backup");

        crearBackupMenuItem.setText("Crear backup");
        crearBackupMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                crearBackupMenuItemActionPerformed(evt);
            }
        });
        backupMenu.add(crearBackupMenuItem);

        restaurarBackupMenuItem.setText("Restaurar backup");
        restaurarBackupMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                restaurarBackupMenuItemActionPerformed(evt);
            }
        });
        backupMenu.add(restaurarBackupMenuItem);

        jMenuBar1.add(backupMenu);

        ayudaMenu.setText("Ayuda");

        verAyudaMenuItem.setText("Ver ayuda");
        verAyudaMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                verAyudaMenuItemActionPerformed(evt);
            }
        });
        ayudaMenu.add(verAyudaMenuItem);

        acercaDeMenuItem.setText("Acerca de");
        acercaDeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                acercaDeMenuItemActionPerformed(evt);
            }
        });
        ayudaMenu.add(acercaDeMenuItem);

        jMenuBar1.add(ayudaMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tituloLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(controlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cargaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(salirButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(tituloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(controlLabel)
                    .addComponent(cargaLabel))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 24, Short.MAX_VALUE)
                .addComponent(salirButton)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generarReporteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_generarReporteButtonActionPerformed
    {//GEN-HEADEREND:event_generarReporteButtonActionPerformed
        // TODO add your handling code here:
        this.setFocusable(false);
        this.setEnabled(false);
        new GenerarReporte(this, productos).setVisible(true);
    }//GEN-LAST:event_generarReporteButtonActionPerformed

    private void salirButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_salirButtonActionPerformed
    {//GEN-HEADEREND:event_salirButtonActionPerformed
        // TODO add your handling code here:
        Salir();
    }//GEN-LAST:event_salirButtonActionPerformed

    private void verProductosButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_verProductosButtonActionPerformed
    {//GEN-HEADEREND:event_verProductosButtonActionPerformed
        // TODO add your handling code here:
        this.setFocusable(false);
        this.setEnabled(false);
        new VerProductos(this, productos).setVisible(true);
    }//GEN-LAST:event_verProductosButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        // TODO add your handling code here:
        Salir();
    }//GEN-LAST:event_formWindowClosing

    private void salirMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_salirMenuItemActionPerformed
    {//GEN-HEADEREND:event_salirMenuItemActionPerformed
        // TODO add your handling code here:
        Salir();
    }//GEN-LAST:event_salirMenuItemActionPerformed

    private void modificarStockButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_modificarStockButtonActionPerformed
    {//GEN-HEADEREND:event_modificarStockButtonActionPerformed
        // TODO add your handling code here:
        this.setFocusable(false);
        this.setEnabled(false);
        new CargarStock(this, productos).setVisible(true);
    }//GEN-LAST:event_modificarStockButtonActionPerformed

    private void modificarProductosButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_modificarProductosButtonActionPerformed
    {//GEN-HEADEREND:event_modificarProductosButtonActionPerformed
        // TODO add your handling code here:
        this.setFocusable(false);
        this.setEnabled(false);
        new CargarProductos(this, productos).setVisible(true);
    }//GEN-LAST:event_modificarProductosButtonActionPerformed

    private void crearBackupMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_crearBackupMenuItemActionPerformed
    {//GEN-HEADEREND:event_crearBackupMenuItemActionPerformed
        // TODO add your handling code here:
        GuardarBackup();
    }//GEN-LAST:event_crearBackupMenuItemActionPerformed

    private void restaurarBackupMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_restaurarBackupMenuItemActionPerformed
    {//GEN-HEADEREND:event_restaurarBackupMenuItemActionPerformed
        // TODO add your handling code here:
        CargarBackup();
    }//GEN-LAST:event_restaurarBackupMenuItemActionPerformed

    private void acercaDeMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_acercaDeMenuItemActionPerformed
    {//GEN-HEADEREND:event_acercaDeMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "<html><body><div align='center'>Por Hernán Alberto Pérez (2017)<br><br>hernanperez1993@gmail.com<br>hernanperez.dev@gmail.com<br><br>v1.1</div></body></html>", "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_acercaDeMenuItemActionPerformed

    private void verAyudaMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_verAyudaMenuItemActionPerformed
    {//GEN-HEADEREND:event_verAyudaMenuItemActionPerformed
        // TODO add your handling code here:
       // for copying style
        JLabel label = new JLabel();
        Font font = label.getFont();

        // create some css from the label's font
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
        style.append("font-size:" + font.getSize() + "pt;");

        // html content
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" //
                + "Video tutorial:<br><a href=\"" + TUTORIAL_LINK_YT + "\">" + TUTORIAL_LINK_YT + "</a>" //
                + "</body></html>");

        // handle link events
        ep.addHyperlinkListener(new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e)
            {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                {
                    //ProcessHandler.launchUrl(e.getURL().toString()); // roll your own link launcher or use Desktop if J6+
                    //System.out.println("okkkkkkk");
                    try 
                    {
                        Desktop.getDesktop().browse(new URL(TUTORIAL_LINK_YT).toURI());
                    } 
                    catch (Exception ee) 
                    {
                    ee.printStackTrace();
                    }
                }
                    
            }
        });
        ep.setEditable(false);
        ep.setBackground(label.getBackground());

        // show
        JOptionPane.showMessageDialog(this, ep, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_verAyudaMenuItemActionPerformed
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(ControlStockMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ControlStockMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ControlStockMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ControlStockMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ControlStockMain().setVisible(true);
            }
        });
    }
    
    private void Salir()
    {
        int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Esta seguro que desea salir?", "Salir", JOptionPane.OK_CANCEL_OPTION);
        if (showConfirmDialog == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaDeMenuItem;
    private javax.swing.JMenu archivoMenu;
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JMenu backupMenu;
    private javax.swing.JLabel cargaLabel;
    private javax.swing.JLabel controlLabel;
    private javax.swing.JMenuItem crearBackupMenuItem;
    private javax.swing.JButton generarReporteButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton modificarProductosButton;
    private javax.swing.JButton modificarStockButton;
    private javax.swing.JMenuItem restaurarBackupMenuItem;
    private javax.swing.JButton salirButton;
    private javax.swing.JMenuItem salirMenuItem;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JMenuItem verAyudaMenuItem;
    private javax.swing.JButton verProductosButton;
    // End of variables declaration//GEN-END:variables
}
