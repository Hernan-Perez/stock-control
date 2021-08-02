/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlstock;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author hernan
 */

//Singleton class
public class LangConfig
{
    private static LangConfig instance = null;
    private Locale locale;
    private ResourceBundle rb;
    
    private LangConfig()
    {
        SetLanguage("en"); //default lang
    }
    
    public void SetLanguage(String s)
    {
        locale = new Locale(s);
        rb = ResourceBundle.getBundle("controlstock.MessagesBundle", locale);
    }
    
    public ResourceBundle getResourceBundle()
    {
        return rb;
    }
    
    public static LangConfig getInstance()
    {
        if (instance == null)
            instance = new LangConfig();
        return instance;
    }
}
