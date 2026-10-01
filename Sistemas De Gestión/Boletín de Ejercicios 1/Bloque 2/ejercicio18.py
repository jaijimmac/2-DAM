# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.
# Descripción: Ejercicio 18.

producto = ("P001", "Monitor", 199.99)

productoObj = {
    "codigo": producto[0],
    "nombre":  producto[1],
    "precio": producto[2]
}

print(f'Producto {productoObj["codigo"]}: {productoObj["nombre"]} - {productoObj["precio"]}')