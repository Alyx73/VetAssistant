/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetServer.controlador;

import com.apr.vetServer.modelo.dao.ClienteDAO;
import com.apr.vetServer.modelo.vo.Cliente;
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
@RequestMapping("/clientes")
public class controladorCliente {
    
    @Autowired
    private ClienteDAO clienteDAO;
    
    //GET todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        try {
            List<Cliente> clientes = clienteDAO.getClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    //GET cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        try {
            Cliente cliente = clienteDAO.getClientePorId(id);
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //POST insertar nuevo cliente
    @PostMapping
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        try {
            int result = clienteDAO.addCliente(cliente);
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

    //PUT modificar cliente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        try {
            cliente.setIdCliente(id);
            int result = clienteDAO.updateCliente(cliente);
            if (result > 0) {
                return ResponseEntity.ok("Cliente actualizado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cliente no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    //DELETE eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable int id) {
        try {
            int result = clienteDAO.deleteCliente(id);
            if (result > 0) {
                return ResponseEntity.ok("Cliente eliminado correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cliente no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }
}
