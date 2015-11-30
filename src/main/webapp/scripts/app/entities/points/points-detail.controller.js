'use strict';

angular.module('myhealthApp')
    .controller('PointsDetailController', function ($scope, $stateParams, Points, User) {
        $scope.points = {};
        $scope.load = function (id) {
            Points.get({id: id}, function(result) {
              $scope.points = result;
            });
        };
        $scope.load($stateParams.id);
    });
