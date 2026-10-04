document.addEventListener("DOMContentLoaded", init);
var dinero = 30;

function init() {
  MostrarDinero();
  botonDado = document.getElementById("dado").onclick = apostar;
}

function apostar() {
  var apuesta = prompt("Introduce el número para apostar.");

  var dado = Math.floor(Math.random() * 6) + 1;

  if (apuesta == dado) {
    dinero += 10;
    alert("Has ganado 10€");
  } else {
    dinero -= 10;
    alert("Has perdido 10€");
  }

  MostrarDinero();

  if (dinero == 0) {
    alert("Te has quedado sin dinero");
    document.getElementById("dado").disabled = true;
  } else if (dinero >= 120) {
    alert("Has llegado a la capacidad maxima");
    document.getElementById("dado").disabled = true;
  }
}

function MostrarDinero() {
  document.getElementById("dinero").textContent = dinero;
}
