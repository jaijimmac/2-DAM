# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2036.
# Descripción: Ejercicio 24.


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


ciudadIngresada = input('Ingresa el nombre de la ciudad: ')
contador = 0

for cliente in clientes:
    if cliente["ciudad"] == ciudadIngresada:
        contador += 1


print(f'Ciudad:  {ciudadIngresada}')

print(f'Número de clientes de {ciudadIngresada}: {contador}')