import java.io.Externalizable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

public class ListaUsuarios implements Serializable {

    LinkedList<Usuario> lista = new LinkedList<>();

    /* CONSTRUCTOR */
    public ListaUsuarios(String[] usuarios, String[] passwords) {
        for (int i = 0; i < usuarios.length; i++) {
            lista.add(new Usuario(usuarios[i], passwords[i]));
        }
    }

    public void MuestraLista() {
        ListIterator it = lista.listIterator();
        Usuario u;
        while (it.hasNext()) {
            u = (Usuario) it.next();
            u.MuestraUsuario();
        }
    }
}