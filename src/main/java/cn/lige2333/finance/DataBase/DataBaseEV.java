package cn.lige2333.finance.DataBase;

import cn.lige2333.finance.entity.ExpectedValue;
import cn.lige2333.finance.entity.Product;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataBaseEV {
    private List<ExpectedValue> products = new ArrayList<>();

    public List<ExpectedValue> getProducts() {
        return products;
    }

    public void setProducts(List<ExpectedValue> products) {
        this.products = products;
    }

   public BigDecimal calEV(){
       BigDecimal ev = new BigDecimal("0");
       for (ExpectedValue product : products) {
           ev=ev.add(product.getProbability().multiply(product.getOutcome()));
       }
       return ev;
   }
}
