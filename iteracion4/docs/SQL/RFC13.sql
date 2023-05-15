SELECT cli.idCliente, COUNT(*) AS numRes
FROM CLIENTE cli
INNER JOIN RESERVACOMUN rc ON cli.idCliente = rc.idCliente
WHERE numRes/12 == 1 OR rc.preciototal >= 150
GROUP BY cli.idCliente;