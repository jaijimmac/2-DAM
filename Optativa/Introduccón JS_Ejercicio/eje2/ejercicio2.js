document.addEventListener("DOMContentLoaded", init);

function init() {
  var cadenaTexto = prompt("Ingrese la cadena de texto: ");
  var numero = parseInt(prompt("Ingrese el número: "));

  var caracter = cadenaTexto.charAt(numero);

  alert(
    `En la posición ${numero} de la cadena ${cadenaTexto} se encuentra el carácter ${caracter}`,
  );
}
