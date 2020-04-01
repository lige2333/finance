package cn.lige2333.finance.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpectedValue {
    private BigDecimal probability;
    private BigDecimal outcome;
}
