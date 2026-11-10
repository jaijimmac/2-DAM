
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

const listadoHorarios = []

const h1 = new Object();
h1.dia = "Lunes";
h1.horario = "08:00 - 14:30";
listadoHorarios.push(h1);

const h2 = new Object();
h2.dia = "Martes";
h2.horario = "08:00 - 14:30";
listadoHorarios.push(h2);

const h3 = new Object();
h3.dia = "Miércoles";
h3.horario = "08:00 - 14:30";
listadoHorarios.push(h3);

const h4 = new Object();
h4.dia = "Jueves";
h4.horario = "08:00 - 14:30";
listadoHorarios.push(h4);

const h5 = new Object();
h5.dia = "Viernes";
h5.horario = "08:00 - 14:30";
listadoHorarios.push(h5);



app.get('/', (req, res) => {
    res.render('index', {titulo: "Inicio"})
})

app.get('/horario', (req, res) => {
    res.render('horario', {titulo: "Horario", listadoHorarios})
})

app.get('/404', (req, res) => {
    res.render('404', {titulo: "Not Found"})
})

app.listen(PORT, () => console.log('En el puerto 3000'))
