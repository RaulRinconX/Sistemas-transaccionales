package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.UsoUsuario;

public class SQLReserva {

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
	public SQLReserva (PersistenciaAlohAndes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarReserva (PersistenceManager pm, Long numeroReserva, String fechaInicio, String fechaFin, Long idOferta, 
			Long docCliente, String tipoDoc, String fechaCancelacion) 
	{
       System.out.println(docCliente);
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservas() 
       + "(num_reserva, fecha_inicio, fecha_fin, id_oferta, doc_cliente, tipo_doc_cliente, fecha_cancelacion) values (?, ?, ?, ?, ?, ?, ?)");
       q.setParameters(numeroReserva, fechaInicio, fechaFin, idOferta, docCliente, tipoDoc,fechaCancelacion);
       return (long) q.executeUnique();
	}
	
	public long eliminarReserva (PersistenceManager pm, Long idReserva, Long idOferta)
	{
        System.out.println(idOferta);
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservas() + " WHERE num_reserva = ? AND id_oferta = ? ");
        q.setParameters(idReserva, idOferta);
        
        return (long) q.executeUnique();
	}
	
	public long eliminarReservaNumeroReserva (PersistenceManager pm, Long idReserva)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservas() + " WHERE num_reserva = ? ");
        q.setParameters(idReserva);
        
        return (long) q.executeUnique();
	}
	
	public List<Reserva> darReservas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservas());
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Reserva>) q.executeResultList(Reserva.class);
	}
	
	public Reserva darUltimaReserva(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL,"SELECT * FROM "+ " (SELECT * FROM " + pp.darTablaReservas() +" ORDER BY num_reserva DESC) "+ " WHERE ROWNUM = 1 "  );
		q.setResultClass(Reserva.class);
		//System.out.println(q.executeList().size());
		return (Reserva) q.executeUnique();
	}
	
	public List<Reserva> darReservasPornumero(PersistenceManager pm, Long idReserva)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservas() + " WHERE num_reserva = ? " );
		q.setParameters(idReserva);
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Reserva>) q.executeResultList(Reserva.class);
	}
	
	public List<Reserva> darReservasOferta(PersistenceManager pm, Long idOferta)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservas() + " WHERE id_oferta = ? " + "ORDER BY fecha_inicio ASC");
		q.setParameters(idOferta);
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Reserva>) q.executeResultList(Reserva.class);
	}
	
	public List<Reserva> darReservasOfertaEnFecha(PersistenceManager pm, Long idReserva, String fechaI, String fechaF)
	{
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservas() + " WHERE id_oferta = ? AND fecha_inicio <= ? AND fecha_fin >= ?" );
		q.setParameters(idReserva,fechaI,fechaF);
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Reserva>) q.executeResultList(Reserva.class);
	}

	public List<UsoUsuario> darUsoUsuario(PersistenceManager pm, String id_usuario)
	{
		Query q = pm.newQuery(SQL, "SELECT adicionales.nombre as SERVICIO, r.num_reserva, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches, adicionales.id_oferta, ofertas.precio"+
								   " FROM reservas r"+
								   " INNER JOIN ofertas ON r.id_oferta = ofertas.id_oferta"+
								   " INNER JOIN clientes u ON r.doc_cliente = u.numero_documento"+
								   " INNER JOIN adicionales ON ofertas.id_oferta = adicionales.id_oferta"+
								   " WHERE u.numero_documento = "+ id_usuario );
		return (List<UsoUsuario>) q.executeResultList(UsoUsuario.class);
	}


}
