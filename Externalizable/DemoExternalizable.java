import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class DemoExternalizable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /* CREANDO USUARIOS */
        System.out.println("Creando el objeto ...");
        String[] usuarios = { "A", "B", "C" };
        String[] passwords = { "1", "2", "3" };

        /* AGREGAMOS LOS USUARIOS A LA LISTA */
        ListaUsuarios lista = new ListaUsuarios(usuarios, passwords);

        /* LOS GUARDAMOS EN UN .OUT */
        ObjectOutputStream objetoSalida = new ObjectOutputStream(new FileOutputStream("objetos.out"));
        objetoSalida.writeObject(lista);
        objetoSalida.close();

        /* LEEMOS EL .OUT */
        System.out.println("\nRecuperando objeto");
        ObjectInputStream objetoEntrada = new ObjectInputStream(new FileInputStream("objetos.out"));

        /* LO CONVERTIMOS A TIPO LISTAUSUARIOS */
        lista = (ListaUsuarios) objetoEntrada.readObject();
        lista.MuestraLista();
        
        objetoEntrada.close();
    }
}