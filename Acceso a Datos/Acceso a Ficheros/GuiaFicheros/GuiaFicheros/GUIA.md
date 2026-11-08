# Guía: leer y escribir ficheros CSV, JSON y TXT sin que nada reviente

Esta guía va acompañada del proyecto **GuiaFicheros**. Cada caso de esta guía aparece en un fichero de `resources/` con el error escrito a propósito, y en el código está la solución comentada. Ejecuta `App.java` y verás cada error registrado con `logger.error`.

---

## 0. La regla de oro: la plantilla de cualquier lector

Sea cual sea el formato, un lector robusto sigue siempre estos pasos:

```java
public List<Pais> leerPaises() {
    List<Pais> lista = new ArrayList<>();

    // 1. ¿Existe el fichero?           -> si no, logger.error y devolver lista vacía
    // 2. Abrir con try-with-resources y UTF-8
    // 3. ¿Está vacío?                  -> logger.error y salir
    // 4. Leer y validar la cabecera (CSV) o la estructura (JSON)
    // 5. Bucle por registro:
    //      a) saltar líneas vacías y comentarios
    //      b) try {
    //             partir -> limpiar (trim, "null") -> convertir (parse) -> validar -> añadir
    //         } catch (...) {
    //             logger.error("Línea X descartada: motivo -> línea")   // y SEGUIR
    //         }
    // 6. logger.info("N registros cargados")
    return lista;
}
```

**La clave de todo:** el `try/catch` de cada registro va **dentro** del bucle. Si va fuera, el primer registro malo detiene la lectura de **todo** el fichero.

---

## 1. Qué falla en tu `leerPaises()`

| # | En tu código | Qué pasa | Arreglo |
|---|---|---|---|
| 1 | `try` **fuera** del `while` | Un número mal escrito en la línea 3 lanza `NumberFormatException`: salta al `catch` y **no se lee nada más** | `try/catch` **dentro** del `while` |
| 2 | `split(",")` | `"a,b,,"` da 2 campos en vez de 4, porque **se pierden los vacíos del final** | `split(",", -1)` |
| 3 | `lineas.length < 5` | No detecta las líneas con columnas **de más** | `!= 5` |
| 4 | Sin `trim()` | `" Asia "` no es igual a `"Asia"` y `Integer.parseInt(" 46000000")` falla | `.trim()` en cada campo |
| 5 | Ningún campo se valida | Se cargan países con nombre o continente vacío (`Brasil,,214000...`) | Comprobar `isEmpty()` y lanzar una excepción |
| 6 | `split(" ")` en idiomas | `"Inglés  Francés"` (dos espacios) da `[Inglés, "", Francés]` | `split("\\s+")` y filtrar los vacíos |
| 7 | `Scanner` sin cerrar | Fuga de recursos; en Windows el fichero queda bloqueado | `try (Scanner in = ...) { }` |
| 8 | `new FileReader(fichero)` sin charset | En Windows las tildes pueden salir como `Ã©` | `new FileReader(fichero, StandardCharsets.UTF_8)` |
| 9 | `in.nextLine()` sin comprobar | Si el fichero está vacío, `NoSuchElementException` | `if (!in.hasNextLine())` antes |
| 10 | `catch (Exception e)` genérico | Mezcla errores de fichero y de datos, con mensajes poco claros | Capturar cada excepción con su mensaje |
| 11 | `System.err.println` | No queda registro de los errores | `logger.error(...)` |
| 12 | `throws FileNotFoundException` y además `catch` | El `throws` sobra si ya la capturas | Quitar el `throws` |

### Tu método, corregido y con tu mismo esquema

