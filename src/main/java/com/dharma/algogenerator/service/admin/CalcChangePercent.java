package com.dharma.algogenerator.service.admin;

import com.dharma.algogenerator.data.entity.CoreData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Component
public class CalcChangePercent extends BaseAdmin {

    LocalDate date;

    @Override
    public void run() {
        System.out.println("---------------------->CalcChangePercent");

        for (String code : allasxcodes) {
            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(code + ".AX");
            System.out.println(" ********************  DONE findTopTwoToday -----" + code + ".AX   " + coreData.size());

            CoreData coreDataToday = coreData.get(0);
            System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());

            CoreData coreDataYest = coreData.get(1);
            System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());


            Double change = coreDataToday.getClose() - coreDataYest.getClose();
            //          System.out.println("---------------------->CalcChangePercent------------" + change);
            coreDataToday.setChanges(change);
            if (change == 0.0) {
                coreDataToday.setChangepercent(0.0);
            } else {
                coreDataToday.setChangepercent(change / coreDataYest.getClose());

            }
//            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
            datarepo.save(coreDataToday);
        }


    }
}
