package cn.lige2333.finance.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CVP {

    private BigDecimal salesPU;
    private BigDecimal varCostPU;
    private BigDecimal weight;
    private BigDecimal BEPUnits;
    private BigDecimal BEPSales;
}
