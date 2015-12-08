'use strict';

angular.module('myhealthApp')
    .factory('PointsSearch', function ($resource) {
        return $resource('api/_search/points/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
