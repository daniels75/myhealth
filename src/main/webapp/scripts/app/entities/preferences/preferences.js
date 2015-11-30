'use strict';

angular.module('myhealthApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('preferences', {
                parent: 'entity',
                url: '/preferences',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.preferences.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/preferences/preferencess.html',
                        controller: 'PreferencesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('preferences');
                        return $translate.refresh();
                    }]
                }
            })
            .state('preferencesDetail', {
                parent: 'entity',
                url: '/preferences/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'myhealthApp.preferences.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/preferences/preferences-detail.html',
                        controller: 'PreferencesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('preferences');
                        return $translate.refresh();
                    }]
                }
            });
    });
