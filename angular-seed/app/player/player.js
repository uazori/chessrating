'use strict';

angular.module('chessApp')
    .factory('playerService', ['Restangular', function (Restangular) {

        var service = Restangular.service('gameService');


        service.getPlayers = function () {
            return Restangular.all('player').getList();
        };

        service.getPlayer = function (playerId) {
            return Restangular.one('player', playerId).get();
        };

        service.savePlayer = function (player) {

            var players = Restangular.all('player');

           return players.post(player);

        };


        service.updatePlayer = function (player) {

            var updatedPlayer = Restangular.one('player', player.id);

            updatedPlayer.name = player.name;
            updatedPlayer.surname = player.surname;
            updatedPlayer.rating = player.rating;
            updatedPlayer.activity = player.activity;
            updatedPlayer.username = player.username;

            updatedPlayer.put();

        };


        console.log("playerService");
        return service;
    }])

    .controller('PlayerCtrl', ['$scope', '$rootScope', 'playerService', 'tournamentService', '$stateParams', function ($scope, $rootScope, playerService, tournamentService, $stateParams) {

        console.log("PlayerCtrl is loaded");


        if ($stateParams.tournamentId) {
            $scope.tournamentEdit = true;

            tournamentService.getTournament($stateParams.tournamentId).then(function (tournament) {

                $scope.tournament = tournament;
                $scope.tournamentPlayers = $scope.tournament.players;
                $scope.gridPlayers.data = $scope.tournament.players;

                playerService.getPlayers().then(function (players) {
                    $scope.players = players;

                    $scope.gridOptions.data = difference($scope.players, $scope.tournamentPlayers);

                });

            });

        } else {
            playerService.getPlayers().then(function (players) {

                $scope.players = players;
                $scope.gridOptions.data = $scope.players

            });
        }


        $scope.gridOptions = {

            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: [
                {field: 'id', displayName: 'Id'},
                {field: 'username', displayName: 'Username'},
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'},
                {field: 'rating', displayName: 'Rating'},



            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    $scope.selectedPlayerId = row.entity.id;
                    console.log("Players Row Selected! " + $scope.selectedPlayerId);

                });

            }

        };

        $scope.gridPlayers = {

            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: [
                {field: 'id', displayName: 'Id'},
                {field: 'username', displayName: 'Username'},
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'},
                {field: 'rating', displayName: 'Rating'},



            ],
            onRegisterApi: function (gridPlayersApi) {
                $scope.gridPlayersApi = gridPlayersApi;
                $scope.tournamentPlayersSelectedRows = $scope.gridPlayersApi.selection.getSelectedRows();
                $scope.gridPlayersApi.selection.on.rowSelectionChanged($scope, function (row) {
                    $scope.selectedTournamentPlayerId = row.entity.id;
                    console.log("Players in tournament Row Selected! " + $scope.selectedTournamentPlayerId);

                });

            }


        };


        $scope.editPlayer = function () {

            console.log("selected Player " + $scope.selectedPlayerId);

            /*$rootScope.$state.go('editplayer', {playerId: });*/

            /*
             console.log('myObj');
             console.log(myObj);
             */
            $rootScope.$state.go('editplayer', {
                playerInTournament: {
                    playerId: $scope.selectedPlayerId,
                    tournamentId: $stateParams.tournamentId
                }
            });


            console.log("grid API " + $scope.gridApi);

        };


        $scope.addPlayer = function () {

            console.log("selected Player " + $scope.selectedPlayerId);

            /*$rootScope.$state.go('editplayer', {playerId: });*/

            /*
             console.log('myObj');
             console.log(myObj);
             */
            $rootScope.$state.go('addplayer', {
                playerInTournament: {
                    playerId: undefined,
                    tournamentId: $stateParams.tournamentId
                }
            });


            console.log("grid API " + $scope.gridApi);

        };

        $scope.addToTournament = function () {


            /*     console.log("selected Player " + $scope.selectedPlayerId);*/

            playerService.getPlayer($scope.selectedPlayerId).then(function (player) {
                /*   console.log(player.plain());*/
                $scope.tournamentPlayers.push(player);
                $scope.gridPlayers.data = $scope.tournamentPlayers;
                $scope.gridOptions.data = $scope.gridOptions.data = difference($scope.players, $scope.tournamentPlayers);
            });

        };


        $scope.removeFromTournament = function () {

            playerService.getPlayer($scope.selectedTournamentPlayerId).then(function (player) {

                $scope.tournamentPlayers = _.filter($scope.tournamentPlayers, function (tournamentPlayers) {
                    return tournamentPlayers.id != player.id;
                });

                $scope.tournament.gameDtos = _.filter($scope.tournament.gameDtos, function (game) {
                    return ((game.whiteId != player.id) && (game.blackId != player.id))
                });

                $scope.gridPlayers.data = $scope.tournamentPlayers;
                $scope.gridOptions.data = difference($scope.players, $scope.tournamentPlayers);

            });

        };

        $scope.goToTournament = function () {


            $scope.tournament.players = $scope.tournamentPlayers;

            tournamentService.getTournament($scope.tournament.id).then(function (updateTournament) {

                updateTournament.players = $scope.tournament.players;
                updateTournament.gameDtos = $scope.tournament.gameDtos;


                updateTournament.put().then(function () {
                    $rootScope.$state.go('tournament', {tournamentId: $stateParams.tournamentId});
                });


            });


        };

        function difference(players, tournamentPlayers) {

            return _.filter(players, function (player) {

                if (_.findIndex(tournamentPlayers, function (tournamentPlayer) {
                        return tournamentPlayer.id == player.id
                    }) == -1)return player

            });


        }


    }]);