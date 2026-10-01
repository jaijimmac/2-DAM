# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.



# Descripción: Ejercicio 7.

precioOriginal = input('Precio del Producto:')
descuento= input('Porcentaje de descuento:')
importeDescontado = float(precioOriginal) * (float(descuento) / 100)
precioFinal = float(precioOriginal) - float(importeDescontado)

print("Precio Original:", precioOriginal)
print("Descuento:", descuento, "%")
print("")
print("")
print("Importe Descontado:", importeDescontado)
print("Precio final:", precioFinal)