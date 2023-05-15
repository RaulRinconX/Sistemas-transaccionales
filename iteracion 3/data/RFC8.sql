WITH subconsulta AS (
  SELECT r.doc_cliente as cliente, 
         COUNT(*) AS numero_de_reservas,
         TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches
  FROM RESERVAS r
  GROUP BY r.doc_cliente, TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio)
)
SELECT s.cliente, s.numero_de_reservas, s.numero_noches
FROM subconsulta s
WHERE s.numero_noches >= 15 AND s.numero_de_reservas >= 2;

