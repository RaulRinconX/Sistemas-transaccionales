SELECT ofertas.id_oferta, ofertas.tipo_oferta
FROM ofertas 
JOIN ADICIONALES ON ofertas.id_oferta = ADICIONALES.id_oferta
WHERE ADICIONALES.NOMBRE IN ('GIMNASIO', 'RESTAURANTE', 'SALA DE ESTUDIO', 'TEATRO', 'MONITORIAS', 'JUEGOS')
AND NOT EXISTS (
    SELECT *
    FROM RESERVAS r
    WHERE r.id_oferta = ofertas.id_oferta
    AND r.fecha_inicio <= '01/01/2020'
    AND r.fecha_fin >= '01/01/2024'
)
GROUP BY ofertas.id_oferta, ofertas.tipo_oferta;
----------------------------------------------------------------------

SELECT ofertas.id_oferta, ofertas.tipo_oferta
FROM ofertas 
JOIN ADICIONALES ON ofertas.id_oferta = ADICIONALES.id_oferta
WHERE ADICIONALES.NOMBRE IN ?
----- DEBE SER TODOS O NADA SUPONGO
AND NOT EXISTS (
    SELECT *
    FROM RESERVAS r
    WHERE r.id_oferta = ofertas.id_oferta
    AND r.fecha_inicio <= ?
    AND r.fecha_fin >= ?
)
GROUP BY ofertas.id_oferta, ofertas.tipo_oferta;