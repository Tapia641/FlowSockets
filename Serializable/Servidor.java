import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String args[]) {
        iniciarServidor();
    }

    public static void iniciarServidor() {

        /* DECLARAMOS LOS OBJETOS */
        ObjectOutputStream objetoSalida = null;
        ObjectInputStream objetoEntrada = null;

        try {

            ServerSocket socketServidor = new ServerSocket(9999);
            System.out.println("Servidor iniciado...");

            while (true) {

                /* ESCUCHA UNA CONEXION A ESTE SOCKET Y LA ACEPTA */
                Socket socketCliente = socketServidor.accept();

                /* RECIBIMOS INFORMACION DEL CLIENTE */
                System.out.println("Cliente conectado desde " + socketCliente.getInetAddress() + " : " + socketCliente.getPort());

                /* INICIALIZAMOS LOS OBJETOS DE ENTRADA Y SALIDA */
                objetoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
                objetoEntrada = new ObjectInputStream(socketCliente.getInputStream());

                /* RECIBIMOS EL OBJETO DEL CLIENTE */
                Usuario usuario1 = (Usuario) objetoEntrada.readObject();
                System.out.println("Objeto recibido... Extrayendo informacion");
                System.out.println("Nombre: " + usuario1.getNombre());
                System.out.println("A. paterno: " + usuario1.getApaterno());
                System.out.println("A. materno: " + usuario1.getAmaterno());
                System.out.println("Password: " + usuario1.getPassword());
                System.out.println("Edad: " + usuario1.getEdad());

                /* REENVIAMOS EL OBJETO */
                System.out.println("Devolviendo objeto...");
                objetoSalida.writeObject(usuario1);
                objetoSalida.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
