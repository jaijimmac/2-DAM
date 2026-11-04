document.addEventListener("DOMContentLoaded", init);

var dineroIni = 30;

function init() {
  construirHTML();
}

function lanzadDado() {
  return Math.floor(Math.random() * 6) + 1;
}

function crearApuesta() {
  var apuesta = parseFloat(prompt("¿Qué cantidad de dinero quiere apostar?"));
  var apuestaDado = parseFloat(prompt("¿A qué número quiere apostar?"));
  if (dineroIni > 120 || dineroIni <= 0) {
    alert("No se puede apostar sin dinero o su dinero supera el límite");
  } else {
    if (apuestaDado === lanzadDado()) {
      dineroIni += apuesta + 10;
      console.log("Aciertas");
    } else {
      dineroIni -= apuesta;
      console.log("Pierdes");
    }
  }

  const h2 = document.getElementsByTagName("h2")[0];
  h2.textContent = `Dinero: ${dineroIni}`;
}

function construirHTML() {
  const body = document.getElementsByTagName("body")[0];
  const h1 = document.createElement("h1");
  const h2 = document.createElement("h2");
  const boton = document.createElement("button");

  h1.textContent = `Apuesta al Dado`;
  h2.textContent = "Dinero: 30";
  boton.textContent = "Lanzar dado";

  body.appendChild(h1);
  body.appendChild(h2);
  body.appendChild(boton);

  boton.onclick = crearApuesta;
}
