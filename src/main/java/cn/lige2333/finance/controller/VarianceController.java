package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.VarianceAnalysis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/variance")
public class VarianceController extends BaseController{
    @RequestMapping("/getResult")
    @ResponseBody
    public HashMap<String, Object> getResult(VarianceAnalysis va){
        HashMap<String, Object> map = new HashMap<>();
        try {
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("MaterialPriceVariance",va.calMaterialPriceVariance());
            map2.put("MaterialUsageVariance",va.calMaterialUsageVariance());
            map2.put("LaborRateVariance",va.calLaborRateVariance());
            map2.put("LaborEfficiencyVariance",va.calLaborEfficiencyVariance());
            map2.put("VOSpendingVariance",va.calVOSpendingVariance());
            map2.put("VOEfficiencyVariance",va.calVOEfficiencyVariance());
            map2.put("FOVolumeVariance",va.calFOVolumeVariance());
            map2.put("FOSpendingVariance",va.calFOSpendingVariance());
            map.put("result", map2);
            map.put("msg","success");
            return map;
        }catch (Exception e){
            map.put("msg","数据有误！");
            return map;
        }

    }
}
