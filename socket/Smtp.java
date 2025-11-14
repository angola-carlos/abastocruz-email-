package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Smtp {
    //private String server = "mail.ficct.uagrm.edu.bo";
    //private String server = "www.tecnoweb.org.bo";
    private String server = "mail.tecnoweb.org.bo";
    private int port = 25;
    private String from = "grupo18sa@tecnoweb.org.bo";
    //private String to = "angola.carlos@ficct.uagrm.edu.bo";
    private String to = "angolausmayo@gmail.com";

    public void enviarEmail(String subject, String body){
        try (Socket socket = new Socket(server, port);
             BufferedReader leer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter escribir = new PrintWriter(socket.getOutputStream(), true);) {

            System.out.println("1. Servidor: " + leer.readLine());

            escribir.println("HELO " + server);
            System.out.println("2. Servidor: " + leer.readLine());

            escribir.println("MAIL FROM: <" + from + ">");
            System.out.println("3. Servidor: " + leer.readLine());

            escribir.println("RCPT TO: <" + to + ">");
            System.out.println("4. Servidor: " + leer.readLine());

            escribir.println("DATA");
            System.out.println("5. Servidor: " + leer.readLine());

            escribir.print("Subject: " + subject + "\r\n");
            escribir.print("From: " + from + "\r\n");
            escribir.print("To: " + to + "\r\n");
            escribir.print("\r\n");
            escribir.print(body.replace("\n", "\r\n") + "\r\n");
            escribir.print(".\r\n");
            escribir.flush();

            System.out.println("6. Servidor: " + leer.readLine());

            escribir.println("QUIT");
            System.out.println("7. Servidor: " + leer.readLine());

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
