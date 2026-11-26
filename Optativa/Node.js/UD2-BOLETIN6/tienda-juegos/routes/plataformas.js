const express = require("express");
const router = express.Router();

const arrayPlataformas = [
  { id: "p01", nombre: "Switch", fabricante: "Nintendo", anio: 2017 },
  { id: "p02", nombre: "PS5", fabricante: "Sony", anio: 2020 },
  { id: "p03", nombre: "Xbox Series X", fabricante: "Microsoft", anio: 2020 },
  { id: "p04", nombre: "Steam Deck", fabricante: "Valve", anio: 2022 },
];

router.get("/", (req, res) => {
  res.render("plataformas", {
    titulo: "Plataformas",
    plataformas: arrayPlataformas,
  });
});

module.exports = router;
