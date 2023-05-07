package uniandes.isis2304.parranderos.negocio;

import java.util.*;
/**
 * Clase para modelar el concepto AlojamientosPopulares del negocio de ALOHANDES
 */
public class AlojamientosPopulares
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los alojamientos
	 */
	private String id_oferta;

	private String id_operador;
	
	/**
	 * La direccion del alojamiento
	 */
	private String tipo_oferta;
	
	/**
	 * La direccion del alojamiento
	 */
	private int disponible;

	private long precio;

	private Date fecha_inicio;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public AlojamientosPopulares() 
    {
		this.id_oferta = "";
		this.id_operador = "";
		this.tipo_oferta = "";
		this.disponible = 0;
		this.precio = 0;
		this.fecha_inicio = null;
	}

	/**
	 * Constructor con valores
	 * @param id - El id del alojamiento
	 * @param nombre - La direccion del alojamiento
	 */
	public AlojamientosPopulares(String id_oferta, String id_operador, String tipo_oferta, int disponible, long precio, Date fecha_inicio)
	{
		this.id_oferta = id_oferta;
		this.id_operador = id_operador;
		this.tipo_oferta = tipo_oferta;
		this.disponible = disponible;
		this.precio = precio;
		this.fecha_inicio = fecha_inicio;
	}

	public String getId_oferta() {
		return id_oferta;
	}
	public void setId_oferta(String id_oferta) {
		this.id_oferta = id_oferta;
	}
	public String getId_operador() {
		return id_operador;
	}
	public void setId_operador(String id_operador) {
		this.id_operador = id_operador;
	}
	public String getTipo_oferta() {
		return tipo_oferta;
	}
	public void setTipo_oferta(String tipo_oferta) {
		this.tipo_oferta = tipo_oferta;
	}
	public int getDisponible() {
		return disponible;
	}
	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}
	public long getPrecio() {
		return precio;
	}
	public void setPrecio(long precio) {
		this.precio = precio;
	}
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del alojamiento
	 */
	public String toString() 
	{
		return "Ofertas mas populares [id_oferta=" + id_oferta + ", id_operador=" + id_operador + ", tipo_oferta=" + tipo_oferta +", disponible="+ disponible+", precio="+ precio +", fecha_inicio="+ fecha_inicio+"]";
	}

}


