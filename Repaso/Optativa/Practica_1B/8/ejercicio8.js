function annadir() {
  const ul = document.getElementsByTagName("ul")[0];

  const producto = prompt("Que producto desea añadir");
  const li = document.createElement("li");
  const textoLi = document.createTextNode(producto);

  li.appendChild(textoLi);
  ul.appendChild(li);
}
