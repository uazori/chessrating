'use strict';

var chessApp = angular.module('chessApp', [
    'ui.router',
    'ui.grid',
    'ui.grid.selection',
    'ngMaterial'
]);

chessApp.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        .state('home', {
            url: '/home',
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'
        })

        .state('rating', {
            url: '/rating',
            templateUrl: 'rating/rating.html',
            controller: 'RatingCtrl'
        })

        .state('addplayer', {
            url: '/players/add',
            templateUrl: 'rating/add/add-player.html',
            controller: 'AddEditPlayerCtrl'
        })

        .state('editplayer', {
            url: '/players/edit',
            templateUrl: 'rating/add/add-player.html',
            controller: 'AddEditPlayerCtrl'
        });

});

chessApp.run(['$rootScope', '$state', '$http',
    function ($rootScope, $state, $http) {

        $rootScope.$state = $state;
        $rootScope.players = [
            {"playerId": "0", "name": "Player Name 2", "rating": 3},
            {"playerId": "1", "name": "Player Name 1", "rating": 2},
            {"playerId": "2", "name": "Player Name 3", "rating": 1}
        ];
    }]);
