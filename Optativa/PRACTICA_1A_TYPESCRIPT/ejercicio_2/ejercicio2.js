"use strict";
const cadenaTexto = 'Esta es la cadena de texto de prueba.';
function contarPalabras(cadena) {
    console.log("Número de palabras: ", cadena.split(" ").length);
}
getAvenger("Personaje 1", "arco", "volar");
function getAvenger(nombre, arma, poder) {
    if (poder != undefined) {
        console.log("Nombre personaje: ", nombre);
        console.log("Arma personaje: ", arma);
        console.log("Poder personaje: ", poder);
    }
    else {
        console.log("Nombre personaje: ", nombre);
    }
}
//# sourceMappingURL=ejercicio2.js.map