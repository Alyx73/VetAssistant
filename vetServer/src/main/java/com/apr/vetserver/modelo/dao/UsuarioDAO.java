/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apr.vetserver.modelo.dao;

import com.apr.vetserver.modelo.vo.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alex
 */
@Repository
public class UsuarioDAO {
    
    @Autowired
    private JdbcTemplate jdbc;
    
    // Método para listar todos los usuarios
    public List<Usuario> getUsuarios(){
        String sql = "SELECT * FROM usuario";
        return jdbc.query(sql, (rs, rowNum)
                -> new Usuario(rs.getInt(1),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getString("idioma")
                )
        );
    }
    
    
    // Método para buscar usuario por id
    public Usuario getUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        return jdbc.query(sql, (rs, rowNum)
                -> new Usuario(rs.getInt(1),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getString("idioma")
                ),id // Paso como argumento el id del usuario
        ).stream().findFirst().orElse(null);    // Recojo la primera fila y devuelvo null si esta vacía
    }
    
    // Método para insertar usuario
    public int addUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (usuario, contrasena, rol) VALUES (?, ?, ?)";
        return jdbc.update(sql,
                usuario.getUsuario(),
                usuario.getContrasena(),
                usuario.getRol()
        );
    }
    
    // Método para modificar usuario
    public int updateUsuario(Usuario usuario){
        String sql = "UPDATE usuario SET usuario=?, contrasena=?, rol=?, idioma=? WHERE idUsuario = ?";
        return jdbc.update(sql,
                usuario.getUsuario(),
                usuario.getContrasena(),
                usuario.getRol(),
                usuario.getIdioma()
                ,usuario.getIdUsuario());
    }
    
    // Método para eliminar usuario
    public int deleteUsuario(int id){
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        return jdbc.update(sql, id);
    }
    
    // Método para verificar usuario y contraseña
    // Si son OK devuelve el Usuario
    public Usuario validarCredenciales(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE usuario=? AND contrasena=?";
        return jdbc.query(sql, (rs, rowNum)
                -> new Usuario(rs.getInt(1),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getString("idioma")
                ), usuario, contrasena  // Paso como argumento el usuario y  la contraseña recibidas
        ).stream().findFirst().orElse(null);    // Recojo la primera fila y devuelvo null si esta vacía
    }
}
