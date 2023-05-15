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
public class PropietarioMiembro extends Operador implements VOPropietarioMiembro
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * El tipo de miembro que es el propietario
	 */
	private String tipoMiembro;

	/**
	 * La ubicacion del propietario miembro
	 */
	private String ubicacion;
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public PropietarioMiembro() 
    {
		super();
		this.tipoMiembro = "";
		this.ubicacion = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public PropietarioMiembro(long id, String nombre, String tipoId, String tipoMiembro, String ubicacion) 
    {
    	super(id, nombre, tipoId);
    	this.tipoMiembro = tipoMiembro;
		this.ubicacion = ubicacion;

	}

  
	
	/**
	 * @return el tipo de miembro
	 */
	public String getTipoMiembro() 
	{
		return tipoMiembro;
	}
	
	/**
	 * @param tipo
	 */
	public void setTipoMiembro(String miembro) 
	{
		this.tipoMiembro = miembro;
	}
	
	/**
	 * @return la ubicacion
	 */
	public String getUbicacion() 
	{
		return ubicacion;
	}
	
	/**
	 * @param tipo
	 */
	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion= ubicacion;
	}


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "PropietarioMiembro [id=" + super.getId() + ", nombre=" + super.getNombre() + ", tipoIdentificacion=" + super.getTipoIdentificacion()  + ", ubicacion=" + ubicacion +"]";
	}
	

}
