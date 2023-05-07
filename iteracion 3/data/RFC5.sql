SELECT id_usuario, tipo_usuario, nombre,
CASE 
  WHEN EXISTS (SELECT 1 FROM clientes c WHERE c.numero_documento = u.id_usuario) THEN 'Cliente'
  WHEN EXISTS (SELECT 1 FROM proveedores p WHERE p.id_proveedor = u.id_usuario) THEN 'Proveedor'
  ELSE 'No especificado'
END AS uso_usuario
FROM usuarios u;

SELECT * FROM USUARIOS;