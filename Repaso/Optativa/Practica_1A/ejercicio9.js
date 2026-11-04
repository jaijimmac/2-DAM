document.addEventListener("DOMContentLoaded", init);

function init() {
  const nombres = prompt(
    "Introduce una lista de nombres separados por una coma.",
  );

  var lista = nombres.split(",");

  const body = document.getElementsByTagName("body")[0];

  lista.forEach((nombre) => {
    let h1 = document.createElement("h1");
    h1.textContent = `Hola ${nombre}`;
    body.appendChild(h1);
  });
}
