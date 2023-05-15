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
public class OfertaComun implements VOOfertaComun
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la oferta
	 */
	private long idOfertaComun;
	
	/**
	 * La capacidad de personas de la oferta
	 */
	private int capacidad;

	/**
	 * Si tiene piscina la oferta
	 */
	private boolean piscina;
	/**
	 * Si tiene parqueadero la oferta
	 */
	private boolean parqueadero;
	/**
	 * Si tiene tvCable la oferta
	 */
	private boolean tvCable;
	/**
	 * Si tiene wifi la oferta
	 */
	private boolean wifi;
	/**
	 * Si esta hbailitado
	 */
	private boolean habilitado;
	
	/**
	 * El precio por noche del hospedaje 
	 */
	private int precioNoche;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public OfertaComun() 
    {
    	this.idOfertaComun = 0;
    	this.capacidad = 0;
    	this.piscina = false;
    	this.parqueadero = false;
    	this.tvCable = false;
    	this.wifi = false;
    	this.habilitado = false;
    	this.precioNoche = 0;
    	
	}

	/**
	 * Constructor con valores
	 * @param id - El id 


	 */
    public OfertaComun(long id, int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precioNoche) 
    {
    	this.idOfertaComun = id;
    	this.capacidad = capacidad;
    	this.piscina = piscina;
    	this.parqueadero = parqueadero;
    	this.tvCable = tvCable;
    	this.wifi = wifi;
    	this.precioNoche = precioNoche;
    	this.habilitado = true;
	}

    /**
	 * @return El id 
	 */
	public long getId() 
	{
		return idOfertaComun;
	}
	
	/**
	 * @param id - El nuevo id 
	 */
	public void setId(long id) 
	{
		this.idOfertaComun = id;
	}
	

	public void setHabilitado(boolean hab) 
	{
		this.habilitado = hab;
	}

	public boolean estaHabilitado() {
		
		return habilitado;
	}
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "OfertaComun [id=" + idOfertaComun + ", capacidad:"+ capacidad+ ", piscina:"+ piscina+ ", parqueadero:"+ parqueadero+ "]";
	}

	@Override
	public boolean tienePiscina() {
		// TODO Auto-generated method stub
		return piscina;
	}

	@Override
	public boolean tieneParqueadero() {
		// TODO Auto-generated method stub
		return parqueadero;
	}

	@Override
	public boolean tieneTvCable() {
		// TODO Auto-generated method stub
		return tvCable;
	}

	@Override
	public boolean tieneWifi() {
		// TODO Auto-generated method stub
		return wifi;
	}
	
	/**
	 * @param id - El nuevo estado de piscina
	 */
	public void setPiscina(boolean piscina) 
	{
		this.piscina = piscina;
	}
	/**
	 * @param id - El nuevo estado de parqueadero
	 */
	public void setParqueadero(boolean parqueadero) 
	{
		this.parqueadero = parqueadero;
	}
	/**
	 * @param id - El nuevo estado de tvCable
	 */
	public void setTvCable(boolean tvCable) 
	{
		this.tvCable = tvCable;
	}
	/**
	 * @param id - El nuevo estado de piscina
	 */
	public void setWifi(boolean wifi) 
	{
		this.wifi = wifi;
	}

	@Override
	public int precioNoche() {
		// TODO Auto-generated method stub
		return precioNoche;
	}
	

}
