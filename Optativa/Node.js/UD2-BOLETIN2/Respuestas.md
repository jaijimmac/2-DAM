# Unidad 2. Boletín 2

## 1. La documentación y los ficheros de una carpeta.

### A) Nivel de estabilidad de `querystring` y de `new Buffer()`

- **Nivel 0 – Deprecated:** está obsoleto. Puede dar avisos al usarlo y no se asegura que siga funcionando en futuras versiones, así que no hay que usarlo.
- **Nivel 1 – Experimental:** es algo nuevo que todavía puede cambiar o quitarse en cualquier versión.
- **Nivel 2 – Stable:** es estable y se puede usar sin problema, no lo van a cambiar salvo que sea muy necesario.
- **Nivel 3 – Legacy:** no lo van a quitar pero ya no se recomienda para código nuevo porque hay otra opción mejor.

**`querystring`:** antes estaba en el nivel **3 (Legacy)** y la documentación decía que para código nuevo se usara **`URLSearchParams`**. Luego le quitaron esa etiqueta porque `querystring` es más rápido, y ahora aparece como **2 (Stable)**. Aun así se sigue recomendando `URLSearchParams`.

```js
const params = new URLSearchParams("nombre=Ana&edad=20");
console.log(params.get("nombre")); // Ana
```

**`new Buffer()`:** está en el nivel **0 (Deprecated)**. Se dejó de usar porque hacía cosas distintas según el tipo de dato que le pasabas, y con un número reservaba memoria sin limpiar que podía tener datos de antes.

- `Buffer.from()` -> para crear un buffer a partir de un string, un array u otro buffer.
- `Buffer.alloc(tamaño)` -> crea un buffer del tamaño indicado relleno de ceros.
- `Buffer.allocUnsafe(tamaño)` -> igual pero sin rellenar, es más rápido pero puede tener datos antiguos.

---

### B) Parámetros de `fs.readdir(path[, options], callback)`

- **`path`** -> obligatorio. Es la ruta de la carpeta.
- **`options`** -> opcional. Puede ser un string con la codificación o un objeto.
- **`callback`** -> obligatorio. Es la función que se ejecuta al terminar y recibe `(err, files)`.

Lo sé porque en la documentación lo que va **entre corchetes `[ ]` es opcional** y lo que va fuera es obligatorio. `options` está dentro de corchetes.

```js
fs.readdir("./", (err, files) => {}); // sin options
fs.readdir("./", { withFileTypes: true }, (err, files) => {}); // con options
```

---

### C) extensiones.js

```js
const fs = require("fs");
const path = require("path");

const carpeta = __dirname;

if (!fs.existsSync(carpeta)) {
  console.error(`Error: la carpeta ${carpeta} no existe`);
  console.error("Uso: node extensiones.js [carpeta]");
  process.exit(1);
}

const ficheros = fs.readdirSync(carpeta, { withFileTypes: true });

const contador = {};

for (const fichero of ficheros) {
  if (!fichero.isFile()) continue;

  let ext = path.extname(fichero.name).toLowerCase();
  if (ext === "") ext = "(sin extensión)";

  contador[ext] = (contador[ext] || 0) + 1;
}

console.log("Carpeta:", path.resolve(carpeta));
console.table(contador);
```

**_Cómo funciona:_**

- \_\_dirname, que es la carpeta donde está el script.

- Compruebo con fs.existsSync que la carpeta existe. Si no existe, muestro el uso con console.error y termino con process.exit(1).

- Leo la carpeta con fs.readdirSync. Le paso { withFileTypes: true } (el parámetro opcional del apartado B) para saber con isFile() si es un fichero o una carpeta, y así no cuento las subcarpetas.

- Con path.extname saco la extensión. Si devuelve '' es que no tiene, y lo cuento como (sin extensión). Lo paso a minúsculas para que .TXT y .txt cuenten juntos.

**Prueba:**

```
$ node extensiones.js prueba
┌─────────┬────────┐
│ (index) │ Values │
├─────────┼────────┤
│ .js     │ 1      │
│ .json   │ 1      │
│ .md     │ 1      │
└─────────┴────────┘
```

## 2. Temporizador de cocina.

- `Codigo Mostrado en temporizador.js`

```js
const [textoSegundos, ...palabras] = process.argv.slice(2);
const segundos = Number(textoSegundos);
const mensaje = palabras.join(" ") || "¡Tiempo!";

if (!Number.isInteger(segundos) || segundos <= 0) {
  console.error("Uso: node temporizador.js <segundos> [mensaje]");
  console.error("Los segundos deben ser un número entero mayor que 0");
  process.exit(1);
}

console.time("Duración total");

let quedan = segundos;
console.log(`Quedan ${quedan} s`);

const intervalo = setInterval(() => {
  quedan--;

  if (quedan > 0) {
    console.log(`Quedan ${quedan} s`);
  } else {
    clearInterval(intervalo);
    console.log(mensaje);
    console.timeEnd("Duración total");
  }
}, 1000);

setTimeout(
  () => {
    console.log("¡Ya vas por la mitad!");
  },
  (segundos * 1000) / 2,
);
```

