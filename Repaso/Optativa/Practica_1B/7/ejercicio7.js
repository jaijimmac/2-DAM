var tamanno = 20;

function aumentar() {
  const parrafo = document.getElementById("p");
  tamanno += 5;
  parrafo.style.fontSize = `${tamanno}px`;
}

function disminuir() {
  const parrafo = document.getElementById("p");
  tamanno -= 5;
  parrafo.style.fontSize = `${tamanno}px`;
}
