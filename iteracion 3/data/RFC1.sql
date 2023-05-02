SELECT ofertas.id_oferta, ofertas.precio
FROM ofertas
INNER JOIN reservas
ON ofertas.id_oferta = reservas.id_oferta;