## 3. Caracteres y Bytes.

- `Codigo Mostrado en bytes.js`

```js
const texto = process.argv.slice(2).join(" ");

if (!texto) {
  console.error("Uso: node bytes.js <texto>");
  process.exit(1);
}

console.log("Texto      :", texto);
console.log("Caracteres :", texto.length);
console.log("Bytes      :", Buffer.byteLength(texto));
console.log("Buffer     :", Buffer.from(texto));

const abc = Buffer.alloc(26);

for (let i = 0; i < abc.length; i++) {
  abc[i] = 65 + i;
}

console.log("Abecedario :", abc.toString("ascii"));
```

## b) Resultados de ejecutar `bytes.js`

| Texto    | Caracteres | Bytes | Buffer                 |
| -------- | :--------: | :---: | ---------------------- |
| `hola`   |     4      |   4   | `68 6f 6c 61`          |
| `España` |     6      |   7   | `45 73 70 61 c3 b1 61` |
| `5 €`    |     3      |   5   | `35 20 e2 82 ac`       |
| `😀`     |     2      |   4   | `f0 9f 98 80`          |

### ¿Por qué no siempre coinciden caracteres y bytes?

Porque Node guarda el texto en **UTF-8**, y en UTF-8 no todos los caracteres ocupan lo mismo:

- **Letras normales, números y espacios** (los del ASCII) ocupan **1 byte**. Por eso en `hola` coinciden: 4 caracteres y 4 bytes.
- **La `ñ`** ocupa **2 bytes**. Por eso `España` tiene 6 caracteres pero 7 bytes.
- **El `€`** ocupa **3 bytes**. `5 €` tiene 3 caracteres (5, espacio y €), y suma 1 + 1 + 3 = 5 bytes.
- **El emoji** ocupa **4 bytes**.

## c) Abecedario con `Buffer.alloc(26)`

```js
const abc = Buffer.alloc(26);

for (let i = 0; i < abc.length; i++) {
  abc[i] = 65 + i;
}

console.log("Abecedario :", abc.toString("ascii"));
```

Salida: `ABCDEFGHIJKLMNOPQRSTUVWXYZ`

## 4. Copiar un fichero en mayúsculas.

- `Codigo Mostrado en mayusculas.js`

```js
const fs = require("fs");
const path = require("path");
const { stdin, stdout } = process;

stdout.write("¿Qué fichero quieres copiar?: ");

stdin.once("data", (resp) => {
  const origen = resp.toString().trim();
  copiarEnMayusculas(origen);
});

function copiarEnMayusculas(origen) {
  const nombreCopia = "MAYUS-" + path.basename(origen);
  const destino = path.join(path.dirname(origen), nombreCopia);

  const lectura = fs.createReadStream(origen, "utf8");
  let escritura;
  let bytes = 0;

  lectura.on("open", () => {
    escritura = fs.createWriteStream(destino);

    escritura.on("finish", () => {
      console.log(`Creado ${nombreCopia} (${bytes} bytes)`);
      process.exit();
    });
  });

  lectura.on("data", (chunk) => {
    bytes += Buffer.byteLength(chunk);
    escritura.write(chunk.toUpperCase());
  });

  lectura.on("end", () => {
    escritura.end();
  });

  lectura.on("error", (err) => {
    console.error("Error:", err.message);
    process.exit(1);
  });
}
```

**_Cómo funciona:_**

- Pregunto el nombre con `stdout.write` y leo la respuesta con `stdin.once("data")`. Le hago `trim()` para quitar el salto de línea del Enter.

- Leo el fichero con `fs.createReadStream(origen, "utf8")`. Con `"utf8"` cada trozo llega como texto, y así puedo hacer `toUpperCase()` antes de escribirlo con `escritura.write`.

- Los bytes los cuento con `Buffer.byteLength(chunk)` y no con `chunk.length`, porque el trozo es texto y `length` contaría caracteres (lo del ejercicio 3).

- El fichero de destino lo creo en el evento `"open"`, así si el fichero no existe no se queda un `MAYUS-` vacío.

- Cuando termina la lectura (`"end"`) cierro la escritura, y cuando se ha escrito todo (`"finish"`) muestro el mensaje y termino.

**Prueba:**

```
$ node mayusculas.js
¿Qué fichero quieres copiar?: nombres.txt
Creado MAYUS-nombres.txt (16 bytes)

$ node mayusculas.js
¿Qué fichero quieres copiar?: nada.txt
Error: ENOENT: no such file or directory, open 'nada.txt'
```

### e) ¿Por qué no puedo usar `lectura.pipe(escritura)`?

Porque `pipe` copia los datos tal cual, lo que se lee se escribe sin cambiar nada. Aquí hay que pasar cada trozo a mayúsculas antes de escribirlo, así que lo hago a mano en el evento `"data"`. Además con `pipe` no podría ir contando los bytes.

### e) ¿Por qué hace falta `process.exit()` al final?

Porque `stdin` (el teclado) se queda abierto esperando más datos, y Node no termina mientras haya algo abierto. Sin `process.exit()` el programa se quedaría colgado y habría que pararlo con Ctrl + C.

