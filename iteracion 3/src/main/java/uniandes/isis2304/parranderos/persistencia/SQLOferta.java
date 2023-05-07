package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.AlojamientosPopulares;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.VOOferta;


public class SQLOferta {


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
	public SQLOferta (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarOferta (PersistenceManager pm, Long id, Long idOperador,  String tipoOferta,  Boolean disponible, Integer precio, String  fechaInicio) 
	{
       Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOfertas() + "(ID_OFERTA, ID_OPERADOR, TIPO_OFERTA, DISPONIBLE, PRECIO , FECHA_INICIO) values (?, ?, ?, ?, ?, ?)");
       q.setParameters(id, idOperador, tipoOferta, disponible, precio, fechaInicio);
       return (long) q.executeUnique();
	}
	
	public long eliminarOferta (PersistenceManager pm, Long idoferta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfertas() + " WHERE id_oferta = ?");
        q.setParameters(idoferta);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOferta - El identificador del tipo de bebida
	 * @return El objeto TIPOBEBIDA que tiene el identificador dado
	 */
	public Oferta darOfertaPorId (PersistenceManager pm, long idOferta) 
	{
		System.out.println(idOferta);
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfertas() + " WHERE id_oferta = ?");
		//q.setResultClass(Oferta.class);
		q.setParameters(idOferta);
		return (Oferta) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOS DE BEBIDA de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TIPOBEBIDA
	 */
	public List<Oferta> darOfertas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfertas() );
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Oferta>) q.executeResultList(Oferta.class);
	}
	
	public List<Oferta> darOfertasTipo (PersistenceManager pm, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOfertas() + " WHERE tipo_oferta = ?");
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		q.setParameters(tipo);
		return (List<Oferta>) q.executeResultList(Oferta.class);
	}
	
	public long cambiarDisponible (PersistenceManager pm,Long idOferta, Boolean disponible)
	{
		// si disponible es true entonces 1 sino 0
		Integer disp = 0;
		if(disponible)
			disp = 1;
		else
			disp = 0;
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOfertas() +" SET disponible = ? "+ " WHERE id_oferta = ?");
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		q.setParameters(disp, idOferta);
		return (long) q.executeUnique();
	}

	public List<AlojamientosPopulares> alojamientosPopulares (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT O.*" +
								   " FROM OFERTAS O, (SELECT * FROM(SELECT ID_OFERTA, COUNT(ID_OFERTA) AS GUSTADO" +
							       " FROM INTERESAN"+
							       " GROUP BY ID_OFERTA"+
							       " ORDER BY GUSTADO DESC)"+
								   " WHERE ROWNUM <=20)POPULARES WHERE O.id_oferta= POPULARES.ID_OFERTA");
		q.setResultClass(AlojamientosPopulares.class);
		return (List<AlojamientosPopulares>) q.executeList();
	}

	public List<VOOferta> RFC4 (PersistenceManager pm, String adicionales, String fecha_inicio, String fecha_fin)
	{		
		
		// quiero que me separes el string adicionales por comas
		String[] adicional = adicionales.split(",");
		String adicionalFinal = "";

		for(int i = 0; i < adicional.length; i++)
		{
			if (i == 0){
				adicional[i] = "('"+adicional[i]+ "'";
			}
			else if (i == adicional.length-1){
				adicional[i] = ",'"+ adicional[i] + "')";
			}
			else{
			adicional[i] = ",'" + adicional[i] + "'";
			}

			adicionalFinal += adicional[i];
		}

		// juntame lo que da el for en un solo string: GIMNASIO,RESTAURANTE,SALA DE ESTUDIO,TEATRO,MONITORIAS,JUEGOS
		Query q = pm.newQuery(SQL, "SELECT ofertas.id_oferta, ofertas.tipo_oferta" +
								   " FROM ofertas" +
								   " JOIN ADICIONALES"+ 
								   " ON ofertas.id_oferta = ADICIONALES.id_oferta" +
								   " WHERE ADICIONALES.NOMBRE IN "+adicionalFinal+
								   " AND NOT EXISTS" +
								   " (SELECT * FROM RESERVAS r WHERE r.id_oferta = ofertas.id_oferta AND" +
								   " r.fecha_inicio <= '"+ fecha_inicio + "' AND r.fecha_fin >= '"+fecha_fin+ "')"+
								   " GROUP BY ofertas.id_oferta, ofertas.tipo_oferta");
		
							
		q.setResultClass(Oferta.class);
		return (List<VOOferta>) q.executeList();
	}

}
