const fs = require("fs");
const path = require("path");
const http = require("http")

const puerto = 3000;

const servidor = http.createServer((req, res) => {
    if(req.url === "/"){
        res.writeHead(200, { "Content-Type": "text/html; charset=utf-8"});
        res.end(`
            <h1>Inicio</h1>
            <a href="/sobre-mi ">Sobre Mí</a>
            <a href="/aficiones">Aficiones</a>
            <a href="/hora">Hora de hoy</a>
        `);
    } else if(req.url === "/sobre-mi") {
        res.writeHead(200, { "Content-Type": "text/html; charset=utf-8"});
        res.end(`
            <h1>Sobre Mi</h1
            <p>Mi nombre es Jaime Jiménez</p>
        `);

    } else if(req.url === "/aficiones") {
        res.writeHead(200, { "Content-Type": "text/html; charset=utf-8"});
        res.end(`
            <h1>Aficiones</h1
            <ul>
                <li>Música</li>
                <li>Deporte</li>
                <li>Videojuegos</li>
            </ul>
        `);

    } else if(req.url === "/hora") {
        const hora = new Date;
        res.writeHead(200, { "Content-Type": "text/html; charset=utf-8"});
        res.end(`
            <h1>Hora</h1
            <p>Son las ${hora.toLocaleTimeString()}</p>
        `);

    } else {
        res.writeHead(404, { "Content-Type": "text/html; charset=utf-8"});
        res.end(`
                <h2>Error 404: la ruta no existe» y un enlace a ${req.url}
            `);
    }
});


servidor.listen(puerto, () => {
  console.log(`Servidor en http://localhost:${puerto}`);
});