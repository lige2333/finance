package cn.lige2333.finance.DataBase;

import cn.lige2333.finance.entity.CVP;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataBaseCVP {
    private BigDecimal fixedCost;
    private List<CVP> cvps = new ArrayList<>();

    public List<CVP> getCvps() {
        return cvps;
    }

    public void setCvps(List<CVP> cvps) {
        this.cvps = cvps;
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(BigDecimal fixedCost) {
        this.fixedCost = fixedCost;
    }

    public void calBEPAmount(){
        BigDecimal mixContris = new BigDecimal("0");
        BigDecimal totalWeight = new BigDecimal("0");
        for (CVP cvp : cvps) {
            BigDecimal mixContri = cvp.getSalesPU().subtract(cvp.getVarCostPU()).multiply(cvp.getWeight());
            mixContris=mixContris.add(mixContri);
            totalWeight =totalWeight.add(cvp.getWeight());
        }
        BigDecimal waContri =mixContris.divide(totalWeight,2,BigDecimal.ROUND_HALF_UP);
        BigDecimal BEPInUnits = fixedCost.divide(waContri, 2, BigDecimal.ROUND_HALF_UP);
        for (int i=0;i<cvps.size();i++){
            CVP cvp = cvps.get(i);
            BigDecimal BEPUnits = BEPInUnits.multiply(cvp.getWeight()).divide(totalWeight, 2, BigDecimal.ROUND_HALF_UP);
            cvp.setBEPUnits(BEPUnits);
            BigDecimal BEPSales = BEPUnits.multiply(cvp.getSalesPU());
            cvp.setBEPSales(BEPSales);
        }
    }
}
