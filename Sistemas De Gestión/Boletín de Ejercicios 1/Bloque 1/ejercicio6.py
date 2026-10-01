# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.
# Descripción: Ejercicio 6.


precio = input('Precio del Producto:')
cantidad = input('Cantidad de producto:')

subtotal = float(precio) * float(cantidad)
ivaAplicado = subtotal * 0.21
total = float(subtotal) + float(ivaAplicado)


print("Subtotal: ",subtotal, "€")
print("IVA:",ivaAplicado, "€")
print("Total:",total, "€")