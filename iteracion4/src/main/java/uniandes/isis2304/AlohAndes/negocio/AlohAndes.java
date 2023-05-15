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
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.AlohAndes.persistencia.PersistenciaAlohAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 */
public class AlohAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AlohAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public AlohAndes ()
	{
		pp = PersistenciaAlohAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public AlohAndes (JsonObject tableConfig)
	{
		pp = PersistenciaAlohAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las consultas
	 *****************************************************************/

	/*public List<Vecino> darListaVecino ()
	{
        log.info ("Listando Vecinos");
        List<Vecino> listaVec = pp.darListaVecino() ;	
        log.info ("Listando Empresas: " + listaVec.size() + " vecinos existentes");
        return listaVec;
	}*/

	/* ****************************************************************
	 * 			Métodos para manejar las OFERTACOMUN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public OfertaComun adicionarOfertaComun (int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio)
	{
        log.info ("Adicionando OfertaComun " );
        OfertaComun oc = pp.adicionarOfertaComun (capacidad, piscina, parqueadero, tvCable, wifi, precio);
        log.info ("Adicionando OfertaComun: " + oc);
        return oc;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOfertaComunPorId (long id)
	{
        log.info ("Eliminando OfertaComun por id: " + id);
        long resp = pp.eliminarOfertaComunPorId (id);
        log.info ("Eliminando OfertaComun por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public OfertaComun darOfertaComunPorId (long id)
	{
        log.info ("Dar información de una OfertaComun por id: " + id);
        OfertaComun oc = pp.darOfertaComunPorId(id);
        log.info ("Buscando OfertaComun por Id: " + oc != null ? oc : "NO EXISTE");
        return oc;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<OfertaComun> darListaOfertaComun ()
	{
        log.info ("Listando OfertaComun");
        List<OfertaComun> oc = pp.darListaOfertaComun ();	
        log.info ("Listando Oferta Comun: " + oc.size() + " ofertas comunes existentes");
        return oc;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOfertaComun> darVOOfertaComun ()
	{
        log.info ("Generando los VO de OfertaComun");
         List<VOOfertaComun> voOC = new LinkedList<VOOfertaComun> ();
        for (OfertaComun oc : pp.darListaOfertaComun ())
        {
        	voOC.add (oc);
        }
        log.info ("Generando los VO de OfertaComun: " + voOC.size() + " ofertas comunes existentes");
       return voOC;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las OFERTAEXCLUSIVA
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public OfertaExclusiva adicionarOfertaExclusiva (boolean hab, int precio)
	{
        log.info ("Adicionando OfertaExclusiva: ");
        OfertaExclusiva oc = pp.adicionarOfertaExclusiva (hab, precio);
        log.info ("Adicionando OfertaExclusiva: " + oc);
        return oc;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOfertaExclusivaPorId (long id)
	{
        log.info ("Eliminando OfertaExclusiva por id: " + id);
        long resp = pp.eliminarOfertaExclusivaPorId (id);
        log.info ("Eliminando OfertaExclusiva por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public OfertaExclusiva darOfertaExclusivaPorId (long id)
	{
        log.info ("Dar información de una OfertaExclusiva por id: " + id);
        OfertaExclusiva oc = pp.darOfertaExclusivaPorId(id);
        log.info ("Buscando OfertaExclusiva por Id: " + oc != null ? oc : "NO EXISTE");
        return oc;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<OfertaExclusiva> darListaOfertaExclusiva ()
	{
        log.info ("Listando OfertaExclusiva");
        List<OfertaExclusiva> oc = pp.darListaOfertaExclusiva ();	
        log.info ("Listando Oferta Exclusiva: " + oc.size() + " ofertas exclusivas existentes");
        return oc;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOfertaExclusiva> darVOOfertaExclusiva ()
	{
        log.info ("Generando los VO de OfertaComun");
         List<VOOfertaExclusiva> voOC = new LinkedList<VOOfertaExclusiva> ();
        for (OfertaExclusiva oc : pp.darListaOfertaExclusiva ())
        {
        	voOC.add (oc);
        }
        log.info ("Generando los VO de OfertaExclusiva: " + voOC.size() + " ofertas exclusivas existentes");
       return voOC;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAUNIVERSITARIA
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public ViviendaUniversitaria adicionarViviendaUniversitaria (boolean hab, int precio, int rest, int sala, int gym, long adm)
	{
        log.info ("Adicionando ViviendaUniversitaria " );
        ViviendaUniversitaria vu = pp.adicionarViviendaUniversitaria (hab, precio, rest, sala, gym, adm);
        log.info ("Adicionando ViviendaUniversitaria: " + vu);
        return vu;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarViviendaUniversitariaPorId (long id)
	{
        log.info ("Eliminando ViviendaUniversitaria por id: " + id);
        long resp = pp.eliminarViviendaUniversitariaPorId (id);
        log.info ("Eliminando ViviendaUniversitaria por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public ViviendaUniversitaria darViviendaUniversitariaPorId (long id)
	{
        log.info ("Dar información de una Vivienda Universitaria por id: " + id);
        ViviendaUniversitaria vu = pp.darViviendaUniversitariaPorId(id);
        log.info ("Buscando ViviendaUniversitaria por Id: " + vu != null ? vu : "NO EXISTE");
        return vu;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<ViviendaUniversitaria> darListaViviendaUniversitaria ()
	{
        log.info ("Listando ViviendaUniversitaria");
        List<ViviendaUniversitaria> vu = pp.darListaViviendaUniversitaria();	
        log.info ("Listando ViviendaUniversitaria: " + vu.size() + "  viviendas universitarias existentes");
        return vu;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOViviendaUniversitaria> darVOViviendaUniversitaria ()
	{
        log.info ("Generando los VO de ViviendaUniversitaria");
         List<VOViviendaUniversitaria> voVU = new LinkedList<VOViviendaUniversitaria> ();
        for (ViviendaUniversitaria vu : pp.darListaViviendaUniversitaria ())
        {
        	voVU.add (vu);
        }
        log.info ("Generando los VO de ViviendaUniversitaria: " + voVU.size() + " viviendas universitarias existentes");
       return voVU;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los APARTAMENTOS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Apartamento adicionarApartamento ( boolean hab, int precio, boolean amob, boolean comi, boolean baño, int costo, long miem)
	{
        log.info ("Adicionando Apartamento: ");
        Apartamento apto = pp.adicionarApartamento( hab, precio, amob, comi, baño, costo, miem);
        log.info ("Adicionando Apartamento: " + apto);
        return apto;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarApartamentoPorId (long id)
	{
        log.info ("Eliminando Apartamento por id: " + id);
        long resp = pp.eliminarApartamentoPorId (id);
        log.info ("Eliminando Apartamento por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Apartamento darApartamentoPorId (long id)
	{
        log.info ("Dar información de una Apartamento por id: " + id);
        Apartamento apto = pp.darApartamentoPorId(id);
        log.info ("Buscando ViviendaUniversitaria por Id: " + apto != null ? apto : "NO EXISTE");
        return apto;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Apartamento> darListaApartamento ()
	{
        log.info ("Listando Apartamento");
        List<Apartamento> apto = pp.darListaApartamento();	
        log.info ("Listando Apartamento: " + apto.size() + "  Apartamentos existentes");
        return apto;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOApartamento> darVOApartamento ()
	{
        log.info ("Generando los VO de Apartamento");
         List<VOApartamento> voApto = new LinkedList<VOApartamento> ();
        for (Apartamento apt : pp.darListaApartamento ())
        {
        	voApto.add (apt);
        }
        log.info ("Generando los VO de ViviendaUniversitaria: " + voApto.size() + " viviendas universitarias existentes");
       return voApto;
	}
	/* ****************************************************************
	 * 			Métodos para manejar las VIVIENDAS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Vivienda adicionarVivienda ( int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, int num, String desc, long id)
	{
        log.info ("Adicionando Vivienda a: " + id);
        Vivienda viv = pp.adicionarVivienda (capacidad, piscina, parqueadero, tvCable, wifi, precio, num, desc, id);
        log.info ("Adicionando Vivienda a: " + viv);
        return viv;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarViviendaPorId (long id)
	{
        log.info ("Eliminando Vivienda por id: " + id);
        long resp = pp.eliminarViviendaPorId (id);
        log.info ("Eliminando Vivienda por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Vivienda darViviendaPorId (long id)
	{
        log.info ("Dar información de una Vivienda por id: " + id);
        Vivienda viv = pp.darViviendaPorId(id);
        log.info ("Buscando OfertaComun por Id: " + viv != null ? viv : "NO EXISTE");
        return viv;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Vivienda> darListaVivienda ()
	{
        log.info ("Listando Vivienda");
        List<Vivienda> viv = pp.darListaVivienda ();	
        log.info ("Listando Vivienda: " + viv.size() + " viviendas existentes");
        return viv;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVivienda> darVOVivienda ()
	{
        log.info ("Generando los VO de Vivienda");
         List<VOVivienda> voViv = new LinkedList<VOVivienda> ();
        for (Vivienda viv : pp.darListaVivienda ())
        {
        	voViv.add (viv);
        }
        log.info ("Generando los VO de Vivienda: " + voViv.size() + " viviendas existentes");
       return voViv;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los HOSPEDAJES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Hospedaje adicionarHospedaje (int capacidad, boolean piscina, boolean parqueadero, boolean tvCable, boolean wifi, int precio, String cat, String tama, boolean rec, boolean rest, long emp)
	{
        log.info ("Adicionando Hospedaje: ");
        Hospedaje hos = pp.adicionarHospedaje ( capacidad, piscina, parqueadero, tvCable, wifi, precio, cat, tama, rec, rest, emp);
        log.info ("Adicionando Hospedaje: " + hos);
        return hos;
	}


	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarHospedajePorId (long id)
	{
        log.info ("Eliminando Hospedaje por id: " + id);
        long resp = pp.eliminarHospedaje (id);
        log.info ("Eliminando Hospedaje por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Hospedaje darHospedajePorId (long id)
	{
        log.info ("Dar información de un Hospedaje por id: " + id);
        Hospedaje hos = pp.darHospedajePorId(id);
        log.info ("Buscando Hospedaje por Id: " + hos != null ? hos : "NO EXISTE");
        return hos;
	}

	



	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Hospedaje> darListaHospedaje ()
	{
        log.info ("Listando Hospedaje");
        List<Hospedaje> hos = pp.darListaHospedaje ();	
        log.info ("Listando Vivienda: " + hos.size() + " hospedajes existentes");
        return hos;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOHospedaje> darVOHospedaje ()
	{
        log.info ("Generando los VO de Hospedaje");
         List<VOHospedaje> voHos = new LinkedList<VOHospedaje> ();
        for (Hospedaje vos: pp.darListaHospedaje ())
        {
        	voHos.add (vos);
        }
        log.info ("Generando los VO de Hospedaje: " + voHos.size() + " hospedajes existentes");
       return voHos;
	}
	/* ****************************************************************
	 * 			Métodos para manejar las empresas
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una empresa
	 * Adiciona entradas al log de la aplicación
	 * @param id - El identificador de la empresa
	 * @param nombre - El nombre de la empresa
	 * @param tipoID - El tipo de identificacion
	 * @param registro - El registro de camara que tiene la empresa
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Empresa adicionarEmpresa (long id, String nombre, String tipoID, String registro, String tipo)
	{
        log.info ("Adicionando Empresa: " + nombre);
        Empresa emp = pp.adicionarEmpresa (id, nombre, tipoID, registro, tipo);
        log.info ("Adicionando Empresa: " + emp);
        return emp;
	}
	

	
	/**
	 * Elimina una empresa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEmpresaPorId (long idEmpresa)
	{
        log.info ("Eliminando Empresa por id: " + idEmpresa);
        long resp = pp.eliminarEmpresaPorId (idEmpresa);
        log.info ("Eliminando Empresa: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los bares en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<Empresa> darListaEmpresa ()
	{
        log.info ("Listando Empresas");
        List<Empresa> listaEmpresas = pp.darListaEmpresa ();	
        log.info ("Listando Empresas: " + listaEmpresas.size() + " empresas existentes");
        return listaEmpresas;
	}

	/**
	 * Encuentra todos las empresas en AlohAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos EMPRESA con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEmpresa> darVOEmpresas ()
	{
		log.info ("Generando los VO de Empresas");
		List<VOEmpresa> voEmp = new LinkedList<VOEmpresa> ();
		for (VOEmpresa emp: pp.darListaEmpresa ())
		{
			voEmp.add (emp);
		}
		log.info ("Generando los VO de Empresas: " + voEmp.size () + " empresas existentes");
		return voEmp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los VECINOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un vecino
	 * Adiciona entradas al log de la aplicación
	 * @param id - El identificador de la empresa
	 * @param nombre - El nombre de la empresa
	 * @param tipoID - El tipo de identificacion
	 * @param registro - El registro de camara que tiene la empresa
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public Vecino adicionarVecino (long id, String nombre, String tipoID, String ubicacion)
	{
        log.info ("Adicionando Vecino: " + nombre);
        Vecino vec = pp.adicionarVecino (id, nombre, tipoID, ubicacion);
        log.info ("Adicionando Vecino: " + vec);
        return vec;
	}
	

	
	/**
	 * Elimina una empresa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVecinoPorId (long idVecino)
	{
        log.info ("Eliminando Vecino por id: " + idVecino);
        long resp = pp.eliminarVecinoPorId (idVecino);
        log.info ("Eliminando Vecino: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los vecinos en ALOHANDES
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<Vecino> darListaVecino ()
	{
        log.info ("Listando Vecinos");
        List<Vecino> listaVec = pp.darListaVecino() ;	
        log.info ("Listando Empresas: " + listaVec.size() + " vecinos existentes");
        return listaVec;
	}

	/**
	 * Encuentra todos las empresas en AlohAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VECINO con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVecino> darVOVecinos ()
	{
		log.info ("Generando los VO de Vecino");
		List<VOVecino> voVec = new LinkedList<VOVecino> ();
		for (VOVecino vec: pp.darListaVecino ())
		{
			voVec.add (vec);
		}
		log.info ("Generando los VO de Vecinos: " + voVec.size () + " vecinos existentes");
		return voVec;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los ADMINVIVIENDA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un vecino
	 * Adiciona entradas al log de la aplicación
	 * @param id - El identificador de la empresa
	 * @param nombre - El nombre de la empresa
	 * @param tipoID - El tipo de identificacion
	 * @param registro - El registro de camara que tiene la empresa
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public AdminVivienda adicionarAdminVivienda (long id, String nombre, String tipoID, String ubicacion)
	{
        log.info ("Adicionando AdminVivienda: " + nombre);
        AdminVivienda adm = pp.adicionarAdminVivienda (id, nombre, tipoID, ubicacion);
        log.info ("Adicionando AdminVivienda: " + adm);
        return adm;
	}
	

	
	/**
	 * Elimina una empresa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminViviendaPorId (long idAdminVivienda)
	{
        log.info ("Eliminando AdminVivienda por id: " + idAdminVivienda);
        long resp = pp.eliminarAdminViviendaPorId (idAdminVivienda);
        log.info ("Eliminando AdminVivienda: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los vecinos en ALOHANDES
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<AdminVivienda> darListaAdminVivienda ()
	{
        log.info ("Listando AdminVivienda");
        List<AdminVivienda> listaAdm = pp.darListaAdminVivienda() ;	
        log.info ("Listando AdminVivienda: " + listaAdm.size() + " adminVivienda existentes");
        return listaAdm;
	}

	/**
	 * Encuentra todos las empresas en AlohAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VECINO con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAdminVivienda> darVOAdminVivienda ()
	{
		log.info ("Generando los VO de AdminVivienda");
		List<VOAdminVivienda> voAdm = new LinkedList<VOAdminVivienda> ();
		for (VOAdminVivienda adm: pp.darListaAdminVivienda())
		{
			voAdm.add (adm);
		}
		log.info ("Generando los VO de Vecinos: " + voAdm.size () + " adminVivienda existentes");
		return voAdm;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las propietarios miembro
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una empresa
	 * Adiciona entradas al log de la aplicación
	 * @param id - El identificador de la empresa
	 * @param nombre - El nombre de la empresa
	 * @param tipoID - El tipo de identificacion
	 * @param ubicacion - La ubicacion del propietario
	 * @return El objeto Empresa adicionado. null si ocurre alguna Excepción
	 */
	public PropietarioMiembro adicionarPropietarioMiembro (long id, String nombre, String tipoID, String tipo, String ubicacion)
	{
        log.info ("Adicionando PropietarioMiembro: " + nombre);
        PropietarioMiembro prop = pp.adicionarPropietarioMiembro (id, nombre, tipoID, tipo, ubicacion);
        log.info ("Adicionando PropietarioMiembro: " + prop);
        return prop;
	}
	

	
	/**
	 * Elimina una empresa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEmpresa - El identificador de la empresa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPropietarioMiembroPorId (long id)
	{
        log.info ("Eliminando PropietarioMiembro por id: " + id);
        long resp = pp.eliminarPropietarioMiembroPorId (id);
        log.info ("Eliminando PropietarioMiembro: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los propietarios
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos PROPIETARIOMIEMBRO con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<PropietarioMiembro> darListaPropietarioMiembro ()
	{
        log.info ("Listando Propietarios");
        List<PropietarioMiembro> listaProp = pp.darListaPropietarioMiembro ();	
        log.info ("Listando Propietarios: " + listaProp.size() + " propietarios existentes");
        return listaProp;
	}

	/**
	 * Encuentra todos las empresas en AlohAndes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos EMPRESA con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPropietarioMiembro> darVOPropietarioMiembro ()
	{
		log.info ("Generando los VO de PropietarioMiembro");
		List<VOPropietarioMiembro> voProp = new LinkedList<VOPropietarioMiembro> ();
		for (VOPropietarioMiembro prop: pp.darListaPropietarioMiembro ())
		{
			voProp.add (prop);
		}
		log.info ("Generando los VO de PropietarioMiembro: " + voProp.size () + " propietarios existentes");
		return voProp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación CLIENTE
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return Un objeto Gustan con los valores dados
	 */
	public Cliente adicionarCliente (long idCliente, String correo, String nombre)
	{
        log.info ("Adicionando Cliente [" + idCliente + "]");
        Cliente resp = pp.adicionarCliente (idCliente, correo, nombre);
        log.info ("Adicionando Cliente: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarCliente (long idCliente)
	{
        log.info ("Eliminando cliente");
        long resp = pp.eliminarCliente (idCliente);
        log.info ("Eliminando cliente: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los gustan en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Cliente> darListaCliente ()
	{
        log.info ("Listando Gustan");
        List<Cliente> cli = pp.darListaCliente ();	
        log.info ("Listando Gustan: " + cli.size() + " clientes existentes");
        return cli;
	}

	/**
	 * Encuentra todos los gustan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCliente> darVOCliente ()
	{
		log.info ("Generando los VO de Cliente");
		List<VOCliente> voCliente= new LinkedList<VOCliente> ();
		for (VOCliente cli: pp.darListaCliente ())
		{
			voCliente.add (cli);
		}
		log.info ("Generando los VO de Afiliado: " + voCliente.size () + "Clientes existentes");
		return voCliente;
	}
	/* ****************************************************************
	 * 			Métodos para manejar la relación CLIENTEMIEMBROCOMUNIDAD
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return Un objeto Gustan con los valores dados
	 */
	public ClienteMiembroComunidad adicionarClienteMiembro (long idCliente, String correo, String nombre, String tipo)
	{
        log.info ("Adicionando ClienteMiembroComunidad [" + idCliente + "]");
        ClienteMiembroComunidad resp = pp.adicionarClienteMiembro (idCliente, correo, nombre, tipo);
        log.info ("Adicionando ClienteMiembroComunidad: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarClienteMiembro (long idCliente)
	{
        log.info ("Eliminando clienteMiembro");
        long resp = pp.eliminarClienteMiembro (idCliente);
        log.info ("Eliminando clienteMiembro: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los gustan en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<ClienteMiembroComunidad> darListaClienteMiembro ()
	{
        log.info ("Listando clienteMiembro");
        List<ClienteMiembroComunidad> cli = pp.darListaClienteMiembro ();	
        log.info ("Listando clienteMiembro: " + cli.size() + " clientes miembro existentes");
        return cli;
	}

	/**
	 * Encuentra todos los gustan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOClienteMiembro> darVOClienteMiembro ()
	{
		log.info ("Generando los VO de Cliente");
		List<VOClienteMiembro> voCliente= new LinkedList<VOClienteMiembro> ();
		for (VOClienteMiembro cli: pp.darListaClienteMiembro ())
		{
			voCliente.add (cli);
		}
		log.info ("Generando los VO de ClienteMiembro: " + voCliente.size () + "Clientes existentes");
		return voCliente;
	}

	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar RESERVACOMUN
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una relacion RESERVACOMUN
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public ReservaComun adicionarReservaComun (long idCliente, long idOferta, Date inicio, Date fin)
	{
        log.info ("Adicionando ReservaComun: " + idCliente + "," + idOferta);
        ReservaComun res = pp.adicionarReservaComun(idCliente, idOferta, inicio, fin);	
        log.info ("Adicionando Contrata: " + res);
        return res;
	}
	

	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarReservaComun (long idCliente, long idOferta)
	{
		log.info ("Eliminando ReservaComun: " + idCliente +", " + idOferta);
        long resp = pp.eliminarReservaComun (idCliente, idOferta);		
        log.info ("Eliminando ReservaComun: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<ReservaComun> darListaReservaComun ()
	{
		log.info ("Consultando ReservaComun");
        List<ReservaComun> res = pp.darListaReservaComun();	
        log.info ("Consultando reservas comunes: " + res.size() + " existentes");
        return res;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOReservaComun> darVOReservaComun()
	{
		log.info ("Generando los VO ReservaComun");        
        List<VOReservaComun> voRes = new LinkedList<VOReservaComun> ();
        for (ReservaComun res : pp.darListaReservaComun ())
        {
        	voRes.add (res);
        }
        log.info ("Generando los VO ReservaComun: " + voRes.size() + " existentes");
        return voRes;
	}

	
	/* ****************************************************************
	 * 			Métodos para manejar Contrato
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una relacion Contrata
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public Contrato adicionarContrato (long idCliente, long idOferta, Date inicio, Date fin)
	{
        log.info ("Adicionando Contrato: " + idCliente + "," + idOferta);
        Contrato res = pp.adicionarContrato(idCliente, idOferta, inicio, fin);	
        log.info ("Adicionando Contrato: " + res);
        return res;
	}
	

	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarContrato (long idCliente, long idOferta)
	{
		log.info ("Eliminando Contrato: " + idCliente +", " + idOferta);
        long resp = pp.eliminarReservaComun (idCliente, idOferta);		
        log.info ("Eliminando Contrato: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<Contrato> darListaContrato ()
	{
		log.info ("Consultando Contrato");
        List<Contrato> res = pp.darListaContrato();	
        log.info ("Consultando Contratos: " + res.size() + " existentes");
        return res;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOContrato> darVOContrato()
	{
		log.info ("Generando los VO Contrato");        
        List<VOContrato> voRes = new LinkedList<VOContrato> ();
        for (Contrato res : pp.darListaContrato ())
        {
        	voRes.add (res);
        }
        log.info ("Generando los VO Contrato: " + voRes.size() + " existentes");
        return voRes;
	}
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAlohAndes ()
	{
        log.info ("Limpiando la BD de AlohAndes");
        long [] borrrados = pp.limpiarAlohAndes();	
        log.info ("Limpiando la BD de AlohAndes: Listo!");
        return borrrados;
	}
}
