
(SELECT idOfertaComun AS id, COUNT(*) AS numVeces
FROM OFERTACOMUN oc
INNER JOIN RESERVACOMUN rc ON oc.idOfertaComun = rc.idOfertaComun
Group BY idOfertaComun
UNION
SELECT idOfertaComun , COUNT(*)
FROM OFERTACOMUN oc
INNER JOIN RESERVACOMUN rc ON oc.idOfertaComun = rc.idOfertaComun
Group BY idOfertaComun)
Order BY numVeces DESC 
WHERE ROWNUM <=20