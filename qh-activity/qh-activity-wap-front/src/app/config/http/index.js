import angular from "angular";
import conf from "../../conf";
import confHttp from "./confHttp.js";
import confNormalizeHttpResp from "./confNormalizeHttpResp.js";

/**
 * 该模块用来预处理 http 请求与响应
 *
 * 参考： https://docs.angularjs.org/api/ng/service/$http#interceptors
 *
 * 该 Angular factory 将http响应进行正规化处理——非JSON数据不处理，空数据和JSON数据则统一格式为：
 *
 * {
 *      code    : "",        // 错误码，成功或失败, 示例值 : "SUCCESS", "ERROR"
 *      msg     : "",        // 可以消息提醒给终端用户的错误消息。该内容可能是 JS 端生成的。
 *      rawMsg  : "",        // 错误消息，内容可能包含错误堆栈信息，适合开发人员调试使用，不适合展示给终端用户。
 *      data    : ...,       // 业务数据，属性key可以不是 "data"，也可以是一级属性。
 *      ...     : ...        // 其他业务数据
 * }
 *
 * 可以在请求时，配置选项 skipNomorelize=false 来跳过处理。
 *
 *
 * 1. 对于200响应:
 *    1.1 如果没有响应内容，则构造一个默认的成功JSON结果 :
 *         {code:"SUCCESS"}
 *    1.2 如果有响应内容，但内容不是 JSON，则不做任何处理。
 *    1.3 如果有 JSON 响应, 且 code == "SUCCESS", 并处理 raw msg
 *    1.4 如果有 JSON 响应, 且 code != "SUCCESS", 则将 code 变更为 "ERROR"， 并处理 raw msg.
 *
 * 2. 对于非200响应：
 *    2.1 如果没有响应内容，则构造一个默认的成功JSON结果 : {code:"ERROR", msg:"系统错误，请稍后重试"}
 *    2.2 如果有响应内容，但内容不是 JSON，则不做任何处理。
 *    2.3 如果有 JSON 响应, 则处理错误码，并处理 raw msg.
 *
 * 3. raw msg 处理：
 *    3.1 {msg:"xxxStackTrace", raw:true}  => {msg:"系统错误，请稍后重试", rawMsg: "xxxStackTrace"}
 *    3.2 {msg:"yyyyyy", raw:false}  => {msg:"yyyyyy"}
 *
 * @param _$log
 * @param _$q
 * @returns {{response: handleResp, responseError: handleRespErr}}
 */
export default angular.module(`${conf.app}.config.http`, [])
    .config(confNormalizeHttpResp)
    .config(confHttp);
