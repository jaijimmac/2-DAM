# Autor: Jaime Jiménez Machuca.
# Fecha: 07/10/2026.
# Descripción: Ejercicio 51

class Producto:

    def __init__(self, codigo, nombre, precio, stock):

        self.codigo = codigo
        self.nombre = nombre
        self.precio = precio
        self.stock = stock

    def valorStock(self):
            return self.stock * self.precio

    def mostrarInfo(self):
        print(f'{self.codigo} - {self.nombre} - {self.precio}€ - Stock: {self.stock}')


p1 = Producto('P001', 'Monitor', 199.99, 8)

print('El valor del stock es: ', p1.valorStock())

