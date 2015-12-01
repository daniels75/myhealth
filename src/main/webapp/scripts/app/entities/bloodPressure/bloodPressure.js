'use strict';

angular.module('myhealthApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bloodPressure', {
                parent: 'entity',
                url: '/bloodPressure',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.bloodPressure.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bloodPressure/bloodPressures.html',
                        controller: 'BloodPressureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bloodPressure');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bloodPressureDetail', {
                parent: 'entity',
                url: '/bloodPressure/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.bloodPressure.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bloodPressure/bloodPressure-detail.html',
                        controller: 'BloodPressureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bloodPressure');
                        return $translate.refresh();
                    }]
                }
            });
    });
