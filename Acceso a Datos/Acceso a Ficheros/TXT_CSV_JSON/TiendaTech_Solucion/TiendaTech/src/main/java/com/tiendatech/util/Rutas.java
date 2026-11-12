package com.tiendatech.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/** Todas las rutas del proyecto en un único sitio. Son relativas a la raíz del proyecto. */
public final class Rutas {

    private Rutas() {}

    public static final Path RESOURCES   = Paths.get("resources");
    public static final Path PRODUCTOS   = RESOURCES.resolve("productos.csv");
    public static final Path PRODUCTOS_V2 = RESOURCES.resolve("productos_v2.csv");
    public static final Path CLIENTES    = RESOURCES.resolve("clientes.json");
    public static final Path PEDIDOS     = RESOURCES.resolve("pedidos.txt");
    public static final Path LOG         = RESOURCES.resolve("tienda.log");
    public static final Path CONFIG      = RESOURCES.resolve("config.properties");
    public static final Path FACTURAS    = RESOURCES.resolve("facturas");
    public static final Path BACKUP      = RESOURCES.resolve("backup");
    public static final Path ORIGINALES  = RESOURCES.resolve("originales");
}
