import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class STCPBArchivo {
    public static void main(String[] args) {
        ServidorArchivo();
    }

    public static void ServidorArchivo() {
        try {
            /* PUERTO EN EL QUE ESCUCHA PETICIONES */
            ServerSocket socketServidor = new ServerSocket(7000);
            System.out.println("Esperando cliente...");

            while (true) {
                /* BLOQUEO HASTA QUE RECIBA ALGUNA PETICION DEL CLIENTE */
                Socket socketCliente = socketServidor.accept();
                System.out.println(
                        "Conexion establecida desde " + socketCliente.getInetAddress() + ":" + socketCliente.getPort());

                /* ESTABLECEMOS EL CANAL DE ENTRADA */
                DataInputStream dis = new DataInputStream(socketCliente.getInputStream());
                byte[] b = new byte[1024];
                String nombre = dis.readUTF();
                System.out.println("Recibimos el archivo " + nombre);
                long tam = dis.readLong();
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

                /* SECCION PARA RECIBIR EL ARCHIVO */
                long recibido = 0;
                int n, porcentaje;
                while (recibido < tam) {
                    n = dis.read(b);
                    dos.write(b, 0, n);
                    dos.flush();

                    /* PORCENTAJE */
                    recibido += n;
                    porcentaje = (int) (recibido * 100 / tam);
                    System.out.println("Recibido: " + porcentaje + "%\r");
                }
                System.out.println("Archivo recibido.");
                dos.close();
                dis.close();
                socketCliente.close();
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}