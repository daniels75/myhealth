'use strict';

angular.module('myhealthApp')
    .controller('WeightDetailController', function ($scope, $stateParams, Weight, User) {
        $scope.weight = {};
        $scope.load = function (id) {
            Weight.get({id: id}, function(result) {
              $scope.weight = result;
            });
        };
        $scope.load($stateParams.id);
    });
