document.addEventListener("DOMContentLoaded", init);

function init() {
    var inputNombre = prompt('Ingrese su nombre: ');
    
    document.getElementById("resultado").textContent = inputNombre;
    document.getElementById("nLetras").textContent = inputNombre.length;
}
