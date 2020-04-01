package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.LearningCurveUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Curve")
public class LearningCurveController {
    @RequestMapping("/getResult")
    @ResponseBody
    public HashMap<String, Object> getResult(Double units,Double time,Double percentage){
        HashMap<String, Object> map = new HashMap<>();
        try {
            Map<String, Object> learnings = LearningCurveUtil.learnings(units, time, percentage);
            map.put("result", learnings);
            map.put("msg","success");
            return map;
        }catch (Exception e){
            map.put("msg","数据有误！");
            return map;
        }

    }

}
