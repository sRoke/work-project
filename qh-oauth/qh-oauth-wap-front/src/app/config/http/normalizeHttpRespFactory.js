import store from "store";
import conf from "../../conf";


var $log, $q, $injector;

// 返回非200相应，且响应内容为空。
let defaultEmptyRespErrMsg = {
    400: "参数请求格式不正确",
    401: "需要先登录呦~",
    403: "您未取得相应的权限！",
    404: "OH no, 页面未找到~",
    500: "哎呀，服务器出错了~",
    502: "服务器网关出错了",
    504: "服务器网关响应超时~"
};

function genDefaultErrJson(response) {
    console.log("logout4444444444");

    let defaultErrMsg = defaultEmptyRespErrMsg[response.status];
    let defautlRespJson = {
        code: 'ERROR',
        msg: defaultErrMsg ? defaultErrMsg : "系统错误，请稍后重试",
        rawMsg: null
    };
    return defautlRespJson;
}


function process200RawMsg(jsonData) {

    if (!jsonData.raw) {
        return
    }
    jsonData.rawMsg = jsonData.msg;
    jsonData.msg = null;
    delete jsonData.raw
}

function processErrRawMsg(jsonData) {

    if (!jsonData.raw) {
        return
    }
    jsonData.rawMsg = jsonData.msg;

    let defaultErrMsg = defaultEmptyRespErrMsg[response.status];

    jsonData.msg = defaultErrMsg ? defaultErrMsg : "系统错误，请稍后重试";

    delete jsonData.raw
}

function process200Json(response) {
    var respData = response.data;
    if (respData && respData.code && respData.code === 'SUCCESS') {
        return response;
    }
}


function processErrJson(response) {
    var respData = response.data;
    if (respData && respData.code && respData.code === 'SUCCESS') {
        return response;
    }
}


/**
 * 处理 200 等正常情况
 */
function handleResp(response) {

    if (response.data.status == '10301' || response.data.status == '10302' || response.data.status == '10303' || response.data.status == '10304' || response.data.status == '10305') {
        console.log('response.data', response.data.data);
        var stateService = $injector.get('$state');
        stateService.go('main.brandApp.joinUs');
    }
    // 是否跳过
    if (response.config.skipNomorelize) {
        return response;
    }

    var respData = response.data;

    // 没有数据，则构造默认值
    if (!respData) {
        response.data = {code: "SUCCESS"};
        return response
    }

    // 非JSON数据
    var contentType = response.headers('Content-Type');
    if (!contentType || contentType.indexOf('application/json') !== 0) {
        return response;
    }

    // JSON数据
    if (!respData.code) {
        respData.code = "SUCCESS";
    }

    if (respData.code === "SUCCESS") {
        process200RawMsg(respData);
        return response;
    }

    // 错误码不是 SUCCESS
    return $q.reject(response);
}

/**
 * 处理 401 404 500 等错误。
 *
 */
function handleRespErr(response) {

    if (response.status == '401') {
        //jso.wipeTokens();
        store.set(conf.brandAppId, null);
        store.set(conf.token, null);
        console.log("logout");
        // location.reload()
    } else if (response.status == '502') {
        console.log("网络连接错误");
    }
    else if (response.status == '500') {
        console.log("服务器异常")
    }
    else if (response.status == '403') {
        console.log("服务器认证失败")
    }
    // 是否跳过
    if (response.config.skipNomorelize) {
        return $q.reject(response);
    }

    var respData = response.data;

    // 没有数据，则构造默认值
    if (!respData) {
        response.data = genDefaultErrJson(response);
        return $q.reject(response);
    }

    // 有数据，但不是json，不处理
    var contentType = response.headers('Content-Type');
    if (!contentType || contentType.indexOf('application/json') !== 0) {
        return $q.reject(response);
    }

    // 有 json 数据
    if (!respData.code) {
        respData.code = "ERROR";
    }
    processErrRawMsg(respData);

    return $q.reject(response);
}

normalizeHttpRespFactory.$inject = ['$log', '$q', '$injector'];
function normalizeHttpRespFactory(_$log, _$q, _$injector) {
    $log = _$log;
    $q = _$q;
    $injector = _$injector;
    return {
        // request: function(config){...}
        // requestError: function(rejection){...}
        response: handleResp,
        responseError: handleRespErr
    };
}

export default normalizeHttpRespFactory;
