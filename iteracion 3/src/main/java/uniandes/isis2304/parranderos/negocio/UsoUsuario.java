package uniandes.isis2304.parranderos.negocio;

public class UsoUsuario {

    private String servicio;

    private String num_reserva;

    private int numero_noches;

    private String id_oferta;

    private long precio;

    // Constructor y setters/getters perra

    public UsoUsuario() {
        this.servicio = "Default";
        this.num_reserva = "Default";
        this.numero_noches = 0;
        this.id_oferta = "Default";
        this.precio = 0;
    }

    public UsoUsuario(String servicio, String num_reserva, int numero_noches, String id_oferta, long precio) {
        this.servicio = servicio;
        this.num_reserva = num_reserva;
        this.numero_noches = numero_noches;
        this.id_oferta = id_oferta;
        this.precio = precio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio= servicio;
    }

    public String getNum_reserva() {
        return num_reserva;
    }

    public void setNum_reserva(String num_reserva) {
        this.num_reserva = num_reserva;
    }

    public int getNumero_noches() {
        return numero_noches;
    }

    public void setNumero_noches(int numero_noches) {
        this.numero_noches = numero_noches;
    }

    public String getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(String id_oferta) {
        this.id_oferta = id_oferta;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio= precio;
    }

    @Override
    public String toString() {
        return "UsoUsuario{" +
                "servicio='" + servicio + '\'' +
                ", num_reserva='" + num_reserva + '\'' +
                ", numero_noches=" + numero_noches +
                ", id_oferta='" + id_oferta + '\'' +
                ", precio=" + precio +
                '}';
    }
    
}
