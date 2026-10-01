# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.
# Descripción: Ejercicio 16.

productos = ["Teclado", "Ratón", "Monitor", "Webcam"]

productos.append("Webcam")
productos.append("Altavoces")
productos.remove("Ratón")

for i, p in enumerate(productos):
    print(f'{i+1}. {p}')
    
