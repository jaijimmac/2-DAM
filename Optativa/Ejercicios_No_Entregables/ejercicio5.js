document.addEventListener("DOMContentLoaded", init)
var p;

function init() {
    p = document.getElementById('p');
}

function crearTextoAceptar () {
    p.textContent = 'Esta Usted Aceptando'
}

function crearTextoCancelar () {
    p.textContent = 'Esta Usted Cancelando'
}