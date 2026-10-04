document.addEventListener("DOMContentLoaded", init);

function init() {
  var numero = prompt("Ingrese un número.");
  var body = document.getElementsByTagName("body")[0];

  while (numero > 6) {
    numero = prompt("Ingrese un número. (entre 1 y 6)");
  }

  for (var i = 0; i < numero; i++) {
    var enca = document.createElement(`h${i + 1}`);
    enca.textContent = `Encabezado ${i + 1}`;
    body.appendChild(enca);
  }
}
