/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetserver.modelo.dao;

import com.apr.vetserver.modelo.vo.Mascota;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */
@Repository
public class MascotaDAO {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    // Métodos CRUD
    // Método para listar todas las mascotas
    public List<Mascota> getMascotas() {
        String sql = "SELECT * FROM mascota";
        return jdbc.query(sql, (rs, rowNum)
                -> new Mascota(
                        rs.getInt(1),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        //rs.getDate("fechaNacimiento").toLocalDate(),
                        //rs.getString("fechaNacimiento"),
                        rs.getTimestamp("fechaNacimiento"),
                        rs.getInt("idCliente"),
                        rs.getString("foto")
                )
        );
    }
    
    // Método para buscar mascota por id
    public Mascota getMascotaPorId(int id) {
        String sql = "SELECT * FROM mascota WHERE idMascota = ?";
        return jdbc.query(sql, (rs, rowNum)
                -> new Mascota(
                        rs.getInt("idMascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        //rs.getDate("fechaNacimiento").toLocalDate(),
                        //rs.getString("fechaNacimiento"),
                        rs.getTimestamp("fechaNacimiento"),
                        rs.getInt("idCliente"),
                        rs.getString("foto")
                ),
                id // Paso como argumento el id de la mascota
        ).stream().findFirst().orElse(null);  // Recojo la primera fial devuelta y devuelvo null si esta vacía
    }

    // Método para insertar mascota
    public int addMascota(Mascota mascota) {
        String sql = "INSERT INTO mascota (nombre, especie, raza, fechaNacimiento, idCliente, foto) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbc.update(sql,
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getFechaNacimiento(),
                //mascota.getFechaNacimiento(),
                mascota.getIdCliente(),
                mascota.getFoto()
        );
    }

    // Método para modificar mascota
    public int updateMascota(Mascota mascota) {
        String sql = "UPDATE mascota SET nombre=?, especie=?, raza=?, fechaNacimiento=?, idCliente=?, foto=? WHERE idMascota=?";
        return jdbc.update(sql,
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                //java.sql.Date.valueOf(mascota.getFechaNacimiento()),
                mascota.getFechaNacimiento(),
                mascota.getIdCliente(),
                mascota.getFoto(),
                mascota.getIdMascota()
        );
    }

    // Metodo para eliminar mascota
    public int deleteMascota(int id) {
        String sql = "DELETE FROM mascota WHERE idMascota=?";
        return jdbc.update(sql, id);
    }
    
    // Método para buscar todas las mascotas de un dueño
    public List<Mascota> getMascotasPorDueno(int idDueno) {
        String sql = "SELECT * FROM mascota WHERE idCliente = ?";
        return jdbc.query(sql, (rs, rowNum)
                -> new Mascota(
                        rs.getInt(1),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        //rs.getDate("fechaNacimiento").toLocalDate(),
                        rs.getTimestamp("fechaNacimiento"),
                        rs.getInt("idCliente"),
                        rs.getString("foto")
                ),
            idDueno // Paso como argumento el id de la mascota
        );
    }
}

