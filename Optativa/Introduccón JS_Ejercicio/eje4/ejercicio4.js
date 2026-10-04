document.addEventListener("DOMContentLoaded", init);

function init() {
  var notaPracticas = parseFloat(prompt("Ingrese la nota de prácticas."));
  var notaExamen = parseFloat(prompt("Ingrese la nota del examen."));
  var notaActitud = parseFloat(prompt("Ingrese la nota de la actitud."));

  var notaMedia = ((notaPracticas + notaExamen + notaActitud) / 3).toFixed(2);

  if (notaMedia < 5) {
    resultado = `El alumno esta suspenso. Nota media:  ${notaMedia}`;
  } else {
    resultado = `El alumno esta aprovado. Nota media:  ${notaMedia}`;
  }

  document.getElementById("p").textContent = resultado;
}
