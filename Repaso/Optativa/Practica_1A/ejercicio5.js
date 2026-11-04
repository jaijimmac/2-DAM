document.addEventListener("DOMContentLoaded", init);

function init() {
  const nombreOrd = prompt("Introduzca el nombre del ordenador.");
  const nombreMod = prompt("Introduzca el modelo del ordenador.");

  var precioOrd = 1000;
  var precioFinal = 0;

  if (nombreOrd == "MSI" && nombreMod == "PRESTIGE") {
    precioFinal = precioOrd - precioOrd * 0.05;
  } else if (nombreOrd == "HP" && nombreMod == "Pavilion") {
    precioFinal = precioOrd - precioOrd * 0.1;
  } else {
    precioFinal = precioOrd;
  }

  const body = document.getElementsByTagName("body")[0];
  const h1 = document.createElement("h1");
  const texto = document.createTextNode(
    `El precio final del ordenador es: ${precioFinal}€`,
  );

  h1.appendChild(texto);
  body.appendChild(h1);
}
