import config.ConexionDB;
import controller.ComandoEmailController;
import socket.Pop;
import socket.Smtp;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ConexionDB db = new ConexionDB();
        try (Connection conn = db.getConnection()) {
            System.out.println("Conexión a PostgreSQL establecida.");

            Pop pop = new Pop();
            String subject = pop.revisarEmail();

            if (subject != null && !subject.isEmpty()) {
                System.out.println("Comando recibido por correo: " + subject);

                ComandoEmailController controller = new ComandoEmailController(conn);
                String resultado = controller.procesarComando(subject);

                System.out.println("Resultado de ejecución:");
                System.out.println(resultado);

                new Smtp().enviarEmail("Respuesta al comando", resultado);
            } else {
                System.out.println(" No se recibió ningún comando por correo.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error al ejecutar el sistema:");
            e.printStackTrace();
        }
    }
}