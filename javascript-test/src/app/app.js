'use strict';

angular.module('app', [])
  .controller('AppCtrl', ['$scope', 'StateService', function($scope, state) {
    $scope.getHeaders = function() {
      return state.getHeaders();
    };

    $scope.getRows = function() {
      return state.getRows();
    };
    console.log(state);
  }])
  .service('StateService',['InitialDataService', function(InitialData) {
    var data = InitialData.getInitialData();

    var headers = [{name: 'A', boxCount: 0}, {name: 'B', boxCount: 0}];
    var state = {
      headers: [{name: 'A', boxCount: 0}, {name: 'B', boxCount: 0}],
      rows: []
    };

    fillRows();
    recalculateBoxCount();

    this.getHeaders = function() {
      return state.headers;
    };

    this.getRows = function() {
      return state.rows;
    };

    function fillRows() {
      var rows = state.rows;
      var row;
      var rowData;
      for (var i = 0; i < data.length; i++) {
        rowData = data[i];
        row = {
          columns: getColumnsFromRowData(rowData),
          sequence: rowData.sequence
        };
        rows.push(row);
      }
    }

    function getColumnsFromRowData(rowData) {
      var columns = [{
        boxes: []
      }, {
        boxes: []
      }];

      var keys = Object.keys(rowData.states);
      for (var i = 0; i < keys.length; i++) {
        var color = keys[i];
        var columnName = rowData.states[color];
        var columnIndex = columnName === 'A' ? 0 : 1;
        addBoxColorToColumnAlongWithSequence(columns[columnIndex], color, rowData.sequence);
      }

      return columns;
    }

    function addBoxColorToColumnAlongWithSequence(column, color, sequence) {
      if (column.boxes.length === 0) {
        column.boxes.push(color);
        return;
      }

      var index = sequence.indexOf(color);
      var nextColor;
      for (var i = 0; i < column.boxes.length; i++) {
        nextColor = column.boxes[i];
        if (sequence.indexOf(nextColor) > index) {
          column.boxes.splice(i, 0, color);
          break;
        }
      }

      if (column.boxes.indexOf(color) < 0) {
        column.boxes.push(color);
      }
    }

    function recalculateBoxCount() {
      var headers = state.headers;
      var rows = state.rows;

      for (var i = 0; i < state.headers.length; i++) {
        var header = headers[i];
        var boxCount = 0;
        for (var j = 0; j < rows.length; j++) {
          var row = rows[j];
          boxCount += row.columns[i].boxes.length;
        }
        header.boxCount = boxCount;
      }
    }
  }])
  .service('InitialDataService', function() {
    // In normal app we should use $http service and AJAX call to
    // the server to get the data. But in this case we don't have any server.
    // So just use static data.
    var data = [
      {
        "sequence": ["red", "green", "yellow", "blue"],
        "states": {
          "red": "A",
          "green": "A",
          "yellow": "A",
          "blue": "A"
        }
      },
      {
        "sequence": ["green", "yellow", "red", "blue"],
        "states": {
          "red": "B",
          "green": "B",
          "yellow": "A",
          "blue": "A"
        }
      },
      {
        "sequence": ["blue", "yellow", "red", "green"],
        "states": {
          "red": "A",
          "green": "A",
          "yellow": "B",
          "blue": "B"
        }
      },
      {
        "sequence": ["yellow", "blue", "green", "red"],
        "states": {
          "red": "A",
          "green": "B",
          "yellow": "A",
          "blue": "B"
        }
      }
    ];

    this.getInitialData = function() {
      return angular.copy(data);
    };
  });