package uniandes.isis2304.parranderos.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.GananciaProveedor;

public class SQLProveedores {

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

    public SQLProveedores(PersistenciaAlohAndes pp)
    {
        this.pp = pp;
    }


	public List<GananciaProveedor> gananciaProveedores (PersistenceManager pm)
	{

		Query q = pm.newQuery(SQL, "SELECT proveedores.id_proveedor, SUM(CASE WHEN EXTRACT(YEAR FROM reservas.fecha_inicio) = EXTRACT(YEAR FROM SYSDATE) THEN ofertas.precio ELSE 0 END) AS dinero_recibido_anio_actual, SUM(ofertas.precio) AS dinero_recibido_anio_corrido" +
					 			   " FROM proveedores" +
 								   " INNER JOIN ofertas " + 
								   " ON proveedores.id_proveedor = ofertas.id_operador" +
 								   " INNER JOIN reservas" + 
								   " ON ofertas.id_oferta = reservas.id_oferta" +
 								   " WHERE EXTRACT(YEAR FROM reservas.fecha_inicio) >= EXTRACT(YEAR FROM SYSDATE)-1" +
 								   " GROUP BY proveedores.id_proveedor");
		
		List<Object[]> aux = (List<Object[]>) q.executeList();
//		System.out.println(aux.size());
		List<GananciaProveedor> lista =  new ArrayList<>();
		for (Object[] datos : aux)
		{
				String id_proveedor = datos[0].toString();
				long dinero_recibido_anio_actual = ((BigDecimal)datos[1]).longValue();
				long dinero_recibido_anio_corrido = ((BigDecimal)datos[2]).longValue();
				lista.add( new GananciaProveedor(id_proveedor, dinero_recibido_anio_actual, dinero_recibido_anio_corrido));
		}
		return lista;
	}

}
