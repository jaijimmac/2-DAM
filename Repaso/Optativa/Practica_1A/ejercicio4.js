document.addEventListener("DOMContentLoaded", init);

function init() {
  const body = document.getElementsByTagName("body")[0];
  const h1 = document.createElement("h1");
  const h1Texto = document.createTextNode("Lenguaje de Marcas");

  h1.appendChild(h1Texto);
  body.appendChild(h1);

  const notaMedia = parseFloat(prompt("Introduzca la nota media de prácticas"));
  const notaExamen = parseFloat(prompt("Introduzca la nota del examen."));
  const notaActitud = parseFloat(prompt("Introduzca la nota actitud."));

  const media = ((notaActitud + notaExamen + notaMedia) / 3).toFixed(2);

  const h2 = document.createElement("h2");
  var texto;

  if (media >= 5) {
    texto = document.createTextNode(
      `El alumno está aprobado. Nota media: ${media}`,
    );
  } else {
    texto = document.createTextNode(
      `El alumno está suspenso. Nota media: ${media}`,
    );
  }

  h2.appendChild(texto);
  body.appendChild(h2);
}
