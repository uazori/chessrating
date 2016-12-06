'use strict';

angular.module('chessApp')

    .controller('AddEditGameCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams) {
            console.log("AddEditGameCtrl is loaded");

            if ($stateParams.gameId) {
                $scope.gameId = $stateParams.gameId;
                console.log("Editing game id = " + $scope.gameId);
                $scope.gameIndex = findGameIndexById($rootScope.games, $scope.gameId);

                console.log("game index = " + $scope.gameIndex);
                $scope.gameModel = $rootScope.games[$scope.gameIndex];
                // $scope.playerModel = $rootScope.players[$scope.playerId];

            } else {

                console.log("else game")

                $scope.gameModel = {
                    gameId: '',
                    whiteId: '',
                    blackId: '',
                    winnerId: '',
                    result: '',
                    start: '',
                    end: ''
                };
            }


            function findGameIndexById(games,id)  {
                for(var i=0; i<games.length; i++) {
                    if (games[i].gameId == id) return i;
                }
            }


            function prepareGame() {
                return {
                    gameId: $rootScope.games.length,
                    whiteId: $scope.gameModel.whiteId,
                    blackId: $scope.gameModel.blackId,
                    winnerId: $scope.gameModel.whiteId,
                    result: $scope.gameModel.result,
                    start: $scope.gameModel.start,
                    end: $scope.gameModel.end
                }
            }

            function yes() {
                console.log("yes");
                var game = {};
                if($stateParams.gameId){
                    $rootScope.games[$scope.gameIndex]  = $scope.gameModel;

                } else {

                    game = prepareGame();
                    $rootScope.games.push(game);
                }
                $state.go('games');

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

                if ($scope.GameForm.$valid) {
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
                $scope.GameForm.$dirty ? showConfirm(ev) : $state.go('games');
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
                    $state.go('games');
                });
            };

        }]);