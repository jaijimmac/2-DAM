class Producto:

    def __init__(self, cod, nombre, precio, stock):
        self.cod = cod
        self.nombre = nombre
        self.precio = precio
        self.stock = stock

    def buscarProducto(self, nombre):
        return self.nombre.lower() == nombre.lower()

    def venderProducto(self, cantidad):
        if cantidad <= 0:
            print('La cantidad debe ser mayor que 0.')
            return False
        if cantidad > self.stock:
            print('La cantidad de stock es inferior a la introducida.')
            return False
        self.stock -= cantidad
        return True

    def reponerProducto(self, cantidad):
        if cantidad <= 0:
            print('La cantidad debe ser mayor que 0.')
            return
        cantidadA = self.stock
        self.stock += cantidad
        print("_______________________________")
        print(f'Cantidad de stock: {cantidadA}\nCantidad de repuesto: {cantidad}\nCantidad total: {self.stock}')

    def valorStock(self):
        return self.stock * self.precio

    def mostrarDatos(self):
        print(f'Código del producto: {self.cod}\nNombre del producto: {self.nombre}\nPrecio del producto: {self.precio}\nStock del producto: {self.stock}')