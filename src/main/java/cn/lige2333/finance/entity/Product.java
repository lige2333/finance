package cn.lige2333.finance.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer num;
    private BigDecimal singlePrice;
    private Integer batch;
}
