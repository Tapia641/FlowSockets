import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFileChooser;
import javafx.stage.FileChooser;

public class CTCPBArchivo {
    public static void main(String[] args) {
        ClienteArchivo();
    }

    public static void ClienteArchivo() {
        try {
            /* PREPARAMOS LOS DATOS DE LA CONEXION */
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escriba la direccion del servidor: ");
            String host = teclado.readLine();
            System.out.println("Escriba el puerto: ");
            int port = Integer.parseInt(teclado.readLine());

            /* CERRAMOS EL SOCKET DEL LADO DEL CLIENTE */
            Socket socketCliente = new Socket(host, port);

            /* ACTIVAMOS MULTIPLE SELECCION */
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);

            /* MOSTRAMOS EL FILECHOOSER */
            int r = jf.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                File[] f = jf.getSelectedFiles();

                /* ENVIAMOS CANTIDAD DE ARCHIVOS */

                /* OBTENEMOS EL CANAL DE SALIDA */
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socketCliente.getOutputStream()));
                System.out.println("Enviando cantidad de archivos...");
                out.println(f.length);
                out.flush();

                for (int i = 0; i < f.length; i++) {
                    /* OBTENEMOS DATOS DE CADA ARCHIVO */
                    String archivo = f[i].getAbsolutePath();
                    String nombre = f[i].getName();
                    long tam = f[i].length();

                    /* OBTENEMOS EL CANAL DE ENTRADA */
                    DataInputStream entrada = new DataInputStream(new FileInputStream(archivo));

                    /* OBTENEMOS EL CANAL DE SALIDA */
                    DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());

                    salida.writeUTF(nombre);
                    salida.flush();
                    salida.writeLong(tam);
                    salida.flush();

                    /* SECCION PARA EL ENVIO DEL ARCHIVO */
                    byte[] b = new byte[1024];
                    long enviado = 0;
                    int porcentaje, n;

                    /* CALCULAMOS EL PORCENTAJE */
                    while (enviado < tam) {
                        n = entrada.read(b);
                        salida.write(b);
                        salida.flush();
                        enviado += n;
                        porcentaje = (int) (enviado * 100 / tam);
                        System.out.println("Enviado: " + porcentaje + "%\r");
                    }

                    System.out.println("Archivo enviado");
                    if (i == f.length - 1) {
                        salida.close();
                        entrada.close();
                    }
                }
                out.close();
                socketCliente.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}