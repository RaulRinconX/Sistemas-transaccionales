package uniandes.isis2304.parranderos.negocio;

public class UsoAlohandes {
    
    private String id_usuario;

    private String tipo_usuario;

    private String nombre;

    private String uso_usuario;

    // hazme constructuro y getters/setters

    public UsoAlohandes() {
        this.id_usuario = "Default";
        this.tipo_usuario = "Default";
        this.nombre = "Default";
        this.uso_usuario = "Default";
    }

    public UsoAlohandes(String id_usuario, String tipo_usuario, String nombre, String uso_usuario) {
        this.id_usuario = id_usuario;
        this.tipo_usuario = tipo_usuario;
        this.nombre = nombre;
        this.uso_usuario = uso_usuario;
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

    public String getUso_usuario() {
        return uso_usuario;
    }

    public void setUso_usuario(String uso_usuario) {
        this.uso_usuario= uso_usuario;
    }

    @Override
    public String toString() {
        return "UsoAlohandes [id_usuario=" + id_usuario + " , tipo_usuario=" + tipo_usuario + ", nombre=" + nombre + ", uso_usuario=" + uso_usuario + "]";
    }
}
