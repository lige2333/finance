package cn.lige2333.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController{
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/system")
    public String system(){
        return "system";
    }
    @RequestMapping("/openWork/{work}")
    public String openWork(@PathVariable("work") String work) {
        return "mystery/" + work;
    }

}
