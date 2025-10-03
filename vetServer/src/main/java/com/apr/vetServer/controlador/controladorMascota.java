/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetServer.controlador;

import com.apr.vetServer.modelo.dao.MascotaDAO;
import com.apr.vetServer.modelo.vo.Mascota;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping("/mascotas")
public class controladorMascota {
    
    @Autowired
    private MascotaDAO mascotaDAO;
    
    //GET todas las mascotas
    //Devuelve una lista con todas las mascota o null si no encuentra nada
    @GetMapping
    public ResponseEntity<List<Mascota>> getAll() {
        try {
            List<Mascota> mascotas = mascotaDAO.getMascotas();
            return ResponseEntity.ok(mascotas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    //GET mascota por ID
    //Devuelve la mascota o null si no la encuentra
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getPorId(@PathVariable int id) {  
        try {
            Mascota mascota = mascotaDAO.getMascotaPorId(id);
            if (mascota == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(mascota);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //POST insertar nueva mascota
    // Devuelve un string con el resultado de accion (error/ok)
    @PostMapping
    public ResponseEntity<String> add(@RequestBody Mascota mascota) {  
        try {
            int result = mascotaDAO.addMascota(mascota);
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Mascota añadida correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al añadir mascota.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }

    //PUT modificar mascota
    // Devuelve un string con el resultado de accion (error/ok)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Mascota mascota) {
        try {
            mascota.setIdMascota(id);
            int result = mascotaDAO.updateMascota(mascota);
            if (result > 0) {
                return ResponseEntity.ok("Mascota actualizada correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Mascota no encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    //DELETE - eliminar mascota
    // Devuelve un string con el resultado de accion (error/ok)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMascota(@PathVariable int id) {
        try {
            int result = mascotaDAO.deleteMascota(id);
            if (result > 0) {
                return ResponseEntity.ok("Mascota eliminada correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Mascota no encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }
}

