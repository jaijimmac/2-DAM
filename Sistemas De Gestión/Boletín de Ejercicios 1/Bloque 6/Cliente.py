class Cliente:

    def __init__(self, nombre, email, telefono):
        self.nombre = nombre
        self.email = email
        self.telefono = telefono

    def buscarCliente(self,nombre):
        if self.nombre.lower() == nombre.lower():
            return True
        else:
            return False


    def mostrarDatos(self):
        print('_______________________________________')
        print(f'Nombre: {self.nombre}, Correo Electrónico: {self.email}, Nº Teléfono: {self.telefono}')
        print('_______________________________________')

