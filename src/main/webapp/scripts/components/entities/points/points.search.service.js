'use strict';

angular.module('myhealthApp')
    .factory('PointsSearch', function ($resource) {
        return $resource('api/_search/pointss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
