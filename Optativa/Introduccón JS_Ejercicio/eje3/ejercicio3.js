document.addEventListener("DOMContentLoaded", init);

function init() {
  var n1 = parseFloat(prompt("Ingrese el número 1"));
  var n2 = parseFloat(prompt("Ingrese el número 2"));

  var suma = n1 + n2;

  var resta = n1 - n2;
  var mul = n1 * n2;

  if (n2 == 0) {
    resultado = `Suma: ${suma} | Resta: ${resta} | Multiplicación: ${mul} | No se puede dividir por 0`;
  } else {
    var divi = n1 / n2;
    resultado = `Suma: ${suma} | Resta: ${resta} | Multiplicación: ${mul} | División: ${divi}`;
  }

  document.getElementById("h1").textContent = resultado;
}
