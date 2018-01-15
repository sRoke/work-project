import conf from "../../conf";

userServiceFactory.$inject = ['$http', '$q'];
function userServiceFactory($http, $q) {

    var _this = {
        getCurUser: getCurUser,
        isLogined: isLogined,
        getMyAuthority: getMyAuthority
    };

    /**
     * 获取当前的用户信息。
     *
     * 如果没获取过，则查询后返回。
     * 如果获取过且reload==false，则直接返回，否则重新获取并返回。
     *
     * @param reload 是否强制从新获取用户当前信息
     * @param required 是否用户必须登录
     * @return 一个代表用户信息的 promise
     */
    function getCurUser(reload, required) {
        // 用户信息为空，或者 需要重新查询
        if (!this.curUser || reload) {
            return $http.get(conf.apiPath + '/user/userInfoLogin', {
                skipGlobalErrorHandler: false,
                showLoginError: false,
                notShowError: false
            }).then(function (resp) {
                _this.curUser = resp;
                return $q.resolve(resp);
            }, function (resp) {
                _this.curUser = resp;
                if (required) {
                    return $q.reject(resp);
                } else {
                    return $q.resolve(resp);
                }
            });
        }
        if (required) {
            if (_this.isLogined()) {
                return $q.resolve(this.curUser);
            } else {
                return $q.reject(this.curUser);
            }
        }
        return $q.resolve(this.curUser);
    }

    /**
     * 检查是否已经登录
     */
    function isLogined() {
        return !!(_this.curUser && _this.curUser.data && _this.curUser.data.code === 'SUCCESS');
    }


    function getMyAuthority() {
        return $http.get(conf.apiPath + "/authChange/myAuthorities")
            .then(function (resp) {
                //_this.myAuthority = resp.data.authorities;
                var tmp = {list: [], map: {}};
                tmp.list = resp.data.authorities;
                for (let i in tmp.list) {
                    tmp.map[tmp.list[i]] = true;
                }
                return $q.resolve(tmp);
            }, function (resp) {
                //_this.myAuthority = resp.data.authorities;
                var tmp = {list: [], map: {}};
                return $q.resolve(tmp);
            });
    }

    return _this;
}


export  default  userServiceFactory;
