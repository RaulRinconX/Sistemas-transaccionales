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

import uniandes.isis2304.AlohAndes.negocio.Empresa;
import uniandes.isis2304.AlohAndes.negocio.Operador;
import uniandes.isis2304.AlohAndes.negocio.PropietarioMiembro;
import uniandes.isis2304.AlohAndes.negocio.Vivienda;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 */
class SQLVivienda 
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
	public SQLVivienda (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BAR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador 
	 * @param nombre - El nombre
	 * @return El número de tuplas insertadas
	 */
	public long adicionarVivienda (PersistenceManager pm, long id, int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, int num, String desc, long vec) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVivienda () + "(idOFERTACOMUN, capacidad, piscina, parqueadero, tvCable, wifi, precioNoche, numHabitaciones, descripcionSeguro, idVecino) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, capacidad, piscina, parqueadero, tvCable, wifi, precio, num, desc, vec);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una EMPRESA de la base de datos de AlohAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador de la empresa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarViviendaPorId (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVivienda () + " WHERE idOFERTACOMUN = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una EMPRESA de la 
	 * base de datos de AlohAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEmpresa - El identificador de la empresa
	 * @return El objeto EMPRESA que tiene el identificador dado
	 */
	public Vivienda darViviendaPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVivienda () + " WHERE idOFERTACOMUN = ?");
		q.setResultClass(Vivienda.class);
		q.setParameters(id);
		return (Vivienda) q.executeUnique();
	}



	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las empresas de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BAR
	 */
	public List<Vivienda> darListaVivienda (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVivienda ());
		q.setResultClass(Vivienda.class);
		return (List<Vivienda>) q.executeList();
	}

	
	
}
