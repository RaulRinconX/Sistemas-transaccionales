SELECT adicionales.nombre as SERVICIO, r.num_reserva, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches, adicionales.id_oferta, ofertas.precio
FROM reservas r
INNER JOIN ofertas ON r.id_oferta = ofertas.id_oferta
INNER JOIN clientes u ON r.doc_cliente = u.numero_documento
INNER JOIN adicionales ON ofertas.id_oferta = adicionales.id_oferta
WHERE u.numero_documento = '1025520319';

SELECT * FROM CLIENTES;


----------------------------------------

SELECT adicionales.nombre as SERVICIO, r.num_reserva, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches, adicionales.id_oferta, ofertas.precio
FROM reservas r
INNER JOIN ofertas ON r.id_oferta = ofertas.id_oferta
INNER JOIN clientes u ON r.doc_cliente = u.numero_documento
INNER JOIN adicionales ON ofertas.id_oferta = adicionales.id_oferta
WHERE u.numero_documento = ?;