# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Ejercicio 29.

importeCompra = input('Ingresa el importe de la compra: ')
vip = input('¿El cliente es vip? (s/n): ').strip().lower()

verdadero = vip == 's' or vip == 'sí'
descuentoVip = float(importeCompra) * 0.10
descuento5 =  float(importeCompra) * 0.05
precioFinal = 0

if verdadero:

    precioFinal = float(importeCompra) - descuentoVip
elif float(importeCompra) > 100: 

    precioFinal = float(importeCompra) - descuento5

else :
    precioFinal = float(importeCompra)

print('Precio final: ', precioFinal )
