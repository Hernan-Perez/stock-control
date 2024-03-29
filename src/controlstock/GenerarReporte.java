/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author hernan
 */
public class GenerarReporte extends javax.swing.JFrame
{

    private final ControlStockMain parent;
    private List<Producto> productos; 
    private String ultimoTextField = "";
    private boolean soloStockMin = false;
    private final JFileChooser fc;
    private ResourceBundle rb;
    
    public GenerarReporte(ControlStockMain referenciaMain)
    {
        parent = referenciaMain;
        
        initComponents();
        setLocationRelativeTo(null);
        SetLanguage();
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setearTabla("");
        fc = new JFileChooser();

        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        
        table.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                if (e.getClickCount() == 2) 
                {
                   JTable target = (JTable)e.getSource();
                    abrirDetalleProducto(target.getSelectedRow());
                }
            }
        });
    }

    private void SetLanguage()
    {
        rb = LangConfig.getInstance().getResourceBundle();
        
        this.setTitle(rb.getString("GenerateReport"));
        
        generarReporteButton.setText(rb.getString("GenerateReportButton"));
        volverButton.setText(rb.getString("Back"));
        buscarLabel.setText(rb.getString("Search"));
        productosPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, rb.getString("Products"), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12)));
        formatoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, rb.getString("Format"), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12)));
        columnasPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, rb.getString("Columns"), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12)));
        productosTodosRadioButton.setText(rb.getString("All"));
        productosStockMinRadioButton.setText(rb.getString("OnlyMinStock"));
        productosFiltradosRadioButton.setText(rb.getString("OnlyFiltered"));
        codigoCheckBox.setText(rb.getString("Code"));
        nombreCheckBox.setText(rb.getString("Name"));
        descripcionCheckBox.setText(rb.getString("Description"));
        stockCheckBox.setText(rb.getString("CurrentStock"));
        stockMinCheckBox.setText(rb.getString("MinStock"));
        table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(rb.getString("Code"));
        table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(rb.getString("Name"));
        table.getTableHeader().getColumnModel().getColumn(2).setHeaderValue(rb.getString("Description"));
        table.getTableHeader().getColumnModel().getColumn(3).setHeaderValue(rb.getString("CurrentStock"));
        table.getTableHeader().getColumnModel().getColumn(4).setHeaderValue(rb.getString("MinStock"));
    }
    
    private void abrirDetalleProducto(int row)
    {
        if (row < 0)
        {
            return;
        }
        row = table.convertRowIndexToModel(row);
        int cod = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
        Producto aux = null;
        for (int i = 0; i < productos.size(); i++)
        {
            if (productos.get(i).getCod() == cod)
            {
                aux = productos.get(i);
                break;
            }
        }
        
        if (aux == null)
        {
            return;
        }
        
        this.setFocusable(false);
        this.setEnabled(false);
        new DetalleProducto(this, aux, false, null).setVisible(true);
    }
    
    private void setearTabla(String indiceBusqueda)
    {
        table.removeAll();
        
        try
        {
            productos = parent.getDB().getMultipleByNombre(indiceBusqueda);
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "Internal SQL error", "Error", JOptionPane.ERROR_MESSAGE);
            Volver();
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() != 0)
        {
            model.removeRow(0);
        }
        
        Producto aux;
        for (int i = 0; i < productos.size(); i++)
        {
            aux = productos.get(i);
            if (indiceBusqueda.equals("") || aux.getNombre().contains(indiceBusqueda))
            {
                if (!soloStockMin || aux.estaEnStockMinimo())
                {
                    model.addRow(new Object[]{aux.getCod(), aux.getNombre(), aux.getDescripcion(), aux.getStock(), aux.getValorStockMin()});
                }
            }
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

        productosButtonGroup = new javax.swing.ButtonGroup();
        formatoButtonGroup = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        volverButton = new javax.swing.JButton();
        buscarTextField = new javax.swing.JTextField();
        buscarLabel = new javax.swing.JLabel();
        generarReporteButton = new javax.swing.JButton();
        formatoPanel = new javax.swing.JPanel();
        formatoTxtRadioButton = new javax.swing.JRadioButton();
        formatoCsvRadioButton = new javax.swing.JRadioButton();
        productosPanel = new javax.swing.JPanel();
        productosTodosRadioButton = new javax.swing.JRadioButton();
        productosStockMinRadioButton = new javax.swing.JRadioButton();
        productosFiltradosRadioButton = new javax.swing.JRadioButton();
        columnasPanel = new javax.swing.JPanel();
        codigoCheckBox = new javax.swing.JCheckBox();
        nombreCheckBox = new javax.swing.JCheckBox();
        descripcionCheckBox = new javax.swing.JCheckBox();
        stockCheckBox = new javax.swing.JCheckBox();
        stockMinCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Generar Reporte");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Código", "Nombre", "Descripción", "Stock actual", "Stock mínimo"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0)
        {
            table.getColumnModel().getColumn(0).setPreferredWidth(20);
            table.getColumnModel().getColumn(3).setPreferredWidth(25);
            table.getColumnModel().getColumn(4).setPreferredWidth(25);
        }

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                volverButtonActionPerformed(evt);
            }
        });

        buscarTextField.setEnabled(false);
        buscarTextField.addInputMethodListener(new java.awt.event.InputMethodListener()
        {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt)
            {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt)
            {
                buscarTextFieldInputMethodTextChanged(evt);
            }
        });
        buscarTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                buscarTextFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                buscarTextFieldKeyReleased(evt);
            }
        });

        buscarLabel.setText("Buscar:");

        generarReporteButton.setBackground(new java.awt.Color(51, 153, 0));
        generarReporteButton.setText("Generar reporte");
        generarReporteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                generarReporteButtonActionPerformed(evt);
            }
        });

        formatoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Formato"));
        formatoPanel.setName("Formato"); // NOI18N

        formatoButtonGroup.add(formatoTxtRadioButton);
        formatoTxtRadioButton.setSelected(true);
        formatoTxtRadioButton.setText("TXT");

        formatoButtonGroup.add(formatoCsvRadioButton);
        formatoCsvRadioButton.setText("CSV (Excel)");

        javax.swing.GroupLayout formatoPanelLayout = new javax.swing.GroupLayout(formatoPanel);
        formatoPanel.setLayout(formatoPanelLayout);
        formatoPanelLayout.setHorizontalGroup(
            formatoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formatoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formatoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formatoTxtRadioButton)
                    .addComponent(formatoCsvRadioButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        formatoPanelLayout.setVerticalGroup(
            formatoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formatoPanelLayout.createSequentialGroup()
                .addComponent(formatoTxtRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formatoCsvRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        productosPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));
        productosPanel.setName("Productos"); // NOI18N

        productosButtonGroup.add(productosTodosRadioButton);
        productosTodosRadioButton.setSelected(true);
        productosTodosRadioButton.setText("Todos");
        productosTodosRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                productosTodosRadioButtonActionPerformed(evt);
            }
        });

        productosButtonGroup.add(productosStockMinRadioButton);
        productosStockMinRadioButton.setText("Solo en stock minimo");
        productosStockMinRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                productosStockMinRadioButtonActionPerformed(evt);
            }
        });

        productosButtonGroup.add(productosFiltradosRadioButton);
        productosFiltradosRadioButton.setText("Solo filtrados en la busqueda");
        productosFiltradosRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                productosFiltradosRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productosPanelLayout = new javax.swing.GroupLayout(productosPanel);
        productosPanel.setLayout(productosPanelLayout);
        productosPanelLayout.setHorizontalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productosPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(productosTodosRadioButton)
                    .addComponent(productosFiltradosRadioButton)
                    .addComponent(productosStockMinRadioButton)))
        );
        productosPanelLayout.setVerticalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosPanelLayout.createSequentialGroup()
                .addComponent(productosTodosRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productosStockMinRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(productosFiltradosRadioButton))
        );

        columnasPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Columnas"));
        columnasPanel.setName("Columnas"); // NOI18N

        codigoCheckBox.setSelected(true);
        codigoCheckBox.setText("Código");

        nombreCheckBox.setSelected(true);
        nombreCheckBox.setText("Nombre");

        descripcionCheckBox.setText("Descripción");

        stockCheckBox.setSelected(true);
        stockCheckBox.setText("Stock");

        stockMinCheckBox.setText("Stock mínimo");
        stockMinCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockMinCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout columnasPanelLayout = new javax.swing.GroupLayout(columnasPanel);
        columnasPanel.setLayout(columnasPanelLayout);
        columnasPanelLayout.setHorizontalGroup(
            columnasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(columnasPanelLayout.createSequentialGroup()
                .addGroup(columnasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codigoCheckBox)
                    .addComponent(nombreCheckBox)
                    .addComponent(descripcionCheckBox)
                    .addComponent(stockCheckBox)
                    .addComponent(stockMinCheckBox))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        columnasPanelLayout.setVerticalGroup(
            columnasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(columnasPanelLayout.createSequentialGroup()
                .addComponent(codigoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nombreCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descripcionCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockMinCheckBox)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buscarLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(formatoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(columnasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(volverButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generarReporteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(generarReporteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 101, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buscarLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(volverButton)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(columnasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formatoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
        );

        formatoPanel.getAccessibleContext().setAccessibleName("");
        formatoPanel.getAccessibleContext().setAccessibleDescription("");
        productosPanel.getAccessibleContext().setAccessibleName("");
        columnasPanel.getAccessibleContext().setAccessibleName("");
        columnasPanel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_volverButtonActionPerformed
    {//GEN-HEADEREND:event_volverButtonActionPerformed
        // TODO add your handling code here:
        Volver();
    }//GEN-LAST:event_volverButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        // TODO add your handling code here:
        Volver();
    }//GEN-LAST:event_formWindowClosing

    private void buscarTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt)//GEN-FIRST:event_buscarTextFieldInputMethodTextChanged
    {//GEN-HEADEREND:event_buscarTextFieldInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_buscarTextFieldInputMethodTextChanged

    private void buscarTextFieldKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_buscarTextFieldKeyPressed
    {//GEN-HEADEREND:event_buscarTextFieldKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_buscarTextFieldKeyPressed

    private void buscarTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_buscarTextFieldKeyReleased
    {//GEN-HEADEREND:event_buscarTextFieldKeyReleased
        // TODO add your handling code here:

        buscarTextField.setText(buscarTextField.getText().toUpperCase());
        ultimoTextField = buscarTextField.getText();
        setearTabla(ultimoTextField);
    }//GEN-LAST:event_buscarTextFieldKeyReleased

    private void generarReporteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_generarReporteButtonActionPerformed
    {//GEN-HEADEREND:event_generarReporteButtonActionPerformed
        // TODO add your handling code here:
        
        if (!codigoCheckBox.isSelected() && !nombreCheckBox.isSelected() && !descripcionCheckBox.isSelected() && !stockCheckBox.isSelected() && !stockMinCheckBox.isSelected())
        {
            JOptionPane.showMessageDialog(this, rb.getString("NoColumn"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Producto> prodReporte = new ArrayList<>();
        
        if (productosTodosRadioButton.isSelected())
        {
            prodReporte = productos;
        }
        else if (productosStockMinRadioButton.isSelected())
        {
            for (int i = 0; i < productos.size(); i++)
            {
                if (productos.get(i).estaEnStockMinimo())
                {
                    prodReporte.add(productos.get(i));
                }
            }
        }
        else    //con filtro
        {
            Producto aux;
            String indiceBusqueda = buscarTextField.getText();
            
            for (int i = 0; i < productos.size(); i++)
            {
                aux = productos.get(i);
                if (indiceBusqueda.equals("") || aux.getNombre().contains(indiceBusqueda))
                {
                    prodReporte.add(productos.get(i));
                }
            }
        }
        
        if (prodReporte.isEmpty())
        {
            JOptionPane.showMessageDialog(this, rb.getString("NoProducts"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //aca ya procese los filtros de productos, la lista a exportar esta en la variable prodReporte

        
        String timeLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        fc.setSelectedFile(new File(""));
                
        if (formatoTxtRadioButton.isSelected()) //TXT-------------------------
        {
            BufferedWriter writer = null;
            try 
            {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(rb.getString("TextFile"), "txt");
                fc.setFileFilter(filter);
                int res = fc.showSaveDialog(this);
                if (res != JFileChooser.APPROVE_OPTION)
                {
                    return;
                }

                File f = fc.getSelectedFile();
                if (!f.getName().endsWith(".txt"))
                {
                    f = new File(fc.getSelectedFile().getPath() + ".txt");
                }
                if (f.exists())
                {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(this, rb.getString("FileExists"), rb.getString("Replace"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (showConfirmDialog != JOptionPane.YES_OPTION)
                    {
                        return;
                    }
                }
                writer = new BufferedWriter(new FileWriter(f));
                writer.write(rb.getString("Title") + "\r\n"+ rb.getString("ProductReport") + " (" + timeLog + ")\r\n\r\n");
                
                if (productosTodosRadioButton.isSelected())
                {
                    writer.write(rb.getString("AllList") + "\r\n");
                }
                else if (productosStockMinRadioButton.isSelected())
                {
                    writer.write(rb.getString("MinStockList") + "\r\n");
                } 
                else
                {
                    writer.write(rb.getString("FilteredList") +"\"" + buscarTextField.getText() + "\"\r\n");
                }
                
                writer.write(rb.getString("ProductAmount") + prodReporte.size() + "\r\n\r\n");
                
                if (codigoCheckBox.isSelected())
                    writer.write(padRight(rb.getString("Code"), 10) + "\t");
                if (nombreCheckBox.isSelected())
                    writer.write(padRight(rb.getString("Name"), 20) + "\t");
                if (descripcionCheckBox.isSelected())
                    writer.write(padRight(rb.getString("Description"), 25) + "\t");
                if (stockCheckBox.isSelected())
                    writer.write(padRight(rb.getString("CurrentStock"), 10) + "\t");
                if (stockMinCheckBox.isSelected())
                    writer.write(padRight(rb.getString("MinStock"), 10) + "\t");
                
                writer.write("\r\n");
                
                prodReporte.sort(Producto.COMPARAR_POR_NOMBRE);
                
                for (int i = 0; i < prodReporte.size(); i++)
                {
                    Producto p = prodReporte.get(i);
                    
                    if (codigoCheckBox.isSelected())
                        writer.write(padRight(p.getCod() + "", 10) + "\t");
                    
                    if (nombreCheckBox.isSelected())
                        writer.write(padRight(p.getNombre(20), 20) + "\t");
                    
                    if (descripcionCheckBox.isSelected())
                        writer.write(padRight(p.getDescripcion(25), 25) + "\t");
                    
                    if (stockCheckBox.isSelected())
                        writer.write(padRight(p.getStock() + "", 10)  + "\t");
                    
                    if (stockMinCheckBox.isSelected())
                        writer.write(padRight(p.getValorStockMin() + "", 10) + "\t");
                    
                    writer.write("\r\n");
                }
                JOptionPane.showMessageDialog(this, rb.getString("FileSuccess"), rb.getString("Success"), JOptionPane.INFORMATION_MESSAGE);
                
            } 
            catch (Exception e) 
            {
                //e.printStackTrace();
                JOptionPane.showMessageDialog(this, rb.getString("FileError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            } 
            finally 
            {
                try 
                {
                    writer.close();
                } 
                catch (Exception e) 
                {
                    //JOptionPane.showMessageDialog(this, "Error al cerrar archivo de reporte.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else //CSV ------------------------
        {
            BufferedWriter writer = null;
            
            DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
            DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
            char sep = symbols.getDecimalSeparator();
            //Aca busco cual es el separador decimal del sistema
            //reutilizo la variable para usarla como separador del csv
            //si sep es ',' pasa a ser ';'
            //si sep tiene otro valor pasa a ser ','
            if (sep == ',')
            {
                sep = ';';
            }
            else
            {
                sep = ',';
            }
            
            
            try 
            {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(rb.getString("FileCSV"), "csv");
                fc.setFileFilter(filter);
                int res = fc.showSaveDialog(this);
                if (res != JFileChooser.APPROVE_OPTION)
                {
                    return;
                }

                File f = fc.getSelectedFile();
                if (!f.getName().endsWith(".csv"))
                {
                    f = new File(fc.getSelectedFile().getPath() + ".csv");
                }
                if (f.exists())
                {
                    int showConfirmDialog = JOptionPane.showConfirmDialog(this, rb.getString("FileExists"), rb.getString("Replace"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (showConfirmDialog != JOptionPane.YES_OPTION)
                    {
                        return;
                    }
                }

                writer = new BufferedWriter(new FileWriter(f));
                writer.write(rb.getString("Title") + "\r\n"+ rb.getString("ProductReport") + " (" + timeLog + ")\r\n\r\n");
                
                if (productosTodosRadioButton.isSelected())
                {
                    writer.write(rb.getString("AllList") + "\r\n\r\n");
                }
                else if (productosStockMinRadioButton.isSelected())
                {
                    writer.write(rb.getString("MinStockList") + "\r\n\r\n");
                } 
                else
                {
                    writer.write(rb.getString("FilteredList") + sep + "\"" + buscarTextField.getText() + "\"\r\n");
                }
                
                writer.write(rb.getString("ProductAmount")  + prodReporte.size() + "\r\n\r\n");
                
                if (codigoCheckBox.isSelected())
                    writer.write(rb.getString("Code") + sep);
                if (nombreCheckBox.isSelected())
                    writer.write(rb.getString("Name") + sep);
                if (descripcionCheckBox.isSelected())
                    writer.write(rb.getString("Description") + sep);
                if (stockCheckBox.isSelected())
                    writer.write(rb.getString("CurrentStock") + sep);
                if (stockMinCheckBox.isSelected())
                    writer.write(rb.getString("MinStock") + sep);
                
                writer.write("\r\n");
                
                prodReporte.sort(Producto.COMPARAR_POR_NOMBRE);
                
                for (int i = 0; i < prodReporte.size(); i++)
                {
                    Producto p = prodReporte.get(i);
                    
                    if (codigoCheckBox.isSelected())
                        writer.write("" + p.getCod() + sep);
                    
                    if (nombreCheckBox.isSelected())
                        writer.write("\"" + p.getNombre() + "\"" + sep);
                    
                    if (descripcionCheckBox.isSelected())
                        writer.write("\"" + p.getDescripcion() + "\"" + sep);
                    
                    if (stockCheckBox.isSelected())
                        writer.write("" + p.getStock() + sep);
                    
                    if (stockMinCheckBox.isSelected())
                        writer.write("" + p.getValorStockMin() + sep);
                    
                    writer.write("\r\n");
                }
                JOptionPane.showMessageDialog(this, rb.getString("FileSuccess"), rb.getString("Success"), JOptionPane.INFORMATION_MESSAGE);
                
            } 
            catch (Exception e) 
            {
                //e.printStackTrace();
                JOptionPane.showMessageDialog(this, rb.getString("FileError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            } 
            finally 
            {
                try 
                {
                    writer.close();
                } 
                catch (Exception e) 
                {
                    //JOptionPane.showMessageDialog(this, "Error al cerrar archivo de reporte.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_generarReporteButtonActionPerformed

    public String padRight(String s, int n) 
    {
        return String.format("%1$-" + n + "s", s);  
    }

    public String padLeft(String s, int n) 
    {
        return String.format("%1$" + n + "s", s);  
    }

    
    
    private void productosTodosRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_productosTodosRadioButtonActionPerformed
    {//GEN-HEADEREND:event_productosTodosRadioButtonActionPerformed
        // TODO add your handling code here:
        //System.out.println("ok");
        soloStockMin = false;
        setearTabla("");
        buscarTextField.setEnabled(false);
    }//GEN-LAST:event_productosTodosRadioButtonActionPerformed

    private void productosStockMinRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_productosStockMinRadioButtonActionPerformed
    {//GEN-HEADEREND:event_productosStockMinRadioButtonActionPerformed
        // TODO add your handling code here:
        soloStockMin = true;
        setearTabla("");
        buscarTextField.setEnabled(false);
    }//GEN-LAST:event_productosStockMinRadioButtonActionPerformed

    private void productosFiltradosRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_productosFiltradosRadioButtonActionPerformed
    {//GEN-HEADEREND:event_productosFiltradosRadioButtonActionPerformed
        // TODO add your handling code here:
        buscarTextField.setEnabled(true);
        soloStockMin = false;
        setearTabla(buscarTextField.getText());
    }//GEN-LAST:event_productosFiltradosRadioButtonActionPerformed

    private void stockMinCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stockMinCheckBoxActionPerformed
    {//GEN-HEADEREND:event_stockMinCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockMinCheckBoxActionPerformed

    private void Volver()
    {
        this.setEnabled(false);
        this.setVisible(false);
        parent.setEnabled(true);
        parent.setFocusable(true);
        parent.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel buscarLabel;
    private javax.swing.JTextField buscarTextField;
    private javax.swing.JCheckBox codigoCheckBox;
    private javax.swing.JPanel columnasPanel;
    private javax.swing.JCheckBox descripcionCheckBox;
    private javax.swing.ButtonGroup formatoButtonGroup;
    private javax.swing.JRadioButton formatoCsvRadioButton;
    private javax.swing.JPanel formatoPanel;
    private javax.swing.JRadioButton formatoTxtRadioButton;
    private javax.swing.JButton generarReporteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox nombreCheckBox;
    private javax.swing.ButtonGroup productosButtonGroup;
    private javax.swing.JRadioButton productosFiltradosRadioButton;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JRadioButton productosStockMinRadioButton;
    private javax.swing.JRadioButton productosTodosRadioButton;
    private javax.swing.JCheckBox stockCheckBox;
    private javax.swing.JCheckBox stockMinCheckBox;
    private javax.swing.JTable table;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
