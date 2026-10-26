// procesar.js -> versión con callbacks
const fs = require("fs");
fs.readFile("config.json", "utf8", (err, datos) => {
  if (err) return console.error("Error:", err.message);
  const config = JSON.parse(datos);
  fs.readFile(config.entrada, "utf8", (err, texto) => {
    if (err) return console.error("Error:", err.message);
    const lineas = texto.trim().split("\n").length;
    const informe = `Fichero: ${config.entrada}\nLíneas: ${lineas}\n`;
    fs.writeFile(config.salida, informe, (err) => {
      if (err) return console.error("Error:", err.message);
      console.log("Informe creado en", config.salida);
    });
  });
});
