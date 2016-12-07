'use strict';

angular.module('chessApp')

    .controller('TournamentCtrl', ['$scope', '$rootScope', function ($scope, $rootScope) {
        console.log("PlayerCtrl is loaded");

        /*$http.get('/data/500_complex.json')
         .success(function (data) {
         $scope.gridOptions.data = data;

         // $interval whilst we wait for the grid to digest the data we just gave it
         $interval(function () {
         $scope.gridApi.selection.selectRow($scope.gridOptions.data[0]);
         }, 0, 1);
         });*/


        $scope.gridOptions = {
            data: $rootScope.tournament2,
            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: generateFields(),

            onRegisterApi: function (gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                    var msg = row.entity.playerId;
                    console.log("Row Selected! " + msg);
                    $rootScope.selectedPlayerId = msg;

                });

            }

        };


        function generateFields() {
            var columnDefs = [];

            var playerInfo = $rootScope.tournament2[0];

            for (var key in playerInfo) {

                if( key === "$$hashKey" ) {

                }else{columnDefs.push({field: key, displayName: key})}


            }

            return columnDefs

        }

    }]);
