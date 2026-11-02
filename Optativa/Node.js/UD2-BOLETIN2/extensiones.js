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
