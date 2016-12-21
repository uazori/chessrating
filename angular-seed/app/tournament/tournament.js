'use strict';

angular.module('chessApp')

    .controller('TournamentCtrl', ['$scope', '$rootScope', 'playerService', 'gameService', function ($scope, $rootScope, playerService, gameService) {
        console.log("TournamentCtrl is loaded");


        playerService.getPlayers().then(function (response) {
            $scope.players = response.plain();

            gameService.getGames().then(function (response) {

                $scope.games = response.plain();
                $scope.tourn = createTournament();

                $scope.gridOptions.data = $scope.tourn;


                $scope.gridOptions.columnDefs = generateFields();
                /*    console.log("stat");
                 console.log(statistic(1099,1137));*/

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

                if (key === "$$hashKey" || key === 'id' || key == 'resultCount') {

                } else {
                    columnDefs.push({field: key, displayName: key})
                }
            }
            return columnDefs
        }

        function statistic(playerId, opponentId) {


            var pointsCount = 0;


            var resultWhite = _.filter($scope.games, function (game) {

                if (( game.whiteId == playerId ) && ( game.blackId == opponentId )) return game;


            });

            var resultBlack = _.filter($scope.games, function (game) {

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


            var player = _.find($scope.players, function (player) {

                return player.id === playerId;

            });
            var opponent = _.find($scope.players, function (player) {

                return player.id === opponentId;

            });


            gameStatistic['ratingDifference'] = opponent.rating - player.rating;

            return gameStatistic;

        }


        function createTournament() {

            var tournament = [];

            _($scope.players).forEach(function (player) {

                var playerScore = {};
                playerScore['id'] = player.id;
                playerScore['Name'] = player.name;
                playerScore['Rating'] = player.rating;

                var resultCount = 0;
                var ratingDifferenceCount = 0;

                _($scope.players).forEach(function (opponentPlayer) {
                    if (player.name == opponentPlayer.name) {
                        playerScore[opponentPlayer.name] = 'N/A';
                    }
                    else {

                        var gameStatistic = statistic(player.id, opponentPlayer.id);


                        playerScore[opponentPlayer.name] = gameStatistic.result;

                        if (gameStatistic.result != '') {
                            resultCount += gameStatistic.result;
                            ratingDifferenceCount += gameStatistic.ratingDifference;
                        }

                    }

                });

                var maxScore = (($scope.players.length - 1) * 2);

                playerScore['resultCount'] = resultCount;
                playerScore['Score'] = "" + resultCount + " / " + maxScore;

                // formula from https://en.wikipedia.org/wiki/Chess_rating_system#CITEREFElo1978
                var K = 40;
                if (player.rating > 2400) {
                    K = 20;
                }


                playerScore['RateUp'] = K / 2 * ( resultCount - (maxScore - resultCount) + ratingDifferenceCount / 400);

                playerScore['New rating'] = player.rating + playerScore.RateUp;


                tournament.push(playerScore);


            });


            var i = 1;
            _.forEach(_.sortBy(tournament, 'resultCount').reverse(), function (sortedPlayerScore) {
                var currentPlayerScore = _.find(tournament, function (playerScore) {
                    return playerScore.id == sortedPlayerScore.id;
                });
                currentPlayerScore['Place'] = i;
                i++;
            });


            return tournament;
        }


    }]);
