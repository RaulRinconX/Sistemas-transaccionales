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
public class Vecino extends Operador implements VOVecino
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * La ubicacion del vecino
	 */
	private String ubicacion;

	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Vecino() 
    {
		super();
		this.ubicacion = "";

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Vecino(long id, String nombre, String tipoId, String ubicacion) 
    {
    	super(id, nombre, tipoId);
    	this.ubicacion = ubicacion;

	}

  
	
	/**
	 * @return la ubicacion del vecino
	 */
	public String getUbicacion() 
	{
		return ubicacion;
	}
	
	/**
	 * @param registro 
	 */
	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion = ubicacion;
	}
	
	



	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Vecino [id=" + super.getId() + ", nombre=" + super.getNombre() + ", tipoIdentificacion=" + super.getTipoIdentificacion()  + ", ubicacion=" + ubicacion +"]";
	}
	

}
