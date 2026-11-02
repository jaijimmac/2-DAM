const http = require("http");
const os = require("os");
const crypto = require("crypto");

const puerto = process.env.PORT || 3000;

const servidor = http.createServer((req, res) => {
  console.log(req.method, req.url);

  if (req.url === "/") {
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(`
      <h1>Servidor de 2º DAM</h1>
      <p>Prueba también <a href="/info">/info</a> y <a href="/dado">/dado</a></p>
    `);
  } else if (req.url === "/info") {
    const info = {
      sistema: os.type(),
      cpus: os.cpus().length,
      node: process.version,
    };
    res.writeHead(200, { "Content-Type": "application/json" });
    res.end(JSON.stringify(info));
  } else if (req.url === "/dado") {
    const numero = crypto.randomInt(1, 7);
    res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
    res.end(`<h1>Has sacado un ${numero}</h1>`);
  } else {
    res.writeHead(404, { "Content-Type": "text/html; charset=utf-8" });
    res.end("404 - Página no encontrada");
  }
});

servidor.listen(puerto, () => {
  console.log(`Servidor en http://localhost:${puerto}`);
});
