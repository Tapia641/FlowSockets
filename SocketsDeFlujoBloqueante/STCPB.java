
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.print.event.PrintJobListener;
import java.io.OutputStreamWriter;

public class STCPB {
    public static void main(String[] args) {

        // SIEMPRE PONER EL SOCKET EN UN TRY-CATCH
        try {
            // SE CREA EL SOCKET
            ServerSocket s = new ServerSocket(1234);
            System.out.println("Esperando cliente...");

            while (true) {
                // BLOQUEO
                Socket cl = s.accept();
                System.out.print("Conexion establecida desde " + cl.getInetAddress() + ":" + cl.getPort());
                String mensaje = "Hola Mundo";
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));

                // SE ENVIA EL MENSAJE
                pw.println(mensaje);

                // SE LIMPIA EL FLUJO EN ORDEN
                pw.flush();
                pw.close();
                cl.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}