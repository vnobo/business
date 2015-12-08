/**
 * Created by billb on 2015-04-07.
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

var businessApp = angular.module('business', ['account', 'goods', 'ngCookies']);
businessApp.directive('ngTooltip', function () {
    return {
        link: function (scope, element, attr) {
            $(element).tooltip();
        }
    };
});
businessApp.directive('ngPopover', function () {
    return {
        link: function (scope, element, attr) {
            var options = {
                animation: true
            };
            $(element).popover(options)
        }
    };
});

businessApp.directive('ngTree', function () {
    return {
        link: function (scope, element, attr) {
            var childa = $(element).find('a');
            childa.on('click', function (event) {
                var children = $(element).find('ul');
                if (children.is(":visible")) {
                    $(childa).find('span').addClass('glyphicon-plus').removeClass('glyphicon-minus');
                    $(childa).find('i').addClass('glyphicon-triangle-top').removeClass('glyphicon-triangle-bottom');
                    children.hide('fast');
                } else {
                    $(childa).find('span').addClass('glyphicon-minus').removeClass('glyphicon-plus');
                    $(childa).find('i').addClass('glyphicon-triangle-bottom').removeClass('glyphicon-triangle-top');
                    children.show('fast');
                }
                event.stopPropagation();
            });
            var childit = $(element).find('input');
            childit.on('click', function (event) {
                var checkboxitem = $(element).find('[type=checkbox]');
                if (childit.is(':checked')) {
                    checkboxitem.prop('checked', true);
                } else {
                    checkboxitem.prop("checked", false);
                }
            });

        }
    };
});
businessApp.directive('ngDatepicker', function () {
    return {
        link: function (scope, element, attr) {
            $(element).daterangepicker({
                startDate: moment().subtract(29, 'days'),
                endDate: moment(),
                timePicker: true,
                timePickerIncrement: 30,
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '过去7天': [moment().subtract(6, 'days'), moment()],
                    '过去30天': [moment().subtract(29, 'days'), moment()],
                    '这个月': [moment().startOf('month'), moment().endOf('month')],
                    '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                opens: 'left',
                drops: 'down',
                buttonClasses: ['btn', 'btn-sm'],
                applyClass: 'btn-primary',
                cancelClass: 'btn-default',
                separator: ' to ',
                locale: {
                    format: 'YYYY-MM-DD hh:mm',
                    applyLabel: '选择',
                    cancelLabel: '取消',
                    fromLabel: '从',
                    toLabel: '到',
                    customRangeLabel: '选取日期',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '冬月', '腊月'],
                    firstDay: 1
                }
            }, scope.selDataCallBack);
        }
    }
});
businessApp.filter('linkurlid', function () {
    return function (input, index) {
        input = input || '';
        index = index || 18;

        input = input.substr(input.lastIndexOf('/') + 1);
        var out = "";
        if (input.length < index) {
            var str0 = "000000";
            input = input + str0;
            out = input.substr(0, index);
        } else {
            out = input;
        }
        return out;
    };
});

businessApp.filter('flagUP', function () {
    return function (flag, type) {
        flag = flag || '';
        var out = '';
        if (flag == 0) {
            out = '未提交';
        } else if (flag == 1) {
            out = '等待审核';
        }
        else if (flag == 2) {
            out = '等待发货';
        } else if (flag == 3) {
            out = '等待收货';
        } else if (flag == 99) {
            out = '已取消';
        }
        else if (flag == 100) {
            out = '已收货';
        }
        return out;
    };
});
businessApp.filter('goodsFlagUP', function () {
    return function (flag) {
        flag = flag || '';
        var out = '';
        if (flag == 8) {
            out = '新品';
        } else if (flag == 0) {
            out = '正常';
        }
        else if (flag == 2) {
            out = '暂停销售';
        } else if (flag == 3) {
            out = '待清退';
        }
        else if (flag == 5) {
            out = '暂停经营';
        }
        else if (flag == 6) {
            out = '清退';
        }
        else if (flag == 7) {
            out = '待启用';
        }
        return out;
    };
});

businessApp.directive('ngShowIcon', function () {
    function link(scope, element, attrs) {
        scope.$watch(attrs.ngShowIcon, function (value) {
            if (value == true) {
                $(element).addClass('glyphicon-ok-sign').removeClass('glyphicon-remove-sign');
            } else {
                $(element).addClass('glyphicon-remove-sign').removeClass('glyphicon-ok-sign');
            }
        });

    }

    return {
        link: link
    };
});

businessApp.directive('ngShowIcon', function () {
    function link(scope, element, attrs) {
        scope.$watch(attrs.ngShowIcon, function (value) {
            if (value == true) {
                $(element).addClass('glyphicon-ok-sign').removeClass('glyphicon-remove-sign');
            } else {
                $(element).addClass('glyphicon-remove-sign').removeClass('glyphicon-ok-sign');
            }
        });

    }

    return {
        link: link
    };
});

businessApp.directive('multipleNumber', [function () {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
           console.log(scope.purchase);
        }
    };
}]);


