document.addEventListener("DOMContentLoaded", init);

function init() {
  fechaHoy = new Date();

  const fechaCompleta = fechaHoy.toLocaleDateString("es-ES", {
    weekday: "long",
    day: "numeric",
    month: "long",
    year: "numeric",
  });

  document.getElementById("ciudad").textContent = "Sevilla";
  document.getElementById("gastos").textContent = "3€";
  document.getElementById("fecha").textContent = `${fechaCompleta}`;
}
