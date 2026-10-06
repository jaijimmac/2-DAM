document.addEventListener('DOMContentLoaded', init);

function init(){
    var title = document.title;
    console.log(title.toUpperCase())
    cambiarNombre();
    saludar();
    añadirPregunta();
    cambiarApellidos();
}


function cambiarNombre() {
    var nombre = document.getElementById('nombre');
    nombre.value = 'Jaime'

    var apellido = document.getElementById('apellido');
    apellido.value = 'Jiménez'
}

function saludar() {
    var p = document.getElementById('saludo');
    p.textContent = 'Hola. Soy Jaime Jiménez.'
}

function añadirPregunta() {
    var p = document.createElement('p');
    var contexto = document.createTextNode('¿Qué tal estás?');

    p.appendChild(contexto);
    document.getElementsByTagName('body')[0].appendChild(p);
}

function cambiarApellidos() {
    var label = document.querySelector("label[for ='apellido'")
    label.textContent = 'Apellidos'
}