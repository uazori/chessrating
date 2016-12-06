'use strict';

var chessApp = angular.module('chessApp', [
    'ui.router',
    'ui.grid',
    'ui.grid.selection',
    'ngMaterial'
    /*'ui.grid.edit',
     'ui.grid.rowEdit',
     'ui.grid.cellNav'*/
]);

chessApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        .state('home', {
            url: '/home',
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'
        })

        .state('player', {
            url: '/player',
            templateUrl: 'player/player.html',
            controller: 'PlayerCtrl'
        })

        .state('addplayer', {
            url: '/players/add',
            templateUrl: 'player/add/add-player.html',
            controller: 'AddEditPlayerCtrl'
        })

        .state('editplayer', {
            url: '/players/{playerId:int}',
            templateUrl: 'player/add/add-player.html',
            controller: 'AddEditPlayerCtrl'
        })
        .state('games', {
            url: '/games',
            templateUrl: '/game/game.html',
            controller: 'GameCtrl'
        })
        .state('addgame', {
            url: '/games/add',
            templateUrl: 'game/add/add-game.html',
            controller: 'AddEditGameCtrl'
        })
        .state('editgame', {
            url: '/games/{gameId:int}',
            templateUrl: 'game/add/add-game.html',
            controller: 'AddEditGameCtrl'
        });

});

chessApp.run(['$rootScope', '$state', '$http',
    function ($rootScope, $state, $http) {

        $rootScope.$state = $state;
        $rootScope.players = [
            {"playerId": "0", "name": "Player Name 2", "surname": "Player SurName 2", "rating": 3},
            {"playerId": "1", "name": "Player Name 1", "surname": "Player SurName 1", "rating": 2},
            {"playerId": "2", "name": "Player Name 3", "surname": "Player SurName 3", "rating": 1},
            {"playerId": "3", "name": "Player Name 4", "surname": "Player SurName 4", "rating": 4},
            {"playerId": "4", "name": "Player Name 5", "surname": "Player SurName 5", "rating": 5},
            {"playerId": "5", "name": "Player Name 6", "surname": "Player SurName 6", "rating": 1}
        ];
        $rootScope.games = [

            {"gameId": "1", "whiteId": "1", "blackId": "2", "winnerId": "1", "result": "MATE", "start": "10:00T12.24.2015", "end": "10:00T12.24.2015"},
            {"gameId": "2", "whiteId": "3", "blackId": "4", "winnerId": "3","result": "DRAW", "start": "10:00T12.24.2015", "end": "10:00T12.24.2015"},
            {"gameId": "3", "whiteId": "5", "blackId": "6", "winnerId": "6","result": "STALEMATE", "start": "10:00T12.24.2015", "end": "10:00T12.24.2015"},
            {"gameId": "4", "whiteId": "7", "blackId": "8", "winnerId": "8","result": "DRAW", "start": "10:00T12.24.2015", "end": "10:00T12.24.2015"},
            {"gameId": "5", "whiteId": "9", "blackId": "7", "winnerId": "7","result": "MATE", "start": "10:00T12.24.2015", "end": "10:00T12.24.2015"}
        ];
    }]);
