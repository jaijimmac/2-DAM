document.addEventListener('DOMContentLoaded', init);


function init(){
    var ciudadesGratis = ["Sevilla", "Madrid","Valencia", "Barcelona"];
    var ciudadesGastos = ["Cantabria", "Pontevedra", "Toledo", "Segovia"];

    var ciudadUsuario = prompt('Ingresa tu ciudad: ') 
    var gastos;


    for(c in ciudadesGastos) {
        if(c == ciudadUsuario) {
            gastos = prompt('Ingrese la cantidad de gastos de envio: ')
        }
    }

    for(c in ciudadesGratis) {
        if(c == ciudadUsuario) {
            var etiqueta = document.createElement('h2');
            var contenido = document.createTextNode('En esta ciudad los gastos de envio son gratuitos');

            etiqueta.appendChild(contenido);
            document.getElementsByTagName('body')[0].appendChild(etiqueta);
        }
    }

    var fechaHoy = new Date;

    const fechaCompleta = fechaHoy.toLocaleDateString('es-ES', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });

    document.getElementById('ciudad').textContent = ciudadUsuario;
    document.getElementById('gastos').textContent = gastos
    document.getElementById('fecha').textContent = fechaCompleta

}