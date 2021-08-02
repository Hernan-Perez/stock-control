/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author hernan
 */
public class VerProductos extends javax.swing.JFrame
{

    /**
     * Creates new form VerProductos
     */
    private final ControlStockMain parent;
    private List<Producto> productos; 
    private String ultimoTextField = "";
    private boolean soloStockMin = false;
    private ResourceBundle rb;
    public VerProductos(ControlStockMain referenciaMain)
    {
        parent = referenciaMain;
        initComponents();
        setLocationRelativeTo(null);
        SetLanguage();
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setearTabla("");
        
        //En vez de poner rowsorter automatico pongo esto para que ya los ordene por nombre de un principio
        //Con este metodo igual te deja tocar en cualquier columna y ordenarlos por valor
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        //sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        
        
        
        //jTable1.setAutoCreateRowSorter(true);
        
        table.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                if (e.getClickCount() == 2) 
                {
                   JTable target = (JTable)e.getSource();
                   //int row = target.getSelectedRow();
                    abrirDetalleProducto(target.getSelectedRow());
                   System.out.println(target.getSelectedRow());
                }
            }
        });
    }
    
    private void SetLanguage()
    {
        rb = LangConfig.getInstance().getResourceBundle();
        
        this.setTitle(rb.getString("ViewProducts"));
        
        filtrarButton.setText(rb.getString("Filter"));
        volverButton.setText(rb.getString("Back"));
        buscarLabel.setText(rb.getString("Search"));
        stockMinimoCheckBox.setText(rb.getString("MinStockCheck"));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        volverButton = new javax.swing.JButton();
        buscarTextField = new javax.swing.JTextField();
        buscarLabel = new javax.swing.JLabel();
        stockMinimoCheckBox = new javax.swing.JCheckBox();
        filtrarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Listar Productos");
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

        stockMinimoCheckBox.setText("Listar solo productos con stock mínimo");
        stockMinimoCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockMinimoCheckBoxActionPerformed(evt);
            }
        });

        filtrarButton.setText("FIltrar");
        filtrarButton.setName(""); // NOI18N
        filtrarButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                filtrarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(stockMinimoCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buscarLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filtrarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(volverButton)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volverButton)
                    .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarLabel)
                    .addComponent(filtrarButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stockMinimoCheckBox)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
        );

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
        /*if (buscarTextField.getText().equals(ultimoTextField))
        {
            return;
        }*/
        //buscarTextField.setText(buscarTextField.getText().toUpperCase());
        ultimoTextField = buscarTextField.getText();
        //setearTabla(ultimoTextField);
    }//GEN-LAST:event_buscarTextFieldKeyReleased

    private void stockMinimoCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stockMinimoCheckBoxActionPerformed
    {//GEN-HEADEREND:event_stockMinimoCheckBoxActionPerformed
        // TODO add your handling code here:
        if (soloStockMin)
        {
            soloStockMin = false;
        }
        else
        {
            soloStockMin = true;
        }
        setearTabla(buscarTextField.getText());
    }//GEN-LAST:event_stockMinimoCheckBoxActionPerformed

    private void filtrarButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filtrarButtonActionPerformed
    {//GEN-HEADEREND:event_filtrarButtonActionPerformed
        // TODO add your handling code here:
        setearTabla(ultimoTextField);
    }//GEN-LAST:event_filtrarButtonActionPerformed

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
    private javax.swing.JButton filtrarButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox stockMinimoCheckBox;
    private javax.swing.JTable table;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
