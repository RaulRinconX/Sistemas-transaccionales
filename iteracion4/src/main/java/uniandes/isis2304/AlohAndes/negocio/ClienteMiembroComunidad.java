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

import java.sql.Date;

/**
 * Clase para modelar la relación GUSTAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor gusta de una bebida y viceversa.
 * Se modela mediante los identificadores del bebedor y de la bebida respectivamente.
 * Debe existir un bebedor con el identificador dado
 * Debe existir una bebida con el identificador dado 
 * 

 */
public class ClienteMiembroComunidad extends Cliente implements VOClienteMiembro
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/


	/**
	 * El tipo de miembro que es el cliente
	 */
	private String tipoMiembro;
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ClienteMiembroComunidad() 
	{
		super();
		this.tipoMiembro = "";
	}

	/**
	 * Constructor con valores
	 * @param idBebedor - El identificador del bebedor. Debe exixtir un bebedor con dicho identificador
	 * @param idBebida - El identificador de la bebida. Debe existir una bebida con dicho identificador
	 */
	public ClienteMiembroComunidad(long idCliente, String correo, String nombre, String tipo) 
	{
		super(idCliente, correo, nombre);
		this.tipoMiembro = tipo;	
	}


	/**
	 * @param idBebida - El nuevo identificador de bebida. Debe existir una bebida con dicho identificador
	 */
	public void setTipo(String tipo) 
	{
		this.tipoMiembro = tipo;
	}
	

	
	
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return super.toString();
	}

	

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return tipoMiembro;
	}


	
	
}
