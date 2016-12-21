'use strict';

angular.module('chessApp')
    .factory('playerService', ['Restangular', function (Restangular) {

        var service = Restangular.service('gameService');


        service.getPlayers = function() {
            return Restangular.all('player').getList();
        };

         service.getPlayer = function(playerId) {
            return Restangular.one('player', playerId).get();
        };

        service.savePlayer = function(player) {

           var players =  Restangular.all('player');

            players.post(player);

        };

        console.log("playerService");
        return service;
    }])

    .controller('PlayerCtrl', ['$scope', '$rootScope', 'playerService', function ($scope, $rootScope, playerService) {

        console.log("PlayerCtrl is loaded");


        playerService.getPlayers().then(function (players) {
            $scope.gridOptions.data =_.sortBy(players,'activity').reverse();
          /*  = players;*/


        });




        $scope.gridOptions = {

            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: [
                {field: 'id', displayName: 'Id'},
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'},
                {field: 'rating', displayName: 'Rating'},
                {field: 'activity', displayName: 'In tournament'}



            ],
            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    var msg = row.entity.id;
                    console.log("Row Selected! " + msg);
                    $rootScope.selectedPlayerId = msg;

                });

            }

        };


        $scope.edit = function () {

            console.log("selected Player " + $scope.selectedPlayerId);

            $rootScope.$state.go('editplayer', {playerId: $scope.selectedPlayerId});
            console.log("grid API " + $scope.gridApi);

        };


    }]);