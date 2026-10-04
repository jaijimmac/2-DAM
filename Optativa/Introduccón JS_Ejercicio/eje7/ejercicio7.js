document.addEventListener("DOMContentLoaded", init);

function init() {
  var respuesta = "Velázquez";
  var intentos = 3;
  var nombre = prompt("¿Quién es el pintor de las Meninas?");

  while (nombre != respuesta && intentos > 1) {
    intentos--;
    nombre = prompt(`Respuesta incorrecta. Le quedan ${intentos} intentos.`);
  }

  if (nombre == respuesta) {
    alert("¡Correcto! Ha acertado.");
  } else {
    alert(
      "¡Lo siento! Se ha quedado sin intentos. La respuesta correcta es Velázquez.",
    );
  }
}
