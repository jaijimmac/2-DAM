# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.
# Descripción: Ejercicio 13.

productos = ["Teclado", "Ratón", "Monitor"]

productos.append("Webcam")
productos.append("Altavoces")
productos.remove("Ratón")

for i, p in enumerate(productos):
    if p == "Monitor": 
        productos[i] = "Monitor 27 pulgadas "
    
print(productos)