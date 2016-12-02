'use strict';

/* https://github.com/angular/protractor/blob/master/docs/toc.md */

describe('chess app', function() {


  it('should automatically redirect to /view1 when location hash/fragment is empty', function() {
    browser.get('index.html');
    expect(browser.getLocationAbsUrl()).toMatch("/home");
  });


  describe('home', function() {

    beforeEach(function() {
      browser.get('index.html#!/home');
    });


    it('should render home when user navigates to /home', function() {
      expect(element.all(by.css('[ng-view] p')).first().getText()).
        toMatch(/partial for home/);
    });

  });


  describe('rating', function() {

    beforeEach(function() {
      browser.get('index.html#!/rating');
    });


    it('should render rating when user navigates to /rating', function() {
      expect(element.all(by.css('[ng-view] p')).first().getText()).
        toMatch(/partial for rating/);
    });

  });
});
