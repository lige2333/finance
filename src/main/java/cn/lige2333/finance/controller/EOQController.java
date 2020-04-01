package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.SqrtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;

@Controller
@RequestMapping("/EOQ")
public class EOQController extends BaseController{
    @RequestMapping("/getResult")
    @ResponseBody
    public HashMap<String, Object> getResult(BigDecimal demand,BigDecimal OC,BigDecimal CC){
        HashMap<String, Object> map = new HashMap<>();
        try {
            BigDecimal inner = new BigDecimal("2").multiply(demand).multiply(OC).divide(CC, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal result = SqrtUtil.sqrt(inner, 2);
            map.put("EOQ", result);
            map.put("msg","success");
            return map;
        }catch (Exception e){
            map.put("msg","数据有误！");
            return map;
        }

    }
}
