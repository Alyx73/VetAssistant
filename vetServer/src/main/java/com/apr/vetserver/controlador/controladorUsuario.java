/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetserver.controlador;


import com.apr.vetserver.modelo.dao.UsuarioDAO;
import com.apr.vetserver.modelo.vo.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Alex
 */
@RestController
@RequestMapping("/usuarios")
public class controladorUsuario {
    
    @Autowired
    private UsuarioDAO usuarioDAO;
    
    //GET todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.getUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    //GET usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        try {
            Usuario usuario = usuarioDAO.getUsuarioPorId(id);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //POST insertar nuevo usuario
    @PostMapping
    public ResponseEntity<String> addUsuario(@RequestBody Usuario usuario) {
        try {
            int result = usuarioDAO.addUsuario(usuario);
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Cliente añadido correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error al añadir cliente.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    //PUT modificar usuario
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        try {
            //usuario.setIdUsuario(id);
            int result = usuarioDAO.updateUsuario(usuario);
            if (result > 0) {
                return ResponseEntity.ok("Usuario actualizado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    //DELETE eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable int id) {
        try {
            int result = usuarioDAO.deleteUsuario(id);
            if (result > 0) {
                return ResponseEntity.ok("Usuario eliminado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }
    
    //LOGIN usuario
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credenciales) {
        Usuario usuario = usuarioDAO.validarCredenciales(credenciales.getUsuario(), credenciales.getContrasena());
        
        return (usuario != null)? ResponseEntity.ok(usuario): ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
