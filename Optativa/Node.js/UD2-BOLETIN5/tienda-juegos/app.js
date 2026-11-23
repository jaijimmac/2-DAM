const express = require('express') 
const app = express() 
const PORT = 3000 
  
app.set('view engine', 'ejs') 
app.set('views', __dirname + '/views') 
app.use(express.static(__dirname + '/public')) 

app.use('/', require('./routes/web'))
app.use('/juegos', require('./routes/juegos'))

app.use((req, res) => { 
  res.status(404).render('404', { titulo: 'Página no encontrada' }) 
}) 
  
app.listen(PORT, () => { 
  console.log(`Servidor en http://localhost:${PORT}`) 
}) 