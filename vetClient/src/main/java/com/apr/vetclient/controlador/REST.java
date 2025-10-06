/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    public REST(String baseUrl, Class<T> tipo) {
        
        this.baseUrl = baseUrl;
        this.tipo = tipo;
    }

    // GET para todos los registros
    public List<T> getAll() throws IOException {
        URL url = new URL(baseUrl); 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

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
        
        verificarRespuestaRest(conn);
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            Type listaTipo = TypeToken.getParameterized(List.class, tipo).getType();
            return gson.fromJson(br, listaTipo);
        } finally {
            conn.disconnect();
        }
    }

    // ---------------------------
    // POST: Crear nuevo registro
    // ---------------------------
    public void create(T obj) throws IOException {
        URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = gson.toJson(obj).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        verificarRespuestaRest(conn);
        conn.disconnect();
    }

    // ---------------------------
    // PUT: Actualizar registro
    // ---------------------------
    public void update(int id, T obj) throws IOException {
        URL url = new URL(baseUrl + "/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
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

        verificarRespuestaRest(conn);
        conn.disconnect();
    }

    // Método para comprobar si hay error en la respuesta REST
    private void verificarRespuestaRest(HttpURLConnection conn) throws IOException {
        int respuesta = conn.getResponseCode();
        if (respuesta < 200 || respuesta >= 300) {
            String error;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                error = br.readLine();
            } catch (Exception e) {
                error = "Error HTTP: " + respuesta;
            }
            throw new IOException(error);
        }
    }
}

