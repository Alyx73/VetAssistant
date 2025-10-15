/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetclient.modelo.dao;

import com.apr.vetclient.modelo.vo.Usuario;
import com.apr.vetclient.util.REST;
import java.io.IOException;

/**
 *
 * @author Alex
 */
public class UsuarioDAO {

    public Usuario iniciarSesion(String usuario, String contrasena) throws IOException {
        return new REST("/usuarios", Usuario.class).login(usuario, contrasena);
    }

    public void modificar(Usuario usuario) throws IOException {
        new REST("/usuarios", Usuario.class).update(usuario.getIdUsuario(), usuario);
    }
}
