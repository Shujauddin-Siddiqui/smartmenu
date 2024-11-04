package com.smd.smartmenu;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @RequestMapping("/home")
    public String loadPage() {
        return "This is my first landing page";

    }
}
