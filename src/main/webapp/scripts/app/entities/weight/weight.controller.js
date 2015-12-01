'use strict';

angular.module('myhealthApp')
    .controller('WeightController', function ($scope, Weight, User, WeightSearch) {
        $scope.weights = [];
        $scope.users = User.query();
        $scope.loadAll = function() {
            Weight.query(function(result) {
               $scope.weights = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Weight.get({id: id}, function(result) {
                $scope.weight = result;
                $('#saveWeightModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.weight.id != null) {
                Weight.update($scope.weight,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Weight.save($scope.weight,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Weight.get({id: id}, function(result) {
                $scope.weight = result;
                $('#deleteWeightConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Weight.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWeightConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            WeightSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.weights = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveWeightModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.weight = {timestamp: null, weight: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
