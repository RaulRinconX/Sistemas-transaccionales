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
public class Operador implements VOOperador
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del operador
	 */
	private long idOperador;
	
	/**
	 * El nombre del bar
	 */
	private String nombre;

	/**
	 * Las caracteristicas de la EPS
	 */
	private String tipoIdentificacion;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Operador() 
    {
    	this.idOperador = 0;
		this.nombre = "";
		this.tipoIdentificacion = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Operador(long id, String nombre, String tipoId) 
    {
    	this.idOperador = id;
		this.nombre = nombre;
		this.tipoIdentificacion = tipoId;

	}

    /**
	 * @return El id del bar
	 */
	public long getId() 
	{
		return idOperador;
	}
	
	/**
	 * @param id - El nuevo id del bar
	 */
	public void setId(long id) 
	{
		this.idOperador = id;
	}
	
	/**
	 * @return el nombre del bar
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * @param nombre El nuevo nombre del bar
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	/**
	 * @return la ciudad del bar
	 */
	public String getTipoIdentificacion() 
	{
		return tipoIdentificacion;
	}
	
	/**
	 * @param ciudad - La nueva ciudad del bar
	 */
	public void setTipoIdentificacion(String tipoId) 
	{
		this.tipoIdentificacion = tipoId;
	}


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Operador [id=" + idOperador + ", nombre=" + nombre + ", tipoIdentificaion=" + tipoIdentificacion + "]";
	}
	

}
