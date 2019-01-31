import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class CTCPB {
    public static void main(String[] args) {
        // SIEMPRE PONER EL SOCKET EN UN TRY-CATCH
        try {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe la direccion del servidor:");
            String host = br1.readLine();
            System.out.printf("\nEscriba el puerto:");
            int port = Integer.parseInt(br1.readLine());

            // CERRAMOS EL SOCKET
            Socket cl = new Socket(host, port);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));

            // NOS CONECTAMOS
            String mensaje = br2.readLine();
            System.out.println("Recibimos un mensaje del servidor");
            System.out.println("Mensaje: " + mensaje);

            // SE LIMPIA EL FLUJO EN ORDEN
            br1.close();
            br2.close();
            cl.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}