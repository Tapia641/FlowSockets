
/* ALUMNO: HERNANDEZ TAPIA LUIS ENRIQUE */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CTCPB {
    public static void main(String[] args) {
        Cliente();
    }

    public static void Cliente() {
        /* SIEMPRE PONER EL SOCKET EN UN TRY-CATCH */
        try {
            /* PREPARAMOS LOS DATOS DE LA CONEXION */
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe la direccion del servidor:");
            String host = teclado.readLine();
            System.out.printf("\nEscriba el puerto:");
            int port = Integer.parseInt(teclado.readLine());

            /* CERRAMOS EL SOCKET DEL LADO DEL CLIENTE */
            Socket socketCliente = new Socket(host, port);

            /* OBTENEMOS EL CANAL DE ENTRADA */
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            /* OBTENEMOS EL CANAL DE SALIDA */
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(socketCliente.getOutputStream()));

            /* ENVIAMOS INFORMACION AL SERVIDOR */
            System.out.println("Enviando saludo...");
            String mensaje = "Hola servidor, soy el cliente.";
            salida.println(mensaje);
            salida.flush();// Lo que faltaba ;)

            /* RECIBIMOS INFORMACION DEL SERVIDOR */
            mensaje = entrada.readLine();
            System.out.println("Recibimos un mensaje del servidor");
            System.out.println("Mensaje: " + mensaje);

            // SE LIMPIA EL FLUJO EN ORDEN
            salida.flush();
            salida.close();
            teclado.close();
            entrada.close();
            socketCliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
