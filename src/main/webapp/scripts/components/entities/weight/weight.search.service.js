'use strict';

angular.module('myhealthApp')
    .factory('WeightSearch', function ($resource) {
        return $resource('api/_search/weights/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
