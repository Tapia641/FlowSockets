import java.io.Externalizable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Usuario implements Externalizable { 
    String usuario;
    String password;

    public Usuario() {
        System.out.println("Creando usuario...");
    }

    public Usuario(String usuario, String password) {
        System.out.println("Creando usuario (" + usuario + ", " + password + ")"); 
        this.usuario = usuario;
        this.password = password;
    }

    /* NECESARIO PARA QUE SEA EXTERNALIZABLE */
    public void writeExternal(ObjectOutput out)
    throws IOException {
        // INDICAMOS CUALES ATRIBUTOS SE VAN A ENVIAR
        out.writeObject(usuario);
        out.writeObject(password);

    }

    /* NECESARIO PARA QUE SEA EXTERNALIZABLE */
    public void readExternal(ObjectInput in)
    throws IOException,
           ClassNotFoundException{
        System.out.println("Usuario.ReadExternal");
        // INDICAMOS CUALES ATRIBUTOS SE RECUPERAN
        this.usuario = (String) in.readObject();
        this.password = (String) in.readObject();            

    
    }

    public void MuestraUsuario() {
        String cadena = ("Usuario: " + this.usuario + " Password: ");
        if (this.password == null) {
            cadena += "No disponible";
        } else {
            cadena += this.password;
        }
        System.out.println(cadena);
    }
}

