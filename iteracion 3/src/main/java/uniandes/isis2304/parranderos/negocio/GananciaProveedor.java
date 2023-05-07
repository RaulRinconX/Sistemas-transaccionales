package uniandes.isis2304.parranderos.negocio;
/**
 * Clase para modelar el concepto GananciaProveedores del negocio de ALOHANDES
 */
public class GananciaProveedor
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * El tipo de documento del proveedor del alojamiento
	 */
	private String id_proveedor;
	
	/**
	 * El número de documento del proveedor del alojamiento
	 */
	private long dinero_recibido_anio_actual;
	
	/**
	 * Ganancia del proveedor en durante el año actual
	 */
	private long dinero_recibido_anio_corrido;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public GananciaProveedor() 
    {
		this.id_proveedor = "";
		this.dinero_recibido_anio_actual = 0;
		this.dinero_recibido_anio_corrido = 0;
	}

	/**
	 * Constructor con valores
	 * @param proveedorTipoDoc - El tipo de documento del proveedor del alojamiento
	 * @param proveedorNumDoc - El número de documento del proveedor del alojamiento
	 * @param ganancia - Ganancia del proveedor en durante el año actual
	 */
	public GananciaProveedor(String id_proveedor, long dinero_recibido_anio_actual, long dinero_recibido_anio_corrido )
	{
		this.id_proveedor = id_proveedor;
		this.dinero_recibido_anio_actual = dinero_recibido_anio_actual;
		this.dinero_recibido_anio_corrido = dinero_recibido_anio_corrido;
	}

    


	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos
	 */
	public String toString() 
	{
		return "GananciaProveedores [id_proveedor=" + id_proveedor + ", dinero_recibido_anio_actual=" + dinero_recibido_anio_actual + ", dinero_recibido_anio_corrido=" + dinero_recibido_anio_corrido +"]";
	}

}