```java
@Override
public List<Pais> leerPaises() {
    this.listadoPaises.clear();

    // try-with-resources: el Scanner se cierra solo. UTF-8 para que las tildes salgan bien.
    try (Scanner in = new Scanner(new FileReader(this.fichero, StandardCharsets.UTF_8))) {

        if (!in.hasNextLine()) {                     // fichero vacío
            logger.error("El fichero {} está vacío", this.fichero);
            return this.listadoPaises;
        }
        in.nextLine();                               // cabecera

        int numLinea = 1;
        while (in.hasNextLine()) {
            String valor = in.nextLine();
            numLinea++;
            if (valor.isBlank()) continue;

            // El try va DENTRO del while: una línea mala no para el resto
            try {
                String[] campos = valor.split(",", -1);   // -1: no pierde los campos vacíos del final
                if (campos.length != 5) {
                    throw new IllegalArgumentException("tiene " + campos.length + " columnas y deben ser 5");
                }

                String nombre = campos[0].trim();
                String continente = campos[1].trim();
                if (nombre.isEmpty())     throw new IllegalArgumentException("falta el nombre");
                if (continente.isEmpty()) throw new IllegalArgumentException("falta el continente");

                int poblacion = Integer.parseInt(campos[2].trim());  // NumberFormatException si no es número
                if (poblacion < 0) throw new IllegalArgumentException("población negativa");

                List<String> idiomas = new ArrayList<>();
                for (String idioma : campos[3].trim().split("\\s+")) {  // \\s+ = 1 o más espacios
                    if (!idioma.isBlank()) idiomas.add(idioma);
                }

                String moneda = campos[4].trim().isEmpty() ? null : campos[4].trim();

                this.listadoPaises.add(new Pais(nombre, continente, poblacion, idiomas, moneda));

            } catch (NumberFormatException e) {          // la hija ANTES que la madre
                logger.error("Línea {} descartada: población no numérica -> {}", numLinea, valor);
            } catch (IllegalArgumentException e) {
                logger.error("Línea {} descartada: {} -> {}", numLinea, e.getMessage(), valor);
            }
        }

    } catch (FileNotFoundException e) {
        logger.error("No se encuentra el fichero {}", this.fichero);
    } catch (IOException e) {
        logger.error("Error leyendo el fichero {}", this.fichero, e);
    }
    return this.listadoPaises;
}
```

> **Orden de los `catch`:** `NumberFormatException` es hija de `IllegalArgumentException`, así que tiene que ir **antes**; si no, no compila. Lo mismo pasa con `FileNotFoundException`, que va antes de `IOException`.

---

## 2. CSV: todos los casos

Fichero de prueba: `resources/paises_errores.csv`. Solución: `LectorCsv.java`, `CsvUtil.java` y `PaisFactory.java`.

