package net.kingsilk.qh.platform.server.controller;

import net.kingsilk.qh.platform.api.test.*;
import net.kingsilk.qh.platform.server.resource.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class Test1Controller {

    @Autowired
    TestController testController;


    @Autowired
    private TestApi testResource;

    @Autowired
    private Test1Resource test1Resource;


    @RequestMapping(path = "/t1", method = {RequestMethod.GET})
    public Object t1() {

        try {
            testResource.c2();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "\nt1 ; \n" +
                "\ntestController= " + testController +
                "\ntestResource  = " + testResource +
                "\ntest1Resource = " + test1Resource;
    }

}
