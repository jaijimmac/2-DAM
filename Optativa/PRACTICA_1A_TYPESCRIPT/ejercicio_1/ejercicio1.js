"use strict";
var posicion = 1;
var playerName = "Jugador 1";
var finishedGame = true;
var ranking = [posicion, playerName, finishedGame];
var hallOfRanking = [];
var posicion2 = 2;
var playerName2 = "Jugador 2";
var finishedGame2 = false;
var ranking2 = [posicion2, playerName2, finishedGame2];
var hallOfRanking = [];
hallOfRanking.push(ranking);
hallOfRanking.push(ranking2);
printRankings(hallOfRanking);
function printRankings(lista) {
    lista.forEach(ranking => {
        console.log(ranking);
    });
}
//# sourceMappingURL=ejercicio1.js.map