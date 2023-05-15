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
public class OfertaExclusiva implements VOOfertaExclusiva
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la oferta
	 */
	private long idOfertaExclusiva;
	

	/**
	 * Si tiene piscina la oferta
	 */
	private boolean habitacionCompartida;

	
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
	public OfertaExclusiva() 
    {
    	this.idOfertaExclusiva = 0;
    	this.habitacionCompartida = false;
    	this.precioNoche = 0;
    	
	}

	/**
	 * Constructor con valores
	 * @param id - El id 


	 */
    public OfertaExclusiva(long id, boolean hab, int precioNoche) 
    {
    	this.idOfertaExclusiva = id;
    	this.habitacionCompartida = hab;
    	this.precioNoche = precioNoche;
	}

    /**
	 * @return El id 
	 */
	public long getId() 
	{
		return idOfertaExclusiva;
	}
	
	/**
	 * @param id - El nuevo id 
	 */
	public void setId(long id) 
	{
		this.idOfertaExclusiva = id;
	}
	



	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "OfertaComun [id=" + idOfertaExclusiva + "]";
	}

	/**
	 * @param id - El nuevo estado de piscina
	 */
	public void setHabitacionCompartida(boolean hab) 
	{
		this.habitacionCompartida = hab;
	}

	public void setPrecioNoche(int precio) 
	{
		this.precioNoche = precio;
	}

	@Override
	public int getPrecioNoche() {
		// TODO Auto-generated method stub
		return precioNoche;
	}

	@Override
	public boolean esHabitacionCompartida() {
		// TODO Auto-generated method stub
		return habitacionCompartida;
	}
	

}
