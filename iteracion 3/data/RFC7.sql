SELECT ofertas.id_oferta, ofertas.tipo_oferta, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS dias_activa, ofertas.precio, r.fecha_inicio AS FECHA_MAYOR
FROM ofertas, reservas r;

SELECT reservas.fecha_inicio, COUNT(*) AS reservas_hechas FROM reservas INNER JOIN ofertas ON(reservas.id_oferta = ofertas.id_oferta);

SELECT COUNT(*) as RESERVAS_REALIZADAS, reservas.fecha_inicio FROM RESERVAS GROUP BY FECHA_INICIO;

SELECT reservas.fecha_inicio as FECHAS_RESERVADAS FROM RESERVAS WHERE (TO_CHAR(FECHA_INICIO, 'MM') = '07');

SELECT * FROM RESERVAS;


WITH subconsulta AS (SELECT r.doc_cliente as cliente, COUNT(*) AS numero_de_reservas, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches FROM RESERVAS r GROUP BY r.doc_cliente, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio)) SELECT s.cliente, s.numero_de_reservas, s.numero_noches FROM subconsulta s WHERE s.numero_noches >= 15 AND s.numero_de_reservas >= 3;