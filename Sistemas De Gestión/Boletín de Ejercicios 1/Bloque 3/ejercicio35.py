# Autor: Jaime Jiménez Machuca.
# Fecha: 01/10/2026.
# Descripción: Ejercicio 35.

importeVentas = input('Introduzca el importe de ventas: ')

totalVentas = 0
totalVendido = 0

while float(importeVentas) !=0:
    importeVentas = input('Introduzca el importe de ventas: ')
    totalVendido =+ 1
    totalVentas = totalVentas + float(importeVentas)


print('Nº Ventas: ', totalVentas)
print('Vendido: ', totalVendido)