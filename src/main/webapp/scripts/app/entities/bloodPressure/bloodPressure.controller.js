'use strict';

angular.module('myhealthApp')
    .controller('BloodPressureController', function ($scope, BloodPressure, User, BloodPressureSearch) {
        $scope.bloodPressures = [];
        $scope.users = User.query();
        $scope.loadAll = function() {
            BloodPressure.query(function(result) {
               $scope.bloodPressures = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            BloodPressure.get({id: id}, function(result) {
                $scope.bloodPressure = result;
                $('#saveBloodPressureModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bloodPressure.id != null) {
                BloodPressure.update($scope.bloodPressure,
                    function () {
                        $scope.refresh();
                    });
            } else {
                BloodPressure.save($scope.bloodPressure,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            BloodPressure.get({id: id}, function(result) {
                $scope.bloodPressure = result;
                $('#deleteBloodPressureConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BloodPressure.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBloodPressureConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            BloodPressureSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bloodPressures = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBloodPressureModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bloodPressure = {timestamp: null, systolic: null, diastolic: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
