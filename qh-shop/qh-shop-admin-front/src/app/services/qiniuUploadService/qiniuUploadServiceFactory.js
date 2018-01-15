import angular from "angular";
import conf from "../../conf";


qiniuUploadServiceFactory.$inject = ['$http', '$q', '$window', '$httpParamSerializer'];


function qiniuUploadServiceFactory($http, $q, $window, $httpParamSerializer) {
    /**
     * js 上传图片
     * @param {File} file 要计算Hash值的文件
     * @param {isReloadToken} isReloadToken 是否获取token
     *
     */
    function upload(file, isReloadToken) {
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        if (!file) {
            deferred.resolve("");
        }

        $window.qiniu.etag(file).then(function (sha1) {
            var etag = $window.qiniu.etagToUrlSafeBase64(sha1);
            //表单上传图片
            var fd = new FormData();
            fd.append('file', file);
            getToken(fd, etag).then(function (data) {
                deferred.resolve(data);
            }).then(function (resp) {
                deferred.reject(resp);
            });
        });
        return deferred.promise;
    }

    /**
     * 保存图片
     * @param etag
     */
    function saveImg(etag) {
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        $http({
            method: "POST",
            url: conf.apiPath + "/common/saveImg",
            data: $httpParamSerializer({
                etag: etag
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            }
        }).success(function (resp) {
            deferred.resolve(resp);
        }).error(function (resp) {
            deferred.reject(resp);
        });
        return deferred.promise;
    }

    /**
     * @return token
     */
    function getToken(fd, etag) {
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        //重新获取token
        $http({
            method: "POST",
            url: conf.apiPath + "/common/getToken",
            data: $httpParamSerializer({
                etag: etag
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            }
        }).success(function (resp) {
            if (resp.code === 'SUCCESS') {
                //如果存在
                if (resp.isExist) {
                    deferred.resolve(resp);
                } else {
                    fd.append('token', resp.token);
                    qiniuJs(fd, etag).then(function (data) {
                        deferred.resolve(data);
                    }).then(function (resp) {
                        deferred.reject(resp);
                    });
                }
            }
        }).error(function (resp) {
            deferred.reject(resp);
        });
        return deferred.promise;
    }

    /**
     * js七牛上传图片
     */
    function qiniuJs(fd, etag) {
        var url = "http://up.qiniu.com";
        if (window.location.href.startsWith("https")) {
            url = "https://up.qbox.me";
        }
        fd.append('token', etag);
        var deferred = $q.defer(); //创建一个等待的意思 先后顺序
        $http.post(url, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).success(function (resp) {
            saveImg(etag).then(function (resp) {
                deferred.resolve(resp);
            }).then(function (resp) {
                deferred.reject(resp);
            });
        }).error(function (resp) {
            deferred.reject(resp);
        });
        return deferred.promise;
    }

    return {
        upload: upload,
    };
}

export default qiniuUploadServiceFactory;
