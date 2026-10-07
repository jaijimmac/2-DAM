# Autor: Jaime Jiménez Machuca.
# Fecha: 06/10/2026.
# Descripción: Ejercicio 43.


def diccionario(productos):
    precio = 0
    obj= {}
    for pro in productos:
        if pro["precio"] > precio:
            precio = pro["precio"]
            obj = pro

    return obj

productos = [
    {"nombre": "Teclado", "precio": 25},
    {"nombre": "Monitor", "precio": 180},
    {"nombre": "Ratón", "precio": 15}
]

print(diccionario(productos))

