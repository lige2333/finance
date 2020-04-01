package cn.lige2333.finance.DataBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearningCurveUtil {
    public static Map<String,Object> learnings(Double units,Double time,Double percentage){
        Map<String,Object> map=new HashMap<>();
        List<BigDecimal> results = new ArrayList<>();
        Double mi = Math.log10(percentage) / Math.log10(2);
        Double total = 0.0;
        for(int i=1;i<=units;i++){
            if(i==1){
                Double unitTime = time * Math.pow(i, mi);
                total+=unitTime;
                BigDecimal unitTime2 = new BigDecimal(unitTime).setScale(2, BigDecimal.ROUND_HALF_UP);
                results.add(unitTime2);
            }else{
                Double avgTime = time * Math.pow(i, mi);
                Double unitTime = avgTime * i - time * Math.pow(i - 1, mi) * (i - 1);
                total+=unitTime;
                BigDecimal unitTime2 = new BigDecimal(unitTime).setScale(2, BigDecimal.ROUND_HALF_UP);
                results.add(unitTime2);
            }

        }
        BigDecimal totalTime = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP);
        Double average=time * Math.pow(units, mi);
        BigDecimal averageTime = new BigDecimal(average).setScale(2, BigDecimal.ROUND_HALF_UP);
        map.put("result", results);
        map.put("total", totalTime);
        map.put("average", averageTime);
        return map;
    }
}
