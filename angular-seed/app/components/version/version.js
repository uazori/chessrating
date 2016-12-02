'use strict';

angular.module('chessApp.version', [
  'chessApp.version.interpolate-filter',
  'chessApp.version.version-directive'
])

.value('version', '0.1');
