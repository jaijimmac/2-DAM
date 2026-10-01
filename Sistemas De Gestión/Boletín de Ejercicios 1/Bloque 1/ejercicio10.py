# Autor: Jaime Jiménez Machuca.
# Fecha: 29/09/2026.
# Descripción: Ejercicio 10.

nombre = input("Ingresa un nombre (Puede contener espaciados)")
sinEspacios = nombre.strip()
enMayus = sinEspacios.upper()
enMinis = sinEspacios.lower()
tamaño = len(sinEspacios)

print('Nombre:', nombre)
print('--------')
print('Sin Espaciado:', sinEspacios)
print('En Mayúsculas:', enMayus)
print('En Minúsculas:', enMinis)
print('Tamaño:', tamaño)

