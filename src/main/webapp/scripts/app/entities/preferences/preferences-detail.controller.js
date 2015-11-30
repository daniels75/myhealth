'use strict';

angular.module('myhealthApp')
    .controller('PreferencesDetailController', function ($scope, $stateParams, Preferences, User) {
        $scope.preferences = {};
        $scope.load = function (id) {
            Preferences.get({id: id}, function(result) {
              $scope.preferences = result;
            });
        };
        $scope.load($stateParams.id);
    });
