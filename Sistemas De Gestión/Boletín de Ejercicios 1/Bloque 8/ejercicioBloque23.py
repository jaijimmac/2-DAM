# Autor: Jaime Jiménez Machuca.
# Fecha: 30/09/2026.
# Descripción: Bloque 23.

from Producto import Producto


def buscar(productos, nombre):
    for p in productos:
        if p.buscarProducto(nombre):
            return p
    return None


productos = []
opcion = -1
contador = 1

while opcion != 7:
    print('1. Añadir producto\n2. Mostrar productos\n3. Buscar producto\n4. Vender producto\n5. Reponer producto\n6. Mostrar valor total del inventario\n7. Salir')
    try:
        opcion = int(input('¿Qué quieres hacer?\n'))
    except ValueError:
        print('Introduce un número del 1 al 7.')
        continue

    match opcion:

        case 1:
            crearCodigo = f'N{contador:03d}'

            nombreProducto = input('Ingresa el nombre del producto: \n')
            precio = float(input('Ingresa el precio del producto: \n'))
            stock = int(input('Ingresa el stock del producto: \n'))

            productos.append(Producto(crearCodigo, nombreProducto, precio, stock))
            contador += 1

        case 2:
            for p in productos:
                print('')
                p.mostrarDatos()
                print('')

        case 3:
            nombre = input('Introduce el nombre de un producto: ')
            p = buscar(productos, nombre)
            if p is None:
                print('Producto no encontrado.')
            else:
                p.mostrarDatos()

        case 4:
            nombre = input('Introduce el nombre de un producto: ')
            p = buscar(productos, nombre)
            if p is None:
                print('Producto no encontrado.')
            else:
                cantidad = int(input('Producto encontrado. ¿Qué cantidad quiere comprar?: '))
                if p.venderProducto(cantidad):
                    p.mostrarDatos()

        case 5:
            nombre = input('Introduce el nombre de un producto: ')
            p = buscar(productos, nombre)
            if p is None:
                print('Producto no encontrado.')
            else:
                cantidad = int(input('Introduce la cantidad de producto a reponer: '))
                p.reponerProducto(cantidad)

        case 6:
            total = sum(p.valorStock() for p in productos)
            print("_______________________________")
            print(f'Valor total del inventario: {total}')

        case 7:
            print('Saliendo...')

        case _:
            print('Opción no válida.')