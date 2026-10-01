# Autor: Jaime Jiménez Machuca.
# Fecha: 01/10/2026.
# Descripción: Ejercicio 35.

totalVentas = 0
totalVendido = 0

importeVentas = input('Introduzca el importe de ventas: ')

while float(importeVentas) !=0:
    
    totalVentas = totalVentas + float(importeVentas)
    totalVendido += 1
    importeVentas = input('Introduzca el importe de ventas: ')


print('Nº Ventas: ', totalVentas)
print('Vendido: ', totalVendido)