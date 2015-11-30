'use strict';

angular.module('myhealthApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('points', {
                parent: 'entity',
                url: '/points',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.points.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/points/pointss.html',
                        controller: 'PointsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('points');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pointsDetail', {
                parent: 'entity',
                url: '/points/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.points.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/points/points-detail.html',
                        controller: 'PointsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('points');
                        return $translate.refresh();
                    }]
                }
            });
    });
