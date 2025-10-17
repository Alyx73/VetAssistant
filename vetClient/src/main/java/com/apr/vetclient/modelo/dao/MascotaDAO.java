/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.modelo.dao;

import com.apr.vetclient.modelo.vo.Mascota;
import com.apr.vetclient.util.REST;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Alex
 */
public class MascotaDAO {

    public List<Mascota> buscarPorDueno(int idCliente) throws IOException{
        return new REST("/mascotas", Mascota.class).getPorParametro("/dueno/" + idCliente);
    }

    public void alta(Mascota mascota) throws IOException {
        new REST("/mascotas", Mascota.class).create(mascota);
    }

    public Mascota buscarPorChip(String chip) throws IOException {
        return (Mascota) new REST("/mascotas", Mascota.class).getPorParametro("/chip/" + chip).getFirst();
    }
}
