'use strict';

angular.module('myhealthApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('weight', {
                parent: 'entity',
                url: '/weight',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.weight.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/weight/weights.html',
                        controller: 'WeightController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('weight');
                        return $translate.refresh();
                    }]
                }
            })
            .state('weightDetail', {
                parent: 'entity',
                url: '/weight/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.weight.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/weight/weight-detail.html',
                        controller: 'WeightDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('weight');
                        return $translate.refresh();
                    }]
                }
            });
    });