---

## 5. Un semáforo con eventos.

- `Codigo Mostrado en semaforo.js`

```js
const EventEmitter = require("events");

class Semaforo extends EventEmitter {
  constructor(ms) {
    super();
    this.colores = ["verde", "ámbar", "rojo"];
    this.indice = 0;
    this.color = this.colores[this.indice];

    this.intervalo = setInterval(() => this.cambiar(), ms);
  }

  cambiar() {
    this.indice = (this.indice + 1) % this.colores.length;
    this.color = this.colores[this.indice];

    this.emit("cambio", this.color);
    if (this.color === "rojo") {
      this.emit("rojo");
    }
  }

  parar() {
    clearInterval(this.intervalo);
    this.emit("parado");
  }
}

module.exports = Semaforo;
```

- `Codigo Mostrado en usar-semaforo.js`

```js
const Semaforo = require("./semaforo");

const semaforo = new Semaforo(1000);
let rojos = 0;

console.log("Semáforo encendido en", semaforo.color);

semaforo.on("cambio", (color) => {
  console.log("Color:", color);
});

semaforo.once("rojo", () => {
  console.log("(primer rojo: ¡frena!)");
});

semaforo.on("rojo", () => {
  rojos++;
  if (rojos === 2) {
    semaforo.parar();
  }
});

semaforo.on("parado", () => {
  console.log("Semáforo apagado");
});
```

**_Cómo funciona:_**

- `Semaforo` hereda de `EventEmitter` con `extends`, por eso tiene `on`, `once` y `emit`. En el constructor hay que llamar a `super()`.

- Para cambiar de color uso `(indice + 1) % 3`, así después del rojo (2) vuelve al verde (0).

- Cada cambio emite `"cambio"` con el color, y si es rojo emite también `"rojo"`.

- El aviso del primer rojo va con `once`, así que solo sale una vez. Para pararlo en el segundo rojo uso un contador.

- `parar()` hace `clearInterval` y emite `"parado"`. Como ya no queda ningún temporizador, el programa termina solo.

**Prueba:**

```
$ node usar-semaforo.js
Semáforo encendido en verde
Color: ámbar
Color: rojo
(primer rojo: ¡frena!)
Color: verde
Color: ámbar
Color: rojo
Semáforo apagado
```

---

## 6. Un servidor web con varias rutas.

- `Codigo Mostrado en servidor.js`

```js
const http = require("http");
const os = require("os");
const crypto = require("crypto");

const puerto = process.env.PORT || 3000;

const servidor = http.createServer((req, res) => {
  console.log(req.method, req.url);

  if (req.url === "/") {
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(`
      <h1>Servidor de 2º DAM</h1>
      <p>Prueba también <a href="/info">/info</a> y <a href="/dado">/dado</a></p>
    `);
  } else if (req.url === "/info") {
    const info = {
      sistema: os.type(),
      cpus: os.cpus().length,
      node: process.version,
    };
    res.writeHead(200, { "Content-Type": "application/json" });
    res.end(JSON.stringify(info));
  } else if (req.url === "/dado") {
    const numero = crypto.randomInt(1, 7);
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(`<h1>Has sacado un ${numero}</h1>`);
  } else {
    res.writeHead(404, { "Content-Type": "text/html; charset=utf-8" });
    res.end("404 - Página no encontrada");
  }
});

servidor.listen(puerto, () => {
  console.log(`Servidor en http://localhost:${puerto}`);
});
```

**_Cómo funciona:_**

- El puerto se coge de `process.env.PORT` y si no existe se usa el 3000.

- En cada petición muestro `req.method` y `req.url` en la terminal.

- Con `if / else if` miro la ruta y respondo con su `Content-Type`:

| Ruta    | Código | Content-Type               | Respuesta                                      |
| ------- | :----: | -------------------------- | ---------------------------------------------- |
| `/`     |  200   | `text/html; charset=utf-8` | Título "Servidor de 2º DAM" y enlaces          |
| `/info` |  200   | `application/json`         | Sistema, número de CPUs y versión de Node      |
| `/dado` |  200   | `text/html; charset=utf-8` | "Has sacado un X" con `crypto.randomInt(1, 7)` |
| otra    |  404   | `text/html; charset=utf-8` | "404 - Página no encontrada"                   |

- En `/info` uso `JSON.stringify` porque `res.end` no acepta objetos, solo texto.

**Prueba:**

```
$ node servidor.js
Servidor en http://localhost:3000
GET /
GET /favicon.ico
GET /info
GET /dado
GET /alumnos
```

### e) ¿Por qué aparece también `GET /favicon.ico`?

Porque el navegador, cuando entras en una página, pide por su cuenta el `favicon.ico`, que es el icono que sale en la pestaña.

### e) ¿Qué pasa si arranco el servidor dos veces en el mismo puerto?

El segundo da error y se para:

```
Error: listen EADDRINUSE: address already in use :::3000
```

`EADDRINUSE` significa que el puerto ya está en uso, y un puerto solo lo puede usar un programa a la vez.
