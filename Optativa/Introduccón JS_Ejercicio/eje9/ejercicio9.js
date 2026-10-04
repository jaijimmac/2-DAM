document.addEventListener("DOMContentLoaded", init);

function init() {
  var body = document.getElementsByTagName("body")[0];
  var personas = prompt(
    "Introduce una lista de nombres separadas por comas (,)",
  );

  var listaPersonas = personas.split(",");

  for (var persona in listaPersonas) {
    var h3 = document.createElement("h3");
    h3.textContent = `Un saludo para ${listaPersonas[persona]}`;

    body.appendChild(h3);
  }
}
