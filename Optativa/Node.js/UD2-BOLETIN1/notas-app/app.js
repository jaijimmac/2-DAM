const fs = require("fs/promises");
const pc = require("picocolors");
const { media, aprobados, mejor } = require("./notas");

async function main() {
  try {
    const lista = JSON.parse(await fs.readFile("notas.json", "utf8"));

    lista.forEach((a) => {
      const color = a.nota >= 5 ? pc.green : pc.red;
      console.log(color(`${a.nombre}: ${a.nota}`));
    });

    console.log("Media:", media(lista).toFixed(2));
    console.log("Aprobados:", aprobados(lista).length);
    console.log("Mejor alumno:", mejor(lista).nombre);
  } catch (err) {
    console.error("Error:", err.message);
  }
}

main();
