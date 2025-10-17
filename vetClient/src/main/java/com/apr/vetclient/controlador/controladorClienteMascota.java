
package com.apr.vetclient.controlador;


import com.apr.vetclient.modelo.dao.*;
import com.apr.vetclient.modelo.vo.Cliente;
import com.apr.vetclient.modelo.vo.Mascota;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Alex
 */
public class controladorClienteMascota {
    
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static MascotaDAO mascotaDAO = new MascotaDAO();


    public static void altaCliente(Cliente cliente) throws IOException {
        clienteDAO.alta(cliente);
    }

    public static void modificarCliente(int idCliente, Cliente cliente) throws IOException {
        clienteDAO.modificar(idCliente, cliente);
    }

    public static void borrarCliente(int idCliente) throws IOException {
        clienteDAO.borrar(idCliente);
    }

    public static Cliente buscarClientePorId(int idCliente) throws IOException {
        return clienteDAO.buscarPorId(idCliente);
    }

    public static Cliente buscarClientePorDni(String dni) throws IOException {
        return clienteDAO.buscarPorDni(dni);
    }

    public static void altaMascota(Mascota mascota) throws IOException {
        mascotaDAO.alta(mascota);
    }

    public static Mascota buscarMascotaPorChip(String chip) throws IOException {
        return mascotaDAO.buscarPorChip(chip);
    }

    public static List<Mascota> buscarMascotasPorDueno(int idCliente) throws IOException {
        return mascotaDAO.buscarPorDueno(idCliente);
    }


    
    
    
}
