/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para leer y guardar los datos de configuracion 
 * de conexion con el servidor
 * @author Alex
 */
public class ServConfig {
    
    private String host;
    private String puerto;
    private String usuario;
    private String contrasena;
    
    private final Path configPath = Paths.get(System.getProperty("user.home"), ".vetassistant");
    private final File file = new File(configPath.toString(), "conexion.properties");

    public ServConfig() {
        leerPropiedades();
    }

    private void leerPropiedades() {
        try {
            if (!configPath.toFile().exists()) {
                Files.createDirectories(configPath);
            }

            if (!file.exists()) {
                guardarPropiedades("localhost", "8080", "root", "trol1230");
                return;
            }
            Properties prop = new Properties();
            try (InputStream is = new FileInputStream(file)) {
                prop.load(is);
            }
            host = prop.getProperty("server.host", "localhost");
            puerto = prop.getProperty("server.port", "8080");
            usuario = prop.getProperty("server.user", "root");
            contrasena = prop.getProperty("server.password", "trol230");

        } catch (IOException ex) {
            Logger.getLogger(ServConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarPropiedades(String host, String puerto, String usuario, String contrasena) {
        
        Properties prop = new Properties();
        prop.setProperty("server.host", host);
        prop.setProperty("server.port", puerto);
        prop.setProperty("server.user", usuario);
        prop.setProperty("server.password", contrasena);
        
        try (OutputStream os = new FileOutputStream(file)){
            prop.store(os, "Configuracion de VetServer.");
            this.host = host;               // Las actualizo aqui por si hay algun error en el .store
            this.puerto = puerto;           // (dentro del try)
            this.usuario = usuario;         // si lo hago fuera y hay error no se guardan en el fichero 
            this.contrasena = contrasena;   // pero se actualizarian en la clase.
        } catch (IOException ex) {
            Logger.getLogger(ServConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getHost() {
        return host;
    }

    public String getPuerto() {
        return puerto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getBaseUrl(){
        return "http://" + host + ":" + puerto;
    }
    
    
    
}
