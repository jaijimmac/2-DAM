## EJERCICIO 2

### **1.** Al cargar / aparecen dos peticiones en la terminal. ¿Cuáles son y por qué?

En mi servidor.js no me sale ninguna peticion en mi terminal.

### **2.** magina que tienes que servir un vídeo de 500 MB. ¿Qué problema tendría fs.readFile y qué alternativa de las vistas en clase usarías? 

El problema sería que se saturaría mucho el servidor a la hora de pedir las peticiones de los usuarios. Y lo que se podría hacer es usar flujo de datos con pipe.