/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hernan
 */
public class Database 
{
    private Connection c;
    public Database()
    {
        
    }

    
    public boolean init(String s)
    {
        c = null;
        
        if (!dbExists(s))
        {
            return false;
        }
        
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + s);
        }
        catch(Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
        return true;
    }
    
    
    
    
    private boolean dbExists(String s)
    {
        File f = new File(s);
        return f.exists();
    }
    
    public List<Producto> getMultipleByNombre(String n) throws SQLException
    {
        String query = "SELECT * FROM productos WHERE nombre LIKE ?";
        n = "%" + n + "%";
        return QueryProductoSelect(query, n);
    }
    
    private List<Producto> QueryProductoSelect(String query, String n) throws SQLException
    {
        List<Producto> lProd = new ArrayList<>();
        Producto prodAux;
        try
        {
            PreparedStatement stmt  = c.prepareStatement(query);
            stmt.setString(1, n);
            ResultSet rs    = stmt.executeQuery();
            
            while (rs.next()) 
            {
                prodAux = new Producto(rs.getInt("id"));
                prodAux.setData(rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("stock"), rs.getInt("stock_minimo"));
                lProd.add(prodAux);
            }
        } 
        catch (SQLException e) 
        {
            throw e;
        }
        
        return lProd;
    }
    
    public void AgregarProducto(Producto producto) throws SQLException
    {
        String query = "INSERT INTO productos (nombre, descripcion, stock, stock_minimo) VALUES (?, ?, ?, ?);";
        
        //System.out.println(query);
        try
        {
            //Statement stmt  = c.createStatement();
            PreparedStatement stmt  = c.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getStock());
            stmt.setInt(4, producto.getValorStockMin());
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw e;
        }
       
    }
    
    public boolean productoExisteByNombre(String n) throws SQLException
    {
        String query = "SELECT * FROM productos WHERE nombre = ?";

        try
        {
            PreparedStatement stmt  = c.prepareStatement(query);
            stmt.setString(1, n);
            ResultSet rs    = stmt.executeQuery();
            
            if (rs.next()) 
            {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            throw e;
        }
        
        return false;
    }
    
    public void ModificarProducto(Producto producto) throws SQLException
    {
        String query = "UPDATE productos "
                + "SET nombre = ?, descripcion = ?, stock = ?, stock_minimo = ? "
                + "WHERE id = " + producto.getCod();
        
        //System.out.println(query);
        try
        {
            PreparedStatement stmt  = c.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getStock());
            stmt.setInt(4, producto.getValorStockMin());
            
            //System.out.println(stmt.toString());
            
            stmt.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw e;
        }
    }
    
    public void ModificarStock(List<Producto> lp) throws SQLException
    {
        Producto p;
        for(int i = 0; i < lp.size(); i++)
        {
            p = lp.get(i);
            ModificarStockSimple(p);
        }
    }
    
    public void ModificarStockSimple(Producto producto) throws SQLException
    {
        String query = "UPDATE productos "
                + "SET stock = " + producto.getStock()
                + " WHERE id = " + producto.getCod();
        
        //System.out.println(query);
        try
        {
            Statement stmt  = c.createStatement();
            stmt.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            throw e;
        }
    }
    
    public void EliminarProducto(Producto p) throws SQLException
    {
        this.EliminarProducto(p.getCod());
    }
    
    public void EliminarProducto(int id) throws SQLException
    {
        String query = "DELETE FROM productos "
                + "WHERE id = " + id + ";";
        
        //System.out.println(query);
        try
        {
            Statement stmt  = c.createStatement();
            stmt.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            throw e;
        }
    }
    
    
    /*
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
    }*/

    
}
