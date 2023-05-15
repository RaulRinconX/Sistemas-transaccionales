SELECT rc.idOfertaComun, rc.fechaInicio, COUNT(*) AS num
FROM RESERVACOMUN rc
INNER JOIN HOSPEDAJE ho ON rc.idOfertaComun = ho.id
GROUP BY rc.idOfertaComun, rc.fechaInicio
ORDER BY num DESC