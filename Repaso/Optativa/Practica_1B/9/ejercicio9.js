function annadir() {
  const ul = document.getElementsByTagName("ul")[0];
  const div = document.createElement("div");
  div.style.display = "flex";

  const producto = prompt("Que producto desea añadir");
  const li = document.createElement("li");
  const textoLi = document.createTextNode(producto);

  li.appendChild(textoLi);
  div.appendChild(li);
  div.appendChild(crearBotones(li));

  ul.appendChild(div);
}

function crearBotones(li) {
  const div = document.createElement("div");
  div.style.display = "flex";

  const botonSi = document.createElement("button");
  botonSi.style.marginLeft = "20px";
  botonSi.textContent = "Si";
  botonSi.onclick = () => si(li);

  const botonNo = document.createElement("button");
  botonNo.style.marginLeft = "20px";
  botonNo.textContent = "No";
  botonNo.onclick = () => no(li);

  div.appendChild(botonSi);
  div.appendChild(botonNo);

  console.log(li);

  return div;
}

function si(li) {
  li.style.color = "green";
}
function no(li) {
  li.style.color = "red";
}