| Caso | Ejemplo en el fichero | Si no lo controlas | Solución |
|---|---|---|---|
| Línea vacía | *(línea en blanco)* | `split` da 1 campo y se lanza `ArrayIndexOutOfBounds` | `if (linea.isBlank()) continue;` |
| Comentario | `# Esto es un comentario` | Se intenta leer como país | `if (linea.trim().startsWith("#")) continue;` |
| Campo vacío (doble coma) | `Brasil,,214000000,...` | Se carga un país sin continente | `trim()` + `isEmpty()` y lanzar una excepción |
| Último campo vacío | `Egipto,África,...,Árabe,` | `split(",")` lo **pierde** y da 4 columnas | `split(",", -1)` |
| Faltan columnas | `Japón,Asia,125800000,Japonés` | `campos[4]` lanza `ArrayIndexOutOfBounds` | Comprobar `campos.length` **antes** de acceder |
| Sobran columnas | `México,...,Peso mexicano,extra` | Se carga mal sin avisar | `campos.length != esperado` |
| Separador distinto | `Nigeria;África;206000000;...` | 1 sola columna | Detectar el `;` y avisar, o leer con el separador correcto |
| Espacios sobrantes | `  China , Asia , 1412000000 ` | `parseInt(" 1412000000 ")` lanza `NumberFormatException` | `.trim()` en cada campo |
| `"null"` / `N/A` como texto | `null,Asia,...` · `...,N/A` | Se carga un país llamado `"null"` | Convertir `"null"`, `"N/A"`, `"-"` y `""` en `null` |
| Número con letras | `Portugal,Europa,diez millones` | `NumberFormatException` | Capturarla dentro del bucle |
| Número con puntos de miles | `Francia,...,67.000.000,...` | `NumberFormatException` | **Reparar:** si encaja con `\d{1,3}(\.\d{3})+`, quitar los puntos y avisar con `warn` |
| Número negativo | `Alemania,Europa,-83000000` | Se carga un dato imposible | Validar el rango |
| Número demasiado grande | `8000000000` en un `int` | `NumberFormatException` (máximo de `int`: unos 2.147 millones) | Usar `long` |
| Varios espacios en una lista | `Inglés  Francés` | `split(" ")` da `[Inglés, "", Francés]` | `split("\\s+")` y filtrar los vacíos |
| Valor fuera de una lista cerrada | `Perú,Atlántida,...` | Se carga un continente inventado | Comprobar contra un `Map`/`Set` de valores válidos |
| Mayúsculas o tildes distintas | `europa` · `Africa` | No coincide con `"Europa"` / `"África"` | Normalizar (minúsculas y sin tildes) y corregir con `warn` |
| Duplicado | `Argentina` dos veces | Hay dos registros iguales | `Set<String>`: `if (!vistos.add(nombre))` es un error |
| Texto entre comillas | `"Corea del Sur","Asia",...` | Las comillas acaban en el dato | Quitar las comillas exteriores |
| Coma dentro de comillas | `"Bosnia, Herzegovina",Europa,...` | `split(",")` da **6** columnas | Partir carácter a carácter respetando las comillas (`CsvUtil.partirLinea`) |
| Comillas sin cerrar | `"Chile,América,...` | El campo se come el resto de la línea | Detectarlo al acabar la línea y lanzar una excepción |
| BOM de Excel | Carácter invisible `﻿` antes de `Nombre` | La cabecera no coincide: `"﻿Nombre" != "Nombre"` | `if (s.startsWith("﻿")) s = s.substring(1);` |
| Cabecera sin una columna | `Nombre,Población,Idioma,Moneda` | Se leen los datos en la columna equivocada | Validar la cabecera y parar con `logger.error` |
| Columnas en otro orden | `Continente,Nombre,...` | Se leen los datos cambiados | Mapear `nombre de columna → posición` a partir de la cabecera |
| Última línea sin salto | `Australia,...` (fin del fichero) | Con `Scanner` o `readLine` **no pasa nada** | Nada que hacer (pero cuidado al **añadir** al final) |
| Saltos de línea de Windows (`\r\n`) | Fichero guardado en Windows | Con `split` el último campo acaba en `\r` | `trim()` lo quita; `Scanner` y `readLine` ya lo gestionan |

---

## 3. JSON: todos los casos

Fichero de prueba: `resources/paises_errores.json`. Solución: `LectorJson.java` y `JsonUtil.java`.

### 3.1 Errores de sintaxis (el JSON está mal escrito)

| Caso | Ejemplo | Si no lo controlas | Solución |
|---|---|---|---|
| Falta una coma | `"continente": "América"` ↵ `"poblacion": ...` | `JsonParser.parseString(todo)` falla y **se pierden TODOS** los registros | Recortar cada `{...}` y parsearlo **por separado** |
| Coma final en un objeto | `"moneda": "Naira",` ↵ `}` | `JsonSyntaxException`; si parseas el fichero entero, se pierde todo | Igual que la coma que falta: parsear cada objeto por separado |
| Coma final en una lista o texto sin comillas | `["Inglés",]` · `{nombre: Perú}` | `JsonParser.parseString` es *lenient*: **los acepta sin avisar** (la lista recibe un `null` extra) | Parsear en modo **estricto** (`reader.setLenient(false)`) |
| Comilla sin cerrar | `"nombre": "Perú,` | El objeto se come al siguiente | Al recortar, cerrar la cadena en el salto de línea |
| Objeto sin cerrar al final | `{ "nombre": "X"` (fin) | Excepción | Devolver el trozo para que falle al parsear y quede en el log |

