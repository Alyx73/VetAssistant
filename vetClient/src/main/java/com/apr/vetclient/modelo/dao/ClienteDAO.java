/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.modelo.dao;

import com.apr.vetclient.modelo.vo.Cliente;
import com.apr.vetclient.util.REST;
import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class ClienteDAO {

    public Cliente buscarPorDni(String dni) {
            Cliente cli = null;
        try {
            cli = (Cliente) (new REST("/clientes", Cliente.class).getPorParametro("/dni/" + dni)).getFirst();
        } catch (IOException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cli;
    }

    public void alta(Cliente cliente) throws IOException {
        new REST("/clientes", Cliente.class).create(cliente);
    }

    public void modificar(int id, Cliente cliente) throws  IOException{
        new REST("/clientes", Cliente.class).update(id, cliente);
    }
    
    
    
    
}
