SELECT cli.idCliente, res.idOferta AS ofe
FROM CLIENTE cli
LEFT JOIN RESERVACOMUN res On cli.idCliente = res.idCliente
WHERE fechaInicio BETWEEN '' AND '' AND ofe IS NULL;