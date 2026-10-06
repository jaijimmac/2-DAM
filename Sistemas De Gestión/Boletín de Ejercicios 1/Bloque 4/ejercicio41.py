# Autor: Jaime Jiménez Machuca.
# Fecha: 06/10/2026.
# Descripción: Ejercicio 41.

def buscar_producto(productos, nombre):
        if nombre in productos:
            return True
        else: 
            return False

productos = ["p1","p2"]

print(buscar_producto(productos, "p1"))
print(buscar_producto(productos, "p2"))
print(buscar_producto(productos, "p3"))