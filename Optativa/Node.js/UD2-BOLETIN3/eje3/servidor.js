const fs = require("fs");
const path = require("path");
const http = require("http");

function enviarArchivo(res, nombreArchivo, contentType) {
  const rutaCompleta = path.join(__dirname, 'recursos', nombreArchivo);

  fs.readFile(rutaCompleta, (err, contenido) => {
    if (err) {
      res.writeHead(500, { 'Content-Type': 'text/plain; charset=utf-8' });
      res.end('Error al cargar el archivo');
      return;
    }

    res.writeHead(200, { 'Content-Type': `${contentType}; charset=utf-8` });
    res.end(contenido);
  });
}

const listadoFirmas = [];

const puerto = 3000;
const servidor = http.createServer((req, res) => {

  if (req.url === '/') {
    enviarArchivo(res, 'formulario.html', 'text/html');

  } 

  if (req.url === "/firmar" && req.method === 'POST') {
    let cuerpo = '' 
    req.on('data', (chunk) => { cuerpo += chunk })
    req.on('end', () => {
        const params = new URLSearchParams(cuerpo)
        const firma = {
            nombre: params.get("nombre"),
            mensaje: params.get("mensaje"),
            fecha: new Date
        }

        listadoFirmas.push(firma);
        res.writeHead(201, { 'Content-Type': 'text/html' })
        res.end(`
            <h3>Gracias por firmar ${firma.nombre} por firmar</h3>
            <a href="/">Formulario</a>
            `)
    })
    
    } else if(req.url === "/firmas" && req.method === 'GET'){
        res.writeHead(200, { 'Content-Type': `text/html; charset=utf-8` });
        res.end(JSON.stringify(listadoFirmas, null, 2));

    } else if(req.url.pathname === '/firmas'){
        const nombre = url.searchParams.get('nombre') || 'desconocido'
        res.writeHead(200, { 'Content-Type': `text/html; charset=utf-8` });

        const firmas = listadoFirmas.filter(firma => firma.nombre == nombre);
        res.end(JSON.stringify(firmas, null, 2))

    } else {
        //res.writeHead(404, { "Content-Type": "text/html; charset=utf-8"});
       
    }

});

servidor.listen(puerto, () => {
  console.log(`Servidor en http://localhost:${puerto}`);
});