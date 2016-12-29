'use strict';

var chessApp = angular.module('chessApp', [
    'ui.router',
    'ui.grid',
    'ui.grid.selection',
    'ngMaterial',
    'ngResource',
    'restangular',
    'smDateTimeRangePicker',

   /* 'ngTouch',*/
    'ui.grid.edit',
    'ui.grid.cellNav'


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
            templateUrl: 'home/home.html',
            controller: 'HomeCtrl'

        })

        .state('tournament', {
            url: '/tournament/{tournamentId:int}',
            templateUrl: 'tournament/tournament.html',
            controller: 'TournamentCtrl'

        })
        .state('addtournament', {
            url: '/tournament/add',
            templateUrl: 'home/add/add-tournament.html',
              controller: 'AddEditTournamentCtrl'
        })
        .state('edittournament', {
            url: '/tournament/edit/{tournamentId:int}',
            templateUrl: 'home/add/add-tournament.html',
            controller: 'AddEditTournamentCtrl'
        })

        .state('addPlayerToTournament', {
            url: '/player/{tournamentId:int}',
            templateUrl: 'player/player.html',
            controller: 'PlayerCtrl'
        })

        .state('player', {
            url: '/player',
            templateUrl: 'player/player.html',
            controller: 'PlayerCtrl'
        })

        .state('addplayer', {
            url: '/players/add',
            templateUrl: 'player/add/add-player.html',
            controller: 'AddEditPlayerCtrl',
            params: {
                playerInTournament: null
            }
        })

        .state('editplayer', {
            url: '/players/edit',
            templateUrl: 'player/add/add-player.html',
            controller: 'AddEditPlayerCtrl',
            params: {
                playerInTournament: null
            }
        })
        .state('games', {
            url: '/games/{tournamentId:int}',
            templateUrl: '/game/game.html',
            controller: 'GameCtrl'
        })
        .state('gamesForPlayers', {
            url: '/games',
            templateUrl: '/game/game.html',
            controller: 'GameCtrl',
            params: {
                playersInTournament: null
            }
        })
        .state('addgame', {
            url: '/games/add',
            templateUrl: 'game/add/add-game.html',
            controller: 'AddEditGameCtrl',
            params: {
                playersInTournament: null
            }
        })
        .state('editgame', {
            url: '/games/edit/',
            templateUrl: 'game/add/add-game.html',
            controller: 'AddEditGameCtrl',
            params: {
                playersInTournament: null
            }
        });

});

chessApp.run(['$rootScope', '$state',
    function ($rootScope, $state ) {


        $rootScope.$state = $state;




    }]);
