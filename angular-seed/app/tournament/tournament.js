'use strict';

angular.module('chessApp')

    .controller('TournamentCtrl', ['$scope', '$rootScope', 'playerService', 'gameService',  function ($scope, $rootScope, playerService, gameService) {
        console.log("TournamentCtrl is loaded");


        playerService.getPlayers().then(function (response) {
            $scope.players = response.plain();

            gameService.getGames().then(function (response) {

                $scope.games = response.plain();

                $scope.tourn = createTournament();

                $scope.gridOptions.data = $scope.tourn;


                $scope.gridOptions.columnDefs = generateFields();

                console.log(statistic(1100, 1101));


            });


        });


        $scope.gridOptions = {
            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    var msg = row.entity.id;
                    console.log("Row Selected! " + msg);
                    $scope.selectedPlayerId = msg;

                });

            }

        };


        function generateFields() {
            var columnDefs = [];

            var playerInfo = $scope.tourn[0];

            for (var key in playerInfo) {

                if (key === "$$hashKey") {

                } else {
                    columnDefs.push({field: key, displayName: key})
                }


            }

            return columnDefs

        }


        function statistic(playerId, opponentId) {

            var gameStatistic = [];
            gameStatistic['result'] = "";
            gameStatistic['score'] = "";

            var resultWhite = _.filter($scope.games, function (game) {

                if (( game.whiteId == playerId ) && ( game.blackId == opponentId )) return game;


            });

            var resultBlack = _.filter($scope.games, function (game) {

                if (( game.whiteId == opponentId ) && ( game.blackId == playerId )) return game;


            });

            console.log(resultWhite[0].result);
            var result = resultWhite[0].result;

            if (resultWhite[0].result == 'MATE') {


                if (resultWhite.winnerId == playerId) {

                    gameStatistic['result'] = 1;
                } else {
                    gameStatistic['result'] = 0;
                }
            } else {

                gameStatistic['result'] = 0.5;
            }


            if ( (resultBlack.result == 'MATE')) {
                if (resultBlack.winnerId == playerId) {
                    gameStatistic['result'] = 1;
                } else {
                    gameStatistic['result'] = 0;
                }
            } else {
                console.log("here ");
                gameStatistic['result'] = 0.5;
            }


            console.log(resultWhite);
            console.log(resultBlack);
            return gameStatistic;

        }

        function gameStat(playerId, opponentId) {
            var gameStatistic = [];
            gameStatistic['result'] = "";
            gameStatistic['score'] = "";


            for (var k = 0; k < $scope.games.length; k++) {

                var game = $scope.games[k];

                if ((playerId == game.whiteId) &&
                    (opponentId == game.blackId)
                ) {


                    if (game.result == "STALEMATE" || game.result == "DRAW") {
                        gameStatistic['result'] = 0.5;
                    } else {
                        if (playerId == game.winnerId) {
                            gameStatistic['result'] = 1;
                        } else {
                            gameStatistic['result'] = 0;
                        }
                    }

                    gameStatistic['score'] = game.whiteScore;
                }
                else if ((opponentId == game.whiteId) &&
                    (playerId == game.blackId)
                ) {

                    if (game.result == "STALEMATE" || game.result == "DRAW") {
                        gameStatistic['result'] = 0.5;
                    } else {
                        if (playerId == game.winnerId) {
                            gameStatistic['result'] = 1;
                        } else {
                            gameStatistic['result'] = 0;
                        }
                    }
                    gameStatistic['score'] = game.blackScore;
                }

            }
            return gameStatistic;


        }

        function createTournament() {

            var tournament = [];

            _($scope.players).forEach(function (player) {

                var playerScore = {};

                playerScore['Name'] = player.name;
                playerScore['Rating'] = player.rating;

                var resultCount = 0;
                var ratingCount = 0;

                _($scope.players).forEach(function (opponentPlayer) {
                    if (player.name == opponentPlayer.name) {
                        playerScore[opponentPlayer.name] = 'N/A';
                    }
                    else {

                        var gameStatistic = gameStat(player.id, opponentPlayer.id);


                        playerScore[opponentPlayer.name] = gameStatistic.result;

                        if (gameStatistic.result != '') {
                            resultCount += gameStatistic.result;
                            ratingCount += gameStatistic.score;
                        }

                    }

                });


                playerScore['Score'] = "" + resultCount + " / " + $scope.players.length;

                playerScore['Rate+'] = ratingCount;

                playerScore['New rating'] = player.rating + ratingCount;

                playerScore['Place'] = 1;

                tournament.push(playerScore);


            });

            return tournament;
        }


    }]);
