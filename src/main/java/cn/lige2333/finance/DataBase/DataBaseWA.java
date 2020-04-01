package cn.lige2333.finance.DataBase;

import cn.lige2333.finance.entity.Product;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataBaseWA {
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getCurrentAmount(){
        Integer total=0;
        for (Product product : products) {
            total+= product.getNum();
        }
        return total;
    }
    public Integer getMaxBatch(){
        Integer max = 0;
        for (Product product : products) {
            if(product.getBatch()>max){
                max=product.getBatch();
            }
        }
        return max;
    }
    public void addPro(Product product){
        if(products.size()==0){
            products.add(product);
        }else {
            Product product1 = products.get(0);
            int totalAmount = product1.getNum() + product.getNum();
            BigDecimal newProTotal = product.getSinglePrice().multiply(new BigDecimal(product.getNum()));
            BigDecimal oldProTotal = product1.getSinglePrice().multiply(new BigDecimal(product1.getNum()));
            BigDecimal newSingle=(newProTotal.add(oldProTotal)).divide(new BigDecimal(totalAmount), 2, BigDecimal.ROUND_HALF_UP);
            Product proNew = new Product();
            proNew.setNum(totalAmount);
            proNew.setSinglePrice(newSingle);
            products.clear();
            products.add(proNew);
        }
    }

    public BigDecimal getCurrentPrice(){
        BigDecimal total=new BigDecimal("0");
        for (Product product : products) {
            BigDecimal singlePrice = product.getSinglePrice().multiply(new BigDecimal(product.getNum()));
            total=total.add(singlePrice);
        }
        return total;
    }
    public HashMap<String, Object> takeOut(Integer outNum){
        if(products.size()>0){
            Product first = products.get(0);
            if(outNum>first.getNum()){
                throw new RuntimeException("没库存了！");
            }
            Product out = new Product();
            BeanUtils.copyProperties(first, out);
            out.setNum(outNum);
            first.setNum(first.getNum()-outNum);
            ArrayList<Product> prod = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            prod.add(out);
            map.put("prod",prod);
            if(first.getNum()==0){
                products.clear();
            }
            return map;
        }else {
            throw new RuntimeException("没库存了！");
        }
    }
}
