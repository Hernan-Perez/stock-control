/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author hernan
 */


public class NuevoProducto extends javax.swing.JFrame
{
    private final JFrame parent;
    private final Database db;
    private boolean huboCambios = false;
    private ResourceBundle rb;
    
    public NuevoProducto(JFrame parent, Database db)
    {
        initComponents();
        setLocationRelativeTo(null);
        
        SetLanguage();
        
        this.parent = parent;
        this.db = db;
        
        cancelarButton.requestFocus();
        
    }
    
    private void SetLanguage()
    {
        rb = LangConfig.getInstance().getResourceBundle();
        
        this.setTitle(rb.getString("NewProductTitle"));
        
        cancelarButton.setText(rb.getString("Cancel"));
        crearProductoButton.setText(rb.getString("CreateProduct"));
        nombreLabel.setText(rb.getString("Name"));
        descripcionLabel.setText(rb.getString("DescriptionOp"));
        currentStockLabel.setText(rb.getString("CurrentStock"));
        minStockLabel.setText(rb.getString("MinStock"));
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

        nombreLabel = new javax.swing.JLabel();
        nombreTextField = new javax.swing.JTextField();
        descripcionLabel = new javax.swing.JLabel();
        descripcionTextField = new javax.swing.JTextField();
        currentStockLabel = new javax.swing.JLabel();
        stockActualTextField = new javax.swing.JTextField();
        minStockLabel = new javax.swing.JLabel();
        stockMinimoTextField = new javax.swing.JTextField();
        cancelarButton = new javax.swing.JButton();
        crearProductoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Crear nuevo producto");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                formWindowClosing(evt);
            }
        });

        nombreLabel.setText("Nombre:");

        nombreTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                nombreTextFieldKeyReleased(evt);
            }
        });

        descripcionLabel.setText("<html>Descripción:<br>(opcional)</html>");

        descripcionTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                descripcionTextFieldKeyReleased(evt);
            }
        });

        currentStockLabel.setText("Stock actual:");

        stockActualTextField.setText("0");
        stockActualTextField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                stockActualTextFieldFocusLost(evt);
            }
        });
        stockActualTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockActualTextFieldActionPerformed(evt);
            }
        });
        stockActualTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                stockActualTextFieldKeyReleased(evt);
            }
        });

        minStockLabel.setText("Stock mínimo:");

        stockMinimoTextField.setText("0");
        stockMinimoTextField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                stockMinimoTextFieldFocusLost(evt);
            }
        });
        stockMinimoTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockMinimoTextFieldActionPerformed(evt);
            }
        });
        stockMinimoTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                stockMinimoTextFieldKeyReleased(evt);
            }
        });

        cancelarButton.setText("Cancelar");
        cancelarButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelarButtonActionPerformed(evt);
            }
        });

        crearProductoButton.setBackground(new java.awt.Color(0, 153, 0));
        crearProductoButton.setText("Crear producto");
        crearProductoButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                crearProductoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(descripcionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                            .addComponent(nombreTextField))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(currentStockLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stockActualTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                                .addComponent(minStockLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(crearProductoButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stockMinimoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cancelarButton)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descripcionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentStockLabel)
                    .addComponent(stockActualTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockMinimoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minStockLabel))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crearProductoButton)
                    .addComponent(cancelarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stockActualTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stockActualTextFieldActionPerformed
    {//GEN-HEADEREND:event_stockActualTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockActualTextFieldActionPerformed

    private void stockMinimoTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stockMinimoTextFieldActionPerformed
    {//GEN-HEADEREND:event_stockMinimoTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockMinimoTextFieldActionPerformed

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelarButtonActionPerformed
    {//GEN-HEADEREND:event_cancelarButtonActionPerformed
        // TODO add your handling code here:
        Volver(true);
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        // TODO add your handling code here:
        Volver(true);
    }//GEN-LAST:event_formWindowClosing

    private void crearProductoButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_crearProductoButtonActionPerformed
    {//GEN-HEADEREND:event_crearProductoButtonActionPerformed
        if (!ValidarData())
        {
            JOptionPane.showMessageDialog(this, rb.getString("ValidationError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Producto producto = new Producto();
        producto.setData(nombreTextField.getText(), descripcionTextField.getText(), 0, Integer.parseInt(stockMinimoTextField.getText()));
        producto.setStock(Integer.parseInt(stockActualTextField.getText()));
        
        
        try
        {
            db.AgregarProducto(producto);
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "SQL Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        Volver(false);
    }//GEN-LAST:event_crearProductoButtonActionPerformed

    private boolean ValidarData()
    {
        if (stockActualTextField.getText().equals(""))
        {
            stockActualTextField.setText("0");
        }
        else
        {
            try 
            {
                int a = Integer.parseInt(stockActualTextField.getText());
                if (a < 0)
                {
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e) 
            {
                 JOptionPane.showMessageDialog(this, rb.getString("StockValidationError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
                 return false;
            }
        }
        
        if (stockMinimoTextField.getText().equals(""))
        {
            stockMinimoTextField.setText("0");
        }
        else
        {
            try 
            {
                int a = Integer.parseInt(stockMinimoTextField.getText());
                
                if (a < 0)
                {
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e) 
            {
                 JOptionPane.showMessageDialog(this, rb.getString("MinStockValidationError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
                 return false;
            }
        }
        
        /*nombreTextField.setText(nombreTextField.getText().toUpperCase());
        descripcionTextField.setText(descripcionTextField.getText().toUpperCase());*/
        
        if (nombreTextField.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, rb.getString("NameValidationError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
        {
            try
            {
                if (db.productoExisteByNombre(nombreTextField.getText()))
                {
                    JOptionPane.showMessageDialog(this, rb.getString("DuplicateNameError"), rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SQLException ex)
            {
                return false;
            }
        }
        return true;
    }
    
    
    private void stockActualTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_stockActualTextFieldFocusLost
    {//GEN-HEADEREND:event_stockActualTextFieldFocusLost
        // TODO add your handling code here:
        if (stockActualTextField.getText().equals(""))
        {
            stockActualTextField.setText("0");
        }
        
    }//GEN-LAST:event_stockActualTextFieldFocusLost

    private void stockMinimoTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_stockMinimoTextFieldFocusLost
    {//GEN-HEADEREND:event_stockMinimoTextFieldFocusLost
        // TODO add your handling code here:
        if (stockMinimoTextField.getText().equals(""))
        {
            stockMinimoTextField.setText("0");
        }
    }//GEN-LAST:event_stockMinimoTextFieldFocusLost

    private void nombreTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_nombreTextFieldKeyReleased
    {//GEN-HEADEREND:event_nombreTextFieldKeyReleased
        // TODO add your handling code here:
        //nombreTextField.setText(nombreTextField.getText().toUpperCase());
        huboCambios = true;
        
    }//GEN-LAST:event_nombreTextFieldKeyReleased

    private void descripcionTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_descripcionTextFieldKeyReleased
    {//GEN-HEADEREND:event_descripcionTextFieldKeyReleased
        // TODO add your handling code here:
        //descripcionTextField.setText(descripcionTextField.getText().toUpperCase());
        huboCambios = true;
    }//GEN-LAST:event_descripcionTextFieldKeyReleased

    private void stockActualTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_stockActualTextFieldKeyReleased
    {//GEN-HEADEREND:event_stockActualTextFieldKeyReleased
        // TODO add your handling code here:
        huboCambios = true;
    }//GEN-LAST:event_stockActualTextFieldKeyReleased

    private void stockMinimoTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_stockMinimoTextFieldKeyReleased
    {//GEN-HEADEREND:event_stockMinimoTextFieldKeyReleased
        // TODO add your handling code here:
        huboCambios = true;
    }//GEN-LAST:event_stockMinimoTextFieldKeyReleased

    private void Volver(boolean comprobar)
    {
        if (huboCambios && comprobar)
        {
            int showConfirmDialog = JOptionPane.showConfirmDialog(this, rb.getString("CancelConfirmation"), rb.getString("Cancel"), JOptionPane.OK_CANCEL_OPTION);
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
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JButton crearProductoButton;
    private javax.swing.JLabel currentStockLabel;
    private javax.swing.JLabel descripcionLabel;
    private javax.swing.JTextField descripcionTextField;
    private javax.swing.JLabel minStockLabel;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JTextField nombreTextField;
    private javax.swing.JTextField stockActualTextField;
    private javax.swing.JTextField stockMinimoTextField;
    // End of variables declaration//GEN-END:variables
}