### 3.2 Errores de tipo (el JSON es válido, pero el dato no es lo que se espera)

| Caso | Ejemplo | Si no lo controlas | Solución |
|---|---|---|---|
| Campo a `null` | `"nombre": null` | `getAsString()` lanza `UnsupportedOperationException` | `if (e == null \|\| e.isJsonNull())` |
| Falta el campo | sin `"continente"` | `obj.get(...)` devuelve `null` y luego `NullPointerException` | Igual que el anterior |
| Número como texto | `"poblacion": "1412000000"` | `getAsLong()` funciona por casualidad | `isString()`: convertir y avisar con `warn` |
| Texto que no es número | `"poblacion": "38 millones"` | `NumberFormatException` | Capturarla y descartar el registro |
| Decimal donde va un entero | `"poblacion": 67000000.5` | `getAsLong()` **trunca** sin avisar | `BigDecimal` + `scale() > 0` es un error |
| Booleano donde va un número | `"poblacion": true` | `NumberFormatException` o un dato raro | Comprobar `isNumber()` |
| Número donde va texto | `"nombre": 123` | Se carga `"123"` como nombre | Comprobar `isString()` |
| Objeto donde va texto | `"moneda": {"codigo": "EUR"}` | `getAsString()` lanza una excepción que **para todo** | Comprobar `isJsonPrimitive()` antes |
| Texto donde va una lista | `"idiomas": "Hindi Inglés"` | `getAsJsonArray()` lanza `IllegalStateException` | `isJsonArray()`; si es texto, separarlo y avisar con `warn` |
| `null` o vacíos dentro de una lista | `["Japonés", null, "  "]` | `null` o `""` en la lista | Saltar los `isJsonNull()` y los vacíos |
| Claves con mayúsculas o tildes | `"Población"` en vez de `"poblacion"` | `obj.get("poblacion")` da `null` | Normalizar las claves al leer |
| Campo desconocido | `"capital": "Pretoria"` | Nada, pero el dato se pierde sin que lo sepas | Ignorarlo y avisar con `warn` |

### 3.3 Errores del fichero entero

| Caso | Solución |
|---|---|
| Vacío | Comprobar `contenido.trim().isEmpty()` |
| No es un array (un objeto suelto `{...}`) | Avisar con `warn` y leer los objetos que contenga |
| No está en UTF-8 | Capturar `MalformedInputException` y avisar de que hay que guardarlo en UTF-8 |

> **¿`gson.fromJson(json, Pais[].class)` directamente?** Es cómodo, pero con un solo error se pierde todo, y no te deja validar campo a campo. Úsalo solo si el fichero es fiable, por ejemplo uno que hayas escrito tú.

---

## 4. TXT (clave=valor): todos los casos

Fichero de prueba: `resources/paises_errores.txt`. Solución: `LectorTxt.java`, que usa `BufferedReader`.

| Caso | Ejemplo | Solución |
|---|---|---|
| Falta el `=` | `continente América` | `indexOf('=') < 0` es un error |
| Clave vacía | `=Europa` | La clave queda vacía tras `trim()`: error |
| Clave repetida | `nombre=Chile` y `nombre=República de Chile` | `Map.containsKey`: error (no se sabe cuál es la buena) |
| Clave desconocida | `color=rojo` | Ignorarla y avisar con `warn` |
| Espacios alrededor del `=` | `  nombre   =   Perú` | `trim()` en la clave y en el valor |
| El valor contiene `=` | `moneda=Peso=MXN` | Partir por el **primer** `=`: `substring(0, i)` / `substring(i + 1)` (o `split("=", 2)`) |
| Valor vacío | `moneda=` | Convertirlo en `null` y decidir si es obligatorio |
| Falta un campo obligatorio | Bloque sin `nombre=` | La validación lanza «falta el nombre» |
| Varias líneas en blanco | | Saltarlas |
| Último bloque sin `---` | `Egipto` al final | **Procesar el bloque pendiente al salir del bucle**: si no, se pierde |

---

