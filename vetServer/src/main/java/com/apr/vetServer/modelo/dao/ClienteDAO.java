/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetServer.modelo.dao;

import com.apr.vetServer.modelo.vo.Cliente;
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
    
    public List<Cliente> getClientes(){
        String sql = "select * from cliente";
        return jdbc.query(sql, (rs, rowNum) -> 
                new Cliente (rs.getInt(1),
                             rs.getString("nombre"),
                             rs.getString("apellidos"),
                             rs.getString("telefono"),
                             rs.getString("email"),
                             rs.getString("direccion")
                )
        );
    }
    
}
