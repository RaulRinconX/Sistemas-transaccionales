/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.AlohAndes.negocio;

/**
 * Clase para modelar el concepto Operador del negocio de AlohAndes
 *
 */
public class Hospedaje extends OfertaComun implements VOHospedaje
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * La categoria del hospedaje
	 */
	private String categoria;
	/**
	 * El tamaño
	 */
	private String tamaño;
	/**
	 * Si la recepcion está abierta las 24h
	 */
	private boolean recepcion24h;
	/**
	 * Si tiene servicio de restaurante
	 */
	private boolean servicioRestaurante;
	/**
	 * El id del dueño
	 */
	private long idEmp;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Hospedaje() 
    {
		super();
		
		this.categoria = "";
		this.tamaño = "";
		this.recepcion24h = false;
		this.servicioRestaurante = false;
		this.idEmp = 0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Hospedaje(long id, int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, String categoria, String tama, boolean recep, boolean rest, long miemb) 
    {
    	super(id, capacidad, piscina, parqueadero, tvCable, wifi, precio);
		this.categoria = categoria;
		this.tamaño = tama;
		this.recepcion24h = recep;
		this.servicioRestaurante = rest;
		this.idEmp = miemb;
	}

	
	/**
	 * @param tipo
	 */
	public void setCategoria(String categoria) 
	{
		this.categoria= categoria;
	}
	/**
	 * @param tipo
	 */
	public void setTamaño(String tam) 
	{
		this.tamaño= tam;
	}
	
	public void setRecepcion(boolean rec) 
	{
		this.recepcion24h= rec;
	}
	/**
	 * @param tipo
	 */
	public void setRestaurante(boolean rest) 
	{
		this.servicioRestaurante= rest;
	}


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return super.toString();
	}

	@Override
	public String getCategoria() {
		// TODO Auto-generated method stub
		return categoria;
	}

	@Override
	public String getTamaño() {
		// TODO Auto-generated method stub
		return tamaño;
	}

	@Override
	public boolean tieneRecepcion24h() {
		// TODO Auto-generated method stub
		return recepcion24h;
	}

	@Override
	public boolean tieneServicioRestaurante() {
		// TODO Auto-generated method stub
		return servicioRestaurante;
	}

	@Override
	public long getIdEmpresa() {
		// TODO Auto-generated method stub
		return idEmp;
	}
	

}
