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
