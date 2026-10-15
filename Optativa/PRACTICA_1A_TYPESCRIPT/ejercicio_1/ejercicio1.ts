type Ranking = [number, string, boolean]

var posicion: number = 1;
var playerName: string = "Jugador 1";
var finishedGame: boolean = true;
var ranking: Ranking = [posicion, playerName, finishedGame];
var hallOfRanking: Ranking[] = [];

var posicion2: number = 2;
var playerName2: string = "Jugador 2";
var finishedGame2: boolean = false;
var ranking2: Ranking = [posicion2, playerName2, finishedGame2];
var hallOfRanking: Ranking[] = [];


hallOfRanking.push(ranking);
hallOfRanking.push(ranking2);

printRankings(hallOfRanking)


function printRankings(lista: Ranking[]) {

    lista.forEach(ranking =>{
        console.log(ranking)
    })
}
