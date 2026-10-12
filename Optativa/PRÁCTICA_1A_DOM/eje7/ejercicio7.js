document.addEventListener("DOMContentLoaded", init);
var parrafo;
var tamaño = 20;

function init() {
  parrafo = document.getElementsByTagName("p")[0].style.fontSize =
    `${tamaño}px`;
}

function aumentarLetra() {
  tamaño += 5;
  document.getElementsByTagName("p")[0].style.fontSize = `${tamaño}px`;
}

function disminuirLetra() {
  tamaño -= 5;
  document.getElementsByTagName("p")[0].style.fontSize = `${tamaño}px`;
}
