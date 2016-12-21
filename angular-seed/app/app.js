'use strict';

var chessApp = angular.module('chessApp', [
    'ui.router',
    'ui.grid',
    'ui.grid.selection',
    'ngMaterial',
    'ngResource',
    'restangular',
    'smDateTimeRangePicker'


]);

chessApp.config(function ($stateProvider, $urlRouterProvider, RestangularProvider, $mdThemingProvider, pickerProvider) {


    pickerProvider.setOkLabel('Save');
    pickerProvider.setCancelLabel('Close');


    pickerProvider.setDayHeader('single');
    pickerProvider.setDivider('To');


    RestangularProvider.setBaseUrl('http://localhost:8080');

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        .state('home', {
            url: '/home',
            templateUrl: 'tournament/tournament.html',
            controller: 'TournamentCtrl'

        })

        .state('player', {
            url: '/player',
            templateUrl: 'player/player.html',
            controller: 'PlayerCtrl'
        })

        .state('addplayer', {
            url: '/players/add',
            templateUrl: 'player/add/add-player.html'
          /*  controller: 'AddEditPlayerCtrl'*/
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

chessApp.run(['$rootScope', '$state',
    function ($rootScope, $state ) {


        $rootScope.$state = $state;




    }]);
