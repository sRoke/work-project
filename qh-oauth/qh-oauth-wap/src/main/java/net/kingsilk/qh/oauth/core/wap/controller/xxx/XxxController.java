package net.kingsilk.qh.oauth.core.wap.controller.xxx;

import org.springframework.security.access.prepost.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.*;
import java.util.*;

/**
 * 示例 controller
 */
@RestController
@RequestMapping("/api/xxx")
@Deprecated
public class XxxController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public String xxx(@AuthenticationPrincipal Object curUser) {
        return "qh-oauth-wap : /xxx " + curUser + " @ " + new Date();
    }

    @RequestMapping(path = "/pub", method = RequestMethod.GET)
    @ResponseBody
    public Object pub(Principal principal, @AuthenticationPrincipal Object curUser) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<String, Serializable>(4);
        map.put("msg", "qh-oauth-wap /xxx/pub");
        map.put("date", new Date());
        map.put("principal", principal.toString());
        map.put("curUser", curUser.toString());
        return map;
    }

    @RequestMapping(path = "/userSec", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('USER')")
    public Object userSec(Principal principal, @AuthenticationPrincipal Object curUser) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<String, Serializable>(4);
        map.put("msg", "qh-oauth-wap /xxx/userSec");
        map.put("date", new Date());
        map.put("principal", principal.toString());
        map.put("curUser", curUser.toString());
        return map;
    }

}
