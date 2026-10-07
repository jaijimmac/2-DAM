# Autor: Jaime Jiménez Machuca.
# Fecha: 07/10/2026.
# Descripción: Ejercicio 47.

class Cliente:
    def __init__(self, nombre, email, telefono):
        self.nombre = nombre
        self.email = email
        self.telefono = telefono


    def mostrarDatos(self):
        print(f'Nombre: {self.nombre}, Email: {self.email}, Teléfono: {self.telefono}.')



c1 = Cliente('Jaime', 'prueba@gmail.com', '604254384')
c2 = Cliente('Paco', 'prueba@gmail.com', '604254384')

c1.mostrarDatos()
c2.mostrarDatos()

        