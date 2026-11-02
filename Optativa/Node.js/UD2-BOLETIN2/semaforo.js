const EventEmitter = require("events");

class Semaforo extends EventEmitter {
  constructor(ms) {
    super();
    this.colores = ["verde", "ámbar", "rojo"];
    this.indice = 0;
    this.color = this.colores[this.indice];

    this.intervalo = setInterval(() => this.cambiar(), ms);
  }

  cambiar() {
    this.indice = (this.indice + 1) % this.colores.length;
    this.color = this.colores[this.indice];

    this.emit("cambio", this.color);
    if (this.color === "rojo") {
      this.emit("rojo");
    }
  }

  parar() {
    clearInterval(this.intervalo);
    this.emit("parado");
  }
}

module.exports = Semaforo;
