# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2036.
# Descripción: Ejercicio 23.

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

emailSolicitado = input('Ingresa el email a buscar: ')

for cliente in clientes:
    if cliente["email"] == emailSolicitado:
        print('Cliente Encontrado: ')
        for clave, valor in cliente.items():
            print(f'{clave}: {valor}')
        break 
else:
    print('No existe ningún cliente con ese email.')