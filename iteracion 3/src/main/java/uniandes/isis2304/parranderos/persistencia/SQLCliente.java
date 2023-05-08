package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClientesFrecuentes;

public class SQLCliente {

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
	public SQLCliente (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCliente (PersistenceManager pm, Long numeroDocumento, String tipoDocumento, String nombre, String nacionalidad, 
			String tipo, String userName, String contrasena) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaClientes() + "(numero_documento, tipo_documento, nombre, nacionalidad, tipo, username, contrasena) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(numeroDocumento, tipoDocumento, nombre, nacionalidad, tipo, userName, contrasena );
        return (long) q.executeUnique();
	}

	// listar clientes
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM CLIENTES ");

		return (List<Cliente>) q.executeResultList(Cliente.class);
	}

	public List<ClientesFrecuentes> darClientesFrecuentes(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "WITH subconsulta AS" +
								   " (SELECT r.doc_cliente as cliente,"+ 
				  				   " COUNT(*) AS numero_de_reservas,"+
				                   " TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches"+
								   " FROM RESERVAS r"+
								   " GROUP BY r.doc_cliente, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio))"+
		 					 	   " SELECT s.cliente, s.numero_de_reservas, s.numero_noches"+
		 						   " FROM subconsulta s"+
		 						   " WHERE s.numero_noches >= 15 AND s.numero_de_reservas >= 3");

		return (List<ClientesFrecuentes>) q.executeResultList(ClientesFrecuentes.class);
	}

}
