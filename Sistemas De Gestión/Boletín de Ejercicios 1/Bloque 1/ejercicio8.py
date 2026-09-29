# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2036.
# Descripción: Ejercicio 8.

precio1 = input('Primer Precio:')
precio2 = input('Segundo Precio:')

if float(precio1) > float(precio2):
    print("El primer precio es mayor")
elif float(precio1) < float(precio2):
    print("El segundo precio es mayor")

elif float(precio1) == float(precio2):
    print("El ambos precios son iguales")
