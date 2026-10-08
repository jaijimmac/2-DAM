document.addEventListener('DOMContentLoaded', init);


function init() {
    var ciudad = document.getElementById('ciudad');
    ciudad.textContent = 'Sevilla';

    var gastos = document.getElementById('gastos');
    gastos.textContent = '3€';

    var fecha = document.getElementById('fecha');
    fechaHoy = new Date;

    const fechaCompleta = fechaHoy.toLocaleDateString('es-ES', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });

    fecha.textContent = fechaCompleta;


    var ciudadBuena = prompt('Cambiar ciudad: ')
    ciudad.textContent = ciudadBuena;


}