/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetServer.modelo.vo;

import java.time.LocalDate;



/**
 *
 * @author Alex
 */
public class Mascota {
    
    private int idMascota;
    private String nombreString;
    private String especieString;
    private String razaString;
    private LocalDate fechaNacimiento;
    private int idCliente;
    private String foto;

    public Mascota(int idMascota, String nombre, String especie, String raza, LocalDate fechaNacimiento, int idCliente, String foto) {
        this.idMascota = idMascota;
        this.nombreString = nombre;
        this.especieString = especie;
        this.razaString = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.idCliente = idCliente;
        this.foto = foto;
    }

    // Getters y Setters
    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombreString;
    }

    public void setNombre(String nombre) {
        this.nombreString = nombre;
    }

    public String getEspecie() {
        return especieString;
    }

    public void setEspecie(String especie) {
        this.especieString = especie;
    }

    public String getRaza() {
        return razaString;
    }

    public void setRaza(String raza) {
        this.razaString = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
}
