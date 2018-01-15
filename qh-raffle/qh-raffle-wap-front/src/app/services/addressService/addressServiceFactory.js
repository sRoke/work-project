import conf from "../../conf";
addressServiceFactory.$inject = ['$http', 'loginService', '$rootScope','$stateParams'];

function addressServiceFactory($http, loginService, $rootScope,$stateParams) {


    let getAdc = () => {
        $http({
            method: 'GET',
            url: conf.apiPath + "/common/getAdcList",
            params: {},
        }).then(function (resp) {
            $rootScope.adc = resp.data;
        })
    };

    if (!$rootScope.adc){
        getAdc();
    }



    function getProvince() {
        $http({
            method: "GET",
            url: conf.apiPath + "/authorities/getAuthorities",
            headers: {},
            data: {}
        }).then(function (data) {
            console.log("999999999999", data);
            return data;

        }, function (response) {
        });
    }

    function getCitys(provinceId) {
        $http({
            method: "GET",
            url: conf.apiPath + "/authorities/getAuthorities",
            headers: {},
            data: {
                provinceId: provinceId,
            }
        }).then(function (data) {
            console.log("88888888888888", data);
            return data;

        }, function (response) {
        });
    }


    function getArea(cityId) {
        $http({
            method: "GET",
            url: conf.apiPath + "/authorities/getAuthorities",
            headers: {},
            data: {
                cityId: cityId,
            }
        }).then(function (data) {
            console.log("9999999999999999", data);
            return data;

        }, function (response) {
        });
    }


    return {
        getProvince: getProvince,
        getCitys: getCitys,
        getArea: getArea,
        getAdc: getAdc
    };

}

export default  addressServiceFactory;


