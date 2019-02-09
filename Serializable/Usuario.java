import java.io.Serializable;

public class Usuario implements Serializable{
    private String nombre;
    private String apaterno;
    private String amaterno;
    /* HACEMOS USO DE TRANSIENT PARA NO SERIALIZAR EL PASSWORD */
    private transient String password;
    private int edad;
    
    /* CONSTRUCTOR */
    public  Usuario(String nombre, String apaterno, String amaterno, String password, int edad){
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.password = password;
        this.edad = edad;
    }

    /* GETTERS AND SETTERS */
    public String getNombre() {
        return nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public String getPassword() {
        return password;
    }

    public int getEdad() {
        return edad;
    }
    
}