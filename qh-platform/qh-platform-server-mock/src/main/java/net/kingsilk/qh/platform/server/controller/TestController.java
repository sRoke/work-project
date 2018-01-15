package net.kingsilk.qh.platform.server.controller;

import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


@RestController
public class TestController {


    @RequestMapping(path = "/", method = {RequestMethod.GET})
    public String home() {
        return "home @ controller : " + new Date();
    }

    @RequestMapping(path = "/api/a", method = {RequestMethod.GET})
    public String a() {
        return "a @ controller : " + new Date();
    }

    @RequestMapping(path = "/api/c", method = {RequestMethod.GET})
    public String c() {
        return "c @ controller : " + new Date();
    }

    @RequestMapping(path = "/s", method = {RequestMethod.GET})
    public Object s(HttpServletRequest request) {

        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();

        return servletRegistrations;
    }


    @RequestMapping(path = "/s1", method = {RequestMethod.GET})
    @PreAuthorize("permitAll")
    public Object s1() {
        return "s1 ";
    }

    @RequestMapping(path = "/s2", method = {RequestMethod.GET})
    @PreAuthorize("denyAll")
    public Object s2() {
        return "s2";
    }
}
