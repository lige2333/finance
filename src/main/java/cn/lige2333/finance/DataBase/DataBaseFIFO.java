package cn.lige2333.finance.DataBase;

import cn.lige2333.finance.entity.Product;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

public class DataBaseFIFO {
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

    public BigDecimal getCurrentPrice(){
        BigDecimal total=new BigDecimal("0");
        for (Product product : products) {
            BigDecimal singlePrice = product.getSinglePrice().multiply(new BigDecimal(product.getNum()));
            total=total.add(singlePrice);
        }
        return total;
    }
    public HashMap<String, Object> takeOut(Integer outNum, HashMap<String, Object> map){
        if(products.size()>0){
            Product first = products.get(0);
            for (Product product : products) {
                if(product.getBatch()<first.getBatch()){
                    first=product;
                }
            }
            if(outNum<=first.getNum()){
                Product listPro= new Product();
                BeanUtils.copyProperties(first, listPro);
                listPro.setNum(outNum);
                first.setNum(first.getNum()-outNum);
                if(map.get("prod")==null){
                    ArrayList<Product> prod = new ArrayList<>();
                    prod.add(listPro);
                    map.put("prod",prod);
                }else {
                    List<Product> prod = (List<Product>) map.get("prod");
                    prod.add(listPro);
                }
                Iterator<Product> it = products.iterator();
                while (it.hasNext()) {
                    Product next = it.next();
                    if (next.getBatch() == first.getBatch()) {
                        next.setNum(first.getNum());
                    }
                    if(next.getNum()==0){
                        it.remove();
                    }
                }
                return map;
            }else{
                Iterator<Product> it = products.iterator();
                while (it.hasNext()) {
                    Product next = it.next();
                    if (next.getBatch() == first.getBatch()) {
                        if(map.get("prod")==null){
                            ArrayList<Product> prod = new ArrayList<>();
                            prod.add(next);
                            map.put("prod",prod);
                        }else {
                            List<Product> prod = (List<Product>) map.get("prod");
                            prod.add(next);
                        }
                       it.remove();
                    }
                }
                HashMap<String, Object> map1 = takeOut(outNum - first.getNum(), map);
                return map1;
            }
        }else {
            throw new RuntimeException("没库存了！");
        }
    }
}
