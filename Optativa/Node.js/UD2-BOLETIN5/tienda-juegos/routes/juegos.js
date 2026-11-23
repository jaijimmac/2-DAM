const express = require("express");
const router = express.Router();

const arrayJuegos = [
  {
    id: "j01",
    titulo: "Mario Kart 8 Deluxe",
    plataforma: "Switch",
    precio: 49.99,
  },
  { id: "j02", titulo: "Minecraft", plataforma: "PC", precio: 26.95 },
  { id: "j03", titulo: "EA Sports FC 26", plataforma: "PS5", precio: 69.99 },
  { id: "j04", titulo: "Stardew Valley", plataforma: "PC", precio: 13.99 },
  {
    id: "j05",
    titulo: "Zelda: Tears of the Kingdom",
    plataforma: "Switch",
    precio: 59.99,
  },
];

const juegosBaratos = arrayJuegos.filter((j) => j.precio <= 30);

router.get("/", (req, res) => {
  res.render("juegos", { titulo: "Juegos", juegos: arrayJuegos });
});

router.get("/baratos", (req, res) => {
  res.render("juegos", {
    titulo: "Juegos de menos de 30 €",
    juegos: juegosBaratos,
  });
});
module.exports = router;
