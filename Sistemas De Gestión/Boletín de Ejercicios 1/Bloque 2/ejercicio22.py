# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Ejercicio 22.

clientes = [
    {
        "nombre": "Ana",
        "email": "ana@email.com",
        "ciudad": "Sevilla"
    },
    {
        "nombre": "Luis",
        "email": "luis@email.com",
        "ciudad": "Córdoba"
    },
    {
        "nombre": "Jaime",
        "email": "jaime@email.com",
        "ciudad": "Sevilla"
    }
]

for cliente in clientes:
    
    for clave,valor in cliente.items():
        print(f'{clave} {valor}')

    print("")
    print("")
        