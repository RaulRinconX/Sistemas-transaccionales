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
public class ViviendaUniversitaria extends OfertaExclusiva implements VOViviendaUniversitaria
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * El costo del servicio de restaurante
	 */
	private int costoRestaurante;
	/**
	 * El costo
	 */
	private int costoSalas;
	/**
	 * EL costo del gimnasio
	 */
	private int costoGimnasio;

	/**
	 * El id del dueño
	 */
	private long idAdmin;
	
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public ViviendaUniversitaria() 
    {
		super();
		
		this.costoGimnasio = 0;
		this.costoRestaurante = 0;
		this.costoSalas = 0;
		this.idAdmin = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public ViviendaUniversitaria(long id, boolean hab, int precio, int gym, int rest, int sala, long admin) 
    {
    	super(id, hab, precio);
		
		this.costoGimnasio = gym;
		this.costoRestaurante = rest;
		this.costoSalas = sala;
		this.idAdmin = admin;
	}

	
	/**
	 * @param tipo
	 */
	public void setCostoGimnasio(int gym) 
	{
		this.costoGimnasio= gym;
	}
	/**
	 * @param tipo
	 */
	public void setCostoSalas(int sala) 
	{
		this.costoSalas= sala;
	}
	/**
	 * @param tipo
	 */
	public void setCostoRestaurante(int rest) 
	{
		this.costoRestaurante= rest;
	}
	
	public String toString() 
	{
		return super.toString();
	}

	@Override
	public int getCostoRestaurante() {
		// TODO Auto-generated method stub
		return costoRestaurante;
	}

	@Override
	public int getCostoSalas() {
		// TODO Auto-generated method stub
		return costoSalas;
	}

	@Override
	public int getCostoGimnasio() {
		// TODO Auto-generated method stub
		return costoGimnasio;
	}

	@Override
	public long getIdAdminVivienda() {
		// TODO Auto-generated method stub
		return idAdmin;
	}
}

