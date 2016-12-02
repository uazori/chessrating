angular.module('chessApp').directive('mhRating', function () {
    return {
        restrict: 'EA',
        replace: true,
        template: '<section class="rating">' +
                    '<md-button ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)" class="md-icon-button" ng-disabled="viewOnly">' +
                        '<md-icon class="material-icons">{{star.icon}}</md-icon>' +
                    '</md-button>' +
                    '</section>',
        scope: {
            ratingValue: '=',
            maxRate: '=',
            viewOnly: '='
        },
        link: function (scope) {

            scope.toggle = function (index) {
                if ((index == 0) && (scope.ratingValue == 1)) {
                    scope.ratingValue = 0;
                } else {
                    scope.ratingValue = index + 1;
                }
            };

            var update = function () {
                scope.stars = [];
                for (var i = 0; i < scope.maxRate; i++) {
                    scope.stars.push({
                        icon: i < scope.ratingValue ? 'star' : 'star_border'
                    });
                }
            };
            update();

            scope.$watch('ratingValue', function (oldVal, newVal) {
                if (newVal !== oldVal) {
                    update();
                }
            });
        }
    }
});
