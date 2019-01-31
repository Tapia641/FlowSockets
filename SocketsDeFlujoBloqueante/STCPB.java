
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.print.event.PrintJobListener;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class STCPB {
    public static void main(String[] args) {
        Servidor();
    }

    public static void Servidor() {
        /* SIEMPRE PONER EL SOCKET EN UN TRY-CATCH */
        try {
            /* PUERTO EN EL QUE ESCUCHA PETICIONES */
            ServerSocket socketServidor = new ServerSocket(1234);
            System.out.println("Esperando cliente...");

            while (true) {
                /* BLOQUEO HASTA QUE RECIBA ALGUNA PETICION DEL CLIENTE */
                Socket socketCliente = socketServidor.accept();
                System.out.println(
                        "Conexion establecida desde " + socketCliente.getInetAddress() + ":" + socketCliente.getPort());

                /* ESTABLECEMOS EL CANAL DE ENTRADA */
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

                /* OBTENEMOS EL CANAL DE SALIDA */
                PrintWriter salida = new PrintWriter(new OutputStreamWriter(socketCliente.getOutputStream()));

                /* ENVIAMOS INFORMACION AL CLIENTE */

                /*
                 * String mensaje = entrada.readLine();
                 * System.out.println("Recibimos un mensaje del servidor");
                 * System.out.println("Mensaje: " + mensaje);
                 */
                String mensaje = "Hola cliente, soy el servidor";
                salida.println(mensaje);

                // String res = entrada.readLine();
                // System.out.println("Recibimos un mensaje del cliente");
                // System.out.println("Mensaje: " + res);

                // SE LIMPIA EL FLUJO EN ORDEN
                salida.flush();
                salida.close();
                entrada.close();
                socketCliente.close();
                // socketServidor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}