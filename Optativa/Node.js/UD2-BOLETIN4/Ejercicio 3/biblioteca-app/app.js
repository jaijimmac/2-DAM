
const express = require('express')
const app = express()
const PORT = 3000

app.set('view engine', 'ejs')
app.set('views', __dirname + '/views')

app.use(express.static(__dirname + '/public'))

app.use((req, res, next) => {
    const hora = new Date().toLocaleTimeString()
    console.log(`${hora} ${req.method} ${req.url}`)
    next()
})

const libros = [
    { titulo: 'El Quijote', autor: 'Miguel de Cervantes', anio: 1605, disponible: true },
    { titulo: 'Cien años de soledad', autor: 'Gabriel García Márquez', anio: 1967, disponible: false},
    { titulo: 'La sombra del viento', autor: 'Carlos Ruiz Zafón', anio: 2001, disponible: true },
    { titulo: 'Clean Code', autor: 'Robert C. Martin', anio: 2008, disponible: false },
    { titulo: 'El nombre del viento', autor: 'Patrick Rothfuss', anio: 2007, disponible: true }
];



app.get('/libros', (req, res) => {
    res.render('libros', {titulo: "Libros", libros: libros })
})

app.get('/libros/disponibles', (req, res) => {
    const librosDispo = libros.filter(libro => libro.disponible); 
    res.render('libros', {titulo: "Libros", libros: librosDispo })
})

app.get('/404', (req, res) => {
    res.render('404', {titulo: "Not Found"})
})

app.listen(PORT, () => console.log('En el puerto 3000'))
