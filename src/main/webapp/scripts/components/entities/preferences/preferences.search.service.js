'use strict';

angular.module('myhealthApp')
    .factory('PreferencesSearch', function ($resource) {
        return $resource('api/_search/preferencess/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