## 5. Errores de fichero (cualquier formato)

| Caso | Qué pasa | Solución |
|---|---|---|
| No existe | `FileNotFoundException` / `NoSuchFileException` | `if (!fichero.exists())` y registrar la **ruta absoluta** (`getAbsolutePath()`) para ver dónde lo está buscando |
| Ruta relativa que «no funciona» | Java busca desde el **directorio de trabajo** (en Eclipse, la raíz del proyecto) | Usar `new File("resources/x.csv")` y comprobar con `getAbsolutePath()` |
| Vacío | `nextLine()` lanza `NoSuchElementException` | `hasNextLine()` antes |
| Tildes rotas (`Ã©`) | Se lee con la codificación del sistema | `StandardCharsets.UTF_8` **siempre**, al leer y al escribir |
| BOM | Carácter invisible al principio | Quitar `﻿` |
| Recurso sin cerrar | Fichero bloqueado y datos sin volcar al escribir | `try (...) { }` |

---

## 6. Escritura: todos los casos

Solución: `EscritorCsv.java`, `EscritorJson.java` y `EscritorTxt.java`.

| Caso | Si no lo controlas | Solución |
|---|---|---|
| La carpeta no existe | `NoSuchFileException` | `Files.createDirectories(ruta.getParent())` |
| Lista `null` | `NullPointerException` | Comprobarla al principio y registrar el error |
| Objetos `null` en la lista | `NullPointerException` a mitad, con el fichero a medias | Saltarlos con `warn` |
| Campo `null` | Se escribe el texto `"null"` | `valor == null ? "" : valor` |
| Valor con el separador | `Bosnia, Herzegovina` rompe las columnas | Rodearlo de comillas (`CsvUtil.escapar`) |
| Valor con comillas | `Tierra "Media"` | Duplicar las comillas: `"Tierra ""Media"""` |
| Decimales con `String.format` | Con el Locale español sale `79,90`, y esa **coma** rompe el CSV | `String.format(Locale.US, "%.2f", x)` |
| Sobrescribir o añadir | Borras lo que había sin querer | Sobrescribir: `Files.newBufferedWriter(ruta)`. Añadir: `StandardOpenOption.CREATE, StandardOpenOption.APPEND` |
| Añadir a un fichero sin salto final | Lo nuevo se pega a la última línea | Comprobar si termina en `\n` y, si no, escribir `newLine()` antes |
| No cerrar el writer | **El fichero queda vacío o a medias** (los datos se quedan en el búfer) | `try (BufferedWriter bw = ...) { }` |
| El programa falla a mitad | El fichero original queda destruido | Escribir en un `.tmp` y después `Files.move(tmp, destino, REPLACE_EXISTING)` |
| JSON en una sola línea | Ilegible | `new GsonBuilder().setPrettyPrinting()` |
| JSON sin los campos `null` | Gson los **omite** por defecto | `.serializeNulls()` |
| JSON con `=` en vez de `=` | Gson escapa caracteres «HTML» | `.disableHtmlEscaping()` |
| Salto de línea dentro de un valor (TXT o CSV) | Rompe el formato al releer | Sustituirlo por un espacio (TXT) o poner comillas (CSV) |

**Comprobación final:** después de escribir, vuelve a leer el fichero con tu propio lector. Si lo que escribes no se puede leer sin errores, el escritor está mal. `App.java` lo hace con los tres formatos.

---

## 7. ¿Descartar, reparar o avisar?

| Situación | Qué hacer | Nivel de log |
|---|---|---|
| Falta un dato obligatorio, el tipo es imposible o la sintaxis está rota | **Descartar** el registro | `logger.error` |
| El dato se entiende sin ambigüedad (`"67.000.000"`, `europa`, número como texto) | **Reparar** y seguir | `logger.warn` |
| Un campo sobra o es desconocido | **Ignorarlo** | `logger.warn` |
| Todo correcto | Contarlo al final | `logger.info` |
| Error inesperado (bug) | Descartar y guardar la **traza** | `logger.error("msg", e)` |

