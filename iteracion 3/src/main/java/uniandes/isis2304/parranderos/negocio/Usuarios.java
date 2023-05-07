package uniandes.isis2304.parranderos.negocio;

public class Usuarios implements VOUsuarios {

    private String id_usuario;

    private String tipo_usuario;

    private String nombre;

    // hazme el constructor y getters and setterss

    public Usuarios() {
        this.id_usuario = "Default";
        this.tipo_usuario = "Default";
        this.nombre = "Default";
    }

    public Usuarios(String id_usuario, String tipo_usuario, String nombre) {
        this.id_usuario = id_usuario;
        this.tipo_usuario = tipo_usuario;
        this.nombre = nombre;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario= id_usuario;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario= tipo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre= nombre;
    }

    @Override
	public String toString() {
		return "Usuarios [id_usuario=" + id_usuario + " , tipo_usuario=" + tipo_usuario + ", nombre=" + nombre + "]";
	}
}
