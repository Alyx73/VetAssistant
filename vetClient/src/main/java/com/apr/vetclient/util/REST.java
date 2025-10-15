/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.util;

import com.apr.vetclient.modelo.vo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * Clase genérica para comunicación REST entre el cliente y el servidor Spring Boot.
 * Soporta operaciones GET, POST, PUT y DELETE.
 *
 * @param <T> tipo de objeto que se enviará/recibirá (Cliente, Mascota, etc.)
 */
public class REST<T> {

    private String baseUrl;
    private Class<T> tipo;
    private Gson gson = new Gson();
    private String cabeceraAutorizacion;

    public REST(String fragmentoPath, Class<T> tipo) throws IOException{
        
        PreferenciasConfig props = new PreferenciasConfig();
        this.baseUrl = props.getBaseUrl() + fragmentoPath;
        this.tipo = tipo;
        //Recojo los valores de la instancia de ServConfig que ya los ha decodificado del fichero
        String auto = props.getUsuario() + ":" + props.getContrasena();
        this.cabeceraAutorizacion = "Basic " + new String(Base64.getEncoder().encodeToString(auto.getBytes()));
        
    }

    // GET para todos los registros
    public List<T> getAll() throws IOException {    // Posibiliad de unificar .getAll y .getPorParamwetro --> ¿pierdo claridad en metodos?
        URL url = new URL(baseUrl); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);

        verificarRespuestaRest(conn);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            Type listaTipo = TypeToken.getParameterized(List.class, tipo).getType();
            return gson.fromJson(br, listaTipo);
        } finally {
            conn.disconnect();
        }
    }

    // GET para un registro por ID
    public T getPorId(int id) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);

        verificarRespuestaRest(conn);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            return gson.fromJson(br, tipo);
        } finally {
            conn.disconnect();
        }
    }
    
    // GET para busquedas con filtro distinto al ID
    public List<T> getPorParametro(String fragmentoPath) throws IOException{
        URL url = new URL( baseUrl + fragmentoPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);
        
        verificarRespuestaRest(conn);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            Type listaTipo = TypeToken.getParameterized(List.class, tipo).getType();
            return gson.fromJson(br, listaTipo);
        } finally {
            conn.disconnect();
        }
    }


    // POST para crear nuevo registro
    public void create(T obj) throws IOException {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);
        conn.setDoOutput(true); // Obligatorio cuando mando datos en la peticion 

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        verificarRespuestaRest(conn);
        conn.disconnect();
    }

    // PUT para actualizar registro
    public void update(int id, T obj) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        verificarRespuestaRest(conn);
        conn.disconnect();
    }

    // ---------------------------
    // DELETE: Eliminar registro
    // ---------------------------
    public void delete(int id) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);

        verificarRespuestaRest(conn);
        conn.disconnect();
    }

    public Usuario login(String user, String password) throws IOException {
        URL url = new URL(baseUrl + "/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", cabeceraAutorizacion);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = gson.toJson(new Usuario(user, password)).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();

        if (code == 200) {
            // Login correcto → leer el usuario devuelto
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                return gson.fromJson(reader, Usuario.class); // Devuelvo el Usuario recibido
                
            } finally {
            conn.disconnect();
        }
        } else if (code == 401) {
            return null; // Credenciales incorrectas
        } else {
            throw new IOException("Error en el servidor: " + code);
        }
        
    }


    
    // Método para comprobar si hay error en la respuesta REST
    private void verificarRespuestaRest(HttpURLConnection conn) throws IOException {
        int respuesta = conn.getResponseCode();
        if (respuesta < 200 || respuesta >= 300) {
            String error;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                error = br.readLine();
            }
            throw new IOException(error);
        }
    }
}

