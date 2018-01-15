package net.kingsilk.qh.oauth.core.wap.controller.testZll;

import org.codehaus.groovy.runtime.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 示例 controller
 */
@RestController
@RequestMapping("/api/testZll")
@Deprecated
public class TestZllController {


    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseBody
    public String xxx(@AuthenticationPrincipal Object curUser) {
        return StringGroovyMethods.plus("qh-oauth-wap : /testZll/ \'" + appId + "\', ", new Date());
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    private String appId;

//    public static void main(String[]args) throws UnknownHostException {
//        System.out.println(java.net.InetAddress.getLocalHost());
//    }
}
