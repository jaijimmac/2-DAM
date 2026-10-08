# Autor: Jaime Jiménez Machuca.
# Fecha: 08/10/2026.
# Descripción: Ejercicio 53



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



class ClienteVIP(Cliente):

    def __init__(self, nombre, email, numeoCliente, descuento):
        super().__init__(nombre, email, numeoCliente)

        self.descuento = descuento


    def calcularDescuento(self, precio):
        descuento = precio * (self.descuento / 100)

        return precio - descuento


c1 = ClienteVIP('Jaime', 'jaime@gmail.com', 'N001', 10)

print(c1.calcularDescuento(100))

