const http = require('http')

const servidor = http.createServer((req, res) => {
    const url = new URL(req.url,'http://localhost')
    res.writeHead(200, { 'Content-Type': 'text/html'})

    if (url.pathname === '/saludo') {
        const nombre = url.searchParams.get('nombre')

        if(nombre) {
            res.end(`<h1>Hola ${nombre}</h1>`)
        } else {
            res.end('<h1>Ruta Desconocido</h1>')
        }
    }else {
        res.statusCode = 404;
        res.end("<h3>Erorr 404<h3>")
    }
});

servidor.listen(3000)


