/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.util.Comparator;

/**
 *
 * @author hernan
 */
public class Producto
{
    private final int cod;
    private String nombre;
    private String descripcion;
    private int valorStockMin;
    private int stock;
    
    public static Comparator<Producto> COMPARAR_POR_NOMBRE = new Comparator<Producto>() {
        public int compare(Producto one, Producto other) {
            return one.nombre.compareTo(other.nombre);
        }
    };
    
    public Producto()
    {
        cod = ControlStockMain.SIGUIENTE_CODIGO;
        ControlStockMain.SIGUIENTE_CODIGO++;
        valorStockMin = 0;
        stock = 0;
    }
    
    public Producto(int codigo)
    {
        cod = codigo;
        valorStockMin = 0;
        stock = 0;
    }
    
    public void setData(String nombre, String descripcion, int valorStockMin)
    {
        setNombre(nombre);
        setDescripcion(descripcion);
        setValorStockMin(valorStockMin);
    }
    
    public int getCod()
    {
        return cod;
    }

    public String getNombre()
    {
        return nombre;
    }
    
    public String getNombre(int c)
    {
        if (nombre.length() < c)
        {
            return nombre;
        }
        return nombre.substring(0, c-1);
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }
    
    public String getDescripcion(int c)
    {
        if (descripcion.length() < c)
        {
            return descripcion;
        }
        return descripcion.substring(0, c-1);
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public int getValorStockMin()
    {
        return valorStockMin;
    }

    public void setValorStockMin(int valorStockMin)
    {
        this.valorStockMin = valorStockMin;
    }
    
    public void setStock(int stock)
    {
        this.stock = stock;
    }
    
    public int getStock()
    {
        return stock;
    }
    
    public void agregarStock(int agregado)
    {
        stock += agregado;
    }
    
    public boolean quitarStock(int quitado)
    {
        stock -= quitado;
        
        if (stock < 0)
        {
            stock += quitado;
            return false;
        }
        return true;
    }
    
    public boolean estaEnStockMinimo()
    {
        return stock <= valorStockMin;
    }
    
    public int compareTo(Producto otro)
    {
        return nombre.compareTo(otro.getNombre());
    }
}
