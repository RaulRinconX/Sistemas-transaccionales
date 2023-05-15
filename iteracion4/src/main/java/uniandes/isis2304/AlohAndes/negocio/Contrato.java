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
public class Contrato implements VOContrato
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
	private long idOfertaExclusiva;
	/**
	 * La fecha de inicio del contrato
	 */
	private Date fechaInicio;
	/**
	 * La fecha de finalizacion del contrato
	 */
	private Date fechaFin;
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Contrato() 
	{
		this.idCliente = 0;
		this.idOfertaExclusiva = 0;
		this.fechaInicio = new Date(0);
		this.fechaFin = new Date(0);
	}

	/**
	 * Constructor con valores
	 * @param id - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 */
	public Contrato(long idCliente, long idOfertaExclusiva, Date inicio, Date fin) 
	{
		this.idCliente = idCliente;
		this.idOfertaExclusiva = idOfertaExclusiva;
		this.fechaInicio = inicio;
		this.fechaFin = fin;
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
		this.idOfertaExclusiva= idOferta;
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
	 * @return El id del tipo de bebida
	 */
	public long getIdOferta() 
	{
		return idOfertaExclusiva;
	}


	/**
	 * @param id - El nuevo 
	 */
	public void setIdCliente(long idCliente) 
	{
		this.idCliente = idCliente;
	}

	/**
	 * @return Una cadena de caracteres con la información del tipo de bebida
	 */
	@Override
	public String toString() 
	{
		return "Contrato [idCliente=" + idCliente +", idOfertaExclusiva"+idOfertaExclusiva+ "]";
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
