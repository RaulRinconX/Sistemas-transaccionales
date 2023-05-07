package uniandes.isis2304.parranderos.persistencia;




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


}
