# Autor: Jaime Jiménez Machuca.
# Fecha: 08/10/2026.
# Descripción: Ejercicio 52


class Persona:

    def __init__(self, nombre, email):
        self.nombre = nombre
        self.email = email

class Cliente(Persona):

    def __init__(self, nombre, email, numeoCliente):
        super().__init__(nombre, email)
        self.numeroCliente = numeoCliente


    def mostrarDatos(self):
        print(f'Número cliente: {self.numeroCliente}\nNombre: {self.nombre}\nCorreo: {self.email}')



c1 = Cliente('Jaime', 'jaime@gmail.com', 'N001')

c1.mostrarDatos();