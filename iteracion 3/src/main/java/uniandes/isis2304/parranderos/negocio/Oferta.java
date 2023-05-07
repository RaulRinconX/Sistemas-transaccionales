package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class Oferta implements VOOferta{

	private String id_oferta;

	private String id_operador;
	
	private String tipo_oferta;
	
	private int disponible;
	
	private int precio;

	private Date fechaInicio;


	public Oferta() {
		this.id_oferta = "Default";
		this.id_operador = "Default";
		this.tipo_oferta = "Default";
		this.disponible = 0;
		this.precio = 0;
		this.fechaInicio = null;
		
	}




	public Oferta(String id_oferta, String id_operador, String tipo_oferta, int disponible, int precio, Date fechaInicio) {
		this.id_oferta = id_oferta;
		this.tipo_oferta = tipo_oferta;
		this.disponible = disponible;
		this.precio = precio;
		this.fechaInicio = fechaInicio;
	}
	



	public String getId() {
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


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	

	@Override
	public String toString() {
		return "Oferta [id_oferta=" + id_oferta + "id_operador="+ id_operador + ", tipo_oferta=" + tipo_oferta + ", disponible=" + disponible
				+ ", precio=" + precio + ", fecha_inicio="+ fechaInicio + "]";
	}



	public String getCadenaFechaInicial(){
		String fecha =""+  fechaInicio;
		
		String año = fecha.split(" ")[0].split("-")[0];
		String mes = fecha.split(" ")[0].split("-")[1];
		String dia = fecha.split(" ")[0].split("-")[2];
		

		return dia+"/"+mes+"/"+año;
	}
	/**
	 * @param tipo - El TipoBebida a comparar
	 * @return True si tienen el mismo nombre
	 */
	public boolean equals(Object tipo) 
	{
		Oferta tb = (Oferta) tipo;
		return id_oferta == tb.id_oferta && ((String) tipo).equalsIgnoreCase (tb.tipo_oferta);
	}
}
