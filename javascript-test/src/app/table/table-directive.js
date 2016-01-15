'use strict';

angular.module('app')
  .directive('table', function() {
    return {
      restrict: 'A',
      require: 'ngModel',
      scope: {
        ngModel: '='
      },
      templateUrl: 'app/table/table.tpl.html'
    };
  });
