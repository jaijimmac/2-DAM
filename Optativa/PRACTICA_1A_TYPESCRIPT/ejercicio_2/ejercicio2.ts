const cadenaTexto: String = 'Esta es la cadena de texto de prueba.'

function contarPalabras(cadena: String) {
    console.log("Número de palabras: ", cadena.split(" ").length);
}

getAvenger("Personaje 1","arco","volar")

function getAvenger(nombre: string, arma: string , poder?: string) {
    if (poder != undefined) {
        console.log("Nombre personaje: ", nombre)
        console.log("Arma personaje: ", arma)
        console.log("Poder personaje: ", poder)
    } else {
        console.log("Nombre personaje: ", nombre)
    }

}