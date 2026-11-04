document.addEventListener("DOMContentLoaded", init);

function init() {
  var numeroInt = 3;

  do {
    var respuesta = prompt(
      `¿Quién es el pintor de las Meninas? Le quedan ${numeroInt} intentos.`,
    );
    numeroInt--;
    if (numeroInt === 0) {
      alert("Lo siento! La respuesta correcta es Velázquez");
      break;
    }
  } while (respuesta !== "Velázquez" || respuesta === null || numeroInt === 0);
}
