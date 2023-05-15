SELECT emp.idOperador AS idOp, emp.nombre AS nombreOperador
FROM EMPRESA emp
INNER JOIN (Select oc.idOfertaComun AS idOfe, COUNT(*) AS numReservas
FROM OfertaComun oc
INNER JOIN ReservaComun rc ON oc.idOfertaComun = rc.idOfertaComun
GROUP BY oc.idOfertaComun
ORDER BY numReservas DESC) AUX ON emp.idOperador = AUX.idOfe
UNION
SELECT vec.idOperador, vec.nombre
FROM VECINO vec
INNER JOIN AUX ON vec.idOperador = AUX.idOfe;