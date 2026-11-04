function verEnlace(lista) {
  var enlace = lista.getAttribute("href");
  var input = document.getElementById("input");
  input.value = enlace;
}
