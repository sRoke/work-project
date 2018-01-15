import conf from "../../../../conf";
// import dialog from "!html-loader?minimize=true!./updateAddress.html";

var $scope,
    $http,
    authService,
    $state,
    $log,
    loginService,
    errorService,
    $filter,
    $mdDialog,
    $stateParams;
class Controller {
    constructor(_$scope,
                _$http,
                _$state,
                _$log,
                _authService,
                _loginService,
                _errorService,
                _$filter,
                _$mdDialog,
                _$stateParams) {
        $scope = _$scope;
        $http = _$http;
        $mdDialog = _$mdDialog;
        $state = _$state;
        authService = _authService;
        loginService = _loginService;
        errorService=_errorService;
        $filter=_$filter;
        $log = _$log;
        $stateParams = _$stateParams;
        /////////////////////////////////
        loginService.loginCtl(true);
        $scope.brandAppId = $stateParams.brandAppId;
        //获取权限，勿删
        // authService.setAuthorities($scope.brandAppId);
        var vm = this;
        $scope.data={};
        $scope.item={};


        $scope.activeNum = '1';
        $scope.changeTab=function(num){
            $scope.activeNum = num;
        };
        //初始化
        $scope.check={};
        $scope.getList=function(){
            $http({
                method: "GET",   ////brandApp/{brandAppId}/sysconf
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).then(function (resp) {
                console.log(44,resp.data.data);
                $scope.check=resp.data.data.partnerTypes;
                $scope.data.cash=$filter('number')(resp.data.data.withdrawalMinAmount/100,2).replace(/,/g, "");      //提现额度 withdrawalMinAmount
                $scope.data.cashCount=$filter('number')(resp.data.data.cashierDiscount/10, 1).replace(/,/g, "");   //折扣  cashierDiscount
                $scope.item.generalAgencyMinPlaceNum=resp.data.data.generalAgencyMinPlaceNum;
                $scope.item.regionaLagencyMinPlaceNum=resp.data.data.regionaLagencyMinPlaceNum;
                $scope.item.leagueMinPlaceNum=resp.data.data.leagueMinPlaceNum;
                $scope.item.generalAgencyMinPlace=resp.data.data.generalAgencyMinPlace;
                $scope.item.regionaLagencyMinPlace=resp.data.data.regionaLagencyMinPlace;
                $scope.item.leagueMinPlace=resp.data.data.leagueMinPlace;

            }, function (resp) {
            });
        };
        $scope.getList();



        //收银
        $scope.isSave=false;
        //失去焦点  输入框
        $scope.changeCount=function(cashCount){
            if(!/^\d+(\.\d{1})?$/.test(cashCount)){
                return errorService.error('折扣最多为一位小数',null);
                $scope.data.cashCount='';
                return false;
            };
            if(cashCount>10){
                return errorService.error('折扣不得超过10',null);
                $scope.data.cashCount='';
                return false;
            };
            if(cashCount<0){
                return errorService.error('折扣不得小于0',null);
                $scope.data.cashCount='';
                return false;
            };
        };

        //收银取消
        //提现
        //提现点击输入框
        $scope.changeMoney=function(cash){
            if(cash<0){
                return errorService.error('提现额度不得小于0',null);
                $scope.data.cash='';
                return false;
            };
            if(!/^\d+(\.\d{1,2})?$/.test(cash)){
                return errorService.error('提现额度最多为两位小数',null);
                $scope.data.cashCount='';
                return false;
            };
        };

        //收银 提现保存
        $scope.updateMoney=function(){
           // console.log('$scope.aa,$scope.bb,$scope.cc',$scope.check.aa,$scope.check.bb,$scope.check.cc)
            if(!/^\d+(\.\d{1})?$/.test( $scope.data.cashCount)){
                return errorService.error('折扣最多为一位小数',null);
                $scope.data.cashCount='';
                return false;
            };
            if( $scope.data.cashCount>10){
                return errorService.error('折扣不得超过10',null);
                $scope.data.cashCount='';
                return false;
            };
            if( $scope.data.cashCount<0){
                return errorService.error('折扣不得小于0',null);
                $scope.data.cashCount='';
                return false;
            };
            if(!$scope.data.cashCount<0){
                return errorService.error('折扣不得为空',null);
                $scope.data.cashCount='';
                return false;
            };
            if(!$scope.data.cash){
                return errorService.error('提现额度不得为空',null);
                $scope.data.cash='';
                return false;
            };
            if($scope.data.cash<0){
                return errorService.error('提现额度不得小于0',null);
                $scope.data.cash='';
                return false;
            };
            if(!/^\d+(\.\d{1,2})?$/.test($scope.data.cash)){
                return errorService.error('提现额度最多为两位小数',null);
                $scope.data.cashCount='';
                return false;
            };
           // alert($scope.cashCount,$scope.cash)
            $http({
                method: "PUT",                    ///brandApp/{brandAppId}/sysconf  PUT （disCount（最小折扣）minAmount（最小额度））
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params:{
                    disCount:$scope.data.cashCount*10,
                    minAmount:$scope.data.cash*100,
                    partnerTypes:{
                        GENERAL_AGENCY:$scope.check.GENERAL_AGENCY,
                        REGIONAL_AGENCY:$scope.check.REGIONAL_AGENCY,
                        LEAGUE:$scope.check.LEAGUE,
                    }
                }
            }).then(function (resp) {
                if(resp.data.status=='200'){
                    return errorService.error('保存成功',null);
                    $scope.getList();
                    $scope.isWithdraw=false;
                };
            }, function (resp) {
            });

        };
        //提现取消
        $scope.cancelMoney=function(){
            $scope.isWithdraw=false;
        };

        $scope.isDefault = false;

        var addr = null;
        $scope.addressData = {};
        //初始化地址
        $scope.getAddress = function (id) {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/addr/" + id,
                params: {},
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                }
            }).success(function (response) {
                //conf.update = false;
                $scope.addressData = response.data;
                $scope.adcNo=$scope.addressData.adcNo;
                console.log('ceshi',response)
                //初始化
                if(response.data.provinceNo==undefined){
                    //台湾
                    if(response.data.cityNo==undefined){
                       vm.provinceNo = response.data.adcNo;
                    }else{
                        //直辖市
                        vm.provinceNo = response.data.cityNo;
                        vm.getAddress(vm.provinceNo,1);
                        vm.cityNo = response.data.adcNo;
                    }
                }else{
                    //三级省市区
                    vm.provinceNo = response.data.provinceNo;
                    vm.getAddress(vm.provinceNo,1);
                    vm.cityNo = response.data.cityNo;
                    vm.getAddress(vm.cityNo,2);
                    vm.countyNo=response.data.adcNo;
                }
            });
        };
       // var address = false;

        vm.getAddress = function (type, level) {
            //限制二级地区时，三级地区码为上一次的情况，进行清空;
            vm.countyNo=undefined;
            $http({
                method: "GET",
                url: conf.apiPath + "/common/queryAdc",
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
                params: {
                    adc: type,
                },
            }).then(function successCallback(response) {
                 console.log('111111111', level,response);
                if (response.status === 200) {
                    if (level === undefined) {
                        vm.provinces = response.data.data;
                        $scope.addressData.adcNo = vm.provinceNo;
                        vm.citys=null;
                        vm.countys=null;
                    } else if (level === 1) {
                        vm.citys = response.data.data;
                        $scope.addressData.adcNo = vm.cityNo;
                        vm.countys=null;
                        //address = true;
                    } else if (level === 2) {
                        vm.countys = response.data.data;
                        $scope.addressData.adcNo = vm.countyNo;
                        //address = true;
                    }
                }
            }, function errorCallback(response) {
            });
        };
        vm.getAddress();
        $scope.getAddressId = function () {
            $http({
                method: "GET",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf/brandAppAddr ",
                params: {},
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                console.log(resp);
                if (!resp.data) {
                    return;
                } else {
                    $scope.getAddress(resp.data);
                }
            });
        };

        $scope.getAddressId();

        //失去焦点判断
        $scope.checkLinkman=function(name){
            if(!name){
                return errorService.error('姓名不得为空',null);
            }
        }
        $scope.checkStreet=function(street){
            if(!street){
                return errorService.error('街道信息不得为空',null);
            }
        }
        $scope.checkPhone=function(number){
            if (!(/^1[34578]\d{9}$/.test(number))) {
                $scope.addressData.phone=$scope.addressData.phone.substr(0,11);
                return errorService.error('请输入正确的手机号',null);
            }
        };

        

        $scope.save = function () {
            if(!$scope.addressData.receiver){
                return errorService.error('姓名不得为空',null);
               // return false;
            };
            if(!$scope.addressData.street){
                return errorService.error('街道信息不得为空',null);
               // return false;
            };
            if (!(/^1[34578]\d{9}$/.test($scope.addressData.phone))) {
                $scope.addressData.phone=$scope.addressData.phone.substr(0,11);
                return errorService.error('请输入正确的手机号',null);
                //return false;
            };
            //判断最后的地址;
            //console.log('aaaaaaaaaaaaaaaaaa', $scope.address.provinceNo,$scope.address.cityNo,$scope.address.countyNo);
            if (vm.provinceNo ===undefined) {
                addr=$scope.adcNo;
                //  console.log('啥情况')
            }else if(vm.provinceNo=="710000"){
                //台湾
                addr=vm.provinceNo;
                //console.log('直接保存taiwan')
            }else if(vm.citys.list.length==0){
                addr=vm.provinceNo;
            }else if(vm.countyNo==undefined){
                addr=vm.cityNo;
            }else{
                //三级省市县
                if (vm.countys.list.length>0) {
                    addr = vm.countyNo;
                   // console.log(1,addr);
                } else if (vm.citys.list.length > 0) {
                    addr = vm.cityNo;
                  //  console.log(2,addr);
                } else {
                    addr = vm.provinceNo;
                    //console.log(3,addr);
                }
            };
           // 当地址选择不详细，提示
           if(!addr){
               return errorService.error('请完善地址选择框',null);
           }
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf/brandAppAddr",
                // data: {
                //     adcNo: vm.countyNo,
                //     street: $scope.addressData.street,
                //     receiver: $scope.addressData.receiver,
                //     phone: $scope.addressData.phone,
                // },

                data: {
                    adcNo:addr,
                    id: $scope.editId ? $scope.editId : null,   //区分新增编辑
                    street: $scope.addressData.street,
                    receiver: $scope.addressData.receiver,
                    phone: $scope.addressData.phone,
                    memo: '',
                    defaultAddr: false,
                },

                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                },
            }).success(function (resp) {
                if(resp.status=='200'){
                    return errorService.error('保存成功',null);
                }
                console.log('?????',resp);
            });
        }

        $scope.checkRecord=function(number){
            if(!/^\d+$/.test(number)){
                return errorService.error('请输入正整数',null);
            };
        };

        
        $scope.updateRecord=function(){
            if(!/^\d+$/.test($scope.item.leagueMinPlaceNum)){
                return errorService.error('加盟商加入排名请输入正整数',null);
            };
            if(!/^\d+$/.test($scope.item.leagueMinPlace)){
                return errorService.error('加盟商销售排名请输入正整数',null);
            };
            if(!/^\d+$/.test($scope.item.regionaLagencyMinPlaceNum)){
                return errorService.error('市代理加入排名请输入正整数',null);
            };
            if(!/^\d+$/.test($scope.item.regionaLagencyMinPlace)){
                return errorService.error('市代销售排名请输入正整数',null);
            };
            if(!/^\d+$/.test($scope.item.generalAgencyMinPlaceNum)){
                return errorService.error('总代加入排名请输入正整数',null);
            };
            if(!/^\d+$/.test($scope.item.generalAgencyMinPlace)){
                return errorService.error('总代销售排名请输入正整数',null);
            };
            $http({
                method: "PUT",
                url: conf.apiPath + "/brandApp/" + $scope.brandAppId + "/sysConf/minPlace",
                params: {
                    leagueMinPlaceNum:$scope.item.leagueMinPlaceNum,                                //加盟商加入排名
                    leagueMinPlace: $scope.item.leagueMinPlace,   //加盟商销售排名
                    regionaLagencyMinPlaceNum:$scope.item.regionaLagencyMinPlaceNum,    //市代理加入排名
                    regionaLagencyMinPlace: $scope.item.regionaLagencyMinPlace,   //市代销售排名
                    generalAgencyMinPlaceNum: $scope.item.generalAgencyMinPlaceNum,   //总代加入排名
                    generalAgencyMinPlace: $scope.item.generalAgencyMinPlace                           //总代销售排名

                },
                headers: {
                    'Authorization': 'Bearer ' + loginService.getAccessToken(),
                    "brandApp-Id": $scope.brandAppId
                },
            }).success(function (resp) {
                if(resp.status=='200'){
                    return errorService.error('保存成功',null);
                }
                console.log('?????',resp);
            });
        }







    }
}

Controller.$inject = [
    '$scope',
    '$http',
    '$state',
    '$log',
    'authService',
    'loginService',
    'errorService',
    '$filter',
    '$mdDialog',
    '$stateParams'
];

export default Controller ;
