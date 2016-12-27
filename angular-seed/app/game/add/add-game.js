'use strict';

angular.module('chessApp')

    .controller('AddEditGameCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams', 'gameService', 'playerService', 'tournamentService',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams, gameService, playerService, tournamentService) {
            console.log("AddEditGameCtrl is loaded");


            playerService.getPlayers().then(function (response) {

                $scope.players = response.plain();


                if ($stateParams.playersInTournament) {
                    console.log("in game");
                    if ($stateParams.playersInTournament.whiteId) {

                        console.log("whiteId");

                        $scope.players = _.filter($scope.players, function (player) {
                            console.log();
                            if ((player.id == $stateParams.playersInTournament.whiteId) || (player.id == $stateParams.playersInTournament.blackId)) return player
                        });
                        console.log($scope.players);
                    }
                }

                $scope.whiteChanged();
            });


            $scope.whiteChanged = function () {

                $scope.blackPlayers = _.filter($scope.players, function (player) {
                    if (player.id != $scope.gameModel.whiteId) {
                        return player;
                    }
                });
            };


            $scope.result = ['MATE', 'DRAW', 'STALEMATE'];

            $scope.playersInGame = ['White', 'Black'];


            $scope.gameModel = {
                gameId: null,
                whiteId: null,
                blackId: null,
                winner: "NONE",
                result: '',
                start: '',
                end: ''

            };

            $scope.variable = 'all ok';


            /*  console.log($stateParams.playersInGame.blackId);*/

            if ($stateParams.playersInTournament) {

                if ($stateParams.playersInTournament.gameId) {

                  /*  $scope.gameId = $stateParams.gameId;
                    console.log("Editing game id = " + $scope.gameId);*/

                    gameService.getGame($stateParams.playersInTournament.gameId).then(function (response) {
                        $scope.gameModel = response.plain();

                    });


                }
            }


            function prepareGame() {

                var winner = function () {
                    if (($scope.gameModel.result == "DRAW")
                        || ($scope.gameModel.result == "STALEMATE")) {
                        return "NONE";
                    }
                    else {
                        return $scope.gameModel.winner;
                    }
                };


                return {
                    id: $scope.gameModel.id,
                    whiteId: $scope.gameModel.whiteId,
                    blackId: $scope.gameModel.blackId,
                    winner: winner(),
                    result: $scope.gameModel.result,
                    start: $scope.gameModel.start,
                    end: $scope.gameModel.end
                }
            }

            function yes() {


                var game = {};
                if ($stateParams.playersInTournament.gameId) {
                    game = prepareGame();
                    gameService.updateGame(game);

                } else {

                    game = prepareGame();
                    gameService.saveGame(game);

                    tournamentService.getTournament($stateParams.playersInTournament.tournamentId).then(function (response) {

                        var tournament = response.plain();


                        var games = tournament.gameDtos;

                        games.push(game);

                        tournament.gameDtos = games;

                        console.log(tournament);

                        tournamentService.updateTournament(tournament);
                    });


                }
                $state.go('gamesForPlayers', {
                    playersInTournament: {
                        whiteId: $stateParams.playersInTournament.whiteId,
                        blackId: $stateParams.playersInTournament.blackId,
                        tournamentId: $stateParams.playersInTournament.tournamentId
                    }
                });

            }

            function no() {
                console.log("no");
            }

            $scope.saveConfirm = function (ev) {


                var confirm = $mdDialog.confirm()
                    .title('Save Changes?')
                    .targetEvent(ev)
                    .ok('Yes')
                    .cancel('No');


                if ($scope.gameForm.$valid) {
                    $mdDialog.show(confirm).then(function () {
                        yes();
                    }, function () {
                        no();
                    });
                } else {
                    validateForm();
                }
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

            $scope.cancel = function (ev) {
                $scope.gameForm.$dirty ? showConfirm(ev) : $state.go('gamesForPlayers',
                    {
                        playersInTournament: {
                            whiteId: $stateParams.playersInTournament.whiteId,
                            blackId: $stateParams.playersInTournament.blackId,
                            tournamentId: $stateParams.playersInTournament.tournamentId
                        }
                    });
            };

            function showConfirm(ev) {
                // Appending dialog to document.body to cover sidenav in docs app
                var confirm = $mdDialog.confirm()
                    .title('Are you sure you want to leave this page?')
                    .textContent('The current page contains unsaved information that will be lost if you leave.')
                    .targetEvent(ev)
                    .ok('Accept')
                    .cancel('Cancel');
                $mdDialog.show(confirm).then(function () {
                    $state.go('gamesForPlayers', {
                        playersInTournament: {
                            whiteId: $stateParams.playersInTournament.whiteId,
                            blackId: $stateParams.playersInTournament.blackId,
                            tournamentId: $stateParams.playersInTournament.tournamentId
                        }
                    });
                });
            }

            $scope.enableWinnerSelect = function () {

                return $scope.gameModel.result != "MATE";

            }

        }]);