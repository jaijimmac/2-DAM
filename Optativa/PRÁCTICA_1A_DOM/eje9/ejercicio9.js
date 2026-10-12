document.addEventListener("DOMContentLoaded", init);

function init() {}

function añadirProducto() {
  var listaPadre = document.getElementById("listaPadre");
  var productoFila = document.createElement("li");
  productoFila.style.marginTop = "10px";
  producto = prompt("Ingrese el nombre del  producto.");

  var contenido = document.createTextNode(producto);

  productoFila.appendChild(contenido);
  crearBotones(productoFila);
  listaPadre.appendChild(productoFila);
}

function crearBotones(padre) {
  var botonSi = document.createElement("button");
  var botonSiContenido = document.createTextNode("Si");
  botonSi.style.marginLeft = "10px";
  botonSi.style.color = "green";
  botonSi.appendChild(botonSiContenido);
  botonSi.onclick = () => marcadoVerde(padre);

  var botonNo = document.createElement("button");
  var botonNoContenido = document.createTextNode("No");
  botonNo.style.marginLeft = "10px";
  botonNo.style.color = "red";
  botonNo.appendChild(botonNoContenido);
  botonNo.onclick = () => marcadoRojo(padre);

  padre.appendChild(botonSi);
  padre.appendChild(botonNo);
}

function marcadoVerde(boton) {
  boton.style.color = "green";
  boton.style.fontStyle = "italic";
  boton.style.fontWeight = 0;
}

function marcadoRojo(boton) {
  boton.style.color = "red";
  boton.style.fontWeight = "bold";
  boton.style.fontStyle = "normal";
}
