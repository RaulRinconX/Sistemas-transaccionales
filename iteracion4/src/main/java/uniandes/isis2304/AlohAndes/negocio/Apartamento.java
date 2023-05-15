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
public class Apartamento extends OfertaExclusiva implements VOApartamento
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	
	/**
	 * El costo del servicio de restaurante
	 */
	private boolean amoblado;
	/**
	 * El costo
	 */
	private boolean comidas;
	/**
	 * El costo
	 */
	private boolean bañoPrivado;
	/**
	 * EL costo del gimnasio
	 */
	private int costoServicios;

	/**
	 * El id del dueño
	 */
	private long idMiembro;

	
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Apartamento() 
    {
		super();
		
		this.amoblado = false;
		this.comidas = false;
		this.bañoPrivado = false;
		this.costoServicios = 0;
		this.idMiembro = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del operador
	 * @param tipoId 

	 */
    public Apartamento(long id, boolean hab, int precio, boolean amoblado, boolean comidas,boolean baño, int costo, long miemb) 
    {
    	super(id, hab, precio);
		
    	this.amoblado = amoblado;
		this.comidas = comidas;
		this.bañoPrivado = baño;
		this.costoServicios = costo;
		this.idMiembro = miemb;
    	
	}

	
	/**
	 * @param tipo
	 */
	public void setAmoblado(boolean amob) 
	{
		this.amoblado= amob;
	}
	/**
	 * @param tipo
	 */
	public void setComidas(boolean comi) 
	{
		this.comidas= comi;
	}
	/**
	 * @param tipo
	 */
	public void setBañoPrivado(boolean baño) 
	{
		this.bañoPrivado= baño;
	}
	/**
	 * @param tipo
	 */
	public void setCostoServicios(int serv) 
	{
		this.costoServicios= serv;
	}
	
	public String toString() 
	{
		return super.toString();
	}

	@Override
	public boolean esAmoblado() {
		// TODO Auto-generated method stub
		return amoblado;
	}

	@Override
	public boolean tieneComidas() {
		// TODO Auto-generated method stub
		return comidas;
	}

	@Override
	public boolean tieneBañoPrivado() {
		// TODO Auto-generated method stub
		return bañoPrivado;
	}

	@Override
	public int getCostoServicios() {
		// TODO Auto-generated method stub
		return costoServicios;
	}

	@Override
	public long getIdPropietarioMiembro() {
		// TODO Auto-generated method stub
		return idMiembro;
	}

	
}

