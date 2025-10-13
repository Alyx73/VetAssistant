/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Alex
 */
public class Idioma {
    
    private Locale locale;
    private ResourceBundle bundle;

    public Idioma(String idioma) {
        
        this.locale = Locale.forLanguageTag(idioma);
        this.bundle = ResourceBundle.getBundle("idioma.textos", locale);
    }

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
    
    public String texto (String clave) {
        return bundle.getString(clave);
    }
}
