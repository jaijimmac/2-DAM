# Autor: Jaime Jiménez Machuca.
# Fecha: 01/10/2026.
# Descripción: Ejercicio 37.

ventas = [100, -25, 50, -10, 200]

totalVentas = 0;

for venta in ventas:
    if venta >= 0:

        totalVentas = totalVentas + venta;
        continue;


print(totalVentas)