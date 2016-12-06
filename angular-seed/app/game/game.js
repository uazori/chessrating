/**
 * Created by Millhouse on 12/5/2016.
 */
'use strict';

angular.module('chessApp')

    .controller('GameCtrl', ['$scope', '$rootScope','$http', '$q', '$interval', function ($scope, $rootScope, $http, $q, $interval) {
        console.log("GameCtrl is loaded");

        /*$http.get('/data/500_complex.json')
         .success(function (data) {
         $scope.gridOptions.data = data;

         // $interval whilst we wait for the grid to digest the data we just gave it
         $interval(function () {
         $scope.gridApi.selection.selectRow($scope.gridOptions.data[0]);
         }, 0, 1);
         });*/


        $scope.gridOptions = {
            data: $rootScope.games,
            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: [
                {field: 'gameId', displayName: 'Id',enableCellEdit: false},
                {field: 'whiteId', displayName: 'whiteId (editable)'},
                {field: 'blackId', displayName: 'blackId'},
                {field: 'winnerId', displayName: 'winnerId'},
                {field: 'result', displayName: 'result'},
                {field: 'start', displayName: 'start'},
                {field: 'end', displayName: 'end'}
            ],

            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
                    var msg = row.entity.gameId;
                    console.log("Row Selected! " + msg);
                    $rootScope.selectedGameId = msg;

                });

            }
        };

        $scope.edit = function () {
            console.log("edit Game " + $scope.gridApi);

            console.log("selected Player " + $scope.selectedPlayerId);

            $rootScope.$state.go('editgame', {gameId: $scope.selectedGameId});
            console.log("grid API " + $scope.gridApi);
        };

      /*  $scope.saveRow = function( rowEntity ) {
            // create a fake promise - normally you'd use the promise returned by $http or $resource
            console.log("save Row")
            var promise = $q.defer();
            $scope.gridApi.rowEdit.setSavePromise( rowEntity, promise.promise );

            // fake a delay of 3 seconds whilst the save occurs, return error if gender is "male"
            $interval( function() {
                if (rowEntity.gender === 'male' ){
                    promise.reject();
                } else {
                    promise.resolve();
                }
            }, 3000, 1);
        };

        $scope.gridOptions.onRegisterApi = function(gridApi){
            //set gridApi on scope
            $scope.gridApi = gridApi;
            gridApi.rowEdit.on.saveRow($scope, $scope.saveRow);
        };*/

        /*
        $http.get('/data/500_complex.json')
            .success(function(data) {
                for(i = 0; i < data.length; i++){
                    data[i].registered = new Date(data[i].registered);
                }
                $scope.gridOptions.data = data;
            });*/
        /*более 2700 – элитный гроссмейстер (неофициальная, условная категория);
         2500-2699 – гроссмейстер;
         2400-2499 – международный мастер;
         2300-2399 – национальный мастер;
         2100-2299 – кандидат в мастера;
         1900-2099 – первый разряд;
         1600-1899 – второй разряд;
         1400-1599 – третий разряд;
         1200-1399 – средний любитель;
         1000-1199 – слабый любитель;
         менее 1000 – новичок.*/
    }]);