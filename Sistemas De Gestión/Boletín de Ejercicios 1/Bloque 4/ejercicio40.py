# Autor: Jaime Jiménez Machuca.
# Fecha: 06/10/2026.
# Descripción: Ejercicio 40.

def aplicar_descuento(precio, descuento=0):
    precioConDescuento = precio * (descuento/100)
    precioFinal = precio - precioConDescuento
    return precioFinal


print(aplicar_descuento(100))
print(aplicar_descuento(100, 10))
print(aplicar_descuento(250, 20))