document.addEventListener("DOMContentLoaded", init);

function init() {
  var body = document.getElementsByTagName("body")[0];
  var personas = prompt(
    "Introduce una lista de nombres separadas por comas (,)",
  );

  var listaPersonas = personas.split(",");

  for (var persona in listaPersonas) {
    var h1 = document.createElement("h1");
    h1.textContent = `Un saludo para ${listaPersonas[persona]}`;

    body.appendChild(h1);
  }

  var h3 = document.createElement("h3");
  h3.textContent = `Numero de personas ${listaPersonas.length}. 
    Primera persona ${listaPersonas[0]}. 
    Última persona ${listaPersonas[listaPersonas.length - 1]}`;
  body.appendChild(h3);

  var tittle = document.createElement("h2");
  tittle.textContent = "Lista ordenada A-Z";
  body.appendChild(tittle);

  for (var persona in listaPersonas.sort()) {
    var h3 = document.createElement("h3");
    h3.textContent = `${listaPersonas[persona]}`;
    body.appendChild(h3);
  }

  var tittle = document.createElement("h2");
  tittle.textContent = "Lista ordenada Z-A";
  body.appendChild(tittle);

  for (var persona in listaPersonas.reverse()) {
    var h3 = document.createElement("h3");

    h3.textContent = `${listaPersonas[persona]}`;
    body.appendChild(h3);
  }
}
