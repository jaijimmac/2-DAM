document.addEventListener('DOMContentLoaded', init);
var mostrado;

function init(){
    mostrado = true
}

function mostrarContenido(){
    var parrafos = document.querySelectorAll('.mostrable');

    if(mostrado) {
        parrafos.forEach(p => {
            p.classList.remove('oculto')
            mostrado = false
        })

        document.getElementsByTagName('span')[0].textContent = 'Mostrar menos'
    }else {
        parrafos.forEach(p => {
            p.classList.add('oculto')
            mostrado = true
        })

        document.getElementsByTagName('span')[0].textContent = 'Mostrar más'
    }
}

