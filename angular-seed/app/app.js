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

            {"playerId": "0", "name": "Igor", "surname": "Boss","status": "", "rating": 5},
            {"playerId": "1", "name": "Nick", "surname": "Java","status": "", "rating": 2},
            {"playerId": "2", "name": "Vitaliy", "surname": "Android","status": "", "rating": 1},
            {"playerId": "3", "name": "Bogdan", "surname": "JavaScriptGuru","status": "", "rating": 4},
            {"playerId": "4", "name": "Artem", "surname": "TeamLead","status": "", "rating": 5},
            {"playerId": "5", "name": "Sergey", "surname": "Java","status": "", "rating": 5}

        ];

        $rootScope.games = [

            {
                "gameId": "1",
                "whiteId": "1",
                "blackId": "2",
                "winnerId": "1",
                "result": "MATE",
                "start": "10:00T12.24.2015",
                "end": "10:00T12.24.2015"
            },
            {
                "gameId": "2",
                "whiteId": "3",
                "blackId": "4",
                "winnerId": "3",
                "result": "DRAW",
                "start": "10:00T12.24.2015",
                "end": "10:00T12.24.2015"
            },
            {
                "gameId": "3",
                "whiteId": "5",
                "blackId": "6",
                "winnerId": "6",
                "result": "STALEMATE",
                "start": "10:00T12.24.2015",
                "end": "10:00T12.24.2015"
            },
            {
                "gameId": "4",
                "whiteId": "7",
                "blackId": "8",
                "winnerId": "8",
                "result": "DRAW",
                "start": "10:00T12.24.2015",
                "end": "10:00T12.24.2015"
            },
            {
                "gameId": "5",
                "whiteId": "9",
                "blackId": "7",
                "winnerId": "7",
                "result": "MATE",
                "start": "10:00T12.24.2015",
                "end": "10:00T12.24.2015"
            }
        ];


        $rootScope.tournament = [

            {"playerName": "Vadim", "result1": "N/A", "result2": "2", "result3": "1", "result4": "1", "result5": "1"},
            {"playerName": "Nikolay", "result1": "0", "result2": "N/A", "result3": "1", "result4": "1", "result5": "1"},
            {"playerName": "Igor", "result1": "1", "result2": "2", "result3": "N/A", "result4": "1", "result5": "1"},
            {"playerName": "Denis", "result1": "1", "result2": "2", "result3": "1", "result4": "N/A", "result5": "1"},
            {"playerName": "Artem", "result1": "0", "result2": "2", "result3": "1", "result4": "1", "result5": "N/A"}

        ];

        function createTournament() {
            var tournament = [];


            var tableLength = $rootScope.players.length;

            for (var i = 0; i < $rootScope.players.length; i++) {

                var playerScore = {};
                var player = $rootScope.players[i];

                playerScore['playerName'] = player.name;


                for (var j = 0; j < tableLength; j++) {


                    var result = 'result_' + (j + 1);
                    if (i==j){
                        playerScore[result] = 'N/A';
                    }else {
                        playerScore[result] = i+j;
                    }

                }
                tournament.push(playerScore);
            }

            return tournament

        }


        $rootScope.tournament2 = createTournament();
    }]);
