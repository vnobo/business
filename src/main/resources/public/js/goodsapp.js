/**
 * Created by billb on 2015-04-07.
 */
angular.module('goods', ['ngRoute']).config(function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/goods', {
            templateUrl: 'goods/goodslist.html',
            controller: 'GoodsCtl'
        }).when('/repairgoods', {
            templateUrl: 'goods/repairgoods.html',
            controller: 'RepairGoodsCtl'
        }).when('/repgoodslog', {
            templateUrl: '/goods/repgoodslog.html',
            controller: 'RepOrderGoodsLogCtl'
        }).when('/order', {
            templateUrl: 'order/orderlist.html',
            controller: 'OrderCtl'
        }).when('/myorder', {
            templateUrl: 'order/userorderlist.html',
            controller: 'MyOrderCtl'
        }).when('/addorder', {
            templateUrl: 'order/adduserorder.html',
            controller: 'AddOrderCtl'
        }).when('/orderdetails/:id', {
            templateUrl: 'order/adduserorder.html',
            controller: 'AddOrderCtl'
        }).when('/orderinfo/:id', {
            templateUrl: 'order/orderdetails.html',
            controller: 'OrderInfoCtl'
        }).when('/orderprint/:id/:owner', {
            templateUrl: '/order/orderprint.html',
            controller: 'OrderPrintCtl'
        }).when('/orderreports', {
            templateUrl: '/order/orderreports.html',
            controller: 'OrderReportsCtl'
        }).when('/goodsreports', {
            templateUrl: '/order/goodsReports.html',
            controller: 'GoodsReportsCtl'
        }).otherwise('/login');
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}).controller('GoodsCtl', function ($http, $location, $scope) {
    var initGoods = function () {
        $http.get('/goodsrest').success(function (data) {
            $scope.goodsesdata = data;
        });
    }
    initGoods();
    $scope.pageFind = function (url) {
        $http.get(decodeURI(url, false)).success(function (data) {
            $scope.goodsesdata = data;
        });
    }

    $scope.searchGoods = function () {
        if ($scope.search) {
            var param = $scope.search.$;
            if (param.length > 0) {
                var $btn = $('#btn_search').button('loading');
                if (isFinite(param)) {
                    $http.get('goodsrest/search/goodsidct?id=' + param).success(function (data) {
                        $scope.goodsesdata = data;
                        $btn.button('reset')
                    });
                } else {
                    $http.get('goodsrest/search/namect?name=' + $scope.search.$).success(function (data) {
                        $scope.goodsesdata = data;
                        $btn.button('reset')
                    });
                }
            } else {
                initGoods();
            }
        }
    }
    $scope.addOrderGoods = function (goods) {
        $http.post('/addOrderGoods', angular.toJson(goods), {
            headers: {
                "content-type": "application/json"
            }
        }).success(function (data) {
            alert(data.content);
        });
    }
}).controller('RepairGoodsCtl', function ($http, $location, $scope) {
    var initOrderGoods = function () {
        $http.get('/ordergoodsrest').success(function (data) {
            $scope.goodses = data;
        });
        $http.get('/deptrest?size=1000').success(function (data) {
            $scope.depts = data;
            $scope.func = function (e) {
                return e.deptId < 999999;
            }
        });
    };
    initOrderGoods();
    $scope.searchOrderGoods = function () {
        if ($scope.search) {
            var param = $scope.search.$;
            if (param.length == 0) {
                initOrderGoods();
            } else {
                var $btn = $('#btn_search').button('loading');
                if (isFinite(param)) {
                    $http.get('ordergoodsrest/search/goodsidct?id=' + param).success(function (data) {
                        $scope.goodses = data;
                        $btn.button('reset');
                    });
                } else {
                    $http.get('ordergoodsrest/search/namect?name=' + $scope.search.$).success(function (data) {
                        $scope.goodses = data;
                        $btn.button('reset');
                    });
                }
            }
        }
    }

    $scope.findDeptForGoods = function () {
        var param = $scope.search.dept.deptId;
        if (param.length > 0) {
            $http.get('ordergoodsrest/search/findbydept?id=' + param).success(function (data) {
                $scope.goodses = data;
            });
        }else{
            initOrderGoods();
        }
    }

    $scope.pageFind = function (url) {
        $http.get(decodeURI(url, false)).success(function (data) {
            $scope.goodses = data;
        });
    }
    $scope.deleteOrderGoods = function (goodsid) {
        $http.post('/deleteOrderGoods', $.param({id: goodsid}), {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).success(function (data) {
            alert(data.content);
            initOrderGoods();
        });
    }

    $scope.editOrderGoods = function (todo) {
        var index = todo.gid;
        if ($('#btn_edit_' + index).data('value')) {
            if (todo.price == 0 || todo.dept.deptId == 999999) {
                alert('错误，类别和单价必须填写，更新失败');
                $('#btn_edit_' + index).find('span').removeClass("glyphicon-floppy-disk").addClass("glyphicon-floppy-remove");

            } else {
                $http.post('/editOrderGoods', angular.toJson(todo), {
                    headers: {
                        "content-type": "application/json"
                    }
                }).success(function () {
                    $('#btn_edit_' + index).data({'value': false});
                    $('#slt_' + index).attr("disabled", true);
                    $('#txt_' + index).attr("disabled", true);
                    $('#btn_edit_' + index).find('span').removeClass("glyphicon-floppy-disk glyphicon-floppy-remove").addClass("glyphicon-floppy-saved");
                });
            }


        } else {
            $('#btn_edit_' + index).data({'value': true});
            $('#slt_' + index).attr("disabled", false);
            $('#txt_' + index).attr("disabled", false);
            $('#btn_edit_' + index).find('span').removeClass("glyphicon-asterisk").addClass("glyphicon-floppy-disk");
        }

    }

}).controller('RepOrderGoodsLogCtl', function ($http, $location, $scope) {
        $scope.search = {
            start: moment().subtract(29, 'days').format('YYYY-MM-DD H:m:s'),
            end: moment().format('YYYY-MM-DD H:m:s'),
            $: ''
        };
        $('#reportrange span').html($scope.search.start + ' - ' + $scope.search.end);
        $scope.selDataCallBack = function (start, end, label) {
            $scope.search.start = start.format('YYYY-MM-DD H:m:s');
            $scope.search.end = end.format('YYYY-MM-DD H:m:s');
            $('#reportrange span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
        }

        $scope.searchLog = function () {
            $scope.logdata = [];
            if ($scope.search.$.length > 0) {
                if (isFinite($scope.search.$)) {
                    $scope.search.id = $scope.search.$;
                    $http.get('ordergoodslogrest/search/findbyides?' + $.param($scope.search)).success(function (data) {
                        $scope.logdata = data;
                    });
                } else {
                    $scope.search.name = $scope.search.$;
                    $http.get('ordergoodslogrest/search/findbynames?' + $.param($scope.search)).success(function (data) {
                        $scope.logdata = data;
                    });
                }
            } else {
                $http.get('/ordergoodslogrest/search/findbydate?' + $.param($scope.search)).success(function (data) {
                    $scope.logdata = data;
                });
            }
        }

        $scope.pageFind = function (url) {
            $http.get(decodeURI(url, false)).success(function (data) {
                $scope.logdata = data;
            });
        }
    }
).controller('OrderCtl', function ($http, $location, $scope) {
        var initOrders = function () {
            $http.get('/purchaserest/search/findByFlagGreaterThan?flag=0&sort=editdate,DESC').success(function (data) {
                $scope.purchases = data;
            });
        }
        initOrders();
        $scope.searchOrders = function () {
            if ($scope.search) {
                $scope.purchases._embedded.purchases = [];
                if ($scope.search.$.length > 0) {
                    $http.get('/purchaserest/' + $scope.search.$).success(function (data) {
                        $scope.purchases._embedded.purchases.push(data);
                    }).error(function (status) {
                        alert('没有找到该订单！请检查。' + status);
                        $scope.search.$ = '';
                    });
                } else {
                    initOrders();
                }
            }
        }
        $scope.searchFilterSheet = function (data) {
            $scope.strict = true;
            if (data) {
                $scope.search = {flag: data};
            } else {
                $scope.search = '';
                $scope.strict = false;
                initOrders();
            }
        }
        $scope.verifyOrders = function (child) {
            var message = '确认要审核吗？';
            switch (child.flag) {
                case 1:
                    message = '确认要审核吗？';
                    if (!confirm(message)) {
                        return;
                    }
                    break;
                case 2:
                    message = '是否需要发货吗？';
                    if (!confirm(message)) {
                        return;
                    } else {
                        var total = 0;
                        angular.forEach(child.items, function (item) {
                            total += item.sendqty;
                        });
                        if (total == 0) {
                            alert("操作失败，发货数量不能为0，无法发货！");
                            $location.path('orderinfo/' + child.sheet);
                            return;
                        }
                    }
                    break;
                case 3:
                    message = '是否需要收货吗？';
                    if (!confirm(message)) {
                        return;
                    } else {
                        var total = 0;
                        angular.forEach(child.items, function (item) {
                            total += item.valqty;
                        });
                        if (total == 0) {
                            alert("操作失败，收获数量不能为0，无法收获！");
                            $location.path('orderinfo/' + child.sheet);
                            return;
                        }
                        if (child.refsheetid == null) {
                            alert("操作失败，你的验收单为空，无法收货！");
                            $location.path('orderinfo/' + child.sheet);
                            return;
                        }
                    }
                    break;
                default:
                    return;
            }

            $http.get('/verifysheet?sheetid=' + child.sheet).success(function (data) {
                alert(data.content);
                initOrders();
            });
        }

        $scope.cancelOrders = function (id) {
            $http.get('/cancelorders?sheetid=' + id).success(function (data) {
                alert(data.content);
                initOrders();
            });
        }

        $scope.pageFind = function (url) {
            $http.get(decodeURI(url, false)).success(function (data) {
                $scope.purchases = data;
            });
        }

    }).controller('MyOrderCtl', function ($rootScope, $scope, $http) {
        var searchUserOrders = function () {
            $http.get('/purchaserest/search/findByUsername?&sort=editdate,DESC&username=' + $rootScope.user.name).success(function (data) {
                $scope.orders = data;
            });
        }
        searchUserOrders();

        $scope.searchBySheetIdOrders = function () {
            if ($scope.search) {
                $scope.orders._embedded.purchases = [];
                if ($scope.search.$.length > 0) {
                    $http.get('/purchaserest/' + $scope.search.$).success(function (data) {
                        $scope.orders._embedded.purchases.push(data);
                    }).error(function (status) {
                        alert('没有找到该订单！请检查。' + status);
                        $scope.search.$ = '';
                    });
                } else {
                    searchUserOrders();
                }
            }
        }
        $scope.searchFilterSheet = function (data) {
            $scope.strict = true;
            if (data) {
                $scope.search = {flag: data};
            } else {
                $scope.search = '';
                $scope.strict = false;
                searchUserOrders();
            }
        }

        $scope.commitOrderSheet = function (id) {
            if (confirm("确认要提交吗？")) {
                $http.get('/verifysheet?sheetid=' + id).success(function (data) {
                    alert('订单提交成功!');
                    searchUserOrders();
                });
            }
        }

        $scope.pageFind = function (url) {
            $http.get(decodeURI(url, false)).success(function (data) {
                $scope.orders = data;
            });
        }
    }).controller('AddOrderCtl', function ($rootScope, $scope, $http, $routeParams, $location) {
        $scope.purchase = {
            sheet: '',
            items: []
        };
        var initOrderGoodsAll = function () {
            $http.get('ordergoodsrest/search/deptandpricenot?dept=999999&price=0').success(function (data) {
                $scope.orderGoodsAll = data;
            });
            $http.get('/deptrest?size=1000').success(function (data) {
                $scope.depts = data;
                $scope.func = function (e) {
                    return e.deptId < 999999;
                }
            });

        };
        initOrderGoodsAll();

        var initOrderHead = function (sheetId) {
            $http.get('/customerRest/' + $rootScope.user.name).success(function (data) {
                if (data != null) {
                    $scope.purchase = data;
                    $scope.purchase.sheet = sheetId;
                    $scope.purchase.items = [];
                }
            });
        };
        var initSheetAll = function (sheetid) {
            $http.get('/purchaserest/' + sheetid).success(function (data) {
                $scope.purchase = data;
                if (data.flag > 0) {
                    $scope.readonly = true;
                }
            });
        };


        if (angular.isUndefined($routeParams.id)) {
            var sheetId = moment().format("YYYYMMDDHHmmsss");
            initOrderHead(sheetId);
        } else {
            var sheetId = $routeParams.id;
            initSheetAll(sheetId);
        }
        $scope.addGoods = function (data) {
            angular.forEach($scope.purchase.items, function (child, key) {
                if (child.goodsid == data.gid) {
                    $scope.purchase.items.splice(key, 1);
                }
            });
            if (data.qty > 0) {
                $scope.purchase.items.push({
                    sheetid: $scope.purchase.sheet,
                    goodsid: data.gid,
                    name: data.goods.name,
                    barcode: data.goods.barcode,
                    price: data.price,
                    qty: data.qty,
                    pknum: data.goods.unitname
                });
            }
            $scope.orderForm.$pristine = false;
        };
        $scope.search = {param: ''};
        $scope.$watch('search.param', function (value) {
            var param = value;
            if (param.length > 0) {
                if (isFinite(param)) {
                    $http.get('ordergoodsrest/search/goodsidct?id=' + param).success(function (data) {
                        $scope.orderGoodsAll = data;
                    });
                } else {
                    $http.get('ordergoodsrest/search/namect?name=' + param).success(function (data) {
                        $scope.orderGoodsAll = data;
                    });
                }
            } else {
                initOrderGoodsAll();
            }
        });

        $scope.filterGoodsByDepid = function (data) {
            if (data > 0) {
                $http.get('ordergoodsrest/search/findbydept?id=' + data).success(function (data) {
                    $scope.orderGoodsAll = data;
                });
            } else {
                initOrderGoodsAll();
            }
        };

        $scope.saveOrderGoods = function () {
            if ($scope.purchase.items.length == 0) {
                $scope.error = true;
                $('#addOrderGoodsErrorMessage').html('错误！');
            }
            $http.post('/saveUserOrder', angular.toJson($scope.purchase), {
                headers: {
                    "content-type": "application/json"
                }
            }).success(function (data) {
                alert(data.content);
                $location.path('/myorder');
            });

        }

        $scope.deleteGoods = function (id) {
            angular.forEach($scope.purchase.items, function (child, key) {
                if (child.goodsid == id) {
                    $scope.purchase.items.splice(key, 1);
                }
            });
            $scope.orderForm.$pristine = false;
        };

        $scope.getTotal = function (type) {
            var total = 0;
            angular.forEach($scope.purchase.items, function (item) {
                if (type == 'Q') {
                    total += (item.price * item.qty);
                } else if (type == 'V') {
                    total += item.price * (item.valqty - item.retqty);
                }
            });
            return total.toFixed(2);
        }
        $scope.getQtyTotal = function (type) {
            var total = 0;
            angular.forEach($scope.purchase.items, function (item) {
                if (type == 'Q') {
                    total = total + Number(item.qty);
                } else if (type == 'V') {
                    total = total + Number(item.valqty - item.retqty);
                }
            });
            return total.toFixed(2);
        }

        $scope.pageFind = function (url) {
            $http.get(decodeURI(url, false)).success(function (data) {
                $scope.orderGoodsAll = data;
            });
        }
    }).controller('OrderInfoCtl', function ($rootScope, $scope, $http, $routeParams, $location) {
        var initSheetAll = function () {
            $http.get('/purchaserest/' + $routeParams.id).success(function (data) {
                $scope.purchase = data;
                $scope.purchase.sheetid = $routeParams.id;
                if (data.flag != 2) {
                    $scope.readonly = true;
                }
            });
        };
        initSheetAll();
        $scope.saveOrderGoods = function () {
            $http.post('/saveUserOrder', angular.toJson($scope.purchase), {
                headers: {
                    "content-type": "application/json"
                }
            }).success(function (data) {
                alert(data.content);
                $location.path('/order');
            });

        }

        $scope.validateValQty = function (todo) {
            if (todo.valqty > todo.sendqty) {
                alert('退货数量不能大于验收数');
                todo.valqty = 0;
            }
        }

        $scope.validateRetQty = function (todo) {
            if (todo.retqty > todo.valqty) {
                alert('退货数量不能大于验收数');
                todo.retqty = 0;
            }
        }
        $scope.getTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += (item.price * item.qty);
                } else if (type == 'V') {
                    total += (item.price * (item.valqty - item.retqty));
                } else if (type == 'R') {
                    total += (item.price * item.retqty);
                }
            });
            return total.toFixed(2);
        }
        $scope.getQtyTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += item.qty;
                } else if (type == 'V') {
                    total += (item.valqty - item.retqty);
                } else if (type == 'R') {
                    total += item.retqty + 0.0;
                } else if (type == 'S') {
                    total += item.sendqty + 0.0;
                }
            });
            return total.toFixed(2);
        }
    }).controller('OrderPrintCtl', function ($scope, $http, $routeParams) {
        var initSheetAll = function () {
            $http.get('/purchaserest/' + $routeParams.id).success(function (data) {
                $scope.purchase = data;
                if ($routeParams.owner == 'A') {
                    $scope.readonly = 2;
                } else if ($routeParams.owner == 'U') {
                    $scope.readonly = 1;
                } else if ($routeParams.owner == 'R') {
                    $scope.readonly = 3;
                }
                $scope.getTotal = function (data) {
                    var total = 0;
                    angular.forEach($scope.purchase.items, function (child) {
                        if (data == 'Q') {
                            total += (child.price * child.qty);
                        } else if (data == 'V') {
                            total += (child.price * (child.valqty - child.retqty));
                        } else if (data == 'R') {
                            total += (child.price * child.retqty);
                        } else if (data == 'S') {
                            total += (child.price * child.sendqty);
                        }
                    });
                    return total.toFixed(2);
                }
                $scope.getTotalQty = function (data) {
                    var total = 0;
                    angular.forEach($scope.purchase.items, function (child) {
                        if (data == 'Q') {
                            total += child.qty;
                        } else if (data == 'V') {
                            total += (child.valqty - child.retqty);
                        } else if (data == 'R') {
                            total += child.retqty;
                        } else if (data == 'S') {
                            total += child.sendqty;
                        }
                    });
                    return total.toFixed(2);
                }

            });
        };
        initSheetAll();
        $scope.pagePrint = function () {
            var printContents = document.getElementById('templatePrint' + $scope.readonly).innerHTML;
            var popupWin = window.open('', '_blank', 'width=1024,height=768');
            popupWin.document.open()
            popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.5/css/bootstrap.min.css" /></head><body onload="window.print()">' + printContents + '</html>');
            popupWin.document.close();

        }

    }).controller('OrderReportsCtl', function ($rootScope, $scope, $http) {
        var initSheetAll = function () {
            $scope.search = {
                start: moment().subtract(29, 'days').format('YYYY-MM-DD H:m:s'),
                end: moment().format('YYYY-MM-DD H:m:s'),
                dept: {deptId: 0, name: '全部'},
                flag: 0,
                gParam: ''
            };
            $http.get('/deptrest?size=1000').success(function (data) {
                $scope.depts = data;
                $scope.depts._embedded.deptLists.push({deptId: 0, name: '全部'})
                $scope.func = function (e) {
                    return e.deptId < 999999;
                }

            });

            $scope.showFlag = false;
            angular.forEach($rootScope.user.authorities, function (item) {
                if (item.authority == 'ROLE_ADMINISTRATORS') {
                    $scope.showFlag = true;
                }
            });

            if (!$scope.showFlag) {
                $scope.search.uName = $rootScope.user.name;
            }
            $('#reportrange span').html($scope.search.start + ' - ' + $scope.search.end);
            $scope.selDataCallBack = function (start, end, label) {
                $scope.search.start = start.format('YYYY-MM-DD H:m:s');
                $scope.search.end = end.format('YYYY-MM-DD H:m:s');
                $('#reportrange span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
            }
        };
        initSheetAll();


        $scope.searchPur = function () {
            $scope.puritems = [];
            $scope.search.deptId = $scope.search.dept.deptId;
            var $btn = $('#btn_search').button('loading');
            $http.post('queryPur/findPur?sort=queryPur.checkdate,DESC&', angular.toJson($scope.search), {
                headers: {
                    "content-type": "application/json"
                }
            }).success(function (data) {
                $scope.puritems = data;
                $btn.button('reset');
            });
        }
        $scope.downloadExcel = function () {
            if ($scope.puritems) {
                window.open('queryPur/DownloadExcelPur?size=' + $scope.puritems.page.totalElements + '&' + $.param($scope.search));
            } else {
                alert('请先查询数据！');
            }
        }

        $scope.getTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += (item.price * item.qty);
                } else if (type == 'V') {
                    total += (item.price * (item.valqty - item.retqty));
                }
            });
            return total.toFixed(2);
        }

        $scope.getQtyTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += item.qty;
                } else if (type == 'V') {
                    total += (item.valqty - item.retqty);
                }
            });
            return total.toFixed(2);
        }
        $scope.pageFind = function (url) {
            $http.post(decodeURI(url, false), angular.toJson($scope.search)).success(function (data) {
                $scope.puritems = data;
            });
        }
    }).controller('GoodsReportsCtl', function ($rootScope, $scope, $http) {
        var initSheetAll = function () {
            $scope.search = {
                start: moment().subtract(29, 'days').format('YYYY-MM-DD H:m:s'),
                end: moment().format('YYYY-MM-DD H:m:s'),
                dept: {deptId: 0, name: '全部'},
                flag: 0,
                gParam: ''
            };
            $http.get('/deptrest?size=1000').success(function (data) {
                $scope.depts = data;
                $scope.depts._embedded.deptLists.push({deptId: 0, name: '全部'})
                $scope.func = function (e) {
                    return e.deptId < 999999;
                }

            });

            $('#reportrange span').html($scope.search.start + ' - ' + $scope.search.end);
            $scope.selDataCallBack = function (start, end, label) {
                $scope.search.start = start.format('YYYY-MM-DD H:m:s');
                $scope.search.end = end.format('YYYY-MM-DD H:m:s');
                $('#reportrange span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
            }
        };
        initSheetAll();
        $scope.searchPur = function () {
            var $btn = $('#btn_search').button('loading');
            $scope.puritems = [];
            $scope.search.deptId = $scope.search.dept.deptId;
            $http.post('queryPur/findPurGroupBy?sort=goodsId,DESC&', angular.toJson($scope.search), {
                headers: {
                    "content-type": "application/json"
                }
            }).success(function (data) {
                $scope.puritems = data;
                $btn.button('reset');
            });

        }

        $scope.downloadExcel = function () {
            if ($scope.puritems) {
                window.open('queryPur/DownloadExcelGoodsSTT?size=' + $scope.puritems.page.totalElements + '&' + $.param($scope.search));
            } else {
                alert('请先查询数据！');
            }
        }

        $scope.getTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += (item.price * item.countQty);
                } else if (type == 'V') {
                    total += item.price * (item.countValQty - item.countRetQty);
                } else if (type == 'R') {
                    total += (item.price * item.countRetQty);
                }
            });
            return total.toFixed(2);
        }

        $scope.getQtyTotal = function (data, type) {
            var total = 0;
            angular.forEach(data, function (item) {
                if (type == 'Q') {
                    total += item.countQty;
                } else if (type == 'V') {
                    total += (item.countValQty - item.countRetQty);
                }
                else if (type == 'R') {
                    total += item.countRetQty;
                }
            });
            return total.toFixed(2);
        }
        $scope.pageFind = function (url) {
            $http.post(decodeURI(url, false), angular.toJson($scope.search)).success(function (data) {
                $scope.puritems = data;
            });
        }
    }).factory('qtyTotal', function (todo, type) {
        var total = 0;
        angular.forEach(data, function (item) {
            if (type == 'Q') {
                total += item.countQty;
            } else if (type == 'V') {
                total += (item.countValQty - item.countRetQty);
            }
            else if (type == 'R') {
                total += item.countRetQty;
            }
        });
        return total.toFixed(2);
    });