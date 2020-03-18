package com.ywy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "views/index";
    }

    @RequestMapping("/pathData")
    public String pathData(String excelPath,String photoPath,String burgName){

        return null;
    }
}
