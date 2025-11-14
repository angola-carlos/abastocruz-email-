package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeUtility; // NECESITA LA LIBRERÍA

public class Pop {
    //private String server = "www.tecnoweb.org.bo";
    private String server = "mail.tecnoweb.org.bo";
    private int port = 110;
    private String user = "grupo18sa";
    private String pass = "grup018grup018*";
    private boolean listening = false;
    private Thread listenerThread;

    public String revisarEmail() {
        String subjectCodificado = "";

        try (Socket socket = new Socket(server, port);
             BufferedReader leer = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter escribir = new PrintWriter(socket.getOutputStream(), true)) {

            socket.setSoTimeout(5000);

            System.out.println("1. Servidor: " + leer.readLine());

            escribir.println("USER " + user);
            System.out.println("2. Servidor: " + leer.readLine());

            escribir.println("PASS " + pass);
            System.out.println("3. Servidor: " + leer.readLine());

            escribir.println("STAT");
            String respuesta = leer.readLine();
            System.out.println("4. Servidor: " + respuesta);

            if (!respuesta.startsWith("-ERR")) {
                String[] partesStat = respuesta.split(" ");
                if (partesStat.length >= 2) {
                    int numMensajes = Integer.parseInt(partesStat[1]);
                    if (numMensajes > 0) {
                        escribir.println("RETR " + numMensajes);
                        String retrRespuesta;

                        while (!(retrRespuesta = leer.readLine()).equals(".")) {
                            if (retrRespuesta.startsWith("Subject: ")) {
                                subjectCodificado = retrRespuesta.replace("Subject: ", "").trim();
                            } else if (!subjectCodificado.isEmpty() && (retrRespuesta.startsWith(" ") || retrRespuesta.startsWith("\t"))) {
                                subjectCodificado += retrRespuesta.trim();
                            }
                        }
                    }
                }
            }


            escribir.println("QUIT");
            System.out.println("5. Servidor: " + leer.readLine());

            if (!subjectCodificado.isEmpty()) {
                return decodificarSubject(subjectCodificado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjectCodificado;
    }

    private String decodificarSubject(String subjectCodificado) {
        subjectCodificado = subjectCodificado.replaceAll("[\\r\\n\\t]+", " ").trim();
        StringBuilder resultado = new StringBuilder();
        String[] partes = subjectCodificado.split("(?==\\?UTF-8\\?)");

        for (String parte : partes) {
            parte = parte.trim();
            if (parte.startsWith("=?") && parte.endsWith("?=")) {
                try {
                    resultado.append(MimeUtility.decodeText(parte));
                } catch (Exception e) {
                    resultado.append(parte);
                }
            } else {
                resultado.append(parte);
            }
        }

        return resultado.toString();
    }


}