/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetserver.modelo.dao;

import com.apr.vetserver.modelo.vo.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */
@Repository
public class ClienteDAO {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    // Método para listar todos los clientes
    public List<Cliente> getClientes() {
        String sql = "SELECT * FROM cliente";
        return jdbc.query(sql, (rs, rowNum)
                -> new Cliente(rs.getInt(1),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                )
        );
    }
    
    // Método para buscar cliente por id
    public Cliente getClientePorId(int id) {
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        return jdbc.query(sql, (rs, rowNum)
                -> new Cliente(rs.getInt(1),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                ),
                id // Paso como argumento el id del cliente
        ).stream().findFirst().orElse(null);    // Recojo la primera fila y devuelvo null si esta vacía
    }
    
    // Método para insertar cliente
    public int addCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellidos, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        return jdbc.update(sql,
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getMail(),
                cliente.getDireccion()
        );
    }
    
    // Método para modificar cliente
    public int updateCliente(Cliente cliente){
        String sql = "UPDATE cliente SET nombre=?, apellidos=?, telefono=?, email=?, direccion=? WHERE idCliente = ?";
        return jdbc.update(sql,
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getTelefono(),
                cliente.getMail(),
                cliente.getDireccion(),
                cliente.getIdCliente()
        );
    }
    
    // Método para eliminar cliente
    public int deleteCliente(int id){
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        return jdbc.update(sql, id);
    }
}
