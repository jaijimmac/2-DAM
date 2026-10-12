document.addEventListener("DOMContentLoaded", init);

function init() {}

function añadirProducto() {
  var listaPadre = document.getElementById("listaPadre");
  var productoFila = document.createElement("li");
  producto = prompt("Ingrese el nombre del  producto.");

  var contenido = document.createTextNode(producto);

  productoFila.appendChild(contenido);
  listaPadre.appendChild(productoFila);
}
