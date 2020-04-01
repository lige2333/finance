package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.PVUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;

@Controller
@RequestMapping("/CAPM")
public class CAPMController extends BaseController{
    @RequestMapping("/getResult")
    @ResponseBody
    public HashMap<String, Object> getResult(BigDecimal beita,BigDecimal RF,BigDecimal RM){
        HashMap<String, Object> map = new HashMap<>();
        try {
            BigDecimal premium = RM.subtract(RF);
            BigDecimal pre = beita.multiply(premium);
            BigDecimal result = RF.add(pre);
            map.put("returnRate", result);
            map.put("msg","success");
            return map;
        }catch (Exception e){
            map.put("msg","数据有误！");
            return map;
        }

    }
}
