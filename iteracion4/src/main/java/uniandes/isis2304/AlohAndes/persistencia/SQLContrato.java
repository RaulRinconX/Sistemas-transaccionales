/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: AlohAndes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.AlohAndes.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.AlohAndes.negocio.Contrato;
import uniandes.isis2304.AlohAndes.negocio.Contrato;
import uniandes.isis2304.AlohAndes.negocio.Operador;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de AlohAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLContrato 
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
	public SQLContrato (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un contrato a la base de datos de AlohAndes
	 * @param pm - El manejador de persistencia
	 * @param idcontrato - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarContrato (PersistenceManager pm, long idCliente, long idOferta, Date inicio, Date fin) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContrato  () + "(idCliente, idOfertaComun, fechaInicio, fechaFin) values (?,?,?,?)");
        q.setParameters(idCliente, idOferta, inicio, fin);
        return (long) q.executeUnique();            
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de AlohAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idcontrato - El identificador del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarContrato (PersistenceManager pm, long idCliente, long idOferta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrato () + " WHERE idCliente = ? AND idOfertaExclusiva = ?");
        q.setParameters(idCliente, idOferta);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de AlohAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idcontrato - El identificador del tipo de bebida
	 * @return El objeto contrato que tiene el identificador dado
	 */
	public Contrato darContrato (PersistenceManager pm, long idCliente, long idOferta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato  () + " WHERE idCliente = ? AND idOfertaExclusiva = ?");
		q.setResultClass(Contrato.class);
		q.setParameters(idCliente, idOferta);
		return (Contrato) q.executeUnique();
	}
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS Contratosde la 
	 * base de datos de AlohAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos contrato
	 */
	public List<Contrato> darListaContrato (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContrato  ());
		q.setResultClass(Contrato.class);
		return (List<Contrato>) q.executeList();
	}
	
}
