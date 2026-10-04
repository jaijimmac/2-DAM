document.addEventListener("DOMContentLoaded", init);

function init() {
  var marca = prompt("Ingrese la marca del producto.");
  var modelo = prompt("Ingrese el modelo del producto.");

  precio = 1000;
  precio5 = precio * 0.05;
  precio10 = precio * 0.1;
  var resultado;

  if (marca === "MSI" && modelo === "PRESTIGE") {
    var precioFinal = precio - precio5;
    resultado = `El producto tiene un descuento del 5% ( -${precio5}€ ), el precio final es de ${precioFinal} `;
  } else if (marca === "HP" || modelo === "Pavilion") {
    var precioFinal = precio - precio10;
    resultado = `El producto tiene un descuento del 10% ( -${precio10}€ ), el precio final es de ${precioFinal} `;
  } else {
    precioFinal = precio;
    resultado = `El producto no tiene descuento, el precio final es de ${precioFinal}€ `;
  }

  document.getElementById("h1").textContent = resultado;
}
