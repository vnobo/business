var accountApp = angular.module('account', ['ngRoute']);
accountApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: '/login.html',
            controller: 'navigation'
        }).when('/', {
        templateUrl: 'home.html',
        controller: 'home'
    }).when('/customer', {
        templateUrl: 'account/customer.html',
        controller: 'customerCtl'
    }).when('/customer/:id', {
        templateUrl: 'account/customer.html',
        controller: 'customerDetailCtl'
    }).when('/manageuser', {
        templateUrl: 'account/userlist.html',
        controller: 'manageUserCtl'
    }).when('/managerole', {
        templateUrl: 'account/rolelist.html',
        controller: 'manageRoleCtl'
    }).when('/managebult', {
        templateUrl: 'account/bult.html',
        controller: 'manageBultCtl'
    }).otherwise({
        redirectTo: '/login'
    });
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]).controller('navigation', function ($rootScope, $scope, $http, $location) {
    var loadInitMenu = function () {
        $http.get('/initmenu').success(function (data) {
            $rootScope.menus = data;
        });
    }
    var authenticate = function (credentials, callback) {
        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":"
                + credentials.password)
        } : {};
        $http.get('user', {headers: headers}).success(function (data) {
            if (data.name) {
                loadInitMenu();
                $rootScope.authenticated = true;
                $rootScope.user = data;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback($rootScope.authenticated);
        }).error(function (data, status, headers, config) {
            $rootScope.authenticated = false;
            callback && callback(false);
            $location.path("/login");
        });

    };
    authenticate();
    $scope.credentials = {};
    $scope.login = function () {
        authenticate($scope.credentials, function (authenticated) {
            if (authenticated) {
                $location.path("/");
                $scope.error = false;
                $rootScope.authenticated = true;
            } else {
                console.log("Login failed");
                $location.path("/login");
                $scope.error = true;
                $rootScope.authenticated = false;
            }
        });
    };

    $scope.logout = function () {
        $http.post('logout', {}).success(function () {
            $rootScope.authenticated = false;
            $location.path("/login");
        }).error(function (data) {
            $location.path("/login");
            $rootScope.authenticated = false;
        });
    };

}).controller('home', function ($rootScope, $scope, $http, $filter) {
    var initBulletin = function () {
        $http.get('/bulletin').success(function (data, status) {

            $scope.bulletins = data._embedded.bulletins;

        }).error(function (data, status) {
            $location.path('/login');
        });
    };
    initBulletin();

}).controller('changPwsCtl', function ($rootScope, $scope, $http, $location) {
    $scope.credentials = {};
    $scope.chang = function () {
        $http.post('/changpwd', $.param($scope.credentials), {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).success(function (data) {
            if (data.id == 'LE500') {
                alert(data.content);
                jQuery('#changModal').modal('hide');
                $location.path("/login");
                return;
            }
            if (data.id == 'LE501') {
                $scope.error = true;
                jQuery('#changpwdErrorMessage').html('ErrorCode:' + data.id + ' Message:' + data.content);
                return;
            }
            alert(data.content);
            jQuery('#changModal').modal('hide');
            $rootScope.authenticated = false;
            $location.path("/login");


        }).error(function (data) {
            $scope.error = true;
            jQuery('#changpwdErrorMessage').html('ErrorCode:0000 Message:未知的服务器错误，请稍后重试！');
        });

    }
}).controller('manageUserCtl', function ($http, $scope, $location) {
    var loadUser = function () {
        $http.get('/customerRest').success(function (data) {
            $scope.customers = data;
        });
    };
    loadUser();
    $scope.credentials = {
        ifAdmin: false
    };
    $scope.addUser = function () {
        $http.get('/addUser?' + $.param($scope.credentials)).success(function (data) {
            alert(data.content);
            jQuery('#addModal').modal('hide');
            loadUser();
        }).error(function (data) {
            $scope.error = true;
            jQuery('#addFormErrorMessage').html('ErrorCode:0000 Message:未知的服务器错误，请稍后重试！' + data.content);
        });

    };

    $scope.editUser = function (url) {
        var id = url.substr(url.lastIndexOf('/') + 1);
        $location.path('/customer/' + id);
    };
    $scope.lockedUser = function (username) {
        if (!confirm('你确认要锁定用户登陆吗？')) {
            return;
        }
        $http.post('/lockedUser?uName=' + username).success(function (data) {
            alert(data.content);
            loadUser();
        });
    };
    $scope.deleteUser = function (userName) {
        if (!confirm('你确认要删除用户吗？')) {
            return;
        }
        $http.post('/deleteUser?uName=' + userName).success(function (data) {
            alert(data.content);
            loadUser();
        });
    }
    $scope.restUserPwd = function (username) {
        if (!confirm('你确认要重置用户密码吗？')) {
            return;
        }
        $http.post('/defaultPassword?uName=' + username).success(function (data) {
            alert(data.content);
        })
    }
    $scope.pageFind = function (url) {
        $http.get(decodeURI(url, false)).success(function (data) {
            $scope.customers = data;
        });
    }

}).controller('customerDetailCtl', function ($http, $location, $scope, $routeParams) {
    var findCust = function () {
        $http.get('/customerRest/' + $routeParams.id).success(function (data) {
            $scope.customer = data;
        }).error(function (data) {
            $scope.error = true;
            jQuery('#modifyInfoErrorMessage').html('ErrorCode:' + data.status + ' Messages:未知的服务器错误，请稍后重试!' + data.message);
        });
        jQuery('#btn_ctm_return').attr('href', '#/manageuser');
        jQuery('#menubread').html('<li><a href="#/">首页</a></li>' +
            '<li><a>系统管理</a></li>' +
            '<li ><a  href="#/manageuser">用户管理</a></li>' +
            '<li class="active">修改资料</li>')
    };
    findCust();
    $scope.modify = function () {
        $http.post('updateUser', angular.toJson($scope.customer), {
            headers: {
                "content-type": "application/json"
            }
        }).success(function (data) {
            alert(data.content);
            $location.path('/manageuser');
        }).error(function (data) {
            $scope.error = true;
            jQuery('#modifyInfoErrorMessage').html('ErrorCode:' + data.status + ' Messages:未知的服务器错误，请稍后重试!' + data.message);
        });
    };
}).controller('customerCtl', function ($rootScope, $scope, $http, $location) {
    var findCust = function () {
        $http.get('/customerRest/' + $rootScope.user.name).success(function (data) {
            $scope.customer = data;
        });
    }
    findCust();
    $scope.modify = function () {
        $http.post('updateUser', angular.toJson($scope.customer), {
            headers: {
                "content-type": "application/json"
            }
        }).success(function (data) {
            alert(data.content);
            $location.path('/');
        }).error(function (data) {
            $scope.error = true;
            jQuery('#modifyInfoErrorMessage').html('ErrorCode:' + data.status + ' Messages:未知的服务器错误，请稍后重试!' + data.message);
        });
    }
}).controller('manageRoleCtl', function ($http, $scope) {
    var initRoleUser = function () {
        $http.get('/loaduserall').success(function (data) {
            $scope.users = data;
        });
        $http.get('/loadmenuall').success(function (data) {
            $scope.loadmenus = data;
        });

        $scope.checkboxModel = {
            authorities: [],
            username: '',
        };
    };
    initRoleUser();


    $scope.initRoles = function (username) {
        $http.get('loadUserByName?uName=' + username).success(function (data) {
            $scope.checkboxModel.username = username;
            $('form [type=checkbox]').prop("checked", false);
            angular.forEach(data.authorities, function (todo) {
                $('form [data-authority=' + todo.authority + ']').prop('checked', true);
            });
            $scope.checkboxModel.authorities = data.authorities;
        });
    };
    $scope.saveRoles = function () {
        var checked = new Array();
        $('[role=menurole] [type=checkbox]:checked').each(function () {
            checked.push($(this).val());
        });
        if ($scope.checkboxModel.username.length != 0 && checked.length != 0) {
            $http.post('/updateuserroles', $.param({username: $scope.checkboxModel.username, authorities: checked}), {
                headers: {
                    "content-type": "application/x-www-form-urlencoded"
                }
            }).success(function (data) {
                alert(data.content);
            }).error(function (data) {
                alert(data.content);
            });
        }
    };

}).controller('manageBultCtl', function ($rootScope, $http, $scope, $cookies) {
    var initBulletin = function () {
        $http.get('/bulletin').success(function (data, status) {
            $scope.bulletins = data._embedded.bulletins;
        }).error(function () {
            $location.path('/login');
        });
    }
    initBulletin();
    $scope.bulletinAdd = function () {
        $scope.bulletin.buldate = new Date();
        $scope.bulletin.username = $rootScope.user.name;
        $http.post('/bulletin', angular.toJson($scope.bulletin), {
            headers: {
                'content-type': 'application/json'
            }
        }).success(function () {
            alert('布告增加成功');
            $scope.bulletin.title = '';
            $scope.bulletin.context = '';
            initBulletin();
        });
    }
    $scope.bulletinDelete = function (index) {
        var cookie = document.cookie;
        cookie = cookie.substr(cookie.indexOf("=") + 1);
        $http.delete('/bulletin/' + index.substr(index.lastIndexOf('/') + 1), {
            headers: {
                'X-XSRF-TOKEN': cookie
            }
        }).success(function (data) {
            alert('删除成功成功');
            initBulletin();
        });
    }
});
