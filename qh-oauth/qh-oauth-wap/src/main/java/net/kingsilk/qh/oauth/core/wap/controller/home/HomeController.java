package net.kingsilk.qh.oauth.core.wap.controller.home;

import org.springframework.security.access.prepost.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 */
@RestController
@Deprecated
public class HomeController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("permitAll")
    public String index(@AuthenticationPrincipal Object curUser) {
        return "qh-oauth-wap :: Hello World!~ " + curUser + " @ " + new Date();
    }

    @RequestMapping(path = "/api", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("permitAll")
    public String api(@AuthenticationPrincipal Object curUser) {
        return "qh-oauth-wap : Hello World!~ " + curUser + " @ " + new Date();
    }

}
