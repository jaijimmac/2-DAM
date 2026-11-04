document.addEventListener("DOMContentLoaded", init);

function init() {
  let numeroSol;

  do {
    numeroSol = parseInt(prompt("Introduzca un número. (Del 1 al 6)"));
  } while (isNaN(numeroSol) || numeroSol < 1 || numeroSol > 6);

  const body = document.getElementsByTagName("body")[0];
  const encabezado = document.createElement("h" + numeroSol);
  const texto = document.createTextNode(`Encabezado nivel ${numeroSol}`);
  //encabezado.appendChild(texto);
  encabezado.textContent = `Encabezado nivel ${numeroSol}`;
  body.appendChild(encabezado);
}
