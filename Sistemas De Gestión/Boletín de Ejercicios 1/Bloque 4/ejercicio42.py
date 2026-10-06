# Autor: Jaime Jiménez Machuca.
# Fecha: 06/10/2026.
# Descripción: Ejercicio 42.

def calcular_total(ventas):
    suma = 0
    for v in ventas:
        suma = suma + v

    return suma


ventas = [10, 10, 10]

print(calcular_total(ventas))