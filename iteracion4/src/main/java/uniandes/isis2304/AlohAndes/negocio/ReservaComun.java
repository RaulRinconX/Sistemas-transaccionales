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
public class ReservaComun implements VOReservaComun
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del cliente
	 */
	private long idCliente;

	/**
	 * El identificador de la iferta
	 */
	private long idOfertaComun;
	/**
	 * La fecho de inicio de la reserva 
	 */
	private Date fechaInicio;
	/**
	 * La fecha de finalizacion de la reserva
	 */
	private Date fechaFin;
	/**
	 * El identificador de la iferta colectiva, 0 si no pertence a una.
	 */
	private long idOfertaColectiva;
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ReservaComun() 
	{
		this.idCliente = 0;
		this.idOfertaComun = 0;
		this.idOfertaColectiva= 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 */
	public ReservaComun(long idCliente, long idOfertaComun,  Date inicio, Date fin, long colec) 
	{
		this.idCliente = idCliente;
		this.idOfertaComun = idOfertaComun;
		this.fechaInicio = inicio;
		this.fechaFin = fin;
		this.idOfertaColectiva= colec;
	}

	/**
	 * @return El id del tipo de bebida
	 */
	public long getIdCliente() 
	{
		return idCliente;
	}

	/**
	 * @param id - El nuevo id del tipo de bebida
	 */
	public void setIdOferta(long idOferta) 
	{
		this.idOfertaComun = idOferta;
	}

	/**
	 * @return El id del tipo de bebida
	 */
	public long getIdOferta() 
	{
		return idOfertaComun;
	}

	/**
	 * @param id - El nuevo 
	 */
	public void setIdCliente(long idCliente) 
	{
		this.idCliente = idCliente;
	}
	/**
	 * 
	 */
	public void setFechaInicio(Date inicio)
	{
		this.fechaInicio = inicio;
	}
	/**
	 * 
	 */
	public void setFechaFin(Date fin)
	{
		this.fechaFin = fin;
	}

	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString() 
	{
		return "ReservaComun [idCliente=" + idCliente +", idOfertaComun"+idOfertaComun+ "]";
	}

	@Override
	public Date getFechaInicio() {
		// TODO Auto-generated method stub
		return fechaInicio;
	}

	@Override
	public Date getFechaFin() {
		// TODO Auto-generated method stub
		return fechaFin;
	}

	


}
