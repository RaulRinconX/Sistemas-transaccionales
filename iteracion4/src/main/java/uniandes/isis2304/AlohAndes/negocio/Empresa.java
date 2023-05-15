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
public class Empresa extends Operador implements VOEmpresa
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * El registro de camara de la empresa
	 */
	private String registroCamara;

	/**
	 * El tipo de alojamiento que ofrece la empresa
	 */
	private String tipoAlojamiento;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Empresa() 
    {
		super();
		this.registroCamara = "";
		this.tipoAlojamiento = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Empresa(long id, String nombre, String tipoId, String registro, String tipo) 
    {
    	super(id, nombre, tipoId);
    	this.registroCamara = registro;
		this.tipoAlojamiento = tipo;

	}

  
	
	/**
	 * @return el registro de camara de la empresa
	 */
	public String getRegistroCamara() 
	{
		return registroCamara;
	}
	
	/**
	 * @param registro 
	 */
	public void setRegistroCamara(String registro) 
	{
		this.registroCamara = registro;
	}
	
	/**
	 * @return el tipo de alojamiento
	 */
	public String getTipoAlojamiento() 
	{
		return tipoAlojamiento;
	}
	
	/**
	 * @param tipo
	 */
	public void setTipoAlojamiento(String tipo) 
	{
		this.tipoAlojamiento = tipo;
	}


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Empresa [id=" + super.getId() + ", nombre=" + super.getNombre() + ", tipoIdentificacion=" + super.getTipoIdentificacion()  + ", tipoAlojamiento=" + tipoAlojamiento +"]";
	}
	

}
