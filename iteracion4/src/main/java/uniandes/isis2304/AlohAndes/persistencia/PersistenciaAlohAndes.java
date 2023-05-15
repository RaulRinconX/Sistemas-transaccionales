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



import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.AlohAndes.negocio.AdminVivienda;

import uniandes.isis2304.AlohAndes.negocio.Cliente;
import uniandes.isis2304.AlohAndes.negocio.ClienteMiembroComunidad;
import uniandes.isis2304.AlohAndes.negocio.Contrato;
import uniandes.isis2304.AlohAndes.negocio.Apartamento;

import uniandes.isis2304.AlohAndes.negocio.ReservaComun;
import uniandes.isis2304.AlohAndes.negocio.Operador;

import uniandes.isis2304.AlohAndes.negocio.Empresa;
import uniandes.isis2304.AlohAndes.negocio.Hospedaje;

import uniandes.isis2304.AlohAndes.negocio.OfertaComun;
import uniandes.isis2304.AlohAndes.negocio.OfertaExclusiva;
import uniandes.isis2304.AlohAndes.negocio.PropietarioMiembro;
import uniandes.isis2304.AlohAndes.negocio.ReservaColectiva;
import uniandes.isis2304.AlohAndes.negocio.Vecino;
import uniandes.isis2304.AlohAndes.negocio.Vivienda;
import uniandes.isis2304.AlohAndes.negocio.ViviendaUniversitaria;

