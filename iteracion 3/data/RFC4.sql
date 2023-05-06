SELECT * FROM OFERTAS;
SELECT * FROM RESERVAS;
SELECT * FROM ADICIONALES;

SELECT ofertas.id_oferta, ofertas.tipo_oferta
FROM ofertas 
JOIN ADICIONALES ON ofertas.id_oferta = ADICIONALES.id_oferta
WHERE ADICIONALES.NOMBRE IN ('GIMNASIO', 'RESTAURANTE', 'SALA DE ESTUDIO', 'TEATRO', 'MONITORIAS', 'JUEGOS')
----- DEBE SER TODOS O NADA SUPONGO
AND NOT EXISTS (
    SELECT *
    FROM RESERVAS r
    WHERE r.id_oferta = ofertas.id_oferta
    AND r.fecha_inicio <= '01/01/20'
    AND r.fecha_fin >= '01/01/24'
)
GROUP BY ofertas.id_oferta, ofertas.tipo_oferta;

