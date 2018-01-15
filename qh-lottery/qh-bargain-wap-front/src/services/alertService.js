angular.module('qh-bargain-wap-front').factory('alertService', ['$q', 'appConfig', '$mdDialog',  function ($q, appConfig, $mdDialog) {
    function confirm(ev, msg, title, leftButton, rightButton) {
        //创建一个等待的意思 先后顺序
        var deferred = $q.defer();

        $mdDialog.show({
            templateUrl: 'views/common/index.root.html',
            parent: angular.element(document.body).find('#qh-wap'),
            targetEvent: ev,
            clickOutsideToClose: true,
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
            templateUrl: 'views/common/alert/index.root.html',
            controllerAs: "vm",
            controller: ['$scope', '$mdDialog', '$rootScope', "$interval", function ($scope, $mdDialog, $rootScope, $interval) {
                var vm = this;
                vm.status = status;
                if (status === 'cancle') {
                    vm.status = 'ks-cancle';
                }
                if (status === 'success') {
                    vm.status = 'ks-success';
                }
                if (status === 'exclamation-circle') {
                    vm.status = 'ks-exclamation-circle';
                }
                vm.msg = msg;
                if (msg.indexOf('成功') >= 0) {
                    //避免遗漏的情况
                    vm.status = 'ks-success';
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
                }, 1000);
            }],
            parent: '.ks-main '

        }).then(function () {
            deferred.resolve(true);//这就是等待的结果
        }, function () {
            deferred.resolve(true);//这就是等待的结果
        });
        return deferred.promise;
    }

    function alert(msg, title, button) {
        if (!button) {
            button = "确定";
        }
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body).find('#qh-wap'))
                .clickOutsideToClose(true)
                .title(title)
                .textContent(msg)
                .ariaLabel('Alert Dialog Demo')
                .ok(button)
            //.targetEvent(ev)
        );
    }


    return {
        confirm: confirm,
        alert: alert,
        msgAlert: msgAlert,
        isUpdateApp: true,
        pages: {item: 0}
    };
}]);