'use strict';

angular.module('chessApp')
    .factory('tournamentService', ['Restangular', function (Restangular) {

        var service = Restangular.service('tournamentService');


        service.getTournaments = function() {
            return Restangular.all('tournament').getList();
        };

        service.getTournament = function(tournamentId) {
            return Restangular.one('tournament', tournamentId).get();
        };

        service.saveTournament = function(tournament) {

            var tournaments =  Restangular.all('tournament');

            console.log('save');

            console.log(tournament);

            tournaments.post(tournament);

        };

        service.updateTournament = function(tournament) {

           Restangular.one('tournament', tournament.id).get().then(function (updateTournament) {
               
               console.log('update service');

               console.log(tournament);

               updateTournament.players = tournament.players;
               updateTournament.gameDtos = tournament.gameDtos;

               console.log('update tourn');

               console.log(updateTournament);

               updateTournament.put();
           });


        };

        console.log("playerService");
        return service;
    }])

    .controller('TournamentCtrl', ['$scope', '$rootScope', 'playerService', 'gameService', 'tournamentService','$stateParams', function ($scope, $rootScope, playerService, gameService, tournamentService, $stateParams) {
        console.log("TournamentCtrl is loaded");

        tournamentService.getTournament($stateParams.tournamentId).then(
            function (response) {

                $scope.tournament = response.plain();

                $scope.games = $scope.tournament.gameDtos;

                $scope.tournamentTable =createTournamentTable($scope.tournament);

                $scope.gridOptions.data = $scope.tournamentTable;

                $scope.gridOptions.columnDefs = generateFields($scope.gridOptions.data);


                console.log($scope.tournament.start);




                $scope.tournamentEnd = function () {

                    var gamesCount = $scope.tournamentTable.length;
                    var allGames = gamesCount*gamesCount - gamesCount;

                    return allGames >= $scope.games.length;


                };

            }
        );




        $scope.gridOptions = {

            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0

        };

        $scope.currentFocused = "";

        $scope.getCurrentFocus = function(){
            var rowCol = $scope.gridApi.cellNav.getFocusedCell();
            if(rowCol !== null) {
                $scope.currentFocused = 'Row Id:' + rowCol.row.entity.id + ' col:' + rowCol.col.colDef.name;

                console.log(rowCol.col.colDef.field);

                $scope.white = rowCol.row.entity.id;
                $scope.blackName = rowCol.col.colDef.name;

                $scope.black = _.filter($scope.tournament.players,function (player) {
                    return player.name == $scope.blackName;
                });

              /*  console.log($scope.black[0]);*/

                $rootScope.$state.go('gamesForPlayers', {playersInTournament:{whiteId: rowCol.row.entity.id , blackId: $scope.black[0].id , tournamentId: $scope.tournament.id} });


            }
        };

        $scope.gridOptions.onRegisterApi = function(gridApi){
            $scope.gridApi = gridApi;
        };

        $scope.addPlayers = function () {

          /*  console.log("Row Selected! " +  $scope.tournamentId);
            $rootScope.tournamentPlayers = $scope.tournament.players;*/
            $rootScope.$state.go('addPlayerToTournament', {tournamentId: $scope.tournament.id});
        };



        $scope.saveTournament = function () {

            console.log("Save tournament! " +  $scope.tournament.id);



            _.forEach($scope.tournamentTable,function (playerScore) {

                    var index = _.findIndex($scope.tournament.players,function (player) {
                        if (playerScore.id == player.id) return player;
                    });

                var player = $scope.tournament.players[index];

                console.log(player);

                player.rating = playerScore['New rating'];

                playerService.updatePlayer(player);

                console.log(player);
            });



        };






        function generateFields(tournamentTable) {
            var columnDefs = [];

            var playerInfo = tournamentTable[0];

            for (var key in playerInfo) {

/*                console.log("key " + key);*/

                if (key === "$$hashKey" || key === 'id' || key == 'resultCount') {

                } else {
                    columnDefs.push({field: key, displayName: key, enableCellEdit: false })
                }
            }
            return columnDefs
        }

        function statistic(playerId, opponentId,tournament) {


            var pointsCount = 0;


            var resultWhite = _.filter(tournament.gameDtos, function (game) {

                if (( game.whiteId == playerId ) && ( game.blackId == opponentId )) return game;


            });

            var resultBlack = _.filter(tournament.gameDtos, function (game) {

                if (( game.whiteId == opponentId ) && ( game.blackId == playerId )) return game;


            });

            /*    console.log("white lengrh");
             console.log(resultWhite.length);*/

            if (resultWhite.length != 0) {


                if (resultWhite[0].result == 'MATE') {

                    if (resultWhite[0].winner == 'White') {


                        pointsCount += 1;
                        /*console.log("white winner");
                         console.log(pointsCount);*/

                    } else {
                        pointsCount += 0;

                    }
                } else {
                    pointsCount = pointsCount + 0.5;

                }
            }

            /*   console.log("Black lengrh");
             console.log(resultBlack.length);*/

            if (resultBlack.length != 0) {

                if ((resultBlack[0].result == 'MATE')) {
                    if (resultBlack[0].winner == 'Black') {

                        pointsCount += 1;

                        /*  console.log("black winner");
                         console.log(pointsCount);*/

                    } else {
                        pointsCount += 0;

                    }
                } else {
                    pointsCount = pointsCount + 0.5;

                }

            }
            /*         console.log("pointsCount");
             console.log(pointsCount);
             */
            var gameStatistic = [];

            if (resultBlack.length != 0 || resultWhite.length != 0) {
                gameStatistic['result'] = pointsCount;
            } else {
                gameStatistic['result'] = '';
            }


            var player = _.find(tournament.players, function (player) {

                return player.id === playerId;

            });
            var opponent = _.find(tournament.players, function (player) {

                return player.id === opponentId;

            });


            gameStatistic['ratingDifference'] = opponent.rating - player.rating;

            return gameStatistic;

        }


        function createTournamentTable(tournament) {

            var tournamentTable = [];



            _(tournament.players).forEach(function (player) {

                var playerScore = {};
                playerScore['id'] = player.id;
                playerScore['Name'] = player.name;
                playerScore['Rating'] = player.rating;

                var resultCount = 0;
                var ratingDifferenceCount = 0;

                _(tournament.players).forEach(function (opponentPlayer) {
                    if (player.name == opponentPlayer.name) {
                        playerScore[opponentPlayer.name] = 'N/A';
                    }
                    else {

                        var gameStatistic = statistic(player.id, opponentPlayer.id,tournament);


                        playerScore[opponentPlayer.name] = gameStatistic.result;

                        if (gameStatistic.result != '') {
                            resultCount += gameStatistic.result;
                            ratingDifferenceCount += gameStatistic.ratingDifference;
                        }

                    }

                });

                var maxScore = ((tournament.players.length - 1) * 2);

                playerScore['resultCount'] = resultCount;
                playerScore['Score'] = "" + resultCount + " / " + maxScore;

                // formula from https://en.wikipedia.org/wiki/Chess_rating_system#CITEREFElo1978
                var K = 40;
                if (player.rating > 2400) {
                    K = 20;
                }


                playerScore['RateUp'] = K / 2 * ( resultCount - (maxScore - resultCount) + ratingDifferenceCount / 400);

                playerScore['New rating'] = player.rating + playerScore.RateUp;

            /*    console.log(playerScore);*/

                tournamentTable.push(playerScore);


            });


            var i = 1;
            _.forEach(_.sortBy(tournamentTable, 'resultCount').reverse(), function (sortedPlayerScore) {
                var currentPlayerScore = _.find(tournamentTable, function (playerScore) {
                    return playerScore.id == sortedPlayerScore.id;
                });
                currentPlayerScore['Place'] = i;
                i++;
            });


            return tournamentTable;
        }


    }]);
