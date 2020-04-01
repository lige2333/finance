package cn.lige2333.finance.controller;

import cn.lige2333.finance.DataBase.DataBaseFIFO;
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
@RequestMapping("/FIFO")
public class FIFOController extends BaseController{
    @ResponseBody
    @RequestMapping("/savePro")
    public HashMap<String, Object> savePro(Product product, HttpServletRequest request, HttpServletResponse response){
        try {
            if(product.getBatch()==null||product.getNum()==null||product.getSinglePrice()==null){
                throw new RuntimeException("添加失败！");
            }
            String fifopro = getCookieVal(request, "FIFOPRO");
            List<Product> products = null;
            if(fifopro==null||"".equals(fifopro)){
                products=new ArrayList<>();
                products.add(product);
            }else {
                products = JSON.parseArray(fifopro, Product.class);
                products.add(product);
            }
            String s = JSON.toJSONString(products);
            setCookieVal(response, "FIFOPRO",s);
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
        String fifopro = getCookieVal(request, "FIFOPRO");
        DataBaseFIFO dataBaseFIFO = new DataBaseFIFO();
        if(fifopro!=null&&!"".equals(fifopro)){
            dataBaseFIFO.setProducts(JSON.parseArray(fifopro, Product.class));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("amount", dataBaseFIFO.getCurrentAmount());
        map.put("price", dataBaseFIFO.getCurrentPrice().toString());
        map.put("products", dataBaseFIFO.getProducts());
        map.put("batch", dataBaseFIFO.getMaxBatch()+1);
        return map;

    }
    @ResponseBody
    @RequestMapping("/takeOut")
    public HashMap<String, Object> takeOut(Integer outNum,HttpServletRequest request,HttpServletResponse response){
        try {
            String fifopro = getCookieVal(request, "FIFOPRO");
            DataBaseFIFO dataBaseFIFO = new DataBaseFIFO();
            if(fifopro!=null&&!"".equals(fifopro)){
                dataBaseFIFO.setProducts(JSON.parseArray(fifopro, Product.class));
            }
            HashMap<String, Object> map = dataBaseFIFO.takeOut(outNum, new HashMap<String, Object>());
            String s = JSON.toJSONString(dataBaseFIFO.getProducts());
            setCookieVal(response, "FIFOPRO",s);
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
        setCookieVal(response, "FIFOPRO","");
        return "success";

    }
}
