## Ejercicio 1.

**a) ¿En qué orden se mostrarán las letras A, B, C y D? Razona la respuesta utilizando la pila de
llamadas, la cola de callbacks y el event loop.**

Se mostrarán en el orden de  **A**, **D**, **B** y **C**.
He puesto ese orden ya que el **A** sale en el primer console.log, el **D** porque no tiene que tener una espera, luego el **B** porque tiene el timeoit y luego ya el **C**. cuando encuentre el archivo y lo lee.
### 

**b) Ejecútalo y comprueba tu respuesta. ¿Qué instrucción es bloqueante? ¿Cuáles son no
bloqueantes? Explica la diferencia.**

La opcion **A** y la opción **D** se ejecutan sin esperar a nada , y luego la **B** y **C** tienene que esperar a leer el archivo.

###

**c) Cambia __filename por 'no-existe.txt' en la llamada a readFile y añade al callback la
gestión del error con el parámetro err (convención «error primero»). ¿Qué muestra ahora? ¿Se
detiene el programa?**

Se detiene el programa y sale el siguiente error:

###

**d) Añade al final console.log(process.version) y console.log(document.title). ¿Qué
ocurre con cada una? Relaciónalo con las diferencias entre JavaScript en el navegador y en
Node.js.**
