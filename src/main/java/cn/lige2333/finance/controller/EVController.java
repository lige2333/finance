package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.DataBaseEV;
import cn.lige2333.finance.DataBase.DataBaseWA;
import cn.lige2333.finance.entity.ExpectedValue;
import cn.lige2333.finance.entity.Product;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/EV")
public class EVController extends BaseController{
    @ResponseBody
    @RequestMapping("/saveOutcome")
    public HashMap<String, Object> savePro(ExpectedValue ev, HttpServletRequest request, HttpServletResponse response){
        try {
            if(ev.getOutcome()==null||ev.getProbability()==null){
                throw new RuntimeException("添加失败！");
            }
            String EV = getCookieVal(request, "EVOUTCOME");
            DataBaseEV dataBaseEV = new DataBaseEV();
            if(EV==null||"".equals(EV)){
                dataBaseEV.getProducts().add(ev);
            }else {
                List<ExpectedValue> products = JSON.parseArray(EV, ExpectedValue.class);
                products.add(ev);
                dataBaseEV.setProducts(products);
            }
            String s = JSON.toJSONString(dataBaseEV.getProducts());
            setCookieVal(response, "EVOUTCOME",s);
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("situations", dataBaseEV.getProducts());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "添加失败！");
            return map;
        }

    }

    @ResponseBody
    @RequestMapping("/calEV")
    public HashMap<String, Object> calEV(HttpServletRequest request,HttpServletResponse response){
        try {
            String EV = getCookieVal(request, "EVOUTCOME");
            DataBaseEV dataBaseEV = new DataBaseEV();
            if(EV==null||"".equals(EV)){
                throw new RuntimeException("请先录入数据！");
            }else {
                List<ExpectedValue> products = JSON.parseArray(EV, ExpectedValue.class);
                dataBaseEV.setProducts(products);
            }
            BigDecimal ev = dataBaseEV.calEV();
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "success");
            map.put("EVresult", ev);
            return map;
        }catch (Exception e){
            HashMap<String, Object> map=new HashMap<>();
            map.put("msg", e.getMessage());
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/clearList")
    public String clearList(HttpServletResponse response){
        setCookieVal(response, "EVOUTCOME","");
        return "success";

    }
}
