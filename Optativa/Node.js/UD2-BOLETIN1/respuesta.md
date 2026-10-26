## Ejercicio 1.

**a)** ¿En qué orden se mostrarán las letras A, B, C y D? Razona la respuesta utilizando la pila de
llamadas, la cola de callbacks y el event loop.\*\*

Se mostrarán en el orden de **A**, **D**, **B** y **C**.
He puesto ese orden ya que el **A** sale en el primer console.log, el **D** porque no tiene que tener una espera, luego el **B** porque tiene el timeoit y luego ya el **C**. cuando encuentre el archivo y lo lee.

###

**b)** Ejecútalo y comprueba tu respuesta. ¿Qué instrucción es bloqueante? ¿Cuáles son no
bloqueantes? Explica la diferencia.\*\*

La opcion **A** y la opción **D** se ejecutan sin esperar a nada , y luego la **B** y **C** tienene que esperar a leer el archivo.

###

**c)** Cambia filename por 'no-existe.txt' en la llamada a readFile y añade al callback la
gestión del error con el parámetro err (convención «error primero»). ¿Qué muestra ahora? ¿Se
detiene el programa?\*\*

Se detiene el programa y sale el error.

En las funciones asíncronas el error no se lanza como excepción. Se
pasa como primer argumento del callback

###

**d)** Añade al final console.log(process.version) y console.log(document.title). ¿Qué
ocurre con cada una? Relaciónalo con las diferencias entre JavaScript en el navegador y en
Node.js.\*\*

- process.version muestra la versión de Node.
- document.title lanza un ReferenceError.

Document pertenece al DOM del navegador, y en Node no hay página ni ventana.

## Ejercicio 2.

###

**a)** Explica por qué este código es un ejemplo de callback hell. ¿Por qué cada readFile/writeFile
tiene que ir dentro del anterior?

Es callback hell porque cada operación asíncrona se anida dentro del callback de la anterior.

Cada paso necesita el resultado del anterior: sin leer config.json no se sabe qué fichero leer, y sin leer ese fichero no se puede escribir el informe.

###

**b)** Reescríbelo en procesar-then.js usando fs/promises con .then() y un único .catch().

```js
const fs = require("fs/promises");

let config;

fs.readFile("config.json", "utf8")
  .then((datos) => {
    config = JSON.parse(datos);
    return fs.readFile(config.entrada, "utf8");
  })
  .then((texto) => {
    const lineas = texto.trim().split("\n").length;
    const informe = `Fichero: ${config.entrada}\nLíneas: ${lineas}\n`;
    return fs.writeFile(config.salida, informe);
  })
  .then(() => console.log("Informe creado en", config.salida))
  .catch((err) => console.error("Error:", err.message));
```

###

**c)** Reescríbelo en procesar-async.js con una función async, await y try/catch.

```js
const fs = require("fs/promises");

async function procesar() {
  try {
    const config = JSON.parse(await fs.readFile("config.json", "utf8"));
    const texto = await fs.readFile(config.entrada, "utf8");
    const lineas = texto.trim().split("\n").length;
    const informe = `Fichero: ${config.entrada}\nLíneas: ${lineas}\n`;
    await fs.writeFile(config.salida, informe);
    console.log("Informe creado en", config.salida);
  } catch (err) {
    console.error("Error:", err.message);
  }
}

procesar();
```

###

**d)** Borra (o renombra) alumnos.txt y ejecuta las tres versiones. ¿En cuántos sitios hay que
gestionar el error en cada una?

Las tres muestran:

Error: ENOENT: no such file or directory, open 'alumnos.txt'

- Callbacks: en 3 sitios, un if (err) en cada callback.
- .then(): en 1 sitio, el .catch() final.
- async/await: en 1 sitio, el try/catch.

###

**e)** (Ampliación) Crea una función esperar(ms) que devuelva una promesa (new Promise) y úsala
para que el programa espere 1 segundo antes de escribir el informe

```js
function esperar(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}
```

En procesar-async.js, justo antes del writeFile:

```js
await esperar(1000);
await fs.writeFile(config.salida, informe);
```
