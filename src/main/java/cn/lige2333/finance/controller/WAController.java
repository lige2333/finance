package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.DataBaseFIFO;
import cn.lige2333.finance.DataBase.DataBaseWA;
import cn.lige2333.finance.entity.Product;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/WA")
public class WAController extends BaseController{
    @ResponseBody
    @RequestMapping("/savePro")
    public HashMap<String, Object> savePro(Product product, HttpServletRequest request, HttpServletResponse response){
        try {
            if(product.getNum()==null||product.getSinglePrice()==null){
                throw new RuntimeException("添加失败！");
            }
            String WApro = getCookieVal(request, "WAPRO");
            DataBaseWA dataBaseWA = new DataBaseWA();
            if(WApro==null||"".equals(WApro)){
                dataBaseWA.addPro(product);
            }else {
                List<Product> products = JSON.parseArray(WApro, Product.class);
                dataBaseWA.setProducts(products);
                dataBaseWA.addPro(product);
            }
            String s = JSON.toJSONString(dataBaseWA.getProducts());
            setCookieVal(response, "WAPRO",s);
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "添加成功！");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "添加失败！");
            return map;
        }

    }
    @ResponseBody
    @RequestMapping("/queryBatch")
    public HashMap<String, Object> queryBatch(HttpServletRequest request){
        String WApro = getCookieVal(request, "WAPRO");
        DataBaseWA dataBaseWA = new DataBaseWA();
        if(WApro!=null&&!"".equals(WApro)){
            dataBaseWA.setProducts(JSON.parseArray(WApro, Product.class));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("amount", dataBaseWA.getCurrentAmount());
        map.put("price", dataBaseWA.getCurrentPrice().toString());
        map.put("products", dataBaseWA.getProducts());
        return map;

    }
    @ResponseBody
    @RequestMapping("/takeOut")
    public HashMap<String, Object> takeOut(Integer outNum,HttpServletRequest request,HttpServletResponse response){
        try {
            String WApro = getCookieVal(request, "WAPRO");
            DataBaseWA dataBaseWA = new DataBaseWA();
            if(WApro!=null&&!"".equals(WApro)){
                dataBaseWA.setProducts(JSON.parseArray(WApro, Product.class));
            }
            HashMap<String, Object> map = dataBaseWA.takeOut(outNum);
            String s = JSON.toJSONString(dataBaseWA.getProducts());
            setCookieVal(response, "WAPRO",s);
            map.put("msg", "success");
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
        setCookieVal(response, "WAPRO","");
        return "success";

    }
}
