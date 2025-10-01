/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetServer.controlador;

import com.apr.vetServer.modelo.dao.ClienteDAO;
import com.apr.vetServer.modelo.vo.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@RestController
//@RequestMapping("/clientes")
public class clienteController {
    
    @Autowired
    private ClienteDAO clienteDAO;
    
    //GET todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> getAll() {
        return clienteDAO.getClientes();
    }
}
