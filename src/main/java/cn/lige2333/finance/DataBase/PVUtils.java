package cn.lige2333.finance.DataBase;

import java.math.BigDecimal;

public class PVUtils {
    public static BigDecimal calPV(BigDecimal ratePV,Integer periodPV){
        BigDecimal add = new BigDecimal("1").add(ratePV.divide(new BigDecimal("100"), 5, BigDecimal.ROUND_HALF_UP));
        return new BigDecimal("1").divide(add.pow(periodPV),3,BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal calAnnuity(BigDecimal ratePV,Integer periodPV){
        BigDecimal annuity = new BigDecimal("0");
        for (int i=1;i<=periodPV;i++){
            annuity=annuity.add(calPV(ratePV, i));
        }
        return annuity;
    }
}
