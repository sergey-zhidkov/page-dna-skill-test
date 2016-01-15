'use strict';

angular.module('app', [])
  .controller('AppCtrl', ['$scope', 'StateService', function($scope, state) {
    console.log(state);
  }])
  .service('StateService', function() {
    this.state = {};
  });