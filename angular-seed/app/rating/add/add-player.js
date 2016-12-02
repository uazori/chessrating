'use strict';

angular.module('chessApp')

    .controller('AddEditPlayerCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams) {
            console.log("AddEditPlayerCtrl is loaded");

            if ($stateParams.playerId) {
                $scope.playerId = $stateParams.playerId;
                console.log("Editing " + $scope.playerId)
            }

            $scope.playerModel = {
                playerId: '',
                name: '',
                rating: ''
            };

            function preparePlayer() {
                return {
                    playerId: $rootScope.players.length,
                    name: $scope.playerModel.name,
                    rating: $scope.playerModel.rating
                }
            }

            function yes() {
                console.log("yes");
                var player = preparePlayer();
                $rootScope.players.push(player);
                $state.go('rating');
            };

            function no() {
                console.log("no");
            };

            $scope.saveConfirm = function (ev) {

                var confirm = $mdDialog.confirm()
                    .title('Save Changes?')
                    .targetEvent(ev)
                    .ok('Yes')
                    .cancel('No');

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
            };

            $scope.cancel = function (ev) {
                $scope.playerForm.$dirty ? showConfirm(ev) : $state.go('rating');
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
                    $state.go('rating');
                });
            };

        }]);