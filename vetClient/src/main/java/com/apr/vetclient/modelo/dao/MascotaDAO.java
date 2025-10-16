/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.modelo.dao;

import com.apr.vetclient.modelo.vo.Mascota;
import com.apr.vetclient.util.REST;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class MascotaDAO {

    public List<Mascota> buscarPorDueno(int idCliente) {
        List<Mascota> mascotas = new ArrayList<>();
        try {
            mascotas = new REST("/mascotas", Mascota.class).getPorParametro("/dueno/" + idCliente);
        } catch (IOException ex) {
            //Logger.getLogger(MascotaDAO.class.getName()).log(Level.SEVERE, null, ex); // No me hace falta esta excepcion
        }
        return mascotas;
    }

    public void alta(Mascota mascota) throws IOException {
        new REST("/mascotas", Mascota.class).create(mascota);
    }
}
