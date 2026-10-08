document.addEventListener('DOMContentLoaded', init);


function init(){
    var ciudadesGratis = ["Sevilla", "Madrid","Valencia", "Barcelona"];
    var ciudadesGastos = ["Cantabria", "Pontevedra", "Toledo", "Segovia"];

    var ciudadUsuario = prompt('Ingresa tu ciudad: ') 

    
    if(ciudadesGastos.some(ciudad => ciudad == ciudadUsuario)){
        var gastos = prompt('Ingrese la cantidad de gastos de envio: ')
        document.getElementById('gastos').textContent = gastos
    }


    if(ciudadesGratis.some(ciudad => ciudad == ciudadUsuario)){
        var etiqueta = document.createElement('h2');
        var contenido = document.createTextNode('En esta ciudad los gastos de envio son gratuitos');
        etiqueta.appendChild(contenido);
        document.getElementById('gastos').textContent = 0
        document.getElementsByTagName('body')[0].appendChild(etiqueta)
    }

    if(!ciudadesGastos.includes(ciudadUsuario) && !ciudadesGratis.includes(ciudadUsuario)){
        var etiqueta = document.createElement('h2');
        var contenido = document.createTextNode('En esta ciudad NO se hacen envios');
        etiqueta.appendChild(contenido);
        document.getElementById('gastos').textContent = 0
        document.getElementsByTagName('body')[0].appendChild(etiqueta)
    }


    var fechaHoy = new Date;

    const fechaCompleta = fechaHoy.toLocaleDateString('es-ES', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });

    document.getElementById('ciudad').textContent = ciudadUsuario;
    document.getElementById('fecha').textContent = fechaCompleta

}