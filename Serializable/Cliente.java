import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String args[]){
        iniciarCliente();
    }

    public static void iniciarCliente() {

        /* DECLARAMOS LOS OBJETOS */
        ObjectOutputStream objetoSalida = null;
        ObjectInputStream objetoEntrada = null;

        /* REALIZAMOS LAS CONFIGURACIONES DE CONEXIÓN */
        String host = "127.0.0.1";// localhost
        int port = 9999;

        try {

            /* CREAMOS UN SOCKET TCP Y LO CONECTAMOS A LA MAQUINA ESPECIFICA */
            Socket socketCliente = new Socket(host, port);
            System.out.println("Conexion establecida...");

            /* INICIALIZAMOS LOS OBJETOS DE ENTRADA Y SALIDA */
            objetoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
            objetoEntrada = new ObjectInputStream(socketCliente.getInputStream());

            /* CREAMOS UN NUEVO USUARIO CON LAS PARAMETROS RESPECTIVOS */
            Usuario usuario1 = new Usuario("Pepito", "Perez", "Juarez", "12346", 20);
            System.out.println("Enviando objeto...");
            
            /* ESCRIBIMOS UN OBJETO EN EL FLUJO */
            objetoSalida.writeObject(usuario1);
            objetoSalida.flush();

            /* CREAMOS UN NUEVO USUARIO PARA RECIBIR EL OBJETO DE TIPO USUARIO */
            System.out.println("Preparado para recibir respuesta");
            Usuario usuario2 = (Usuario) objetoEntrada.readObject();
            System.out.println("Objeto recibido... Extrayendo datos");
            System.out.print("Nombre: " + usuario2.getNombre() + "\nA. paterno: " + usuario2.getApaterno()
                    + "\nA. materno: " + usuario2.getAmaterno() + "\nPassword: " + usuario2.getPassword() + "\nEdad: "
                    + usuario2.getEdad() + "\n");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
