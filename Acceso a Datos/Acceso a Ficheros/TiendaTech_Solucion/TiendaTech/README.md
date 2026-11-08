# TiendaTech: solución del Ejercicio 3

## Cómo ejecutarlo

1. Importa la carpeta en Eclipse/IntelliJ como **proyecto Maven** (`pom.xml`). Maven descarga solo Gson, SLF4J y Logback.
   - Sin Maven: añade al Build Path `gson`, `slf4j-api`, `logback-classic` y `logback-core`, y marca `src/main/java` y `src/main/resources` como carpetas de fuentes.
2. Ejecuta `com.tiendatech.controller.TiendaController` (tiene el `main`).
3. El directorio de trabajo debe ser la **raíz del proyecto**, porque las rutas son relativas (`resources/...`). En Eclipse ya viene así por defecto.

Cada ejecución empieza llamando a `resetDatos()`, que copia `resources/originales/*` sobre `resources/` y borra las facturas y los backups. El log **no** se borra: se acumula entre ejecuciones y cada una empieza con una línea `INICIO DE EJECUCIÓN`. Así la demo siempre da el mismo resultado. Si quieres ver cómo se acumulan los cambios entre ejecuciones, comenta esa línea en el `main`.

En `salida_consola_ejemplo.txt` tienes la salida completa de una ejecución para compararla con la tuya.

## Estructura

```
controller/  TiendaController     -> prueba todo y captura las excepciones
service/     ProductoService, ClienteService, PedidoService, InformeService
repository/  ProductoRepository (CSV), ClienteRepository (JSON), PedidoRepository (TXT)
model/       Producto, Proveedor, Cliente, Direccion, Pedido, LineaPedido, EstadoPedido
exception/   TiendaException (base) + DatosInvalidos, EntidadNoEncontrada,
             StockInsuficiente, OperacionNoPermitida
util/        Rutas, FechaUtil, Validador, BackupUtil, Config, JsonUtil
src/main/resources/logback.xml      -> configuración del logger
```

## Dónde está cada apartado del enunciado

| Apartado | Dónde |
|---|---|
| 2. Log | `logger.error/warn/info` (SLF4J) en cada clase + `logback.xml` (append a `resources/tienda.log`) |
| 3.1 Carga CSV | `ProductoRepository.cargar()` / `parsear()` |
| 3.2 Carga JSON | `JsonUtil.extraerObjetos()` + `ClienteRepository.cargar()` / `leerDireccion()` / `leerTelefonos()` / `leerVip()` |
| 3.3 Carga TXT | `PedidoRepository.cargar()` / `parsear()` / `parsearLineas()` |
| 4. Productos | `ProductoService` |
| 5. Clientes | `ClienteService` |
| 6.1 Crear pedido | `PedidoService.crearPedido()` (dos pasadas: primero comprueba, luego descuenta) |
| 6.2 Estados | `EstadoPedido.puedePasarA()` + `PedidoService.cambiarEstado()` |
| 6.3 Factura | `InformeService.generarFactura()` |
| 7. Informes | `InformeService` |
| 8. Backups | `BackupUtil` (lo llaman los repositorios antes de sobrescribir) |
| 9. Versión 2 | `ProductoRepository` (detecta la cabecera, `migrarAV2`) + `InformeService.productosPorPais()` |
| 10. Extra config | `Config` + `resources/config.properties` |

## Lectura tolerante a errores

Los tres ficheros traen registros rotos a propósito. La regla es la misma en todos: **un registro malo se descarta, se registra con `logger.error` (con su id y número de línea) y la carga sigue**.

- **JSON con errores de sintaxis en un objeto** (C007, al que le falta una coma): si se hace `JsonParser.parseString(ficheroEntero)`, Gson falla y se pierden **todos** los clientes. `JsonUtil.extraerObjetos()` recorre el texto contando llaves (ignorando las que van dentro de comillas), recorta cada `{...}` y lo parsea **por separado** y en modo estricto.
- **JSON con errores de tipo** (C008, C009, C011): `getAsString()` sobre un objeto o una lista lanza una excepción y pararía todo. Por eso cada campo se comprueba antes (`isJsonObject`, `isJsonArray`, `isBoolean`...) y se convierte en una `DatosInvalidosException` con un mensaje claro.
- **CSV/TXT con líneas incompletas** (P013 o la línea 14 de pedidos): se compara el número de columnas o campos antes de leerlos.
- **Red de seguridad**: cada registro va además en un `catch (RuntimeException e)`, que hace `logger.error("...", e)` con la traza completa. Pase lo que pase, un registro raro no tumba la carga.

## Detalles que suelen fallar (y cómo están resueltos)

- **`split(",", -1)`**: sin el `-1`, Java elimina los campos vacíos del final. Entonces `P006,...,200,` tendría 5 columnas en vez de 6.
- **`split("\\|")`**: `|` es un carácter especial en regex y hay que escaparlo.
- **`String.format(Locale.US, "%.2f")`**: con el Locale español sale `79,90`, y esa coma rompe el CSV.
- **Fechas estrictas**: `ResolverStyle.STRICT` hace que `32/10/2026` falle. Con STRICT hay que usar `uuuu` y no `yyyy`.
- **Siguiente id** (clientes y pedidos): se calcula con el mayor número visto en el fichero, *incluidos los registros descartados*. Si no, se repetiría un id que ya está en el fichero aunque sea erróneo (`PED-010`, `C011`...).
- **Parámetros `{}` en el logger**: `logger.error("Producto {} descartado: {}", id, motivo)` es mejor que concatenar con `+`, porque el texto solo se construye si ese nivel de log está activo.
- **JSON con `JsonParser`**: en vez de `gson.fromJson(..., Cliente.class)` se lee campo a campo para poder validar, convertir la fecha a `LocalDate` y aceptar `direccion: null`.
- **Nombres de fichero sin tildes**: `productos_Taiwán.json` se guarda como `productos_Taiwan.json`, porque en algunos sistemas las tildes en el nombre dan `InvalidPathException`.

## Para pensar (posible pregunta del profe)

Al eliminar **P001** (permitido porque solo está en pedidos ENTREGADO), `pedidos.txt` sigue teniendo `P001x...`. En la **siguiente carga**, PED-001 y PED-008 se descartarían porque "el producto P001 no existe". En una base de datos real esto se evita con un **borrado lógico**: un campo `activo=false` en lugar de borrar la línea. Es una buena mejora si quieres subir nota.
