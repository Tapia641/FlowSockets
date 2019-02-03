import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

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
                DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
                byte[] b = new byte[1024];
                String nombre = entrada.readUTF();
                System.out.println("Recibimos el archivo " + nombre);
                long tam = entrada.readLong();
                DataOutputStream salida = new DataOutputStream(new FileOutputStream(nombre));

                /* SECCION PARA RECIBIR EL ARCHIVO */
                long recibido = 0;
                int n, porcentaje;

                n = entrada.read(b);
                JOptionPane.showMessageDialog(null, n);
                
                while (recibido < tam) {
                    n = entrada.read(b);
                    salida.write(b, 0, n);
                    salida.flush();

                    /* PORCENTAJE */
                    recibido += n;
                    porcentaje = (int) (recibido * 100 / tam);
                    System.out.println("Recibido: " + porcentaje + "%\r");
                }

                System.out.println("Archivo recibido.");
                salida.close();
                entrada.close();
                socketCliente.close();
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}