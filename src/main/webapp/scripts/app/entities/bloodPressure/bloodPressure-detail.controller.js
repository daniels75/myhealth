'use strict';

angular.module('myhealthApp')
    .controller('BloodPressureDetailController', function ($scope, $stateParams, BloodPressure, User) {
        $scope.bloodPressure = {};
        $scope.load = function (id) {
            BloodPressure.get({id: id}, function(result) {
              $scope.bloodPressure = result;
            });
        };
        $scope.load($stateParams.id);
    });
