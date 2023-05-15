SELECT Vecino.nombre, ReservaComun.precioTotal
FROM OfertaComun, ReservaComun, Vecino
WHERE CURRENT_DATE > ReservaComun.fechaInicio
  AND 01/01/2020 < ReservaComun.fechaInicio
  AND OfertaComun.idOfertaComun = Vecino.idOferta;
  
SELECT Empresa.nombre, ReservaComun.precioTotal
FROM OfertaComun, ReservaComun, Ofrece, Empresa
WHERE CURRENT_DATE >  ReservaComun.fechaInicio
  AND 01/01/2020 <  ReservaComun.fechaInicio
  AND OfertaComun.idOfertaComun = Ofrece.idOferta
  AND Ofrece.idOperador = Empresa.idOperador;

SELECT PropietarioMiembro.nombre, Contrato.precioTotal
FROM Contrato, OfertaExclusiva, PropietarioMiembro
WHERE CURRENT_DATE > Contrato.fechaInicio
  AND 01/01/2020 < Contrato.fechaInicio
  AND OfertaExclusiva.idOfertaExclusiva = PropietarioMiembro.idApartamento;
  
SELECT AdminVivienda.nombre, Contrato.precioTotal
FROM Contrato, OfertaExclusiva, Administra, AdminVivienda
WHERE CURRENT_DATE > Contrato.fechaInicio
  AND 01/01/2020 < Contrato.fechaInicio
  AND OfertaExclusiva.idOfertaExclusiva = Administra.idOferta
  AND Administra.idOperador = AdminVivienda.idOperador;