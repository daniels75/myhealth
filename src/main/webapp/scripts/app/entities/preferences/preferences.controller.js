'use strict';

angular.module('myhealthApp')
    .controller('PreferencesController', function ($scope, Preferences, User, PreferencesSearch) {
        $scope.preferencess = [];
        $scope.users = User.query();
        $scope.loadAll = function() {
            Preferences.query(function(result) {
               $scope.preferencess = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Preferences.get({id: id}, function(result) {
                $scope.preferences = result;
                $('#savePreferencesModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.preferences.id != null) {
                Preferences.update($scope.preferences,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Preferences.save($scope.preferences,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Preferences.get({id: id}, function(result) {
                $scope.preferences = result;
                $('#deletePreferencesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Preferences.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePreferencesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PreferencesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.preferencess = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePreferencesModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.preferences = {weeklyGoal: null, weightUnits: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
