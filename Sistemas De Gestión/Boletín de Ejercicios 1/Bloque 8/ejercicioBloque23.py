# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Bloque 23.

from Producto import Producto

productos = []
opcion = -1
contador = 1

while opcion != 7:
    print('1. Añadir producto\n2. Mostrar productos\n3. Buscar producto\n4. Vender producto\n5. Reponer producto\n6. Mostrar valor total del inventario\n7. Salir')
    opcion = input('¿Qué quieres hacer?\n')
    match int(opcion):

        case 1:
            crearCodigo = f'N{contador:03d}'

            nombreProducto = input('Ingresa el nombre del producto: \n')
            precio = float(input('Ingresa el precio del producto: \n'))
            stock = int(input('Ingresa el stock del producto: \n'))

            p = Producto(crearCodigo, nombreProducto, precio, stock)
            contador += 1
            productos.append(p)

        case 2:
            for p in productos:
                print("")
                p.mostrarDatos();
                print("")

        case 3:
            print('')
            nombre = input('Introduce el nombre de un producto: ')
            print('')
            for i, p in enumerate(productos):
                if p.buscarProducto(nombre):
                    p.mostrarDatos()
                    break
                elif i == len(productos) - 1:
                    print('Producto no encontrado')

        case 4:
            print('')
            nombre = input('Introduce el nombre de un producto: ')
            p = p.venderProducto(nombre)
            if p != None :
                p.mostrarDatos()

        case 5:
            nombre = input('Introduce el nombre de un producto: ')
            p = p.reponerProducto(nombre)
            if p != None :
                p.mostrarDatos()


                    
        case default: 
            opcion = input('¿Qué quieres hacer?\n')