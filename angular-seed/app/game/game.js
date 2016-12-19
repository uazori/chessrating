/**
 * Created by Millhouse on 12/5/2016.
 */
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

            console.log("sgame service update");








           var updatedGame =  Restangular.one('game', game.id);


             updatedGame.whiteId = game.whiteId;
             updatedGame.blackId = game.blackId;
             updatedGame.winner = game.winner;
             updatedGame.result = game.result;
             updatedGame.start = game.start;
             updatedGame.end = game.end;

            updatedGame.post();




        };

        console.log("gameService");
        return service;
    }])


    .controller('GameCtrl', ['$scope', '$rootScope', '$http', '$q', '$interval', 'gameService', 'playerService', function ($scope, $rootScope, $http, $q, $interval, gameService, playerService) {
        console.log("GameCtrl is loaded");


        playerService.getPlayers().then(
            function (response) {
                $scope.players = response.plain();

                gameService.getGames().then(function (response) {
                    var games = [];
                    response.map(function (game) {

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


                        $scope.gridOptions.data = games;
                    });
                });

            }
        );


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
                    var msg = row.entity.id;
                    console.log("Row Selected! " + msg);
                    $rootScope.selectedGameId = msg;

                });

            }
        };

        $scope.edit = function () {

            $rootScope.$state.go('editgame', {gameId: $scope.selectedGameId});

        };


    }
    ])
;