> **No repares lo que es ambiguo.** Si `"1.500"` puede ser mil quinientos o uno con cinco, es mejor descartarlo y avisar.

---

## 8. Chuleta de métodos

```java
// ---------- LEER ----------
// Línea a línea con Scanner
try (Scanner in = new Scanner(new File("resources/x.csv"), StandardCharsets.UTF_8)) {
    while (in.hasNextLine()) { String l = in.nextLine(); }
}
// Línea a línea con BufferedReader (readLine() devuelve null al final)
try (BufferedReader br = Files.newBufferedReader(Path.of("resources/x.txt"), StandardCharsets.UTF_8)) {
    String l;
    while ((l = br.readLine()) != null) { }
}
// Todo de golpe
List<String> lineas = Files.readAllLines(Path.of("x.csv"), StandardCharsets.UTF_8);
String texto = Files.readString(Path.of("x.json"), StandardCharsets.UTF_8);

// ---------- ESCRIBIR ----------
Files.createDirectories(Path.of("salida"));
try (BufferedWriter bw = Files.newBufferedWriter(Path.of("salida/x.csv"), StandardCharsets.UTF_8)) {
    bw.write("texto"); bw.newLine();
}
// Añadir al final
Files.writeString(ruta, "linea\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

// ---------- PARTIR Y LIMPIAR ----------
"a,b,,".split(",", -1);          // [a, b, "", ""]  (no pierde los vacíos)
"a|b".split("\\|");              // | . * + ? ( ) [ ] { } $ ^ \ hay que escaparlos
"a  b".split("\\s+");            // [a, b]
"k=v=w".split("=", 2);           // [k, v=w]
s.trim();  s.isBlank();  s.isEmpty();

// ---------- CONVERTIR ----------
Integer.parseInt(" 12 ".trim()); // NumberFormatException si no es número
Long.parseLong("8000000000");    // para números grandes
Double.parseDouble("79.90");     // usa PUNTO, no coma
LocalDate.parse("05/10/2026", DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT));   // 32/10 -> DateTimeParseException

// ---------- JSON (Gson) ----------
JsonElement e = obj.get("campo");
e == null || e.isJsonNull()      // falta o es null
e.isJsonPrimitive()              // texto, número o booleano
e.getAsJsonPrimitive().isString() / .isNumber() / .isBoolean()
e.isJsonObject() / e.isJsonArray()
```

### Excepciones que te vas a encontrar

| Excepción | Cuándo |
|---|---|
| `FileNotFoundException` / `NoSuchFileException` | El fichero o la carpeta no existen |
| `NoSuchElementException` | `nextLine()` sin más líneas (fichero vacío) |
| `ArrayIndexOutOfBoundsException` | `campos[4]` cuando la línea tiene menos columnas |
| `NumberFormatException` | `parseInt` / `parseLong` con algo que no es un número |
| `DateTimeParseException` | Fecha con un formato incorrecto o imposible |
| `JsonSyntaxException` / `JsonParseException` | JSON mal escrito |
| `IllegalStateException` | `getAsJsonArray()` / `getAsJsonObject()` sobre un tipo que no lo es |
| `UnsupportedOperationException` | `getAsString()` sobre un `JsonNull` |
| `NullPointerException` | Un campo que falta y que usas sin comprobar |
| `MalformedInputException` | El fichero no está en la codificación indicada |

---

## 9. Cómo usar el proyecto GuiaFicheros

1. Impórtalo en Eclipse como **proyecto Maven** (se descargan Gson, SLF4J y Logback).
2. Ejecuta `com.guia.ficheros.App`.
3. Mira la consola y el fichero `errores.log`, y compara cada error con el fichero de `resources/` donde está escrito.
4. Revisa la carpeta `salida/`: tiene los ficheros que se han escrito y vuelto a leer sin errores.

**Para practicar:** introduce un error nuevo en cualquier fichero de `resources/` y comprueba que tu lector lo descarta con un mensaje claro y **sigue leyendo** el resto.
