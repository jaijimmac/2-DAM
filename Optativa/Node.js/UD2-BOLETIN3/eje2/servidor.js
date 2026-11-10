const fs = require("fs");
const path = require("path");
const http = require("http")


function enviarArchivo(res, nombreArchivo, contentType) {
  const rutaCompleta = path.join(__dirname, 'paginas', nombreArchivo);

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

const puerto = 3000;
const servidor = http.createServer((req, res) => {
  if (req.url === '/') {
    enviarArchivo(res, 'index.html', 'text/html');

  } else if (req.url === '/contacto') {
    enviarArchivo(res, 'contacto.html', 'text/html');

  } else if (req.url === '/estilos') {
    enviarArchivo(res, 'estilos.css', 'text/css');

  } else {
    enviarArchivo(res, '404.html', 'text/html');
  }
});

servidor.listen(puerto, () => {
  console.log(`Servidor en http://localhost:${puerto}`);
});