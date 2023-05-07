SELECT 
  ofertas.id_oferta, 
  ofertas.fecha_inicio as FECHA_INICIO_OFERTA
FROM ofertas 
LEFT JOIN (
  SELECT 
    r.id_oferta
  FROM RESERVAS r, ofertas
  WHERE r.fecha_inicio <= SYSDATE
  AND r.fecha_fin > ADD_MONTHS(ofertas.fecha_inicio, 1)
  GROUP BY r.id_oferta
  HAVING COUNT(*) = 0
) r ON ofertas.id_oferta = r.id_oferta
WHERE r.id_oferta IS NULL
AND SYSDATE > ADD_MONTHS(ofertas.fecha_inicio, 1);


