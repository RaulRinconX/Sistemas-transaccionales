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
public class Vivienda extends OfertaComun implements VOVivienda
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * El numero de habitaciones
	 */
	private int numHabitaciones;

	/**
	 * La descripción del seguro que tiene la vivienda
	 */
	private String descripSeguro;
	
	/**
	 * El id del dueño
	 */
	private long idVecino;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Vivienda() 
    {
		super();
		this.numHabitaciones = 0;
		this.descripSeguro = "";
		this.idVecino =0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Vivienda(long id, int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, int num, String descrip, long vec) 
    {
    	super(id, capacidad, piscina, parqueadero, tvCable, wifi, precio);
    	this.numHabitaciones = num;
		this.descripSeguro = descrip;
		this.idVecino = vec;
	}

  
	
	/**
	 * @return el numero de habitaciones
	 */
	public int getNumHabitaciones() 
	{
		return numHabitaciones;
	}
	
	/**
	 * @param numHabitaciones
	 */
	public void setNumHabitaciones(int numHabitaciones) 
	{
		this.numHabitaciones = numHabitaciones;
	}
	
	/**
	 * @return la descripcion del seguro
	 */
	public String getDescripSeguro() 
	{
		return descripSeguro;
	}
	
	/**
	 * @param tipo
	 */
	public void setDescripSeguro(String desc) 
	{
		this.descripSeguro= desc;
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
	public long getIdVecino() {
		// TODO Auto-generated method stub
		return idVecino;
	}
	

}
