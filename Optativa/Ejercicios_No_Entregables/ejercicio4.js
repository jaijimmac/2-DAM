document.addEventListener('DOMContentLoaded', init);

function init(){
    var nParrafos = prompt('Introduzca el número de parrafos: ');
    var h1 = document.getElementsByTagName('h1')[0]

    for (i=0; i <= nParrafos.length; i++) {
        let nuevoP = document.createElement('p')
        let contenido = document.createTextNode(`Párrafo ${i+1}`)
        
        nuevoP.appendChild(contenido)
        h1.appendChild(nuevoP);
    }
}

