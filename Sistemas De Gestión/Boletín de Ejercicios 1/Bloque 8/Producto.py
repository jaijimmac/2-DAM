class Producto:

    def __init__(self, cod, nombre, precio, stock):
        self.cod = cod
        self.nombre = nombre
        self.precio = precio
        self.stock = stock

    def buscarProducto(self,nombre):
        enconcontrado = True

        if self.nombre.lower() == nombre.lower() :
            enconcontrado = True
        else:
            enconcontrado = False

        return enconcontrado

    def reponerProducto(sefl, cantidad):
        cantidadA = sefl.stock
        sefl.stock + cantidad
        print(_______________________________)
        print(f'Cantidad de stock: {cantidadA}\nCantidad de repuesto: {cantidad}\nCantidad total: {sefl.stock}')


    def mostrarDatos(self):
        print(f'Código del producto: {self.cod}\nNombre del producto: {self.nombre}\nPrecio del producto: {self.precio}\nStock del producto: {self.stock}')