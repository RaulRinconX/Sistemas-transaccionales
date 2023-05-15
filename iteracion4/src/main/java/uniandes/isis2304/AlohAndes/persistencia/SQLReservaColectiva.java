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

import uniandes.isis2304.AlohAndes.negocio.ReservaComun;
import uniandes.isis2304.AlohAndes.negocio.Operador;
import uniandes.isis2304.AlohAndes.negocio.ReservaColectiva;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLReservaColectiva 
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
	public SQLReservaColectiva (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOBEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarReservaColectiva (PersistenceManager pm, long idColectiva, int cantidad, String nomb) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservaColectiva  () + "(nombreEvento, cantidad, idColectiva) values (?,?,?)");
        q.setParameters(nomb, cantidad, idColectiva);
        return (long) q.executeUnique();            
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaColectiva (PersistenceManager pm, long idColectiva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservaColectiva () + " WHERE idColectiva = ? ");
        q.setParameters(idColectiva);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TIPOBEBIDA que tiene el identificador dado
	 */
	public ReservaColectiva darReservaColectiva (PersistenceManager pm, long idColectiva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaColectiva  () + " WHERE idColectiva = ? ");
		q.setResultClass(ReservaColectiva.class);
		q.setParameters(idColectiva);
		return (ReservaColectiva) q.executeUnique();
	}
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOS DE BEBIDA de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TIPOBEBIDA
	 */
	public List<ReservaColectiva> darListaReservaColectiva (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaColectiva  ());
		q.setResultClass(ReservaColectiva.class);
		return (List<ReservaColectiva>) q.executeList();
	}
	
}
