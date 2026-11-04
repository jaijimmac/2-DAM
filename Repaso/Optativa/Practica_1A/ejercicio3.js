document.addEventListener("DOMContentLoaded", init);

function init() {
  const n1 = Number.parseInt(prompt("Introduzca el 1º Número."));
  const n2 = Number.parseInt(prompt("Introduzca el 2º Número."));

  const suma = n1 + n2;
  const resta = n2 - n2;
  const multi = n1 * n2;
  if (n2 == 0) {
    alert("No se puede dividir entre cero");
  } else {
    const divi = n1 / n2;
    alert(
      `Suma: ${suma}\nResta: ${resta}\nMultiplicación: ${multi}\nDivisión: ${divi}`,
    );
  }
}
