'use strict';

angular.module('chessApp')
    .factory('tournamentService', ['Restangular', function (Restangular) {

        var service = Restangular.service('tournamentService');

        var currentTournament;

        service.getTournaments = function () {
            console.log('get all tournaments');
            return Restangular.all('tournament').getList();
        };

        service.getTournament = function (tournamentId) {
            console.log('get tournament = ' + tournamentId);
            return Restangular.one('tournament', tournamentId).get();
        };

        service.saveTournament = function (tournament) {

            var tournaments = Restangular.all('tournament');

            console.log('save tournament');


            tournaments.post(tournament);

        };

        service.updateTournament = function (tournament) {

            Restangular.one('tournament', tournament.id).get().then(function (updateTournament) {


                updateTournament.players = tournament.players;
                updateTournament.gameDtos = tournament.gameDtos;
                updateTournament.initialRatings = tournament.initialRatings;
                updateTournament.tournamentFinished = tournament.tournamentFinished;
                updateTournament.end = tournament.end;

                console.log('update tourn');


                updateTournament.put();

            });


        };

        service.setCurrentTournament = function (tournamentId) {
            currentTournament = tournamentId;
        };

        service.getCurrentTournament = function () {
            return currentTournament;
        };
        console.log("tournament Service");
        return service;
    }])

    .controller('TournamentCtrl', ['$scope', '$rootScope', 'playerService', 'gameService', 'tournamentService', '$stateParams','$mdDialog', '$mdToast', function ($scope, $rootScope, playerService, gameService, tournamentService, $stateParams,$mdDialog,$mdToast) {
        console.log("TournamentCtrl is loaded");

        tournamentService.getTournament($stateParams.tournamentId).then(
            function (response) {

                $scope.tournament = response.plain();

                $scope.games = $scope.tournament.gameDtos;

                $scope.tournamentTable = createTournamentTable($scope.tournament);

                $scope.gridOptions.data = $scope.tournamentTable;

                $scope.gridOptions.columnDefs = generateFields($scope.gridOptions.data);

                var playersCount = $scope.tournament.players.length;

                $scope.gamesAmountInTournament = playersCount * playersCount - playersCount;


                $scope.tournamentEnd = function () {

                    return !($scope.gamesAmountInTournament == $scope.games.length);


                };

            }
        );


        $scope.gridOptions = {

            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0

        };


        $scope.getCurrentFocus = function () {
            var rowCol = $scope.gridApi.cellNav.getFocusedCell();
            if (rowCol !== null) {

                $scope.white = rowCol.row.entity.id;
                $scope.blackUserName = rowCol.col.colDef.name;

                $scope.black = _.filter($scope.tournament.players, function (player) {
                    return player.username == $scope.blackUserName;
                });


                $rootScope.$state.go('gamesForPlayers', {
                    playersInTournament: {
                        whiteId: rowCol.row.entity.id,
                        blackId: $scope.black[0].id,
                        tournamentId: $scope.tournament.id
                    }
                });


            }
        };

        $scope.gridOptions.onRegisterApi = function (gridApi) {
            $scope.gridApi = gridApi;
        };
        $scope.disableEditGames = function () {
            var rowCol = $scope.gridApi.cellNav.getFocusedCell();
            if (rowCol !== null) {

                $scope.white = rowCol.row.entity.id;
                $scope.blackName = rowCol.col.colDef.name;

                $scope.black = _.filter($scope.tournament.players, function (player) {
                    return player.name == $scope.blackName;
                });

                if ($scope.black[0]) {
                    return false
                } else {
                    return true;
                }
            }
        };

        $scope.editTournamentDescription = function () {


            $rootScope.$state.go('edittournament', {tournamentId: $scope.tournament.id});
        };


        $scope.addPlayers = function () {


            $rootScope.$state.go('addPlayerToTournament', {tournamentId: $scope.tournament.id});
        };


        $scope.saveTournament = function () {




        };


        function generateFields(tournamentTable) {
            var columnDefs = [];

            var playerInfo = tournamentTable[0];

            for (var key in playerInfo) {

                /*                console.log("key " + key);*/

                if (key === "$$hashKey" || key === 'id' || key == 'resultCount' || key === 'newRateDecimal') {

                } else {
                    columnDefs.push({field: key, displayName: key, enableCellEdit: false})
                }
            }
            return columnDefs
        }

        function statistic(playerId, opponentId, tournament) {


            var pointsCount = 0;


            var resultWhite = _.filter(tournament.gameDtos, function (game) {

                if (( game.whiteId == playerId ) && ( game.blackId == opponentId )) return game;


            });

            var resultBlack = _.filter(tournament.gameDtos, function (game) {

                if (( game.whiteId == opponentId ) && ( game.blackId == playerId )) return game;


            });


            if (resultWhite.length != 0) {


                if (resultWhite[0].result == 'MATE') {

                    if (resultWhite[0].winner == 'White') {


                        pointsCount += 1;


                    } else {
                        pointsCount += 0;

                    }
                } else {
                    pointsCount = pointsCount + 0.5;

                }
            }


            if (resultBlack.length != 0) {

                if ((resultBlack[0].result == 'MATE')) {
                    if (resultBlack[0].winner == 'Black') {

                        pointsCount += 1;


                    } else {
                        pointsCount += 0;

                    }
                } else {
                    pointsCount = pointsCount + 0.5;

                }

            }

            var gameStatistic = [];

            if (resultBlack.length != 0 || resultWhite.length != 0) {
                gameStatistic['result'] = pointsCount;
            } else {
                gameStatistic['result'] = '';
            }


            var player;
            var opponent;

            if (tournament.tournamentFinished) {

                player = _.find(tournament.initialRatings, function (playerInitRate) {

                    return playerInitRate.playerId === playerId;

                });
                opponent = _.find(tournament.initialRatings, function (playerInitRate) {

                    return playerInitRate.playerId === opponentId;

                });

            } else {
                player = _.find(tournament.players, function (player) {

                    return player.id === playerId;

                });
                opponent = _.find(tournament.players, function (player) {

                    return player.id === opponentId;

                });
            }
            gameStatistic['ratingDifference'] = opponent.rating - player.rating;


            return gameStatistic;

        }


        function createTournamentTable(tournament) {

            var tournamentTable = [];


            _(tournament.players).forEach(function (player) {

                var playerScore = {};
                playerScore['id'] = player.id;
                playerScore['Username'] = player.username;

                if (tournament.tournamentFinished) {


                    var rateIndex = _.findIndex(tournament.initialRatings, function (rate) {
                        return rate.playerId == player.id;
                    });


                    playerScore['Rating'] = tournament.initialRatings[rateIndex].rating;


                } else {
                    playerScore['Rating'] = player.rating;
                }


                var resultCount = 0;
                var ratingDifferenceCount = 0;

                _(tournament.players).forEach(function (opponentPlayer) {
                    if (player.name == opponentPlayer.name) {
                        playerScore[opponentPlayer.name] = 'N/A';
                    }
                    else {

                        var gameStatistic = statistic(player.id, opponentPlayer.id, tournament);


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

                var rateUp = K / 2 * ( resultCount - (maxScore - resultCount) + ratingDifferenceCount / 400);

                playerScore['RateUp'] = _.round(rateUp);


                playerScore['New rating'] = _.round(playerScore.Rating + rateUp);

                playerScore['newRateDecimal'] = playerScore.Rating + rateUp;

                /*    console.log(playerScore); */

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


        function yes() {
            console.log("yes");

            console.log("Save tournament! " + $scope.tournament.id);

            var initialRatings = [];
            _.forEach($scope.tournament.players, function (player) {

                initialRatings.push({
                    id: null,
                    playerId: player.id,
                    rating: player.rating
                });

            });


            _.forEach($scope.tournamentTable, function (playerScore) {

                var index = _.findIndex($scope.tournament.players, function (player) {
                    if (playerScore.id == player.id) return player;
                });


                var player = $scope.tournament.players[index];

                /*   console.log(player);*/

                player.rating = playerScore['newRateDecimal'];

                playerService.updatePlayer(player);

                console.log(player);
            });


            $scope.tournament['initialRatings'] = initialRatings;
            $scope.tournament.tournamentFinished = true;


            function pad(number) {
                if (number < 10) {
                    return '0' + number;
                }
                return number;
            }

            var date = new Date();


            $scope.tournament.end = date.getFullYear() +
            '-' + pad(date.getMonth() + 1) +
            '-' + pad(date.getDate()) +
            'T' + pad(date.getHours()) +
            ':' + pad(date.getMinutes());


            console.log($scope.tournament);

            tournamentService.updateTournament($scope.tournament);

        }

        function no() {
            console.log("no");
        }

        $scope.saveConfirm = function (ev) {
            console.log("save confirm");

            var confirm = $mdDialog.confirm()
                .title('Player rating will be updated, after saving tournament will be read only')
                .targetEvent(ev)
                .ok('Yes Save')
                .cancel('No');


                $mdDialog.show(confirm).then(function () {
                    yes();
                }, function () {
                    no();
                });

        };

        function validateForm() {
            var last = {
                bottom: false,
                top: true,
                left: false,
                right: true
            };


            $mdToast.show(
                $mdToast.simple()
                    .textContent('Please fill the required fields!')
                    .position(last)
                    .hideDelay(3000)
            );
        }






    }]);
