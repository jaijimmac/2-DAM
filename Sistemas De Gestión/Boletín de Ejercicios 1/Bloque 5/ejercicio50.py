# Autor: Jaime Jiménez Machuca.
# Fecha: 07/10/2026.
# Descripción: Ejercicio 50

class Producto:

    def __init__(self, codigo, nombre, precio, stock):

        self.codigo = codigo
        self.nombre = nombre
        self.precio = precio
        self.stock = stock

    def vender(self, cantidad):
        if cantidad > self.stock:
            print('No hay stock suficiente')
            return
        else :
            cantidadNueva = self.stock - cantidad
            self.stock = cantidadNueva

    def mostrarInfo(self):
        print(f'{self.codigo} - {self.nombre} - {self.precio}€ - Stock: {self.stock}')


p1 = Producto('P001', 'Monitor', 199.99, 8)

p1.mostrarInfo();
p1.vender(10);
p1.vender(2);
p1.mostrarInfo();

