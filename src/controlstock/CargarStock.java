/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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
public class CargarStock extends javax.swing.JFrame
{

    /**
     * Creates new form CargarProductos
     */
    private final ControlStockMain parent;
    private List<Producto> productos; 
    private String ultimoTextField = "";
    private boolean soloStockMin = false;
    private boolean necesitaActualizacion = false; //esto lo uso para cuando se cierra alguna ventana hija
    
    private final List<Integer> valor_carga;
    private final List<Integer> valor_row;
    
    
    public CargarStock(ControlStockMain referenciaMain)
    {
        parent = referenciaMain;
        
        initComponents();
        setLocationRelativeTo(null);
        
        valor_carga = new ArrayList<>();
        valor_row = new ArrayList<>();
        
        try
        {
            productos = parent.getDB().getMultipleByNombre("");
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "Internal SQL error", "Error", JOptionPane.ERROR_MESSAGE);
            Volver(false);
        }
        
        for (int i = 0; i < productos.size(); i++)
        {
            valor_row.add(productos.get(i).getCod());
            valor_carga.add(0);
        }   
        
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
        
        
    }

    private void setValorCargaPorCodigo(int codigo, int valor)
    {
        for (int i = 0; i < valor_row.size(); i++)
        {
            if (valor_row.get(i) == codigo)
            {
                valor_carga.set(i, valor);
                //System.out.println("set" + codigo + " " + valor);
            }
        }
    }
    
    private Integer getValorCargaPorCodigo(int codigo)
    {
        for (int i = 0; i < valor_row.size(); i++)
        {
            if (valor_row.get(i) == codigo)
            {
                //System.out.println("get" + codigo + " " + valor_carga.get(i));
                return valor_carga.get(i);
            }
        }
        return 0;
    }
    
    private void setearTablaLimpia(String indiceBusqueda)
    {
        table.removeAll();

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
                    model.addRow(new Object[]{aux.getCod(), aux.getNombre(), aux.getDescripcion(), aux.getStock(), aux.getValorStockMin(), getValorCargaPorCodigo(aux.getCod())});
                }
            }
        }
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
            Volver(false);
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (model.getRowCount() != 0)
        {
            if (table.getModel().getValueAt(0, 5) == null)
            {
                table.getModel().setValueAt(0, 0, 5);
            }
            setValorCargaPorCodigo(Integer.valueOf(table.getModel().getValueAt(0, 0).toString()), Integer.valueOf(table.getModel().getValueAt(0, 5).toString()));
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
                    model.addRow(new Object[]{aux.getCod(), aux.getNombre(), aux.getDescripcion(), aux.getStock(), aux.getValorStockMin(), getValorCargaPorCodigo(aux.getCod())});
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
        jLabel1 = new javax.swing.JLabel();
        stockMinimoCheckBox = new javax.swing.JCheckBox();
        guardarCambiosButton = new javax.swing.JButton();
        ayudaButton = new javax.swing.JButton();
        filtrarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Carga De Stock");
        addWindowStateListener(new java.awt.event.WindowStateListener()
        {
            public void windowStateChanged(java.awt.event.WindowEvent evt)
            {
                formWindowStateChanged(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener()
        {
            public void windowGainedFocus(java.awt.event.WindowEvent evt)
            {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt)
            {
            }
        });
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
                "Código", "Nombre", "Descripción", "Stock actual", "Stock mínimo", "CARGA"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, true
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

        jLabel1.setText("Buscar:");

        stockMinimoCheckBox.setText("Ver solo productos con stock mínimo");
        stockMinimoCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockMinimoCheckBoxActionPerformed(evt);
            }
        });

        guardarCambiosButton.setBackground(new java.awt.Color(0, 153, 0));
        guardarCambiosButton.setText("Guardar cambios");
        guardarCambiosButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                guardarCambiosButtonActionPerformed(evt);
            }
        });

        ayudaButton.setText("Ayuda");
        ayudaButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ayudaButtonActionPerformed(evt);
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(stockMinimoCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filtrarButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(ayudaButton)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guardarCambiosButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(volverButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buscarTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(filtrarButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stockMinimoCheckBox)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardarCambiosButton)
                            .addComponent(ayudaButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(volverButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_volverButtonActionPerformed
    {//GEN-HEADEREND:event_volverButtonActionPerformed
        // TODO add your handling code here:
        Volver(true);
    }//GEN-LAST:event_volverButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        // TODO add your handling code here:
        Volver(true);
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
        //System.out.println("cambiooo");
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

    private void formWindowStateChanged(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowStateChanged
    {//GEN-HEADEREND:event_formWindowStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowGainedFocus
    {//GEN-HEADEREND:event_formWindowGainedFocus
        // TODO add your handling code here:
        if (necesitaActualizacion)
        {
            necesitaActualizacion = false;
            setearTabla(ultimoTextField);
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void guardarCambiosButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_guardarCambiosButtonActionPerformed
    {//GEN-HEADEREND:event_guardarCambiosButtonActionPerformed
        // TODO add your handling code here:
        if (!HuboCambios())
        {
            JOptionPane.showMessageDialog(this, "No hay cambios de stock a realizar.", "No hay cambios", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //para los valores negativos controlo de que al restarlo al stock actual no de negativo
        for (int i = 0; i < valor_row.size(); i++)
        {
            if (valor_carga.get(i) >= 0)
            {
                continue;
            }
            for (int c = 0; c < productos.size(); c++)
            {
                if (valor_row.get(i) == productos.get(c).getCod())
                {
                    if (productos.get(c).getStock() + valor_carga.get(i) < 0)
                    {
                        JOptionPane.showMessageDialog(this, "Error: producto (cod: " + valor_row.get(i) + ") en stock negativo.\nSe canceló la acción.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    break;
                }
            }
        }
        
        for (int i = 0; i < valor_row.size(); i++)
        {
            for (int c = 0; c < productos.size(); c++)
            {
                if (valor_row.get(i) == productos.get(c).getCod())
                {
                    productos.get(c).setStock(productos.get(c).getStock() + valor_carga.get(i));
                    valor_carga.set(i, 0);
                    break;
                }
            }
        }
        
        try
        {
            parent.getDB().ModificarStock(productos);
        } 
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "SQL ERROR", "Error", JOptionPane.ERROR_MESSAGE);
        }
        setearTablaLimpia(ultimoTextField);
        JOptionPane.showMessageDialog(this, "Se actualizó el stock con exito.", "Correcto", JOptionPane.INFORMATION_MESSAGE);
        //Volver(false);        
    }//GEN-LAST:event_guardarCambiosButtonActionPerformed

    private void ayudaButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ayudaButtonActionPerformed
    {//GEN-HEADEREND:event_ayudaButtonActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, "<html><body><div align='center'>Para cada producto que deseé cambiar el stock,<br>utilice el campo bajo la columna CARGA para<br>indicar la cantidad que se agrega<br>o se quita al stock.<br>Para quitar stock de un producto utilice<br>valores negativos (si se intenta<br>restar una cantidad mayor a la existente<br>el programa lanzará un error).</div></body></html>", "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_ayudaButtonActionPerformed

    private void filtrarButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_filtrarButtonActionPerformed
    {//GEN-HEADEREND:event_filtrarButtonActionPerformed
        // TODO add your handling code here:
        setearTabla(ultimoTextField);
        
    }//GEN-LAST:event_filtrarButtonActionPerformed

    
    private boolean HuboCambios()
    {
        setearTabla(ultimoTextField);
        for (int i = 0; i < valor_carga.size(); i++)
        {
            if (valor_carga.get(i) != 0)
            {
                return true;
            }
        }
        return false;
    }
    
    private void Volver(boolean comprobar)
    {
        if (comprobar && HuboCambios())
        {
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Está seguro que cancelar la carga de stock?", "Volver", JOptionPane.OK_CANCEL_OPTION);
            if (showConfirmDialog != JOptionPane.YES_OPTION)
            {
                return;
            }
        }
        this.setEnabled(false);
        this.setVisible(false);
        parent.setEnabled(true);
        parent.setFocusable(true);
        parent.setVisible(true);
        //parent.GuardarBaseDeDatos();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ayudaButton;
    private javax.swing.JTextField buscarTextField;
    private javax.swing.JButton filtrarButton;
    private javax.swing.JButton guardarCambiosButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox stockMinimoCheckBox;
    private javax.swing.JTable table;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
