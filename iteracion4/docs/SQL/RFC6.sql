SELECT RESERVACOMUN.*, OFERTACOMUN.*
FROM RESERVACOMUN, OFERTACOMUN
WHERE reservacomun.idofertacomun = ofertacomun.idofertacomun;

SELECT CONTRATO.*, OFERTACOMUN.*
FROM CONTRATO, OFERTACOMUN
WHERE contrato.idofertacomun = ofertacomun.idofertacomun;
