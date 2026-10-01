# Autor: Jaime Jiménez Machuca.
# Fecha: 01/10/2026.
# Descripción: Ejercicio 32.

precios = [10, 250, 30, 150, 80, 300]
cantidad = 0

for precio in precios:
    if precio > 100:
        cantidad += 1
        print(precio)

print(f'La cantidad de productos mayores son: {cantidad}')
