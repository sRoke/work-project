import angular from "angular";
import confirmHtml from "!html-loader?minimize=true!./confirm.tpl.html";
import html from "!html-loader?minimize=true!./view.html";

alertServiceFactory.$inject = ['$q', '$interval', '$mdDialog'];
function alertServiceFactory($q, $interval, $mdDialog) {

    function confirm(ev, msg, title, leftButton, rightButton) {
        //创建一个等待的意思 先后顺序
        var deferred = $q.defer();

        $mdDialog.show({
            template: confirmHtml,
            parent: angular.element(document.body).find('#qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin-front'),
            targetEvent: ev,
            clickOutsideToClose: false,
            fullscreen: false,
            controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                var vm = this;
                vm.msg = msg;
                vm.title = title;
                vm.leftButton = leftButton ? leftButton : "取消";
                vm.rightButton = rightButton ? rightButton : "确定";
                vm.checkSubmit = function () {
                    $mdDialog.hide(true);
                };
                vm.cancel = function () {
                    $mdDialog.cancel();
                };
            }],
            controllerAs: "vm"
        })
            .then(function (answer) {
                deferred.resolve(answer);//这就是等待的结果
            }, function () {
                deferred.resolve(false);//这就是等待的结果
            });
        return deferred.promise;
    }

    function msgAlert(status, msg) {
        //创建一个等待的意思 先后顺序
        var deferred = $q.defer();
        //加入购物车成功
        var intervalStop = undefined;
        $mdDialog.show({
            template: html,
            parent: angular.element(document.body).find('#qh-shop-wap-front'),
            controllerAs: "vm",
            clickOutsideToClose: true,
            fullscreen: false,
            controller: ['$scope', '$mdDialog', '$rootScope', "$interval", function ($scope, $mdDialog, $rootScope, $interval) {
                var vm = this;
                vm.status = status;
                if (status === 'cancle') {
                    vm.status = 'ks-close';
                }
                if (status === 'success') {
                    vm.status = 'ks-check';
                }
                vm.msg = msg;
                if (msg.indexOf('成功') >= 0) {
                    //避免遗漏的情况
                    vm.status = 'ks-check';
                }
                vm.cancel = function () {
                    $mdDialog.cancel();
                };
                var i = 1;
                intervalStop = $interval(function () {
                    if (i <= 0) {
                        vm.cancel();
                        $interval.cancel(intervalStop);//解除计时器
                        intervalStop = undefined;
                    }
                    i--;
                }, 800);
            }],
            //parent: '.ks-main '

        }).then(function () {
            deferred.resolve(true);//这就是等待的结果
        }, function () {
            deferred.resolve(true);//这就是等待的结果
        });
        return deferred.promise;
    }

    return {
        msgAlert: msgAlert,
        confirm: confirm,
        isUpdateApp: true,
        pages: {item: 0}
    };
}


export default  alertServiceFactory;



