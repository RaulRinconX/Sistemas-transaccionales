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
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class ReservaColectiva implements VOReservaColectiva
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador de la reserva colectiva
	 */
	private long idColectiva;

	/**
	 * La fecho de inicio de la reserva 
	 */
	private int cantidad;
	/**
	 * La fecha de finalizacion de la reserva
	 */
	private String nombreEvento;
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ReservaColectiva() 
	{
		this.idColectiva= 0;
		this.cantidad = 0;
		this.nombreEvento ="";
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 */
	public ReservaColectiva(long idColectiva, int cantidad, String nomb) 
	{
		this.idColectiva = idColectiva;
		this.cantidad = cantidad;
		this.nombreEvento = nomb;
	}

	/**
	 * @return El id del tipo de bebida
	 */
	public long getIdColectiva() 
	{
		return idColectiva;
	}

	/**
	 * @param id - El nuevo id del tipo de bebida
	 */
	public void setIdColectiva(long idColectiva) 
	{
		this.idColectiva = idColectiva;
	}

	
	/**
	 * @param id - El nuevo 
	 */
	public void setNombre(String nomb) 
	{
		this.nombreEvento= nomb;
	}
	/**
	 * 
	 */
	public void setCantidad(int cant)
	{
		this.cantidad = cant;
	}


	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString() 
	{
		return "ReservaColectiva [idColectiva=" + idColectiva +", nombreEvento:" + nombreEvento + "]";
	}

	@Override
	public int getCantidad() {
		// TODO Auto-generated method stub
		return cantidad;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombreEvento;
	}

	

	


}
