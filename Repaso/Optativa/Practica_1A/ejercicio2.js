document.addEventListener("DOMContentLoaded", init);

function init() {
  var cadenaTexto = prompt("Ingrese la cadena de texto: ");
  var numero = parseInt(prompt("Ingrese el número: "));

  var caracter = cadenaTexto.charAt(numero);

  alert(
    `En la posición ${numero} de la cadena ${cadenaTexto} se encuentra el carácter ${caracter}`,
  );
}

/*function init() {
  const cadenaTexto = prompt("Introduce una cadena de texto.");
  const posicion = prompt("Introduzca un número.");

  const body = document.getElementsByTagName("body")[0];
  const h2 = document.createElement("h2");
  const array = cadenaTexto.split("");
  var letra = array[posicion - 1];

  const texto = document.createTextNode(
    `En la posición ${posicion} de la cadena ${cadenaTexto} se encuentra el carácter ${letra}`,
  );

  h2.appendChild(texto);
  body.appendChild(h2);
}*/
