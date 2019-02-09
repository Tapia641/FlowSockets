
/* ALUMNO: HERNANDEZ TAPIA LUIS ENRIQUE */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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
                /* HASTA QUE RECIBA ALGUNA PETICION DEL CLIENTE */
                Socket socketCliente = socketServidor.accept();
                System.out.println(
                        "Conexion establecida desde " + socketCliente.getInetAddress() + ":" + socketCliente.getPort());

                /* ESTABLECEMOS EL CANAL DE ENTRADA DE TIPO BUFFERED */
                BufferedReader in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                int cantidad = Integer.parseInt(in.readLine());
                System.out.print("\nCantidad de archivos que se recibien: ");
                System.out.print(cantidad + "\n");

                for (int i = 0; i < cantidad; i++) {

                    /* ESTABLECEMOS EL CANAL DE ENTRADA DE TIPO DATA */
                    DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
                    byte[] b = new byte[1024];
                    String nombre = entrada.readUTF();
                    System.out.println("Recibiendo el archivo: " + nombre);
                    long tam = entrada.readLong();

                    /* ESTABLECEMOS EL CANAL DE SALIDA DE TIPO DATA */
                    DataOutputStream salida = new DataOutputStream(new FileOutputStream(nombre));

                    /* SECCION PARA RECIBIR EL ARCHIVO */
                    long recibido = 0;
                    int n = 0, porcentaje = 0;

                    while (recibido < tam) {
                        n = entrada.read(b);
                        salida.write(b, 0, n);
                        salida.flush();

                        /* PORCENTAJE */
                        recibido += n;
                        porcentaje = (int) (recibido * 100 / tam);
                        System.out.println("Recibido: " + porcentaje + "%\r");
                    }

                    /* CONCLUIMOS CON EL ARCHVIO i */
                    System.out.println("Archivo " + nombre + " recibido.");

                    /* SOLO CERRAMOS CUANDO SE RECIBAN TODOS */
                    if (i == cantidad - 1) {
                        salida.close();
                        entrada.close();
                    }
                }
                in.close();
                socketCliente.close();
            }
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}