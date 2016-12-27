'use strict';

angular.module('chessApp')

    .controller('HomeCtrl', ['tournamentService', '$scope', '$rootScope', function (tournamentService, $scope, $rootScope) {

        console.log("HomeCtrl is loaded");


        tournamentService.getTournaments().then(
            function (response) {

                var tournaments = response.plain();


                _.forEach(tournaments, function (tournament) {

                    tournament.players = tournament.players.length;
                    tournament.gameDtos = tournament.gameDtos.length;
                });


                $scope.gridOptions.data = tournaments;
                $scope.gridOptions.columnDefs = generateFields(tournaments);
                console.log("all tournaments");


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

                if (key === "$$hashKey" || key === 'id') {

                } else if (key === "gameDtos") {

                    columnDefs.push({field: key, displayName: 'games'})

                } else {

                    columnDefs.push({field: key, displayName: key})
                }
            }
            return columnDefs
        }

        $scope.edit = function () {


            console.log("Row Selected! " + $scope.selectedId);

            $rootScope.$state.go('tournament', {tournamentId: $scope.selectedId});

        };


        $scope.editTournamentDescription = function () {

            /*  console.log("Row Selected! " +  $scope.tournamentId);
             $rootScope.tournamentPlayers = $scope.tournament.players;*/
            $rootScope.$state.go('edittournament', {tournamentId: $scope.selectedId});
        };

    }]);