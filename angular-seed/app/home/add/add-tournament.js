/**
 * Created by Vadim Ovcharuk uazori@mail.ru on 12/22/2016.
 * controller for adding new tournament
 */


'use strict';

angular.module('chessApp')

    .controller('AddEditTournamentCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams', 'tournamentService',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams, tournamentService) {

            console.log("AddEditTournamentCtrl is loaded");

            if ($stateParams.tournamentId) {

                console.log("Editing Tournament =  " + $stateParams.tournamentId);

                tournamentService.getTournament($stateParams.tournamentId).then(function (response) {
                    $scope.TournamentModel = response;

                    console.log($scope.TournamentModel);
                });

            } else {

                $scope.TournamentModel = {
                    id: '',
                    name: '',
                    description: '',
                    system: '',
                    start: '',
                    end: ''

                };

            }

            function prepareTournament() {
                return {

                    name: $scope.TournamentModel.name,
                    description: $scope.TournamentModel.description,
                    system: $scope.TournamentModel.system,
                    players: [],
                    gameDtos: [],
                    start: $scope.TournamentModel.start,
                    end: $scope.TournamentModel.end
                }
            }

            function yes() {
                console.log("yes yes yes");
                var tournament = {};

                if ($stateParams.tournamentId) {

                    $scope.TournamentModel.put();


                } else {

                    tournament = prepareTournament();
                    tournamentService.saveTournament(tournament);

                }


                $state.go('home');
            }

            function no() {
                console.log("no");
            }

            $scope.saveConfirm = function (ev) {

                console.log("save confirm");

                var confirm = $mdDialog.confirm()
                    .title('Save Changes?')
                    .targetEvent(ev)
                    .ok('Yes Save')
                    .cancel('No');
                console.log($scope.tournamentForm.$valid);
                if ($scope.tournamentForm.$valid) {
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
                $scope.tournamentForm.$dirty ? showConfirm(ev) : $state.go('home');
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
                    state.go('home');
                });
            }

        }]);