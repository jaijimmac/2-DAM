# Autor: Jaime Jiménez Machuca.
# Fecha: 07/10/2026.
# Descripción: Bloque 22


class Cliente:

    def __init__(self, nombre, email, telefono):
        self.nombre = nombre
        self.email = email
        self.telefono = telefono

    def mostrarDatos(self):
        print(f'Nombre: {self.nombre}, Correo Electrónico: {self.email}, Nº Teléfono: {self.telefono}')



opcion = -1
clientes = []

while int(opcion) != 5:
    opcion = input('Elige una opción')

    print('1. Añadir Cliente')
    print('2. Mostrar Clientes')
    print('3. Buscar Cliente')
    print('4. Eliminar Cliente')
    print('5. Añadir Cliente')
    print('')
    print('')

    
    match int(opcion):
        case 1:
            nombre = input('Ingrese el nombre del cliente: ')
            correo = input('Ingrese el correo electrónico del cliente: ')
            telefono = input('Ingrese el telefono del cliente: ')

            c1 = Cliente(nombre, correo, telefono)
            clientes.__add__(c1)

    




