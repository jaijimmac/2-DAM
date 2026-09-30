# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2036.
# Descripción: Ejercicio 21.

cliente = {
    "nombre": "Ana López",
    "email": "ana@email.com",
    "telefono": "600123456",
    "activo": True
}

for clave, valor in cliente.items():
    print(f'{clave} -> {valor}')