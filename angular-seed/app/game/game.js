'use strict';

angular.module('chessApp')

    .factory('gameService', ['Restangular', function (Restangular) {


        var service = Restangular.service('gameService');

        service.getGames = function () {
            return Restangular.all('game').getList();
        };

        service.getGame = function (gameId) {
            return Restangular.one('game', gameId).get();
        };

        service.saveGame = function (game) {

            var games = Restangular.all('game');

            games.post(game);


            console.log("Game Service save Game ");


        };
        service.updateGame = function (game) {
            console.log("game service update");
            var updatedGame = Restangular.one('game', game.id);

            updatedGame.whiteId = game.whiteId;
            updatedGame.blackId = game.blackId;
            updatedGame.winner = game.winner;
            updatedGame.result = game.result;
            updatedGame.start = game.start;
            updatedGame.end = game.end;
            updatedGame.put().then(function () {
                console.log('game save');
            });
        };

        console.log("gameService");
        return service;
    }])


    .controller('GameCtrl', ['$scope', '$rootScope', '$http', '$q', '$interval', 'gameService', 'playerService', 'tournamentService', '$stateParams', function ($scope, $rootScope, $http, $q, $interval, gameService, playerService, tournamentService, $stateParams) {
        console.log("GameCtrl is loaded");

        if ($stateParams.playersInTournament) {
            $scope.tournamentEdit = true;

            tournamentService.getTournament($stateParams.playersInTournament.tournamentId).then(function (response) {

                $scope.tournament = response.plain();


                $scope.players = $scope.tournament.players;

                $scope.tournamentGames = _.filter($scope.tournament.gameDtos, function (game) {

                    if ((( game.whiteId == $stateParams.playersInTournament.whiteId ) && ( game.blackId == $stateParams.playersInTournament.blackId )) || (( game.whiteId == $stateParams.playersInTournament.blackId ) && ( game.blackId == $stateParams.playersInTournament.whiteId ))) return game;

                });


                var games = [];
                $scope.tournamentGames.map(function (game) {

                    games.push(
                        {
                            id: game.id,
                            white: getPlayerName(game.whiteId),
                            black: getPlayerName(game.blackId),
                            winner: game.winner,

                            result: game.result,
                            start: game.start,
                            end: game.end

                        });


                });
                $scope.gridOptions.data = _.sortBy(games, 'white');

            });
        } else {
            $rootScope.$state.go('home')
        }


        function getPlayerName(id) {

            var name = {};
            $scope.players.forEach(function (player) {

                    if (player.id == id) {
                        name = player.name;
                    }
                }
            );
            return name;

        }


        $scope.gridOptions = {
            /* data:$rootScope.games2 , gameService.getGames()*/
            enableRowSelection: true,
            enableSelectAll: false,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,
            enableSorting: false,

            columnDefs: [
                /*{field: 'id', displayName: 'Id', enableCellEdit: false},*/
                {field: 'white', displayName: 'White'},
                {field: 'result', displayName: 'Result'},
                {field: 'black', displayName: 'Black'},
                {field: 'winner', displayName: 'Winner'},
                {field: 'start', displayName: 'Start'},
                {field: 'end', displayName: 'end'}

            ],

            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    $rootScope.selectedGameId = row.entity.id;
                });

            }
        };

        $scope.edit = function () {

            $rootScope.$state.go('editgame', {
                playersInTournament: {
                    whiteId: $stateParams.playersInTournament.whiteId,
                    blackId: $stateParams.playersInTournament.blackId,
                    tournamentId: $stateParams.playersInTournament.tournamentId,
                    gameId: $rootScope.selectedGameId
                }
            });

        };




        $scope.addGameForPlayers = function () {

            $rootScope.$state.go('addgame', {
                playersInTournament: {
                    whiteId: $stateParams.playersInTournament.whiteId,
                    blackId: $stateParams.playersInTournament.blackId,
                    tournamentId: $stateParams.playersInTournament.tournamentId
                }
            });

        };

        $scope.goToTournament = function () {

            $rootScope.$state.go('tournament', {tournamentId: $stateParams.playersInTournament.tournamentId});

        };


    }
    ])
;