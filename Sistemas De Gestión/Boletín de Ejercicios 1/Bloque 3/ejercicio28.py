# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Ejercicio 28.

stock = input('Ingresa el stock del producto: ')
cantidad = input('Ingresa la cantidad del producto: ') 

if float(cantidad) <= float(stock):
    print('Venta posible')
else:
    print('Stock insuficiente')