## EJERCICIO 1

### **1.** Al recargar, en la terminal aparece a veces GET /favicon.ico. 
### ¿Quién hace esa petición y qué le responde tu servidor?

Esa peticón la hace el servidor porque tiene icono del `<title>` de la página.
Y el servidor le responde con el icono o con vacío.

### **2.** Si escribes la hora directamente como valor dentro del objeto de rutas, siempre sale la misma. ¿Por qué ocurre?

Pues que la hora no cambia porque no está refrescando la hora, sería fija hasta que se se inicie el servidor.