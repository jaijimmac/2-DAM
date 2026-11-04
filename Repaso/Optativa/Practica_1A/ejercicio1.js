document.addEventListener("DOMContentLoaded", init);

function init() {
  const nombre = prompt("Ingrese su nombre");

  const body = document.getElementsByTagName("body")[0];

  const h4 = document.createElement("h4");
  const h5 = document.createElement("h5");

  const texto = document.createTextNode(`Hola ${nombre}`);
  const textoLetras = document.createTextNode(
    "Tu nombre tiene " + nombre.length + " letras.",
  );

  h4.appendChild(texto);
  h5.appendChild(textoLetras);

  body.appendChild(h4);
  body.appendChild(h5);
}
