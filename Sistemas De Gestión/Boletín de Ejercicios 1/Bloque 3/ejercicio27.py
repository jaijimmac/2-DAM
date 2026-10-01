# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Ejercicio 27.

precioProducto = input('Ingresa el precio del producto: ')

if float(precioProducto)<20: 
    print('-> Producto económico')
elif float(precioProducto)>=20 and float(precioProducto) <=100 : 
    print('-> Precio medio')
elif float(precioProducto) > 100:
    print('-> Producto de precio elevado')