'use strict';

angular.module('chessApp')

    .controller('PlayerCtrl', ['$scope', '$rootScope', function ($scope, $rootScope) {
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
            data: $rootScope.players,
            enableRowSelection: true,
            enableSelectAll: true,
            enableRowHeaderSelection: false,
            multiSelect: false,
            modifierKeysToMultiSelect: false,
            enableHorizontalScrollbar: 0,

            columnDefs: [
                {field: 'playerId', displayName: 'Id'},
                {field: 'name', displayName: 'Name'},
                {field: 'surname', displayName: 'Surname'},
                {
                    field: 'rating',
                    displayName: 'Rating',
                    cellTemplate: '<mh-rating rating-value="row.entity.rating" view-only="true" max-rate="5" >' +
                    '</mh-rating>'
                }
            ],
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                $scope.mySelectedRows = $scope.gridApi.selection.getSelectedRows();
                $scope.gridApi.selection.on.rowSelectionChanged($scope, function(row) {
                    var msg = row.entity.playerId;
                    console.log("Row Selected! " + msg);
                    $rootScope.selectedPlayerId = msg;

                });

            }

        };

       /* $scope.gridOptions.onRegisterApi = function (gridApi) {


            $scope.gridApi = gridApi;



            gridApi.selection.on.rowSelectionChanged($scope,function(row){
                var msg = 'row selected ' + row.isSelected;
                console.log(msg);
                var rowID = 'rowId ' + row.entity;
                console.log(rowID);


                $scope.mySelectedRows = gridApi.selection.getSelectedRows();
                console.log("myRows" +  gridApi.selection.getSelectedRows());

            });

            gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
                var msg = 'rows changed ' + rows.length;
                console.log(msg);
            });

            // $scope.gridApi.grid.registerRowsProcessor($scope.singleFilter, 200);
        };*/

        $scope.edit = function () {

            console.log("edit Player " + $scope.gridApi);

            console.log("selected Player " + $scope.selectedPlayerId);
            // $rootScope.$state.go('editplayer', {playerId: $scope.gridApi.selection.getSelectedRows()[0].id});
            $rootScope.$state.go('editplayer', {playerId: $scope.selectedPlayerId});
            console.log("grid API " + $scope.gridApi);
         /*   console.log("selected: " + $scope.gridApi.selection.getSelectedRows()[0].id);*/
        };

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