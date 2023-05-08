package uniandes.isis2304.parranderos.negocio;

public class ClientesFrecuentes {

    private String id_cliente;

    private int numero_de_reservas;

    private int numero_noches;

    // Constructor y setters and getters

    public ClientesFrecuentes() {
        this.id_cliente = "";
        this.numero_de_reservas = 0;
        this.numero_noches = 0;
    }

    public ClientesFrecuentes(String id_cliente, int numero_de_reservas, int numero_noches) {
        this.id_cliente = id_cliente;
        this.numero_de_reservas = numero_de_reservas;
        this.numero_noches = numero_noches;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public int getNumero_de_reservas() {
        return numero_de_reservas;
    }

    public int getNumero_noches() {
        return numero_noches;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setNumero_de_reservas(int numero_de_reservas) {
        this.numero_de_reservas = numero_de_reservas;
    }

    public void setNumero_noches(int numero_noches) {
        this.numero_noches = numero_noches;
    }

    @Override
    public String toString() {
        return "ClientesFrecuentes{" +
                "id_cliente='" + id_cliente+
                ", numero_de_reservas=" + numero_de_reservas +
                ", numero_noches=" + numero_noches +
                '}';
    }
}
