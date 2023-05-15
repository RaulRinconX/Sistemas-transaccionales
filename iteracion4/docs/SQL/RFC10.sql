SELECT * 
FROM CLIENTE
INNER JOIN RESERVACOMUN res On cli.idCliente = res.idCliente
WHERE fechaInicio BETWEEN '  ' AND '   ';