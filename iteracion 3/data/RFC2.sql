SELECT *
FROM(SELECT ID_OFERTA, COUNT(ID_OFERTA) AS GUSTADO
                     FROM INTERESAN
                     GROUP BY ID_OFERTA
                     ORDER BY GUSTADO DESC)
WHERE ROWNUM <=20;