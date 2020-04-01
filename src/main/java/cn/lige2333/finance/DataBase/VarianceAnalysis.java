package cn.lige2333.finance.DataBase;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VarianceAnalysis {
    private BigDecimal DMSP;
    private BigDecimal DLSP;
    private BigDecimal VOSP;
    private BigDecimal FOSP;
    private BigDecimal DMSQ;
    private BigDecimal DLSQ;
    private BigDecimal VOSQ;
    private BigDecimal FOBP;
    private BigDecimal DMAP;
    private BigDecimal DLAP;
    private BigDecimal VOAP;
    private BigDecimal FOAP;
    private BigDecimal DMAQ;
    private BigDecimal DLAQ;
    private BigDecimal VOAQ;
    private BigDecimal materialPriceVariance;
    private BigDecimal materialUsageVariance;
    private BigDecimal laborRateVariance;
    private BigDecimal laborEfficiencyVariance;
    private BigDecimal VOSpendingVariance;
    private BigDecimal VOEfficiencyVariance;
    private BigDecimal salesPriceVariance;
    private BigDecimal salesVolumeVariance;
    private BigDecimal FOVolumeVariance;
    private BigDecimal FOSpendingVariance;

    public BigDecimal calMaterialPriceVariance(){
       return DMAQ.multiply(DMSP.subtract(DMAP));
    }
    public BigDecimal calMaterialUsageVariance(){
        return DMSP.multiply(DMSQ.subtract(DMAQ));
    }
    public BigDecimal calLaborRateVariance(){
        return DLAQ.multiply(DLSP.subtract(DLAP));
    }
    public BigDecimal calLaborEfficiencyVariance(){
        return DLSP.multiply(DLSQ.subtract(DLAQ));
    }
    public BigDecimal calVOSpendingVariance(){
        return VOAQ.multiply(VOSP.subtract(VOAP));
    }
    public BigDecimal calVOEfficiencyVariance(){
        return VOSP.multiply(VOSQ.subtract(VOAQ));
    }
    public BigDecimal calFOVolumeVariance(){
        return FOSP.subtract(FOBP);
    }
    public BigDecimal calFOSpendingVariance(){
        return FOBP.subtract(FOAP);
    }
}
