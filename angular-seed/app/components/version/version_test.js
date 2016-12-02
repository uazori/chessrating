'use strict';

describe('chessApp.version module', function() {
  beforeEach(module('chessApp.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
