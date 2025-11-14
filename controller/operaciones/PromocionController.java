package controller.operaciones;

import logic.operaciones.PromocionLogic;
import model.DetallePromocion;
import model.Promocion;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PromocionController {
    private PromocionLogic promocionLogic;

    public PromocionController(Connection connection) {
        this.promocionLogic = new PromocionLogic(connection);
    }
    public String registrarPromocion(String[] parametros) throws SQLException {
        if (parametros.length < 6) {
            return "Parámetros insuficientes para REGISTRAR_PROMOCION. Se requiere: [cabecera, detalle]";
        }

        try {
            System.out.println("PARAMETROS: " + Arrays.toString(parametros));

            Promocion promocion = new Promocion();
            promocion.setNombre(parametros[0].replace("\"", "").trim());
            promocion.setDescripcion(parametros[1].replace("\"", "").trim());
            promocion.setFechaInicio(LocalDate.parse(parametros[2].replace("\"", "").trim()));
            promocion.setFechaFin(LocalDate.parse(parametros[3].replace("\"", "").trim()));
            promocion.setEstado(parametros[4].replace("\"", "").trim());

            // --- Extraer todo lo que empiece con DETALLE[
            StringBuilder detalleBuilder = new StringBuilder();
            for (int i = 5; i < parametros.length; i++) {
                detalleBuilder.append(parametros[i]).append(" ");
            }
            String detalleString = detalleBuilder.toString().trim();
            System.out.println("DETALLE STRING CRUDO: " + detalleString);

            // --- Parsear los detalles
            List<DetallePromocion> detalles = parsearDetalles(detalleString);

            if (detalles.isEmpty()) {
                return "No se especificaron detalles de productos para la promoción.";
            }

            promocion.setDetallePromocion(detalles);
            Promocion promocionRegistrada = promocionLogic.registrarPromocion(promocion);

            return "Promoción '" + promocionRegistrada.getNombre() +
                    "' registrada con ID: " + promocionRegistrada.getIdPromocion() +
                    " y " + detalles.size() + " detalles.";

        } catch (NumberFormatException e) {
            return "Error de formato en los datos numéricos (ID de producto o valor de descuento).";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al registrar la promoción: " + e.getMessage();
        }
    }


    private List<DetallePromocion> parsearDetalles(String detalleString) throws NumberFormatException {
        List<DetallePromocion> detalles = new ArrayList<>();
        if (detalleString == null || detalleString.isEmpty()) {
            return detalles;
        }

        // --- Extrae cada bloque "DETALLE[...]"
        Pattern patron = Pattern.compile("DETALLE\\[(.*?)\\]");
        Matcher matcher = patron.matcher(detalleString);

        while (matcher.find()) {
            String contenido = matcher.group(1).trim(); // ej: "1, FIJO, 5.00"
            String[] partes = contenido.split(",");
            if (partes.length == 3) {
                DetallePromocion dp = new DetallePromocion();
                dp.setIdProducto(Long.parseLong(partes[0].trim()));
                dp.setTipoDescuento(partes[1].trim());
                dp.setValorDescuento(new BigDecimal(partes[2].trim()));
                detalles.add(dp);
            }
        }

        System.out.println("DETALLES PARSEADOS: " + detalles.size());
        return detalles;
    }


    public String obtenerPorId(String[] parametros) throws SQLException {
        if (parametros.length < 1) {
            return "Parámetros insuficientes para BUSCAR_PROMOCION. Se requiere: [idpromocion]";
        }

        try {
            int idPromocion = Integer.parseInt(parametros[0].replace("\"", "").trim());
            Promocion promocion = promocionLogic.buscarPorId(idPromocion);

            if (promocion != null) {
                int numDetalles = promocion.getDetallePromocion() != null ? promocion.getDetallePromocion().size() : 0;
                return "Promoción encontrada: ID=" + promocion.getIdPromocion() +
                        ", Nombre=" + promocion.getNombre() +
                        ", Válida hasta=" + promocion.getFechaFin() +
                        ", Productos en promoción: " + numDetalles;
            } else {
                return "Promoción no encontrada con ID: " + idPromocion;
            }
        } catch (NumberFormatException e) {
            return "El ID de promoción proporcionado es inválido";
        }
    }

    public String listarPromociones() throws SQLException {
        try {
            List<Promocion> promociones = promocionLogic.listarTodos();

            if (promociones.isEmpty()) {
                return "No se encontraron promociones registradas.";
            }

            StringBuilder sb = new StringBuilder("Lista de Promociones:\n");
            for (Promocion p : promociones) {
                sb.append("ID=").append(p.getIdPromocion())
                        .append(", Nombre=").append(p.getNombre())
                        .append(", Vigencia=").append(p.getFechaInicio()).append(" a ").append(p.getFechaFin())
                        .append(", Estado=").append(p.getEstado()).append("\n");
            }
            return sb.toString();
        } catch (SQLException e) {
            return "Error SQL al listar las promociones: " + e.getMessage();
        }
    }
}
