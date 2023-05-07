SELECT * FROM RESERVAS;
SELECT * FROM OFERTAS;


SELECT TRUNC(r.fecha_fin) - TRUNC(r.fecha_inicio) AS numero_noches
FROM RESERVAS r
INNER JOIN ofertas of ON(r.id_oferta = of.id_oferta)
WHERE tiempo >= r.fecha_inicio AND tiempo <= r.fecha_fin AND tipo = of.tipo_oferta;