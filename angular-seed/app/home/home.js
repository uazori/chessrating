'use strict';

angular.module('chessApp')

    .controller('HomeCtrl', ['tournamentService', '$scope', '$rootScope', function (tournamentService, $scope, $rootScope) {

        console.log("HomeCtrl is loaded");


        tournamentService.getTournaments().then(
            function (response) {

                var tournaments = response.plain();


                _.forEach(tournaments, function (tournament) {
                    var playersCount = tournament.players.length;
                    var gamesAmountInTournament = playersCount*playersCount - playersCount
                    tournament.players = tournament.players.length;
                    tournament.gameDtos = tournament.gameDtos.length + " / " + gamesAmountInTournament;
                });


                $scope.gridOptions.data = _.sortBy(_.sortBy(tournaments,'start').reverse(),'tournamentFinished');

                $scope.gridOptions.columnDefs = generateFields(tournaments);

                tournamentService.setCurrentTournament(undefined);
            }
        );


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
                    $scope.selectedId = row.entity.id;
                    console.log("Row Selected! " + $scope.selectedId);


                });

            }

        };


        function generateFields(tournaments) {

            var columnDefs = [];

            var tournamentInfo = tournaments[0];

            for (var key in tournamentInfo) {

                if (key === "$$hashKey" || key === 'id' || key === 'initialRatings') {

                } else if (key === "gameDtos") {

                    columnDefs.push({field: key, displayName: 'games'})

                } else {

                    columnDefs.push({field: key, displayName: key})
                }
            }
            return columnDefs
        }

        $scope.edit = function () {

            tournamentService.setCurrentTournament($scope.selectedId);

            console.log("Row Selected! " + $scope.selectedId);

            $rootScope.$state.go('tournament', {tournamentId: $scope.selectedId});


        };




    }]);