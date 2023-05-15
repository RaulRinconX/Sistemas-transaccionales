SELECT cli.idCliente, cli.nombre, COUNT(*) AS res
FROM CLIENTE cli
INNER JOIN RESERVACOMUN rc ON cli.idCliente = rc.idCliente
GROUP BY cli.idCliente
WHERE res >=3
