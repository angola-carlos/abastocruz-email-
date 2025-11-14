package controller.administracion;

import logic.administracion.UsuarioLogic;
import model.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioLogic usuarioLogic;

    public UsuarioController(Connection connection) {
        this.usuarioLogic = new UsuarioLogic(connection);
    }


    public String registrarUsuario(String[] parametros) throws SQLException {
        if (parametros.length < 5) {
            return "Parámetros insuficientes para REGISTRAR_USUARIO. Se requieren: [nombre, apellido, email, password, rol]";
        }

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(parametros[0].replace("\"", "").trim());
            usuario.setApellido(parametros[1].replace("\"", "").trim());
            usuario.setEmail(parametros[2].replace("\"", "").trim());
            usuario.setPassword(parametros[3].replace("\"", "").trim());
            usuario.setRol(parametros[4].replace("\"", "").trim());

            Usuario usuarioCreado = usuarioLogic.registrarUsuario(usuario);
            return "Usuario registrado exitosamente con ID: " + usuarioCreado.getIdUsuario();
        } catch (SQLException e) {
            return "Error SQL al registrar el usuario: " + e.getMessage();
        }
    }

    // --- Método ACTUALIZAR ---
    // Recibe: [idUsuario, nombre, apellido, email, password, rol]
    public String actualizarUsuario(String[] parametros) throws SQLException {
        if (parametros.length < 6) {
            return "Parámetros insuficientes para ACTUALIZAR_USUARIO. Se requieren: [idUsuario, nombre, apellido, email, password, rol]";
        }

        try {
            long idUsuario = Integer.parseInt(parametros[0].replace("\"", "").trim());

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario.setNombre(parametros[1].replace("\"", "").trim());
            usuario.setApellido(parametros[2].replace("\"", "").trim());
            usuario.setEmail(parametros[3].replace("\"", "").trim());
            usuario.setPassword(parametros[4].replace("\"", "").trim());
            usuario.setRol(parametros[5].replace("\"", "").trim());

            boolean actualizado = usuarioLogic.actualizarUsuario(usuario);
            return actualizado ? "Usuario ID " + idUsuario + " actualizado exitosamente" : "No se encontró o no se pudo actualizar el usuario ID " + idUsuario;

        } catch (NumberFormatException e) {
            return "El primer parámetro (ID) es un número inválido.";
        } catch (SQLException e) {
            return "Error SQL al actualizar el usuario: " + e.getMessage();
        }
    }

    public String eliminarUsuario(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para ELIMINAR_USUARIO. Se requiere: [idUsuario]";
        }

        try {
            int idUsuario = Integer.parseInt(parametros[0].replace("\"", "").trim());
            boolean eliminado = usuarioLogic.eliminarUsuario(idUsuario);

            return eliminado ? "Usuario ID " + idUsuario + " eliminado exitosamente" : "No se encontró o no se pudo eliminar el usuario ID " + idUsuario;
        } catch (NumberFormatException e) {
            return "El ID de usuario proporcionado es inválido";
        } catch (SQLException e) {
            return "Error SQL al eliminar el usuario: " + e.getMessage();
        }
    }

    public String listarTodos() throws SQLException {
        try {
            List<Usuario> usuarios = usuarioLogic.listarUsuarios();

            if (usuarios.isEmpty()) {
                return "No se encontraron usuarios registrados.";
            }

            StringBuilder sb = new StringBuilder("Lista de Usuarios:\n");
            for (Usuario u : usuarios) {
                sb.append("ID=").append(u.getIdUsuario())
                        .append(", Nombre=").append(u.getNombre())
                        .append(", Email=").append(u.getEmail())
                        .append(", Rol=").append(u.getRol()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar los usuarios: " + e.getMessage();
        }
    }
}