package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.UsoAlohandes;
import uniandes.isis2304.parranderos.negocio.Usuarios;

public class SQLUsuarios {

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

    public SQLUsuarios(PersistenciaAlohAndes pp)
    {
        this.pp = pp;
    }

	public List<Usuarios> darUsuarios(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM USUARIOS ");
		//q.setResultClass(Oferta.class);
		//System.out.println(q.executeList().size());
		return (List<Usuarios>) q.executeResultList(Usuarios.class);
	}

	public List<UsoAlohandes> darUso(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT id_usuario, tipo_usuario, nombre,"+
								   " CASE"+ 
		 						   " WHEN EXISTS (SELECT 1 FROM clientes c WHERE c.numero_documento = u.id_usuario) THEN 'Cliente'"+
		 						   " WHEN EXISTS (SELECT 1 FROM proveedores p WHERE p.id_proveedor = u.id_usuario) THEN 'Proveedor'"+
		  						   " ELSE 'No especificado'"+
								   " END AS uso_usuario"+
								   " FROM usuarios u");
		return (List<UsoAlohandes>) q.executeResultList(UsoAlohandes.class);
	}
    
}
