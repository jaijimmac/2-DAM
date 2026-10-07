# Autor: Jaime Jiménez Machuca.
# Fecha: 07/10/2026.
# Descripción: Bloque 22


from Cliente import Cliente

opcion = -1
clientes = []

while int(opcion) != 5:
    print('')
    print('')
    print('1. Añadir Cliente')
    print('2. Mostrar Clientes')
    print('3. Buscar Cliente')
    print('4. Eliminar Cliente')
    print('5. Salir')
    print('')
    print('')

    print('')
    opcion = int(input('Elige una opción: '))
    print('')
    
    
    match int(opcion):
        case 1:
            print('')
            nombre = input('Ingrese el nombre del cliente: ')
            correo = input('Ingrese el correo electrónico del cliente: ')
            telefono = input('Ingrese el telefono del cliente: ')

            c1 = Cliente(nombre, correo, telefono)
            clientes.append(c1)

        case 2:
            for cliente  in clientes:
                cliente.mostrarDatos()

        case 3:
            print('')
            nombre = input('Introduce el nombre de un cliente: ')
            print('')
            for i, cliente in enumerate(clientes):
                if cliente.buscarCliente(nombre):
                    cliente.mostrarDatos()
                    break
                elif i == len(clientes) - 1:
                    print('Cliente no encontrado')


            
        case 4: 
            print('')
            nombre = input('Introduce el nombre a eliminar: ')
            print('')
            for i, cliente in enumerate(clientes):
                if cliente.buscarCliente(nombre):
                    clientes.remove(cliente)
                    print('Cliente eliminado')
                    break
                elif i == len(clientes) - 1:
                    print('Cliente no encontrado')


