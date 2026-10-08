document.addEventListener('DOMContentLoaded', init);

function init() {}

function cambiarCorrecto() {
    var listah2 = document.querySelectorAll('h2');
    
    for (var h of listah2) {
        h.classList.remove('boton-rojo');
        h.classList.add('boton-verde');
    }
}   
function cambiarIncorrecto() {
    var listah2 = document.querySelectorAll('h2');
    
    for (var h of listah2) {
        h.classList.remove('boton-verde');
        h.classList.add('boton-rojo');
    }
}