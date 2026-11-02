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
