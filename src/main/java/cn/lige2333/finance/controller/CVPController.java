package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.DataBaseCVP;
import cn.lige2333.finance.DataBase.DataBaseEV;
import cn.lige2333.finance.entity.CVP;
import cn.lige2333.finance.entity.ExpectedValue;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/CVP")
public class CVPController extends BaseController{
    @ResponseBody
    @RequestMapping("/saveProduct")
    public HashMap<String, Object> savePro(CVP cvp, HttpServletRequest request, HttpServletResponse response){
        try {
            if(cvp.getSalesPU()==null||cvp.getVarCostPU()==null||cvp.getWeight()==null){
                throw new RuntimeException("添加失败！");
            }
            String CVP = getCookieVal(request, "CVPPROS");
            DataBaseCVP dataBaseCVP = new DataBaseCVP();
            if(CVP==null||"".equals(CVP)){
                dataBaseCVP.getCvps().add(cvp);
            }else {
                List<CVP> products = JSON.parseArray(CVP, CVP.class);
                products.add(cvp);
                dataBaseCVP.setCvps(products);
            }
            String s = JSON.toJSONString(dataBaseCVP.getCvps());
            setCookieVal(response, "CVPPROS",s);
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("cvps", dataBaseCVP.getCvps());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "添加失败！");
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/queryCVP")
    public HashMap<String, Object> queryBatch(HttpServletRequest request){
        String cvppros = getCookieVal(request, "CVPPROS");
        List<CVP> products = new ArrayList<>();
        if(cvppros!=null&&!"".equals(cvppros)){
            products=JSON.parseArray(cvppros, CVP.class);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("cvps", products);
        return map;

    }

    @ResponseBody
    @RequestMapping("/calCVP")
    public HashMap<String, Object> calEV(BigDecimal fixCost,HttpServletRequest request,HttpServletResponse response){
        try {
            String cvp = getCookieVal(request, "CVPPROS");
            DataBaseCVP dataBaseCVP = new DataBaseCVP();
            if(cvp==null||"".equals(cvp)){
                throw new RuntimeException("请先录入数据！");
            }else {
                List<CVP> products = JSON.parseArray(cvp, CVP.class);
                dataBaseCVP.setCvps(products);
            }
            dataBaseCVP.setFixedCost(fixCost);
            dataBaseCVP.calBEPAmount();
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("CVPresult", dataBaseCVP.getCvps());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map=new HashMap<>();
            map.put("msg", e.getMessage());
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/calMargin")
    public HashMap<String, Object> calMargin(BigDecimal bepSales,BigDecimal budSales,HttpServletRequest request,HttpServletResponse response){
        try {
            BigDecimal marginOfSafety = budSales.subtract(bepSales).divide(budSales, 2, BigDecimal.ROUND_HALF_UP);
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("marginOfSafety", marginOfSafety);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map=new HashMap<>();
            map.put("msg", e.getMessage());
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/salesAmount")
    public HashMap<String, Object> calMargin(BigDecimal targetProfit,BigDecimal FC,BigDecimal CPU,HttpServletRequest request,HttpServletResponse response){
        try {
            BigDecimal SalesAmount = FC.add(targetProfit).divide(CPU, 2, BigDecimal.ROUND_HALF_UP);
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("SalesAmount", SalesAmount);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map=new HashMap<>();
            map.put("msg", e.getMessage());
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/clearList")
    public String clearList(HttpServletResponse response){
        setCookieVal(response, "CVPPROS","");
        return "success";

    }
}
