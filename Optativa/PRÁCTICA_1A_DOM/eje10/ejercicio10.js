document.addEventListener("DOMContentLoaded", init);

const productos = [
  { id: 1, nombre: "Patata", precio: 1, imagen: "patata.jpg" },
  { id: 2, nombre: "Cebolla", precio: 1.2, imagen: "cebolla.jpg" },
  { id: 3, nombre: "Calabacin", precio: 2.1, imagen: "calabacin.jpg" },
  { id: 4, nombre: "Fresas", precio: 0.6, imagen: "fresas.jpg" },
];

function init() {
  var listaCompra = document.getElementById("listaCompra");

  productos.forEach((producto) => {
    var productoFila = document.createElement("li");
    productoFila.style.display = "flex";
    productoFila.style.alignItems = "center";
    productoFila.style.gap = "20px";
    productoFila.style.marginTop = "20px";
    productoFila.style.padding = "10px";

    var img = document.createElement("img");
    img.src = `./img/${producto.imagen}`;
    img.alt = producto.nombre;
    img.style.width = "80px";
    img.style.height = "80px";

    var nombre = document.createElement("p");
    var contenidoNombre = document.createTextNode(producto.nombre);
    nombre.style.fontWeight = "bold";

    var precio = document.createElement("p");
    var contenidoPrecio = document.createTextNode(`${producto.precio} €`);
    precio.style.color = "green";

    nombre.appendChild(contenidoNombre);
    precio.appendChild(contenidoPrecio);

    productoFila.appendChild(img);
    productoFila.appendChild(nombre);
    productoFila.appendChild(precio);
    crearBotonDisponible(productoFila);

    listaCompra.appendChild(productoFila);
  });
}

function crearBotonDisponible(padre) {
  var boton = document.createElement("button");
  var botonContenido = document.createTextNode("Disponible");
  boton.style.marginLeft = "10px";
  boton.className = "disabled";
  boton.appendChild(botonContenido);
  boton.onclick = () => cambiarEstado(boton);

  padre.appendChild(boton);
}

function cambiarEstado(boton) {
  if (boton.className == "disabled") {
    boton.className = "enabled";
  } else {
    boton.className = "disabled";
  }
}
