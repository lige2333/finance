package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.PVUtils;
import cn.lige2333.finance.DataBase.VarianceAnalysis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;

@Controller
@RequestMapping("/PV")
public class PVController extends BaseController{
    @RequestMapping("/getResult")
    @ResponseBody
    public HashMap<String, Object> getResult(BigDecimal ratePV,Integer periodPV){
        HashMap<String, Object> map = new HashMap<>();
        try {
            BigDecimal rate = PVUtils.calPV(ratePV, periodPV);
            BigDecimal Annuity = PVUtils.calAnnuity(ratePV, periodPV);
            map.put("PV", rate);
            map.put("Annuity", Annuity);
            map.put("msg","success");
            return map;
        }catch (Exception e){
            map.put("msg","数据有误！");
            return map;
        }

    }
}
