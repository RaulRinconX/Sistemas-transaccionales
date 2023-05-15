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

package uniandes.isis2304.AlohAndes.persistencia;


import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.AlohAndes.negocio.OfertaComun;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BEBEDOR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLOfertaComun 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAlohAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAlohAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLOfertaComun (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BEBEDOR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarOfertaComun (PersistenceManager pm, long id, int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertaComun () + "(idOfertaComun, capacidad, piscina, parqueadero, tvCable, wifi, precioNoche) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, capacidad, piscina, parqueadero, tvCable, wifi, precio);
        return (long) q.executeUnique();
	}



	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BEBEDOR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebeodor
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOfertaComunPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertaComun () + " WHERE idOfertaComun = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BEBEDOR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebeodor
	 * @return EL número de tuplas eliminadas
	 */
	public long habilitarOfertasYDeshabilitar(PersistenceManager pm, boolean b)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOfertaComun () + " SET habilitado = ?");
        q.setParameters(b);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BEBEDOR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR que tiene el identificador dado
	 */
	public OfertaComun darOfertaComunPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfertaComun () + " WHERE idOfertaComun = ?");
		q.setResultClass(OfertaComun.class);
		q.setParameters(id);
		return (OfertaComun) q.executeUnique();
	}

	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BEBEDOR
	 */
	public List<OfertaComun> darListaOfertaComun (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfertaComun());
		q.setResultClass(OfertaComun.class);
		return (List<OfertaComun>) q.executeList();
	}
   






}