/**
 * Clase para el manejador de persistencia del proyecto 
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaAlohAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaAlohAndes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaAlohAndes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	

	

	/**
	 * Atributo para el acceso a la tabla EMPRESA de la base de datos
	 */
	private SQLEmpresa sqlEmpresa;
	/**
	 * Atributo para el acceso a la tabla VECINO de la base de datos
	 */
	private SQLVecino sqlVecino;
	/**
	 * Atributo para el acceso a la tabla ADMINVIVIENDA de la base de datos
	 */
	private SQLAdminVivienda sqlAdminVivienda;
	/**
	 * Atributo para el acceso a la tabla PROPIETARIOMIEMBRO de la base de datos
	 */
	private SQLPropietarioMiembro sqlPropietarioMiembro;
	/**
	 * Atributo para el acceso a la tabla OfertaComun de la base de datos
	 */
	private SQLOfertaComun sqlOfertaComun;
	/**
	 * Atributo para el acceso a la tabla OfertaComun de la base de datos
	 */
	private SQLOfertaExclusiva sqlOfertaExclusiva;
	/**
	 * Atributo para el acceso a la tabla OfertaComun de la base de datos
	 */
	private SQLViviendaUniversitaria sqlViviendaUniversitaria;
	/**
	 * Atributo para el acceso a la tabla OfertaComun de la base de datos
	 */
	private SQLApartamento sqlApartamento;
	/**
	 * Atributo para el acceso a la tabla VIVIENDA de la base de datos
	 */
	private SQLVivienda sqlVivienda;
	/**
	 * Atributo para el acceso a la tabla HOSPEDAJE de la base de datos
	 */
	private SQLHospedaje sqlHospedaje;
	

	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLCliente sqlCliente;
	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLClienteMiembro sqlClienteMiembro;
	
	/**
	 * Atributo para el acceso a la tabla ReservaComun de la base de datos
	 */
	private SQLReservaComun sqlReservaComun;
	/**
	 * Atributo para el acceso a la tabla ReservaComun de la base de datos
	 */
	private SQLReservaColectiva sqlReservaColectiva;
	/**
	 * Atributo para el acceso a la tabla Contrato de la base de datos
	 */
	private SQLContrato sqlContrato;

	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaAlohAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("AlohAndes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("AlohAndes_sequence");
		tablas.add ("EMPRESA");
		tablas.add ("VECINO");
		tablas.add ("ADMINVIVIENDA");
		tablas.add ("PROPIETARIOMIEMBRO");
		tablas.add ("OFERTACOMUN");
		tablas.add ("VIVIENDA");
		tablas.add ("HOSPEDAJE");
		tablas.add ("OFERTAEXCLUSIVA");
		tablas.add ("VIVIENDAUNIVERSITARIA");
		tablas.add ("APARTAMENTO");
		tablas.add ("CLIENTE");
		tablas.add ("CLIENTEMIEMBROCOMUNIDAD");
		tablas.add ("RESERVACOMUN");
		tablas.add ("CONTRATO");
		tablas.add ("RESERVACOLECTIVA");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaAlohAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohAndes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{

		sqlEmpresa = new SQLEmpresa(this);
		sqlVecino = new SQLVecino(this);
		sqlAdminVivienda = new SQLAdminVivienda(this);
		sqlPropietarioMiembro = new SQLPropietarioMiembro(this);
		sqlOfertaComun = new SQLOfertaComun(this);
		sqlOfertaExclusiva = new SQLOfertaExclusiva(this);
		sqlViviendaUniversitaria = new SQLViviendaUniversitaria(this);
		sqlApartamento = new SQLApartamento(this);
		sqlVivienda= new SQLVivienda(this);
		sqlCliente = new SQLCliente(this);
		sqlClienteMiembro = new SQLClienteMiembro(this);
		sqlReservaComun = new SQLReservaComun (this);
		sqlContrato = new SQLContrato(this);
		sqlReservaColectiva = new SQLReservaColectiva (this);
		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqAlohAndes ()
	{
		return tablas.get (0);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de EMPRESA de AlohAndes
	 */
	public String darTablaEmpresa ()
	{
		return tablas.get (6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de VECINO de AlohAndes
	 */
	public String darTablaVecino ()
	{
		return tablas.get (12);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ADMINVIVIENDA de AlohAndes
	 */
	public String darTablaAdminVivienda ()
	{
		return tablas.get (1);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de PROPIETARIOMIEMBRO
	 */
	public String darTablaPropietarioMiembro ()
	{
		return tablas.get (10);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OFERTACOMUN
	 */
	public String darTablaOfertaComun ()
	{
		return tablas.get (8);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de VIVIENDA
	 */
	public String darTablaVivienda ()
	{
		return tablas.get (13);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de HOSPEDAJE
	 */
	public String darTablaHospedaje ()
	{
		return tablas.get (7);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OFERTAEXCLUSIVA
	 */
	public String darTablaOfertaExclusiva()
	{
		return tablas.get (9);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de VIVIENDAUNIVERSITARIA
	 */
	public String darTablaViviendaUniversitaria()
	{
		return tablas.get (14);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de APARTAMENTO
	 */
	public String darTablaApartamento()
	{
		return tablas.get (2);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaCliente ()
	{
		return tablas.get (3);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaClienteMiembro ()
	{
		return tablas.get (4);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaReservaComun ()
	{
		return tablas.get (11);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaContrato ()
	{
		return tablas.get (5);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaReservaColectiva ()
	{
		return tablas.get (15);
	}
	
	
	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Método para las consultas
	 *****************************************************************/
	/*public OfertaComun darOfertaComunPorId (long id) 
	{
		return (OfertaComun) sqlOfertaComun.darOfertaComunPorId (pmf.getPersistenceManager(), id);
	}*/


	/* ****************************************************************
	 * 			Métodos para manejar la OFERTACOMUN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public OfertaComun adicionarOfertaComun(int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id =nextval();
			tx.begin();
			long tuplasInsertadas = sqlOfertaComun.adicionarOfertaComun(pmf.getPersistenceManager(), id, capacidad, piscina, parqueadero, tvCable, wifi, precio);
			tx.commit();

			log.trace ("Inserción de OfertaComun: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new OfertaComun (id, capacidad, piscina, parqueadero, tvCable, wifi, precio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfertaComunPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlOfertaComun.eliminarOfertaComunPorId (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public OfertaComun darOfertaComunPorId (long id) 
	{
		return (OfertaComun) sqlOfertaComun.darOfertaComunPorId (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<OfertaComun> darListaOfertaComun ()
	{
		return sqlOfertaComun.darListaOfertaComun(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la OFERTACEXCLUSIVA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public OfertaExclusiva adicionarOfertaExclusiva(boolean hab,int precio) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			long tuplasInsertadas = sqlOfertaExclusiva.adicionarOfertaExclusiva(pmf.getPersistenceManager(), id, hab,precio);
			tx.commit();

			log.trace ("Inserción de OfertaExclusiva: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new OfertaExclusiva (id, hab, precio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfertaExclusivaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlOfertaExclusiva.eliminarOfertaExclusivaPorId (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public OfertaExclusiva darOfertaExclusivaPorId (long id) 
	{
		return (OfertaExclusiva) sqlOfertaExclusiva.darOfertaExclusivaPorId (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<OfertaExclusiva> darListaOfertaExclusiva ()
	{
		return sqlOfertaExclusiva.darListaOfertaExclusiva(pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la VIVIENDAUNIVERSITARIA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaUniversitaria adicionarViviendaUniversitaria(boolean hab,int precio, int rest, int sala, int gym, long adm) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			long tuplasInsertadas = sqlViviendaUniversitaria.adicionarViviendaUniversitaria(pmf.getPersistenceManager(), id, hab,precio, rest, sala, gym, adm);
			tx.commit();

			log.trace ("Inserción de ViviendaUniversitaria: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new ViviendaUniversitaria (id, hab, precio, rest, sala, gym, adm);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarViviendaUniversitariaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlViviendaUniversitaria.eliminarViviendaUniversitariaPorId (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long id) 
	{
		return (ViviendaUniversitaria) sqlViviendaUniversitaria.darViviendaUniversitaria (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<ViviendaUniversitaria> darListaViviendaUniversitaria ()
	{
		return sqlViviendaUniversitaria.darListaViviendaUniversitaria(pmf.getPersistenceManager());
	}
	/* ****************************************************************
	 * 			Métodos para manejar la APARTAMENTO
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Apartamento adicionarApartamento(boolean hab,int precio, boolean amob, boolean comi, boolean baño, int costo, long miem) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			long tuplasInsertadas = sqlApartamento.adicionarApartamento(pmf.getPersistenceManager(), id, hab,precio, amob, comi, baño, costo, miem);
			tx.commit();

			log.trace ("Inserción de ViviendaUniversitaria: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Apartamento (id, hab, precio, amob, comi, baño, costo, miem);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarApartamentoPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlApartamento.eliminarApartamento (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public Apartamento darApartamentoPorId (long id) 
	{
		return (Apartamento) sqlApartamento.darApartamento (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Apartamento> darListaApartamento ()
	{
		return sqlApartamento.darListaApartamento(pmf.getPersistenceManager());
	}
	/* ****************************************************************
	 * 			Métodos para manejar la VIVIENDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Vivienda adicionarVivienda( int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, int num, String desc, long idOp) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			long tuplasInsertadas = sqlVivienda.adicionarVivienda(pmf.getPersistenceManager(), id, capacidad, piscina, parqueadero, tvCable, wifi, precio, num, desc, idOp);
			tx.commit();

			log.trace ("Inserción de Vivienda: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Vivienda(id, capacidad, piscina, parqueadero, tvCable, wifi, precio, num, desc, idOp);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarViviendaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlVivienda.eliminarViviendaPorId (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public Vivienda darViviendaPorId (long id) 
	{
		return (Vivienda) sqlVivienda.darViviendaPorId (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Vivienda> darListaVivienda ()
	{
		return sqlVivienda.darListaVivienda(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar la HOSPEDAJE
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Hospedaje adicionarHospedaje( int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, String cat, String tama, boolean rec, boolean rest, long emp) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			long tuplasInsertadas = sqlHospedaje.adicionarHospedaje(pmf.getPersistenceManager(), id, capacidad, piscina, parqueadero, tvCable, wifi, precio, cat, tama, rec, rest, emp);
			tx.commit();

			log.trace ("Inserción de Hospedaje: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Hospedaje(id, capacidad, piscina, parqueadero, tvCable, wifi, precio, cat, tama, rec, rest, emp);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarHospedaje (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHospedaje.eliminarHospedajePorId (pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public Hospedaje darHospedajePorId (long id) 
	{
		return (Hospedaje) sqlHospedaje.darHospedajePorId (pmf.getPersistenceManager(), id);
	}



	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Hospedaje> darListaHospedaje ()
	{
		return sqlHospedaje.darListaHospedaje(pmf.getPersistenceManager());
	}



	/* ****************************************************************
	 * 			Métodos para manejar las EMPRESAS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
	 * Adiciona entradas al log de la aplicación

	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Empresa adicionarEmpresa(long id, String nombre, String tipoIdentificacion, String registro, String tipo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlEmpresa.adicionarEmpresa(pm, id, nombre, tipoIdentificacion, registro, tipo);
			tx.commit();

			log.trace ("Inserción de EMPRESA: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Empresa (id, nombre, tipoIdentificacion, registro, tipo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el identificador de la empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarEmpresaPorId (long idEmpresa) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlEmpresa.eliminarEmpresaPorId (pm, idEmpresa);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA
	 * @return La lista de objetos EMPRESA, construidos con base en las tuplas de la tabla n
	 */
	public List<Empresa> darListaEmpresa ()
	{
		return sqlEmpresa.darListaEmpresa(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA que tienen el identificador dado
	 * @param idEmpresa - El identificador de la EMPRESA
	 * @return El objeto EMPRESA, construido con base en la tuplas de la tabla EMPRESA, que tiene el identificador dado
	 */
	public Empresa darEmpresaPorId (long idEmpresa)
	{
		return sqlEmpresa.darEmpresaPorId (pmf.getPersistenceManager(), idEmpresa);
	}

	/* ****************************************************************
	 * 			Métodos para manejar los VECINOS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
	 * Adiciona entradas al log de la aplicación

	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Vecino adicionarVecino(long id, String nombre, String tipoIdentificacion, String ubicacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlVecino.adicionarVecino(pm, id, nombre, tipoIdentificacion, ubicacion);
			tx.commit();

			log.trace ("Inserción de VECINO: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Vecino(id, nombre, tipoIdentificacion, ubicacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el identificador de la empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVecinoPorId (long idVecino) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlVecino.eliminarVecinoPorId (pm, idVecino);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA
	 * @return La lista de objetos EMPRESA, construidos con base en las tuplas de la tabla n
	 */
	public List<Vecino> darListaVecino ()
	{
		return sqlVecino.darListaVecino(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA que tienen el identificador dado
	 * @param idEmpresa - El identificador del VECINO
	 * @return El objeto EMPRESA, construido con base en la tuplas de la tabla EMPRESA, que tiene el identificador dado
	 */
	public Vecino darVecinoPorId (long idVecino)
	{
		return sqlVecino.darVecinoPorId (pmf.getPersistenceManager(), idVecino);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ADMINVIVIENDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
	 * Adiciona entradas al log de la aplicación

	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public AdminVivienda adicionarAdminVivienda(long id, String nombre, String tipoIdentificacion, String ubicacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlAdminVivienda.adicionarAdminVivienda(pm, id, nombre, tipoIdentificacion, ubicacion);
			tx.commit();

			log.trace ("Inserción de AdminVivienda: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new AdminVivienda(id, nombre, tipoIdentificacion, ubicacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el identificador de la empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarAdminViviendaPorId (long idAdminVivienda) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlAdminVivienda.eliminarAdminViviendaPorId (pm, idAdminVivienda);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA
	 * @return La lista de objetos EMPRESA, construidos con base en las tuplas de la tabla n
	 */
	public List<AdminVivienda> darListaAdminVivienda ()
	{
		return sqlAdminVivienda.darListaAdminVivienda(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA que tienen el identificador dado
	 * @param idEmpresa - El identificador del VECINO
	 * @return El objeto ADMINVIVIENDA, construido con base en la tuplas de la tabla EMPRESA, que tiene el identificador dado
	 */
	public AdminVivienda darAdminViviendaPorId (long idAdminVivienda)
	{
		return sqlAdminVivienda.darAdminViviendaPorId (pmf.getPersistenceManager(), idAdminVivienda);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROPIETARIOMIEMBRO
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla EMPRESA
	 * Adiciona entradas al log de la aplicación

	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public PropietarioMiembro adicionarPropietarioMiembro(long id, String nombre, String tipoIdentificacion, String tipoMiembro, String ubicacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlPropietarioMiembro.adicionarPropietarioMiembro(pm, id, nombre, tipoIdentificacion, tipoMiembro, ubicacion);
			tx.commit();

			log.trace ("Inserción de PROPIETARIOMIEMBRO: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new PropietarioMiembro (id, nombre, tipoIdentificacion, tipoMiembro, ubicacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla EMPRESA, dado el identificador de la empresa
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPropietarioMiembroPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPropietarioMiembro.eliminarPropietarioMiembroPorId (pm, id);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA
	 * @return La lista de objetos EMPRESA, construidos con base en las tuplas de la tabla n
	 */
	public List<PropietarioMiembro> darListaPropietarioMiembro()
	{
		return sqlPropietarioMiembro.darListaPropietarioMiembro(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla EMPRESA que tienen el identificador dado
	 * @param idEmpresa - El identificador de la EMPRESA
	 * @return El objeto EMPRESA, construido con base en la tuplas de la tabla EMPRESA, que tiene el identificador dado
	 */
	public PropietarioMiembro darPropietarioMiembroPorId (long id)
	{
		return sqlPropietarioMiembro.darPropietarioMiembroPorId (pmf.getPersistenceManager(), id);
	}
	/* ****************************************************************
	 * 			Métodos para manejar la relación CLIENTE
	 *****************************************************************/


	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla n
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del n
	 * @param ciudad - La ciudad del n
	 * @param presupuesto - El presupuesto del n (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del n en la ciudad (Mayor que 0)
	 * @return El objeto n adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(long id, String correo,String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlCliente.adicionarCliente(pm, id, correo, nombre);
			tx.commit();

			log.trace ("Inserción de Cliente: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Cliente (id, correo, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarCliente(long idCliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarCliente(pm, idCliente);           
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<Cliente> darListaCliente ()
	{
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación CLIENTEMIEMBRO
	 *****************************************************************/


	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla n
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del n
	 * @param ciudad - La ciudad del n
	 * @param presupuesto - El presupuesto del n (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del n en la ciudad (Mayor que 0)
	 * @return El objeto n adicionado. null si ocurre alguna Excepción
	 */
	public ClienteMiembroComunidad adicionarClienteMiembro(long id, String correo,String nombre, String tipo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlClienteMiembro.adicionarClienteMiembro(pm, id, correo, nombre, tipo);
			tx.commit();

			log.trace ("Inserción de ClienteMiembro: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new ClienteMiembroComunidad (id, correo, nombre, tipo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClienteMiembro(long idCliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlClienteMiembro.eliminarClienteMiembro(pm, idCliente);           
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<ClienteMiembroComunidad> darListaClienteMiembro ()
	{
		return sqlClienteMiembro.darClientesMiembro (pmf.getPersistenceManager());
	}



	
	/* ****************************************************************
	 * 			Métodos para manejar RESERVACOMUN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla contrato
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto contrato adicionado. null si ocurre alguna Excepción
	 */
	public ReservaComun adicionarReservaComun(long idCliente, long idOferta, Date inicio, Date fin)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlReservaComun.adicionarReservaComun(pm, idCliente, idOferta, inicio, fin, 0);
			tx.commit();

			log.trace ("Inserción de ReservaComun: " + idCliente +", "+idOferta + ": " + tuplasInsertadas + " tuplas insertadas");
			return new ReservaComun (idCliente, idOferta, inicio, fin, 0);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public ReservaComun adicionarReservaComunDeColectiva(long idCliente, long idOferta, Date inicio, Date fin, long colec)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlReservaComun.adicionarReservaComun(pm, idCliente, idOferta, inicio, fin, colec);
			tx.commit();

			log.trace ("Inserción de ReservaComun: " + idCliente +", "+idOferta + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ReservaComun (idCliente, idOferta, inicio, fin, colec);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla 
	 * Adiciona entradas al log de la aplicación
	 * @param idcontrato - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaComun (long idCliente, long idOferta) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReservaComun.eliminarReservaComun(pm, idCliente, idOferta);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla contrato
	 * @return La lista de objetos contrato, construidos con base en las tuplas de la tabla contrato
	 */
	public List<ReservaComun> darListaReservaComun ()
	{
		return sqlReservaComun.darListaReservaComun (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Orden con un identificador dado
	 * @param idOrden- El identificador de la orden
	 * @return El objeto Orden, construido con base en las tuplas de la tabla ORDEN con el identificador dado
	 */
	public ReservaComun darReservaComun (long idCliente, long idOferta)
	{
		return sqlReservaComun.darReservaComun(pmf.getPersistenceManager(), idCliente, idOferta);
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar CONTRATO
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla contrato
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto contrato adicionado. null si ocurre alguna Excepción
	 */
	public Contrato adicionarContrato(long idCliente, long idOferta, Date inicio, Date fin)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlContrato.adicionarContrato(pm, idCliente, idOferta, inicio, fin);
			tx.commit();

			log.trace ("Inserción de Contrato: " + idCliente +", "+idOferta + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Contrato (idCliente, idOferta, inicio, fin);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla 
	 * Adiciona entradas al log de la aplicación
	 * @param idcontrato - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarContrato (long idCliente, long idOferta) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlContrato.eliminarContrato(pm, idCliente, idOferta);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla contrato
	 * @return La lista de objetos contrato, construidos con base en las tuplas de la tabla contrato
	 */
	public List<Contrato> darListaContrato ()
	{
		return sqlContrato.darListaContrato (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Orden con un identificador dado
	 * @param idOrden- El identificador de la orden
	 * @return El objeto Orden, construido con base en las tuplas de la tabla ORDEN con el identificador dado
	 */
	public Contrato darContrato (long idCliente, long idOferta)
	{
		return sqlContrato.darContrato(pmf.getPersistenceManager(), idCliente, idOferta);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar RESERVACOMUN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla contrato
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto contrato adicionado. null si ocurre alguna Excepción
	 */
	public ReservaColectiva adicionarReservaColectiva(String nomb, int cantidad, String tipo, Date inicio, Date fin, long idEmp)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();
			int count = 0;
			boolean comp = true;
			if(tipo == "vivienda" )
			{
				List<Hospedaje> ofertas = darListaHospedaje();
				for(int o = 0; o <= ofertas.size() &&comp == true;o++)
				{
					long idOf = ofertas.get(o).getId();
					ReservaComun res = adicionarReservaComun(idEmp, idOf, inicio, fin);
					if(res != null)
					{
						count++;
					}
					else 
					{
						comp =false;
					}
				}
			}
			if(count ==cantidad){
				
			
			long tuplasInsertadas = sqlReservaColectiva.adicionarReservaColectiva(pm, id,cantidad, nomb);
			tx.commit();

			log.trace ("Inserción de ReservaColectiva: " + id + tuplasInsertadas + " tuplas insertadas");

			return new ReservaColectiva (id,cantidad, nomb);
			}
			else
				return null;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla 
	 * Adiciona entradas al log de la aplicación
	 * @param idcontrato - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaColectiva (long idColectiva) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp1 = sqlReservaComun.eliminarReservaComunPorColectiva(pm, idColectiva);
			long resp = sqlReservaColectiva.eliminarReservaColectiva(pm, idColectiva);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla contrato
	 * @return La lista de objetos contrato, construidos con base en las tuplas de la tabla contrato
	 */
	public List<ReservaColectiva> darListaReservaColectiva ()
	{
		return sqlReservaColectiva.darListaReservaColectiva (pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Orden con un identificador dado
	 * @param idOrden- El identificador de la orden
	 * @return El objeto Orden, construido con base en las tuplas de la tabla ORDEN con el identificador dado
	 */
	public ReservaColectiva darReservaColectiva (long idColectiva)
	{
		return sqlReservaColectiva.darReservaColectiva(pmf.getPersistenceManager(), idColectiva);
	}
	
	
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos 
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * contrato, BEBEDOR y n, respectivamente
	 */
	public long [] limpiarAlohAndes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarAlohAndes (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}


}
