document.addEventListener("DOMContentLoaded", init);

function init() {
  const nombres = prompt(
    "Introduce una lista de nombres separados por una coma.",
  );

  var lista = nombres.split(",");

  const body = document.getElementsByTagName("body")[0];

  lista.forEach((nombre) => {
    let h1 = document.createElement("h1");
    h1.textContent = `Hola ${nombre.trim()}`;
    body.appendChild(h1);
  });

  const h2 = document.createElement("h2");
  h2.textContent = `Nº nombres: ${lista.length}\nPrimera persona: ${lista[0].trim()}\nÚltima persona: ${lista[lista.length - 1].trim()}`;

  body.appendChild(h2);

  console.log(lista.sort());
  console.log(lista.reverse());
}
