angular.module("app/main", [
    'ngRoute',
    'ngAnimate',
    'ngTouch',

    'app/notice/main',
    'app/home/main'
]).run(['$route', '$http', '$templateCache', '$window', function ($route, $http, $templateCache, $window) {
    // preload the template of routes
    angular.forEach($route.routes, function (r) {
        if (r.templateUrl) {
            $http.get(r.templateUrl, {cache: $templateCache});
        }
    });
}]).controller('MainCtrl', ['$scope'
    , '$rootScope'
    , '$route'
    , '$location'
    , '$window'
    , '$timeout'
    , function ($scope, $rootScope, $route, $location, $window, $timeout) {


    }]);

