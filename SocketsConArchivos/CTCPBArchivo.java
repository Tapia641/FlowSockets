import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JFileChooser;
import javafx.stage.FileChooser;

public class CTCPBArchivo {
    public static void main(String[] args) {
        ClienteArchivo();
    }

    public static void ClienteArchivo() {
        try {
            /* REALIZAMOS LA CONFIGURACION ADECUADA */
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escriba la direccion del servidor: ");
            String host = teclado.readLine();
            System.out.println("Escriba el puerto: ");
            int port = Integer.parseInt(teclado.readLine());

            /* INICIALIZAMOS EL SOCKET */
            Socket socketCliente = new Socket(host, port);

            /* ACTIVAMOS MULTIPLE SELECCION */
            JFileChooser jf = new JFileChooser();
            jf.setMultiSelectionEnabled(true);

            /* MOSTRAMOS EL FILECHOOSER */
            int r = jf.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                File[] f = jf.getSelectedFiles();

                for (int i = 0; i < f.length; i++) {
                    /* OBTENEMOS DATOS DE CADA ARCHIVO */

                    String archivo = f[i].getAbsolutePath();
                    String nombre = f[i].getName();
                    long tam = f[i].length();

                    DataOutputStream dos = new DataOutputStream(socketCliente.getOutputStream());
                    DataInputStream dis = new DataInputStream(new FileInputStream(archivo));

                    dos.writeUTF(nombre);
                    dos.flush();
                    dos.writeLong(tam);
                    dos.flush();

                    /* SECCION PARA EL ENVIO DEL ARCHIVO */
                    byte[] b = new byte[1024];
                    long enviado = 0;
                    int porcentaje, n;

                    /* CALCULAMOS EL PORCENTAJE */
                    while (enviado < tam) {
                        n = dis.read(b);
                        dos.write(b);
                        dos.flush();
                        enviado += n;
                        porcentaje = (int) (enviado * 100 / tam);
                        System.out.println("Enviado: " + porcentaje + "%\r");
                    }

                    System.out.println("Archivo enviado");
                    dos.close();
                    dis.close();
                }
                socketCliente.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}