'use strict';

angular.module('chessApp')

    .controller('AddEditGameCtrl', ['$scope', '$mdDialog', '$mdToast', '$state', '$rootScope', '$stateParams', 'gameService', 'playerService',
        function ($scope, $mdDialog, $mdToast, $state, $rootScope, $stateParams, gameService, playerService) {
            console.log("AddEditGameCtrl is loaded");


            playerService.getPlayers().then(function (response) {

                $scope.players = response;

                $scope.whiteChanged()
            });



            $scope.whiteChanged = function (){

             $scope.blackPlayers = _.filter($scope.players,function (player) {
                            if (player.id!= $scope.gameModel.whiteId){return player;}
                        });
                };


            $scope.result = ['MATE', 'DRAW', 'STALEMATE'];

            $scope.playersInGame = ['White', 'Black'];


            $scope.gameModel = {
                gameId: null,
                whiteId: null,
                blackId: null,
                winner: "NONE",
                result: '',
                start: '',
                end: ''

            };

            $scope.variable = 'all ok';


            if ($stateParams.gameId) {

                $scope.gameId = $stateParams.gameId;
                console.log("Editing game id = " + $scope.gameId);

                gameService.getGame($stateParams.gameId).then(function (response) {
                $scope.gameModel = response;

                });


            }


            function prepareGame() {

                var winner = function () {
                    if (($scope.gameModel.result == "DRAW")
                        || ($scope.gameModel.result == "STALEMATE")) {
                        return "NONE";
                    }
                    else {
                        return $scope.gameModel.winner;
                    }
                };


                return {
                    id: $scope.gameModel.id,
                    whiteId: $scope.gameModel.whiteId,
                    blackId: $scope.gameModel.blackId,
                    winner: winner(),
                    result: $scope.gameModel.result,
                    start: $scope.gameModel.start,
                    end: $scope.gameModel.end
                }
            }

            function yes() {


                var game = {};
                if ($stateParams.gameId) {
                    game = prepareGame();
                    gameService.updateGame(game);
                } else {

                    game = prepareGame();
                    gameService.saveGame(game)

                }
                $state.go('games');

            }

            function no() {
                console.log("no");
            }

            $scope.saveConfirm = function (ev) {


                var confirm = $mdDialog.confirm()
                    .title('Save Changes?')
                    .targetEvent(ev)
                    .ok('Yes')
                    .cancel('No');


                if ($scope.gameForm.$valid) {
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
                $scope.gameForm.$dirty ? showConfirm(ev) : $state.go('games');
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
                    $state.go('games', {updateData:true});
                });
            }

            $scope.enableWinnerSelect = function () {

                return $scope.gameModel.result != "MATE";

            }

        }]);