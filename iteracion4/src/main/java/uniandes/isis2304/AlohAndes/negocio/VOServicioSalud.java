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
 * Interfaz para los métodos get de BEBIDA.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOServicioSalud 
{
	/**
	 * @return El id del medico
	 */
	public long getId();

	/**
	 * @return El nombre del medico
	 */
	public String getNombre();

	/**
	 * @return El tipo de documento del medico
	 */
	public int getCapacidad();


	/**
	 * @return La especialidad del medico
	 */
	public String getHorarioAtencion();

	/**
	 * @return Una cadena con la información básica de la bebida
	 */
	@Override
	public String toString();

}
