'use strict';

angular.module('chessApp')

    .controller('AddEditPlayerCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams', 'playerService',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams, playerService) {
            console.log("AddEditPlayerCtrl is loaded");

            var playerInTournament = $stateParams.playerInTournament;

            if (playerInTournament){


            if (playerInTournament.playerId) {

                console.log("Editing player =  " + $stateParams.playerInTournament.playerId);
                console.log("Tournament =  " + $stateParams.playerInTournament.tournamentId);


                playerService.getPlayer($stateParams.playerInTournament.playerId).then(function (player) {
                    $scope.playerModel = player;
                });

            } else {

                console.log($stateParams.playerInTournament);


                $scope.playerModel = {
                    id: '',
                    username:'',
                    name: '',
                    surname: '',
                    rating: '',
                    activity:''
                };

            }}else {$state.go('home');}

            function preparePlayer() {
                return {

                    name: $scope.playerModel.name,
                    surname: $scope.playerModel.surname,
                    rating: $scope.playerModel.rating,
                    username: $scope.playerModel.username
                }
            }

            function yes() {
                console.log("yes yes yes");
                var player = {};

                if (playerInTournament.playerId) {

                    $scope.playerModel.put().then(function () {
                        $rootScope.$state.go('addPlayerToTournament', {tournamentId: $stateParams.playerInTournament.tournamentId});

                    });



                } else {

                    player = preparePlayer();

                   playerService.savePlayer(player).then(function () {
                        $rootScope.$state.go('addPlayerToTournament', {tournamentId: $stateParams.playerInTournament.tournamentId});
                    });
                }

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
                console.log($scope.playerForm.$valid);
                if ($scope.playerForm.$valid) {
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
                $scope.playerForm.$dirty ? showConfirm(ev) : $rootScope.$state.go('addPlayerToTournament', {tournamentId: $stateParams.playerInTournament.tournamentId});
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
                    $state.go('player');
                });
            }

        }]);