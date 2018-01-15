import conf                                                     from "../conf";
import sMain                                                    from "./main";
import sMainOtherMain                                           from "./main/otherMain";
import sMainBindPhone                                           from "./main/bindPhone";
import sMainWxLogin                                             from "./main/wxLogin";
import sMainLoginTime                                           from "./main/loginTime";
import sMainOauth                                               from "./main/oauth";
import sMainOauthAuthorize                                      from "./main/oauth/authorize";
import sMainLogin                                               from "./main/login";
import sMainLoginPwd                                            from "./main/login/pwd";
import sMainLoginPhone                                          from "./main/login/phone";
import sMainLoginWapPhone                                       from "./main/login/wapPhone";
import sMainLoginWxMp                                           from "./main/login/wxMp";
import sMainLoginWxComMp                                           from "./main/login/wxComMp";


import sMainUser                                                from "./main/user";
import sMainUserBindWx                                          from "./main/user/bindWx";

import sMainReg                                                 from "./main/reg";
import sMainRegPhone                                            from "./main/reg/phone";
// 注意：以下代码，仅仅演示用，提交测试后应当注释掉。
import sMainTest                                                from "./main/test";
import sMainTestZll                                             from "./main/test/zll";
import sMainTestZllLogin                                        from "./main/test/zll/login";
import sMainTestZllLoginPwd                                     from "./main/test/zll/login/pwd";
import sMainTestZllLoginWxComMp                                 from "./main/test/zll/login/wxComMp";
import sMainTestZllLoginWxMp                                    from "./main/test/zll/login/wxMp";
import sMainTestZllLoginPhone                                   from "./main/test/zll/login/phone";
import sMainTestZllOAuth                                        from "./main/test/zll/oauth";
import sMainTestZllOAuthAuthorize                               from "./main/test/zll/oauth/authorize";

export default angular.module(`${conf.app}.states`, [
    sMain.name,
    sMainOtherMain.name,
    sMainBindPhone.name,
    sMainWxLogin.name,
    sMainLoginTime.name,
    sMainOauth.name,
    sMainOauthAuthorize.name,
    sMainLogin.name,
    sMainLoginPwd.name,
    sMainLoginWxMp.name,
    sMainLoginWxComMp.name,

    sMainLoginPhone.name,
    sMainLoginWapPhone.name,

    sMainUser.name,
    sMainUserBindWx.name,

    sMainReg.name,
    sMainRegPhone.name,

    sMainTest.name,
    sMainTestZll.name,
    sMainTestZllLogin.name,
    sMainTestZllLoginPwd.name,
    sMainTestZllLoginWxComMp.name,
    sMainTestZllLoginWxMp.name,
    sMainTestZllLoginPhone.name,
    sMainTestZllOAuth.name,
    sMainTestZllOAuthAuthorize.name


])