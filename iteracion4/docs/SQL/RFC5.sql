SELECT vivienda.*
FROM VIVIENDA, VECINO
WHERE vivienda.idvecino = vecino.idoperador;

SELECT HOSPEDAJE.*
FROM HOSPEDAJE, EMPRESA
WHERE HOSPEDAJE.IDEMPRESA = empresa.idoperador;

SELECT OFERTACOMUN.*
FROM RESERVACOMUN, OFERTACOMUN, CLIENTE
WHERE reservacomun.idofertacomun = ofertacomun.idofertacomun
AND reservacomun.idcliente = cliente.idcliente;

SELECT OFERTACOMUN.*
FROM CONTRATO, CLIENTE, OFERTACOMUN
WHERE contrato.idcliente = cliente.idcliente
AND contrato.idofertacomun = ofertacomun.idofertacomun;