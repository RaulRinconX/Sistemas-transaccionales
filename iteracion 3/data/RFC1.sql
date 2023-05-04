SELECT proveedores.id_proveedor, 
       SUM(CASE WHEN EXTRACT(YEAR FROM reservas.fecha_inicio) = EXTRACT(YEAR FROM SYSDATE) THEN ofertas.precio ELSE 0 END) AS dinero_recibido_anio_actual,
       SUM(ofertas.precio) AS dinero_recibido_anio_corrido
FROM proveedores
INNER JOIN ofertas ON proveedores.id_proveedor = ofertas.id_operador
INNER JOIN reservas ON ofertas.id_oferta = reservas.id_oferta
WHERE EXTRACT(YEAR FROM reservas.fecha_inicio) >= EXTRACT(YEAR FROM SYSDATE)-1
GROUP BY proveedores.id_proveedor;

